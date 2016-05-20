/**
 * CAPGEMINI APPLIANCE CHAINS.
 * Copyright (c) 2015-2015 All Rights Reserved.
 */
package com.capgemini.wdapp.model;

import java.util.List;


/**
 * 
 * functional description： Category
 * @author  zhihuang@capgemini.com
 * @created Dec 18, 2015 10:22:27 AM
 * @date Dec 18, 2015 10:22:27 AM
 */

public class Cart {
    
    private Integer id;
    
    private Integer userId;
    
    private Integer goodsId;
    private Integer stockPriceId;
    private Integer quantity;
    
    private Goods goods;
    private StockPrice stockPrice;
    
    
    
    
	/**
	 * @return the goods
	 */
	public Goods getGoods() {
		return goods;
	}
	/**
	 * @param goods the goods to set
	 */
	public void setGoods(Goods goods) {
		this.goods = goods;
	}
	/**
	 * @return the stockPrice
	 */
	public StockPrice getStockPrice() {
		return stockPrice;
	}
	/**
	 * @param stockPrice the stockPrice to set
	 */
	public void setStockPrice(StockPrice stockPrice) {
		this.stockPrice = stockPrice;
	}
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the userId
	 */
	public Integer getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	/**
	 * @return the goodsId
	 */
	public Integer getGoodsId() {
		return goodsId;
	}
	/**
	 * @param goodsId the goodsId to set
	 */
	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}
	/**
	 * @return the stockPriceId
	 */
	public Integer getStockPriceId() {
		return stockPriceId;
	}
	/**
	 * @param stockPriceId the stockPriceId to set
	 */
	public void setStockPriceId(Integer stockPriceId) {
		this.stockPriceId = stockPriceId;
	}
	/**
	 * @return the quantity
	 */
	public Integer getQuantity() {
		return quantity;
	}
	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
    
   
    

}
