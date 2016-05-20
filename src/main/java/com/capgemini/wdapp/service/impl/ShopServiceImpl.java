/**
 * CAPGEMINI APPLIANCE CHAINS.
 * Copyright (c) 2015-2015 All Rights Reserved.
 */
package com.capgemini.wdapp.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.capgemini.wdapp.api.exception.DataAccessDeniedException;
import com.capgemini.wdapp.common.WDConstant;
import com.capgemini.wdapp.dao.IShopDao;
import com.capgemini.wdapp.model.Shop;
import com.capgemini.wdapp.response.ResponseUtil;
import com.capgemini.wdapp.service.IShopService;
import com.capgemini.wdapp.util.ConfigUtil;
import com.capgemini.wdapp.util.ImageUtil;
import com.capgemini.wdapp.vendor.wechat.Button;
import com.capgemini.wdapp.vendor.wechat.ButtonCondition;
import com.capgemini.wdapp.vendor.wechat.Buttons;
import com.capgemini.wdapp.vendor.wechat.Industry;
import com.capgemini.wdapp.vendor.wechat.WeChatApiUtil;
import com.capgemini.wdapp.vendor.wechat.WechatConstants;

/**
 * 
 * functional description： seller interface
 * @author  zhihuang@capgemini.com
 * @created Dec 18, 2015 10:37:13 AM
 * @date Dec 18, 2015 10:37:13 AM
 */

@Service(value = "shopService")
public class ShopServiceImpl implements IShopService {

	@Autowired
	private IShopDao shopDao;
	

	private final static Log logger = LogFactory.getLog(ShopServiceImpl.class);
	
	
	public List<Shop> getShop(Shop shop){
		return shopDao.getShop(shop);
	}
	
	public Shop getShopBySellerId(Integer sellerId){
		Shop shop = new Shop();
		shop.setSellerId(sellerId);
		List<Shop> list = shopDao.getShop(shop);
		if(list != null && list.size()>0){
			return list.get(0);
		}
		
		return null;
		
	}
	
	public Shop getShopById(Integer shopId){
		Shop shop = new Shop();
		shop.setId(shopId);
		List<Shop> list = shopDao.getShop(shop);
		if(list != null && list.size()>0){
			return list.get(0);
		}
		
		return null;
		
	}
	
	
	public int addShop(Shop shop, CommonsMultipartFile logoImg, CommonsMultipartFile bcgImg){
		int ret = 0;
		
		if(logoImg != null){
			String logoUrl = uploadShopLogoImage(logoImg, shop.getSellerId());
			shop.setLogoImg(logoUrl);
		}
		
		if(bcgImg != null){
			String bcgUrl = uploadBackgroundImage(bcgImg, shop.getSellerId());
			shop.setBackgImg(bcgUrl);
		}
		
		
		
		shopDao.addShop(shop);
		
		return ret;
	}
	
	public int updateShop(Shop shop, CommonsMultipartFile logoImg, CommonsMultipartFile bcgImg){
		int ret = 0;
		
		if(logoImg != null){
			String logoUrl = uploadShopLogoImage(logoImg, shop.getSellerId());
			shop.setLogoImg(logoUrl);
		}
		
		if(bcgImg != null){
			String bcgUrl = uploadBackgroundImage(bcgImg, shop.getSellerId());
			shop.setBackgImg(bcgUrl);
		}
		
		shopDao.updateShop(shop);
		
		return ret;
	}
	
	
	
	private String uploadShopLogoImage(CommonsMultipartFile logoImg, Integer sellerId) {
		
		int i =  logoImg.getOriginalFilename().indexOf(".");
		if(i ==-1){
			return null;
		}
		String imgType = logoImg.getOriginalFilename().substring(i);
		String filename = "logo_" +sellerId+ System.currentTimeMillis() + imgType;
		
		String relativePath = WDConstant.SHOP_LOGO_IMAGE + filename;
		
		// upload image
		ImageUtil.uploadStaticResource(logoImg, relativePath);
		 
		 return relativePath;
		
	}
	
	
	private String uploadBackgroundImage(CommonsMultipartFile bcgImg, Integer sellerId) {
		
		int i =  bcgImg.getOriginalFilename().indexOf(".");
		if(i ==-1){
			return null;
		}
		String imgType = bcgImg.getOriginalFilename().substring(i);
		String filename = "bcg_" +sellerId + + System.currentTimeMillis() +imgType;
		String staticResPath = ConfigUtil.getWDStaticResPath();
		String relativePath = WDConstant.SHOP_BACKGROUND_IMAGE + filename;
//		String width = ConfigUtil.getPropertyValue("upload.image.thumb.width");
//		String height = ConfigUtil.getPropertyValue("upload.image.thumb.height");
		String filePath = staticResPath + relativePath;
		

		 try{
			 
			 // upload image
			 ImageUtil.uploadStaticResource(bcgImg, relativePath);
			 
			 // generate thumb image
//			 new ImageUtil().thumbnailImage(filePath, Integer.parseInt(width), Integer.parseInt(height));
//			 
			 String thumbPath = WDConstant.SHOP_BACKGROUND_IMAGE + "thumb_"+filename;
			 
			 return relativePath;
			 
		 }catch(Exception e){
			 logger.error("uploadBackgroundImage CommonsMultipartFile transfer error: " + e);
			 throw new DataAccessDeniedException("uploadBackgroundImage CommonsMultipartFile transfer error");
		 }
		 
	}

	@Override
	public int updateShopWechatKey(Shop entity) {
		Shop shop = this.getShopBySellerId(entity.getSellerId());
		if (shop == null) {
			return -1;
		}
		String accessToken = WeChatApiUtil.getAccesstoken(entity.getWeChatAppKey(),entity.getWeChatAppSecret());
    	Industry industry = new Industry();
    	industry.setIndustry_id1(WechatConstants.TEMPLATE_MESSAGE_INDUSTRY_1); 
    	industry.setIndustry_id2(WechatConstants.TEMPLATE_MESSAGE_INDUSTRY_14);
    	//由于用到了凯捷公众号，先去掉这个功能
    	//WeChatApiUtil.setIndustry(accessToken,industry);
    	Buttons buttons= new Buttons();
    	List<Button> buttonsList = new ArrayList<Button>();
    	Button button= new Button();
    	button.setType(Button.TYPE_VIEW);
    	button.setName("进入店铺");
    	button.setKey("V1001");
    	button.setUrl(ConfigUtil.getPropertyValue("server.root") +"api/wechat/index?action=" + WechatConstants.ACTION_HOMEPAGE + "&shopid=" + shop.getId());
    	Button button1= new Button();
    	button1.setType(Button.TYPE_VIEW);
    	button1.setName("我的订单");
    	button1.setUrl(ConfigUtil.getPropertyValue("server.root") +"api/wechat/index?action=" + WechatConstants.ACTION_ORDER + "&shopid=" + shop.getId());
    	buttonsList.add(button);
    	buttonsList.add(button1);
    	buttons.setButton(buttonsList);
    	String response = WeChatApiUtil.setButtons(accessToken,buttons).toString();
    	//ButtonCondition condition = new ButtonCondition();
    	//condition.setGroupId("100"); //mobility
    	//buttons.setMatchrule(condition);
    	//String response = WeChatApiUtil.setConditionButtons(accessToken,buttons);
    	if (WeChatApiUtil.isApiSuccess(response)) {
    		shopDao.updateShop(entity);
    		return 0;
    	} else {
    		return -1;
    	}
		
	}


	@Override
	public int unbindWechatKey(Shop entity) {
		shopDao.updateShop(entity);
		return 0;
	}

	@Override
	public Shop getShopByWeChatAppKey(String weChatAppKey) {
		List<Shop> shops = shopDao.getShopByWeChatAppKey(weChatAppKey);
		if(shops != null && shops.size()>0){
			return shops.get(0);
		}
		
		return null;
	}
}
