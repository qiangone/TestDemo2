/**
 * CAPGEMINI APPLIANCE CHAINS.
 * Copyright (c) 2015-2015 All Rights Reserved.
 */
package com.capgemini.wdapp.buyer.controller;

import java.util.ArrayList;
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
import com.capgemini.wdapp.model.Cart;
import com.capgemini.wdapp.model.Goods;
import com.capgemini.wdapp.model.StockPrice;
import com.capgemini.wdapp.response.ResponseUtil;
import com.capgemini.wdapp.response.WDResponse;
import com.capgemini.wdapp.service.ICartService;
import com.capgemini.wdapp.util.ConfigUtil;

/**
 * 
 * functional description： Seller Controller
 * 
 * @author zhihuang@capgemini.com
 * @created Dec 18, 2015 10:40:17 AM
 */
@Controller
@RequestMapping("/api/user/cart")
public class CartController {

	private final Log logger = LogFactory.getLog(CartController.class);

	@Autowired
	private ICartService cartService;
	
	

	private static final String ADDCART_SUCCESS_MSG = "add cart successfully";
	private static final String ADDCART_EXCEPTION_MSG = "add cart exception";
	private static final String UPDATECART_SUCCESS_MSG = "update cart successfully";
	private static final String UPDATECART_EXCEPTION_MSG = "update cart exception";
	
	private static final String GETCART_SUCCESS_MSG = "get cart successfully";
	private static final String GETCART_EXCEPTION_MSG = "get cart exception";
	
	@RequestMapping(value = "/addCart", method = RequestMethod.POST)
	public @ResponseBody WDResponse addCart(@RequestBody Cart cart) {
		
		if (StringUtils.isEmpty(cart)) {
			return ResponseUtil.apiError("1001", "缺少必要参数");
		}
		if (StringUtils.isEmpty(cart.getUserId())) {
			return ResponseUtil.apiError("1002", "userId is null");
		}
		if (StringUtils.isEmpty(cart.getGoodsId()) ) {
			return ResponseUtil.apiError("1003", "goodsId  is null");
		}
		if (StringUtils.isEmpty(cart.getQuantity())) {
			return ResponseUtil.apiError("1004", "quantity is null");
		}	
		
		List<Cart> list =  cartService.getCartByUserId(cart.getUserId());
		// shop cart max num
		String maxNum = ConfigUtil.getPropertyValue("shop.cart.maxnumber");
		if(list != null && list.size()>Integer.parseInt(maxNum)){
			return ResponseUtil.apiError("1005", "shop cart is full");
		}
		
				
		try {
			int quantity = cartService.addCart(cart);
			cart.setQuantity(quantity);
			logger.info(ADDCART_SUCCESS_MSG);
			return ResponseUtil.apiSuccess(cart, ADDCART_SUCCESS_MSG);
		} catch (Exception e) {
			logger.error(ADDCART_EXCEPTION_MSG , e);
			return ResponseUtil.apiError("1100", "未知错误");
		}
	

	}
	
	@RequestMapping(value = "/updateCart", method = RequestMethod.POST)
	public @ResponseBody WDResponse updateCart(@RequestBody JsonParamObj cart) {
		
		if (StringUtils.isEmpty(cart)||cart.getCartList() == null || cart.getCartList().size()==0) {
			return ResponseUtil.apiError("1001", "缺少必要参数");
		}
		if (StringUtils.isEmpty(cart.getUserId())) {
			return ResponseUtil.apiError("1002", "userId is null");
		}
		
		List<Cart> list = cart.getCartList();
		for(Cart c : list){
			if(StringUtils.isEmpty(c.getGoodsId())){
				return ResponseUtil.apiError("1003", "goods id is null");
			}
		}
		
		
		
				
		try {
			cartService.updateCart(cart.getCartList(), cart.getUserId());
			logger.info(UPDATECART_SUCCESS_MSG);
			return ResponseUtil.apiSuccess("update shop cart successfully", UPDATECART_SUCCESS_MSG);
		} catch (Exception e) {
			logger.error(UPDATECART_EXCEPTION_MSG , e);
			return ResponseUtil.apiError("1100", "未知错误");
		}
	

	}
	
	
	@RequestMapping(value = "/getCartList", method = RequestMethod.GET)
	public @ResponseBody WDResponse getCartList(@RequestParam Integer userId) {
		
		
		if (StringUtils.isEmpty(userId)) {
			return ResponseUtil.apiError("1002", "userId is null");
		}
				
		try {
			List<Cart> list = cartService.getCartByUserId(userId);
			
			List<Map<String,Object>> retList = new ArrayList<Map<String,Object>>();
			if(list != null && list.size()>0){
				for(Cart cart : list){
					Map<String,Object> map = new HashMap<String,Object>();
					StockPrice sp = cart.getStockPrice();
					Goods good = cart.getGoods();
					
				    Integer goodsId = good.getId();
				    Integer stockPriceId = cart.getStockPriceId();
				    map.put("goodsId", goodsId);
				    map.put("stockPriceId", stockPriceId);
				    map.put("userId", cart.getUserId());
				    map.put("goodsName", good.getName());
				    map.put("number", cart.getQuantity());
				  
				    map.put("image", good.getImages());
				    if(!StringUtils.isEmpty(cart.getStockPriceId())){
				    	 map.put("specValue", sp.getSpecValueFirst());
				    	 map.put("price", sp.getPrice());
				    	  map.put("quantity", sp.getQuantity());
				    }else{
				    	 map.put("specValue", null);
				    	 map.put("price", good.getPrice());
				    	  map.put("quantity", good.getQuantity());
				    }
				    retList.add(map);
				}
			}
			logger.info(GETCART_SUCCESS_MSG);
			return ResponseUtil.apiSuccess(retList, GETCART_SUCCESS_MSG);
		} catch (Exception e) {
			logger.error(GETCART_EXCEPTION_MSG , e);
			return ResponseUtil.apiError("1100", "未知错误");
		}

	}
	
	
	
}


