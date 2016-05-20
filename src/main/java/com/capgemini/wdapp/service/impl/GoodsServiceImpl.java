/**
 * CAPGEMINI APPLIANCE CHAINS.
 * Copyright (c) 2015-2015 All Rights Reserved.
 */
package com.capgemini.wdapp.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.capgemini.wdapp.common.WDConstant;
import com.capgemini.wdapp.dao.ICategoryDao;
import com.capgemini.wdapp.dao.IGoodsDao;
import com.capgemini.wdapp.model.Category;
import com.capgemini.wdapp.model.Goods;
import com.capgemini.wdapp.model.GoodsCategory;
import com.capgemini.wdapp.model.PageResults;
import com.capgemini.wdapp.model.Pagination;
import com.capgemini.wdapp.model.StockPrice;
import com.capgemini.wdapp.service.IGoodsService;
import com.capgemini.wdapp.util.ImageUtil;
import com.capgemini.wdapp.util.JsonUtil;
import com.google.gson.reflect.TypeToken;

/**
 * 
 * functional description： seller interface
 * 
 * @author zhihuang@capgemini.com
 * @created Dec 18, 2015 10:37:13 AM
 * @date Dec 18, 2015 10:37:13 AM
 */

@Service(value = "goodsService")
public class GoodsServiceImpl implements IGoodsService {

	private final static Log logger = LogFactory.getLog(GoodsServiceImpl.class);

	@Autowired
	private IGoodsDao goodsDao;

	@Autowired
	private ICategoryDao cateDao;

	public int updateGoods(Goods goods) {
		return goodsDao.updateGoods(goods);
	}

	public int addGoods(Goods goods, CommonsMultipartFile[] files) {
		int i = 0;
		goodsDao.addGoods(goods);
		
		if(isUploadFile(files)){
			String images = uploadGoodsImage(files, goods.getId());

			if (StringUtils.isNotEmpty(images)) {
				String[] arr = images.split("@");
				String imageStr = arr[0];
				String thumbStr = arr[1];

				String[] imageArr = imageStr.split("\\|");
				String[] thumbArr = thumbStr.split("\\|");
				List<ImageJson> list = new ArrayList<ImageJson>();
				for (int j = 0; j < imageArr.length; j++) {
					ImageJson ij = new ImageJson();
					ij.setImage(imageArr[j]);
					ij.setThumbImage(thumbArr[j]);
					list.add(ij);
				}

				goods.setImages(JsonUtil.toJson(list));

			}
		}

		goodsDao.updateGoods(goods);
		addGoodsCategory(goods);
		// addGoodsWithCategory(goods);
		addStockPrice(goods);
		return i;

	}

	private String uploadGoodsImage(CommonsMultipartFile[] files,
			Integer goodsid) {
		if (files == null || files.length == 0) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		StringBuffer thumbSb = new StringBuffer();
		int j = 0;
		for (CommonsMultipartFile image : files) {
			j++;
			if (image.getSize() == 0) {
				continue;
			}
			int i = image.getOriginalFilename().indexOf(".");
			String imgType = image.getOriginalFilename().substring(i);
			String filename = "gid_" + goodsid + System.currentTimeMillis()
					+ "_" + j + imgType;
			String relativePath = WDConstant.GOODS_IMAGE + filename;

			// upload image
			ImageUtil.uploadStaticResource(image, relativePath);

			String thumbPath = WDConstant.GOODS_IMAGE + "thumb_" + filename;

			if (sb.length() != 0) {
				sb.append("|");
				sb.append(relativePath);
			} else {
				sb.append(relativePath);
			}

			if (thumbSb.length() != 0) {
				thumbSb.append("|");
				thumbSb.append(thumbPath);
			} else {
				thumbSb.append(thumbPath);
			}

		}
		return sb.toString() + "@" + thumbSb.toString();

	}

	public StockPrice getStockPriceById(Integer id) {
		StockPrice sp = new StockPrice();
		sp.setId(id);
		List<StockPrice> list = goodsDao.getStockPrice(sp);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	private void addStockPrice(Goods goods) {
		List<StockPrice> stockPriceList = goods.getStockPriceList();
		if (stockPriceList == null || stockPriceList.size() == 0) {
			return;
		}

		int totalQuantity = 0;
		float price = 0;
		int i = 0;
		for (StockPrice sp : stockPriceList) {
			if (i++ == 0) {
				price = sp.getPrice();
			}
			totalQuantity += sp.getQuantity();
			if (price > sp.getPrice()) {
				price = sp.getPrice();// 取最低的价格
			}
			sp.getQuantity();
			sp.setGoodsId(goods.getId());
		}
		goods.setQuantity(totalQuantity);
		goods.setPrice(price);
		goodsDao.updateGoods(goods);
		goodsDao.addStockPrice(stockPriceList);
	}

	public int updateStockPrice(StockPrice sp) {
		return goodsDao.updateStockPrice(sp);
	}

	private void updateStockPrice(Goods goods) {
		List<StockPrice> stockPriceList = goods.getStockPriceList();
		List<StockPrice> list = new ArrayList<StockPrice>();
		for (StockPrice sp : stockPriceList) {
			if (StringUtils.isNotEmpty(sp.getId() + "")) {// not null to update
				goodsDao.updateStockPrice(sp);
			} else {
				sp.setGoodsId(goods.getId());
				list.add(sp);
			}

		}
		goodsDao.addStockPrice(list);
	}

	private void addGoodsWithCategory(Goods goods) {
		List<Category> categoryList = goods.getCategoryList();
		List<GoodsCategory> list = new ArrayList<GoodsCategory>();
		if (categoryList == null || categoryList.size() == 0) {
			return;
		}
		for (Category cat : categoryList) {
			if (cat.getId() == null) {// add
				cat.setShopId(goods.getShopId());
				cateDao.addCategory(cat);
			} else {
				cateDao.updateCategory(cat);
			}

			GoodsCategory gc = new GoodsCategory();
			gc.setCategoryId(cat.getId());
			gc.setGoodsId(goods.getId());
			list.add(gc);
		}
		goodsDao.addGoodsCategory(list);
	}

	private void addGoodsCategory(Goods goods) {
		List<Category> categoryList = goods.getCategoryList();
		List<GoodsCategory> list = new ArrayList<GoodsCategory>();
		if (categoryList == null || categoryList.size() == 0) {
			return;
		}
		for (Category cat : categoryList) {
			GoodsCategory gc = new GoodsCategory();
			gc.setCategoryId(cat.getId());
			gc.setGoodsId(goods.getId());
			list.add(gc);
		}
		goodsDao.addGoodsCategory(list);
	}
	
	private boolean isUploadFile(CommonsMultipartFile[] files){
		if (files != null && files.length != 0) {
			for(CommonsMultipartFile cf : files){
				if(cf.getSize()>0){
					return true;
				}
			}
		}
		
		return false;
	}

	///////////////////////
	public int updateGoods(Goods origi, Goods newGood, CommonsMultipartFile[] files) {

		String updateImageStr = "";

		String deleteImage = newGood.getDeleteImages();

		if (StringUtils.isNotEmpty(deleteImage)) {// have delete image not empty
			String origiStr = origi.getImages();

			if (!StringUtils.isEmpty(origiStr)
					&& !StringUtils.isEmpty(deleteImage)) {

				java.lang.reflect.Type type = new TypeToken<List<ImageJson>>() {
				}.getType();

				List<ImageJson> origiList = JsonUtil.fromJson(origiStr, type);
				List<ImageJson> deleteList = JsonUtil.fromJson(deleteImage,
						type);
				if (origiList != null && origiList.size() > 0) {
					for (ImageJson ij : deleteList) {
						if (origiList.contains(ij)) {
							origiList.remove(ij);
						}
					}
				}

				updateImageStr = JsonUtil.toJson(origiList);

			}
		}else{
			updateImageStr = origi.getImages();
		}

		if (isUploadFile(files)) {
			String images = uploadGoodsImage(files, newGood.getId());

			if (StringUtils.isNotEmpty(images)) {
				String[] arr = images.split("@");
				String imageStr = arr[0];
				String thumbStr = arr[1];

				String[] imageArr = imageStr.split("\\|");
				String[] thumbArr = thumbStr.split("\\|");
				List<ImageJson> list = new ArrayList<ImageJson>();
				for (int j = 0; j < imageArr.length; j++) {
					ImageJson ij = new ImageJson();
					ij.setImage(imageArr[j]);
					ij.setThumbImage(thumbArr[j]);
					list.add(ij);
				}
				
				if(StringUtils.isNotEmpty(updateImageStr)){
					java.lang.reflect.Type type = new TypeToken<List<ImageJson>>() {}.getType();
					List<ImageJson> updateList = JsonUtil.fromJson(updateImageStr, type);
					for(ImageJson im : updateList){
						list.add(im);
					}
				}
				

				newGood.setImages(JsonUtil.toJson(list));

			}
		}else{
			newGood.setImages(updateImageStr);
		}

		

		 goodsDao.updateGoods(newGood);
		 goodsDao.deleteGoodsCategory(newGood.getId());
		 goodsDao.deleteStockPrice(newGood.getId());
		 addGoodsCategory(newGood);
		 addStockPrice(newGood);

		return 0;
	}

	public int deleteGoods(Integer id) {
		goodsDao.deleteGoodsCategory(id);
		goodsDao.deleteGoods(id);
		return 0;
	}

	public List<Goods> getGoods(Goods goods) {
		List<Goods> list = goodsDao.getGoods(goods);
		if (list == null || list.size() == 0) {
			return list;
		}

		// List<Category> retList = new ArrayList<Category>();
		// for(Goods gd: list){
		// List<Category> catList = gd.getCategoryList();
		// for(Category cate : catList){
		// Category tmpCate = cateDao.getCategoryById(cate.getId());
		// retList.add(tmpCate);
		// }
		// gd.setCategoryList(retList);
		// }

		return list;
	}

	public Goods getGoodsById(Integer id) {
//		Goods goods = new Goods();
//		goods.setId(id);
//		List<Goods> list = getGoods(goods);
		
		Map map = new HashMap();
		map.put("id", id);
		List<Goods> list = goodsDao.getGoodsListByPage(map);
		
		if (list == null || list.size() == 0) {
			return null;
		}
		Goods ret = list.get(0);

//		StockPrice sp = new StockPrice();
//		sp.setGoodsId(id);
//		List<StockPrice> stockPriceList = goodsDao.getStockPrice(sp);
//		ret.setStockPriceList(stockPriceList);

		return ret;
	}

	public PageResults<Goods> getGoodsListByPage(
			Map<String, Object> conditions, Pagination page) {

		if (null == page || !page.isValidPage()) {
			return null;
		}

		// get counts
		List<Goods> list = goodsDao.getGoodsListByPage(conditions);
		if (list == null || list.size() == 0) {
			return null;
		}

		int totalCount = list.size();
		page.setTotalCount(totalCount);

		conditions.put("showpage", 1);
		conditions.put(Pagination.STARTINDEX, page.getStartIndex());
		conditions.put(Pagination.PAGESIZE, page.getPageSize());

		List<Goods> list2 = goodsDao.getGoodsListByPage(conditions);

		PageResults<Goods> result = new PageResults<Goods>(page, list2);

		return result;

	}

	class ImageJson {
		String image;
		String thumbImage;

		/**
		 * @return the image
		 */
		public String getImage() {
			return image;
		}

		/**
		 * @param image
		 *            the image to set
		 */
		public void setImage(String image) {
			this.image = image;
		}

		/**
		 * @return the thumbImage
		 */
		public String getThumbImage() {
			return thumbImage;
		}

		/**
		 * @param thumbImage
		 *            the thumbImage to set
		 */
		public void setThumbImage(String thumbImage) {
			this.thumbImage = thumbImage;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			// TODO Auto-generated method stub
			return image.hashCode() + thumbImage.hashCode();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			// TODO Auto-generated method stub

			ImageJson ij = (ImageJson) obj;
			return image.equals(ij.getImage())
					&& thumbImage.equals(ij.getThumbImage());
		}

	}

}
