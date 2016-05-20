/**
 * CAPGEMINI APPLIANCE CHAINS.
 * Copyright (c) 2015-2015 All Rights Reserved.
 */
package com.capgemini.wdapp.service;

import java.util.List;

import com.capgemini.wdapp.common.CartUpdateParam;
import com.capgemini.wdapp.model.Cart;

/**
 * 
 * functional description： seller interface
 * @author  zhihuang@capgemini.com
 * @created Dec 18, 2015 10:37:13 AM
 * @date Dec 18, 2015 10:37:13 AM
 */

public interface ICartService {
	public int addCart(Cart addr);
	
	public int updateCart(Cart addr);
	
	public int updateCart(List<Cart> list, Integer userId);
	
	
	public List<Cart> getCartByUserId(Integer userId);
	
	public Cart getCartByDiffId(Integer userId, Integer goodsId, Integer stockPriceId);
	
}
