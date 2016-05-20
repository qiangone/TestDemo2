/**
 * CAPGEMINI APPLIANCE CHAINS.
 * Copyright (c) 2015-2015 All Rights Reserved.
 */
package com.capgemini.wdapp.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.capgemini.wdapp.common.JsonParamObj;
import com.capgemini.wdapp.model.Category;
import com.capgemini.wdapp.model.Goods;
import com.capgemini.wdapp.model.PageResults;
import com.capgemini.wdapp.model.Pagination;
import com.capgemini.wdapp.response.ResponseUtil;
import com.capgemini.wdapp.response.WDResponse;
import com.capgemini.wdapp.service.IGoodsService;

/**
 * 
 * functional description： Seller Controller
 * 
 * @author zhihuang@capgemini.com
 * @created Dec 18, 2015 10:40:17 AM
 */
@Controller
@RequestMapping("/api/goods")
public class GoodsController {

	private final Log logger = LogFactory.getLog(GoodsController.class);

	@Autowired
	private IGoodsService goodsService;
	
	private static final String ADD_SUCCESS_MSG = "add goods successfully";
	private static final String ADD_EXCEPTION_MSG = "add goods exception";
	private static final String UPDATE_SUCCESS_MSG = "update goods successfully";
	private static final String UPDATE_EXCEPTION_MSG = "update goods exception";
	private static final String DELETE_SUCCESS_MSG = "delete goods successfully";
	private static final String DELETE_EXCEPTION_MSG = "delete goods exception";
	private static final String GETSINGLE_SUCCESS_MSG = "get goods info successfully";
	private static final String GETSINGLE_EXCEPTION_MSG = "get goods info exception";
	private static final String GETGOODSLIST_SUCCESS_MSG = "getGoodsList successfully";
	private static final String GETGOODSLIST_EXCEPTION_MSG = "getGoodsList exception";
	private static final String PUTGOODSONOFF_SUCCESS_MSG = "putGoodsOnOff successfully";
	private static final String PUTGOODSONOFF_EXCEPTION_MSG = "putGoodsOnOff exception";
	
	
	@RequestMapping(value = "/addGoods", method = RequestMethod.POST)
	public @ResponseBody WDResponse addGoods(@ModelAttribute Goods goods, @RequestParam(value="files" , required=false) CommonsMultipartFile[] files) {
		if (goods == null) {
			return ResponseUtil.apiError("1001", "Missing parameter!");
		}

		if (StringUtils.isEmpty(goods.getShopId())) {
			return ResponseUtil.apiError("1002", "shop id is null!");
		}
		
		if (StringUtils.isEmpty(goods.getName())) {
			return ResponseUtil.apiError("1003", "goods name is null!");
		}
		
		if (files == null || files.length ==0) {
			//return ResponseUtil.apiError("1004", "goods image is null!");
		}
		
		try {
			goods.setCreateTime(new Date());
			goodsService.addGoods(goods, files);
			logger.info(ADD_SUCCESS_MSG);
			return ResponseUtil.apiSuccess(goods, ADD_SUCCESS_MSG);
		} catch (Exception e) {
			logger.error(ADD_EXCEPTION_MSG , e);
			return ResponseUtil.apiError("1100", "未知错误");
		}
	

	}
	
	
//	@RequestMapping(value = "/addStockPrice", method = RequestMethod.POST)
//	public @ResponseBody WDResponse addStockPrice(@RequestBody StockPrice sp) {
//		if (sp == null) {
//			return ResponseUtil.apiError("1001", "Missing parameter!");
//		}
//
//		if (StringUtils.isEmpty(goods.getShopId())) {
//			return ResponseUtil.apiError("1002", "shop id is null!");
//		}
//		
//		if (StringUtils.isEmpty(goods.getName())) {
//			return ResponseUtil.apiError("1003", "goods name is null!");
//		}
//		
//		if (files == null || files.length ==0) {
//			return ResponseUtil.apiError("1004", "goods image is null!");
//		}
//		
//		try {
//			goodsService.addGoods(goods, files);
//			logger.info(ADD_SUCCESS_MSG);
//			return ResponseUtil.apiSuccess(goods, ADD_SUCCESS_MSG);
//		} catch (Exception e) {
//			logger.error(ADD_EXCEPTION_MSG , e);
//			return ResponseUtil.apiError("1100", "未知错误");
//		}
//	
//
//	}
	
	
	
	@RequestMapping(value = "/updateGoods", method = RequestMethod.POST)
	public @ResponseBody WDResponse updateGoods(@ModelAttribute Goods goods, @RequestParam(value="files" , required=false) CommonsMultipartFile[] files) {
		if (goods == null) {
			return ResponseUtil.apiError("1001", "Missing parameter!");
		}

		if (StringUtils.isEmpty(goods.getId())) {
			return ResponseUtil.apiError("1002", "goods id is null!");
		}
		
		Goods g = goodsService.getGoodsById(goods.getId());
		if(g == null){
			return ResponseUtil.apiError("1003", "goods not exist");
		}
		
		//
//		String deleteImage = goods.getDeleteImages();
//		String oldImage = g.getImages();
//		if(!StringUtils.isEmpty(oldImage)){
//			String[] oldImageArr = oldImage.split("@");
//			String images = oldImageArr[0];
//			String thumbImages =  oldImageArr[1];
//		}
		
		try {
			
			goodsService.updateGoods(g, goods, files);
			logger.info(UPDATE_SUCCESS_MSG);
			return ResponseUtil.apiSuccess(goods, UPDATE_SUCCESS_MSG);
		} catch (Exception e) {
			logger.error(UPDATE_EXCEPTION_MSG , e);
			return ResponseUtil.apiError("1100", "未知错误");
		}

	}
	
	@RequestMapping(value = "/putGoodsOnOff", method = RequestMethod.POST)
	public @ResponseBody WDResponse putGoodsOnOff(@RequestBody Goods goods) {
		if (goods == null) {
			return ResponseUtil.apiError("1001", "Missing parameter!");
		}

		if (StringUtils.isEmpty(goods.getId())) {
			return ResponseUtil.apiError("1002", "goods id is null!");
		}
		if (StringUtils.isEmpty(goods.getIsOnSale())) {
			return ResponseUtil.apiError("1003", "isOnSale is null!");
		}
		
		
		try {
//			goodsService.updateGoods(goods, null);
			logger.info(PUTGOODSONOFF_SUCCESS_MSG);
			return ResponseUtil.apiSuccess(goods, PUTGOODSONOFF_SUCCESS_MSG);
		} catch (Exception e) {
			logger.error(PUTGOODSONOFF_EXCEPTION_MSG , e);
			return ResponseUtil.apiError("1100", "未知错误");
		}
	

	}
	
	
	@RequestMapping(value = "/deleteGoods", method = RequestMethod.GET)
	public @ResponseBody WDResponse deleteGoods(@RequestParam Integer id) {
		if (id == null) {
			return ResponseUtil.apiError("1001", "Missing parameter!");
		}

		if (StringUtils.isEmpty(id)) {
			return ResponseUtil.apiError("1002", "goods id is null!");
		}
		
		try {
			goodsService.deleteGoods(id);
			logger.info(DELETE_SUCCESS_MSG);
			return ResponseUtil.apiSuccess(id, DELETE_SUCCESS_MSG);
		} catch (Exception e) {
			logger.error(DELETE_EXCEPTION_MSG , e);
			return ResponseUtil.apiError("1100", "未知错误");
		}
	

	}
	
	
	@RequestMapping(value = "/getGoodsById", method = RequestMethod.GET)
	public @ResponseBody WDResponse getGoodsById(@RequestParam Integer id) {
		if (id == null) {
			return ResponseUtil.apiError("1001", "Missing parameter!");
		}

		if (StringUtils.isEmpty(id)) {
			return ResponseUtil.apiError("1002", "goods id is null!");
		}
		
		try {
			Goods goods = goodsService.getGoodsById(id);
			if(goods  == null){
				return ResponseUtil.apiError("1003", "goods not exist!");
			}
			logger.info(GETSINGLE_SUCCESS_MSG);
			return ResponseUtil.apiSuccess(goods, GETSINGLE_SUCCESS_MSG);
		} catch (Exception e) {
			logger.error(GETSINGLE_EXCEPTION_MSG , e);
			return ResponseUtil.apiError("1100", "未知错误");
		}
	

	}
	
	private Map<String, String> getHeadersInfo(HttpServletRequest request) {
	    Map<String, String> map = new HashMap<String, String>();
	    Enumeration headerNames = request.getHeaderNames();
	    while (headerNames.hasMoreElements()) {
	        String key = (String) headerNames.nextElement();
	        String value = request.getHeader(key);
	        map.put(key, value);
	    }
	    return map;
	  }
	
	@RequestMapping(value = "/getGoodsList", method = RequestMethod.POST)
	public @ResponseBody WDResponse getGoodsList(@RequestBody JsonParamObj query) {
		if (query == null) {
			return ResponseUtil.apiError("1001", "Missing parameter!");
		}
		
		Map<String, Object> conditions = new HashMap<String, Object>();
		
//		Integer shopId = req.getShopId();
		Integer currentPage = query.getCurrentPage();
		Integer pageSize = query.getPageSize();
//		Integer IsOnSale = req.getIsOnSale();
//		Integer categoryId = req.getCategoryId();
//		Integer sortByStock = req.getSortByStock();
//		String goodsName = req.getGoodsName();
		

		if (query.getShopId() == null) {
			return ResponseUtil.apiError("1002", "shop id is null!");
		}
		
		try {
			
			
			//condition
			if(query.getId() != null){
				conditions.put("id", query.getId());
			}
			
			if(query.getName() != null){
				conditions.put("goodsName", query.getName());
			}
			if(query.getShopId() != null){
				conditions.put("shopId", query.getShopId());
			}
			
			if(query.getIsOnSale() != null){
				conditions.put("IsOnSale", query.getIsOnSale());
			}
			
			if(query.getCategoryId() != null){//goods category
				conditions.put("categoryId", query.getCategoryId());
			}
			if(query.getSortByStock() != null){//goods category
				conditions.put("sortByStock", query.getSortByStock());
			}
			if(query.getSortByCreatTime() != null){
				conditions.put("sortByCreatTime", query.getSortByCreatTime());
			}
			
			Pagination page = new Pagination();
			if(currentPage != null){
				page.setCurrentPage(currentPage);
			}
			if(pageSize != null){
				page.setPageSize(pageSize);
			}
			
			PageResults<Goods> result = goodsService.getGoodsListByPage(conditions, page);
			
			logger.info(GETGOODSLIST_SUCCESS_MSG);
			return ResponseUtil.apiSuccess(result, GETGOODSLIST_SUCCESS_MSG);
		} catch (Exception e) {
			logger.error(GETGOODSLIST_EXCEPTION_MSG , e);
			return ResponseUtil.apiError("1100", "未知错误");
		}
	

	}
	
	
	
	

	

}
