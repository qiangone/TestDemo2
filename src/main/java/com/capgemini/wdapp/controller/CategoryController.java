/**
 * CAPGEMINI APPLIANCE CHAINS.
 * Copyright (c) 2015-2015 All Rights Reserved.
 */
package com.capgemini.wdapp.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.capgemini.wdapp.model.Category;
import com.capgemini.wdapp.response.ResponseUtil;
import com.capgemini.wdapp.response.WDResponse;
import com.capgemini.wdapp.service.ICategoryService;

/**
 * 
 * functional description： Seller Controller
 * 
 * @author zhihuang@capgemini.com
 * @created Dec 18, 2015 10:40:17 AM
 */
@Controller
@RequestMapping("/api/category")
public class CategoryController {

	private final Log logger = LogFactory.getLog(CategoryController.class);

	@Autowired
	private ICategoryService cateService;


	@RequestMapping(value = "/addCategory", method = RequestMethod.POST)
	public @ResponseBody WDResponse addCategory(@RequestBody Category cat) {
		if (cat == null) {
			return ResponseUtil.apiError("1001", "Missing parameter!");
		}

		if (StringUtils.isEmpty(cat.getShopId())) {
			return ResponseUtil.apiError("1002", "shop id is null!");
		}
		
		if (StringUtils.isEmpty(cat.getName())) {
			return ResponseUtil.apiError("1003", "category name is null!");
		}
		
		List<Category> list = cateService.getCategoryByName(cat.getName(), cat.getShopId());
		if(list != null && list.size()>0){
			return ResponseUtil.apiError("1004", "category name already exist!");
		}
		
		try {
			cateService.addCategory(cat);
			logger.info("add category successful");
			return ResponseUtil.apiSuccess(cat, "add category successful");
		} catch (Exception e) {
			logger.error("add category exception: " , e);
			return ResponseUtil.apiError("1100", "未知错误");
		}
	

	}
	
	@RequestMapping(value = "/updateCategory", method = RequestMethod.POST)
	public @ResponseBody WDResponse updateCategory(@RequestBody Category cat) {
		if (cat == null) {
			return ResponseUtil.apiError("1001", "Missing parameter!");
		}

//		if (StringUtils.isEmpty(cat.getShopId())) {
//			return ResponseUtil.apiError("1002", "shop id is null!");
//		}
		
		if (StringUtils.isEmpty(cat.getName())) {
			return ResponseUtil.apiError("1002", "category name is null!");
		}
		
		if (StringUtils.isEmpty(cat.getId())) {
			return ResponseUtil.apiError("1003", "category id is null!");
		}
		
		try {
			cateService.updateCategory(cat);
			logger.info("update category successful");
			return ResponseUtil.apiSuccess(cat, "update category successful");
		} catch (Exception e) {
			logger.error("getVerifyNum exception: " , e);
			return ResponseUtil.apiError("1100", "未知错误");
		}
	

	}
	
	
	@RequestMapping(value = "/getCategoryById", method = RequestMethod.GET)
	public @ResponseBody WDResponse getCategory(@RequestParam Integer id) {
		if (id == null) {
			return ResponseUtil.apiError("1001", "Missing parameter!");
		}

		if (StringUtils.isEmpty(id)) {
			return ResponseUtil.apiError("1002", "category id is null!");
		}
		
		try {
			Category category = cateService.getCategoryById(id);
			logger.info("get category successful");
			return ResponseUtil.apiSuccess(category, "get category successful");
		} catch (Exception e) {
			logger.error("getVerifyNum exception: " , e);
			return ResponseUtil.apiError("1100", "未知错误");
		}
	

	}
	
	
	@RequestMapping(value = "/deleteCategory", method = RequestMethod.GET)
	public @ResponseBody WDResponse deleteCategory(@RequestParam Integer id) {
		if (id == null) {
			return ResponseUtil.apiError("1001", "Missing parameter!");
		}

		if (StringUtils.isEmpty(id)) {
			return ResponseUtil.apiError("1002", "category id is null!");
		}
		
		try {
			cateService.deleteCategory(id);
			logger.info("delete category successful");
			return ResponseUtil.apiSuccess(id, "delete category successful");
		} catch (Exception e) {
			logger.error("delete category  exception: " , e);
			return ResponseUtil.apiError("1100", "未知错误");
		}
	

	}
	
	
	@RequestMapping(value = "/getAllCategory", method = RequestMethod.GET)
	public @ResponseBody WDResponse getAllCategory(@RequestParam Integer shopId) {
		if (StringUtils.isEmpty(shopId)) {
			return ResponseUtil.apiError("1002", "shop id is null!");
		}
				
		try {
			List<Category> list = cateService.getAllCategoryList(shopId);
			logger.info("get all category successful");
			return ResponseUtil.apiSuccess(list, "get all category successful");
		} catch (Exception e) {
			logger.error("getVerifyNum exception: " , e);
			return ResponseUtil.apiError("1100", "未知错误");
		}
	

	}
	
	@RequestMapping(value = "/getCategory", method = RequestMethod.GET)
	public @ResponseBody WDResponse getCategory(@RequestParam String name, @RequestParam Integer shopId) {
		
		if (shopId == null) {
			return ResponseUtil.apiError("1002", "shopId is null");
		}
		
				
		try {
			List<Category> list = cateService.getCategoryByName(name, shopId);
			logger.info("get category successful");
			return ResponseUtil.apiSuccess(list, "get category successful");
		} catch (Exception e) {
			logger.error("get category exception: " , e);
			return ResponseUtil.apiError("1100", "未知错误");
		}

	}
	

	

}
