/**
 * CAPGEMINI APPLIANCE CHAINS.
 * Copyright (c) 2015-2015 All Rights Reserved.
 */
package com.capgemini.wdapp.buyer.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.capgemini.wdapp.common.JsonParamObj;
import com.capgemini.wdapp.model.Appraisal;
import com.capgemini.wdapp.model.Cart;
import com.capgemini.wdapp.model.Category;
import com.capgemini.wdapp.model.Goods;
import com.capgemini.wdapp.model.PageResults;
import com.capgemini.wdapp.model.Pagination;
import com.capgemini.wdapp.model.Shop;
import com.capgemini.wdapp.model.User;
import com.capgemini.wdapp.response.ResponseUtil;
import com.capgemini.wdapp.response.WDResponse;
import com.capgemini.wdapp.service.IAppraisalService;
import com.capgemini.wdapp.service.ICartService;
import com.capgemini.wdapp.service.ICategoryService;
import com.capgemini.wdapp.service.IGoodsService;
import com.capgemini.wdapp.service.IShopService;
import com.capgemini.wdapp.service.IUserService;

/**
 * 
 * functional description： Seller Controller
 * 
 * @author zhihuang@capgemini.com
 * @created Dec 18, 2015 10:40:17 AM
 */
@Controller
@RequestMapping("/api/user/index")
public class IndexController {

	private final Log logger = LogFactory.getLog(IndexController.class);

	@Autowired
	private IGoodsService goodsService;
	
	@Autowired
	private IShopService shopService;
	
    @Autowired
    private IUserService userService;
    
	@Autowired
	private ICategoryService cateService;
	
	@Autowired
	private ICartService cartService;
	
	@Autowired
	private IAppraisalService appraisalService;
	
	

	private static final String GETGOODS_SUCCESS_MSG = "get goods info successfully";
	private static final String GETGOODS_EXCEPTION_MSG = "get goods info exception";
	private static final String GETGOODSLIST_SUCCESS_MSG = "getGoodsList successfully";
	private static final String GETGOODSLIST_EXCEPTION_MSG = "getGoodsList exception";
	private static final String GETALLCATEGORY_SUCCESS_MSG = "get all category list successfully";
	private static final String GETALLCATEGORY_EXCEPTION_MSG = "get all category list exception";
	
	@RequestMapping(value = "/getAllCategory", method = RequestMethod.GET)
	public @ResponseBody WDResponse getAllCategory(@RequestParam Integer shopId) {
		
		if (StringUtils.isEmpty(shopId)) {
			return ResponseUtil.apiError("1002", "shop id is null!");
		}
				
		try {
			List<Category> list = cateService.getBuyerCategoryList(shopId);
			logger.info(GETALLCATEGORY_SUCCESS_MSG);
			return ResponseUtil.apiSuccess(list, GETALLCATEGORY_SUCCESS_MSG);
		} catch (Exception e) {
			logger.error(GETALLCATEGORY_EXCEPTION_MSG , e);
			return ResponseUtil.apiError("1100", "未知错误");
		}
	

	}
	
	
	@RequestMapping(value = "/getGoodsInfo", method = RequestMethod.GET)
	public @ResponseBody WDResponse getGoodsInfo(@RequestParam Integer goodsId) {
		Map<String, Object> map = new HashMap<String, Object>();

		if (StringUtils.isEmpty(goodsId)) {
			return ResponseUtil.apiError("1002", "goods id is null!");
		}
		try {
			Goods goods = goodsService.getGoodsById(goodsId);
			
			// get first page appraisal list
			Pagination page = new Pagination();
			Map<String, Object> conditions = new HashMap<String, Object>();
			conditions.put("goodsId", goodsId);
			PageResults<Appraisal> result = appraisalService.getAppraisalListByPage(conditions, page);
			map.put("goods", goods);
			if(result != null){
				map.put("appraisalList", result);
			}else{
				map.put("appraisalList", null);
			}
			
			logger.info(GETGOODS_SUCCESS_MSG);
			return ResponseUtil.apiSuccess(map, GETGOODS_SUCCESS_MSG);
		} catch (Exception e) {
			logger.error(GETGOODS_EXCEPTION_MSG , e);
			return ResponseUtil.apiError("1100", "未知错误");
		}
	}
	
	@RequestMapping(value = "/getGoodsList", method = RequestMethod.POST)
	public @ResponseBody WDResponse getGoodsList(@RequestBody JsonParamObj query) {
		if (query == null) {
			return ResponseUtil.apiError("1001", "Missing parameter!");
		}
		
		Map<String, Object> conditions = new HashMap<String, Object>();
		
		Integer currentPage = query.getCurrentPage();
		Integer pageSize = query.getPageSize();

		if (query.getShopId() == null) {
			return ResponseUtil.apiError("1002", "shop id is null!");
		}
		
		try {
			
			
			//condition
			if(query.getId() != null){
				conditions.put("id", query.getId());
			}
			
			if(query.getKeyword() != null){
				conditions.put("goodsName", query.getKeyword());
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
	
	
	@RequestMapping(value = "/getIndex", method = RequestMethod.POST)
	public @ResponseBody WDResponse getIndex(@RequestBody JsonParamObj json) {

		if (StringUtils.isEmpty(json)) {
			return ResponseUtil.apiError("1001", "缺少必要参数");
		}
		
		if (StringUtils.isEmpty(json.getOpenId())) {
			return ResponseUtil.apiError("1002", "openId is null");
		}
		
		if (StringUtils.isEmpty(json.getShopId())) {
			return ResponseUtil.apiError("1003", "shopId is null");
		}
		
		
		try {
			Shop shop = shopService.getShopById(json.getShopId());
			User user = userService.getUserInfo(json.getOpenId()+"");
			
			Map<String, Object> conditions = new HashMap<String, Object>();
			conditions.put("shopId", json.getShopId());
			Pagination page = new Pagination();
			Integer currentPage = json.getCurrentPage();
			Integer pageSize = json.getPageSize();
			if(currentPage != null){
				page.setCurrentPage(currentPage);
			}
			if(pageSize != null){
				page.setPageSize(pageSize);
			}
			
			PageResults<Goods> goodsList = goodsService.getGoodsListByPage(conditions, page);
			
			List<Cart> list = cartService.getCartByUserId(user.getId());
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("shop", shop);
			map.put("goodsList", goodsList);
			map.put("user", user);
			

			if(list == null || list.size()==0){
				map.put("goodsNumInCart", 0);
			}else{
				int totals = 0;
				for(Cart c : list){
					totals += c.getQuantity();
				}
				map.put("goodsNumInCart", totals);
			}
			
			logger.info(GETGOODS_SUCCESS_MSG);
			return ResponseUtil.apiSuccess(map, GETGOODS_SUCCESS_MSG);
		} catch (Exception e) {
			logger.error(GETGOODS_EXCEPTION_MSG , e);
			return ResponseUtil.apiError("1100", "未知错误");
		}
	}
	
	
	@RequestMapping(value = "/getShopInfo", method = RequestMethod.GET)
	public @ResponseBody WDResponse getShopInfo(
			@RequestParam(required = false) Integer shopId) {
		if (StringUtils.isEmpty(shopId)) {
			return ResponseUtil.apiError("1002", "shop Id is null!");
		}

		Shop shop = new Shop();
		shop.setId(shopId);
		List<Shop> list = shopService.getShop(shop);
		if (list == null || list.size() == 0) {
			return ResponseUtil.apiError("1003", "shop Id not exist");
		}

		return ResponseUtil.apiSuccess(list.get(0), "get shop info successful");

	}
	
	

}


