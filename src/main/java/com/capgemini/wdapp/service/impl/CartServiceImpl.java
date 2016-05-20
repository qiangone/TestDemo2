/**
 * CAPGEMINI APPLIANCE CHAINS.
 * Copyright (c) 2015-2015 All Rights Reserved.
 */
package com.capgemini.wdapp.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.capgemini.wdapp.common.CartUpdateParam;
import com.capgemini.wdapp.dao.ICartDao;
import com.capgemini.wdapp.model.Cart;
import com.capgemini.wdapp.service.ICartService;

/**
 * 
 * functional description： seller interface
 * @author  zhihuang@capgemini.com
 * @created Dec 18, 2015 10:37:13 AM
 * @date Dec 18, 2015 10:37:13 AM
 */

@Service(value = "cartService")
public class CartServiceImpl implements ICartService {

	private final static Log logger = LogFactory.getLog(CartServiceImpl.class);
	
	@Autowired
	private ICartDao cartrDao;
	
	public int addCart(Cart addr){
		Cart cart = getCartByDiffId(addr.getUserId(), addr.getGoodsId(), addr.getStockPriceId());
		if(cart != null){
			Integer qua = cart.getQuantity() + addr.getQuantity();
			cart.setQuantity(qua);
			cartrDao.updateCart(cart);
			return qua;
		}else{
			cartrDao.addCart(addr);
			return  addr.getQuantity();
		}
		
	}
	
	
	public int updateCart(Cart addr){
		return cartrDao.updateCart(addr);
	}
	
	public int updateCart(List<Cart> list, Integer userId){
		cartrDao.deleteCartbyUserId(userId);
		if(list != null && list.size()>0){
			for(Cart cp : list){
				cp.setUserId(userId);
			}
		}
//		List<Cart> cartList = new ArrayList<Cart>();
//		for(Cart cp : list){
////			Cart cart = new Cart();
//			cp.setUserId(userId);
////			Integer goodsId = cp.getGoodsId();
////			Integer stockPriceId = cp.getStockPriceId();
////			Integer quantity = cp.getQuantity();
////			cart.setGoodsId(goodsId);
////			cart.setStockPriceId(stockPriceId);
////			cart.setQuantity(quantity);
////			cartList.add(cart);
//		}
		return cartrDao.addCardList(list);
	}
	
	public List<Cart> getCartByUserId(Integer userId){
		Map map = new HashMap();
		map.put("userId", userId);
		List<Cart> list = cartrDao.getCart(map);
		
		return list;
	}
	
	public Cart getCartByDiffId(Integer userId, Integer goodsId, Integer stockPriceId){
		Map map = new HashMap();
		map.put("userId", userId);
		if(!StringUtils.isEmpty(goodsId)){
		    map.put("goodsId", goodsId);	
		}
		if(!StringUtils.isEmpty(stockPriceId)){
			map.put("stockPriceId", stockPriceId);	
		}
		
		List<Cart> list = cartrDao.getCart(map);
		
		if(list != null && list.size()>0){
			return list.get(0);
		}
		
		return null;
		
	}
	
	
}
