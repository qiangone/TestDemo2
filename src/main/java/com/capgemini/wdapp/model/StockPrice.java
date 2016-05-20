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

public class StockPrice {
    
    private Integer id;
    
    private Integer goodsId;
    
    private String specValueFirst;
    
    private String specValueSecond;
    
    private String specValueThird;
    
    private Integer quantity;
    
    private Float price;

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
	 * @return the specValueFirst
	 */
	public String getSpecValueFirst() {
		return specValueFirst;
	}

	/**
	 * @param specValueFirst the specValueFirst to set
	 */
	public void setSpecValueFirst(String specValueFirst) {
		this.specValueFirst = specValueFirst;
	}

	/**
	 * @return the specValueSecond
	 */
	public String getSpecValueSecond() {
		return specValueSecond;
	}

	/**
	 * @param specValueSecond the specValueSecond to set
	 */
	public void setSpecValueSecond(String specValueSecond) {
		this.specValueSecond = specValueSecond;
	}

	
	/**
	 * @return the specValueThird
	 */
	public String getSpecValueThird() {
		return specValueThird;
	}

	/**
	 * @param specValueThird the specValueThird to set
	 */
	public void setSpecValueThird(String specValueThird) {
		this.specValueThird = specValueThird;
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

	/**
	 * @return the price
	 */
	public Float getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(Float price) {
		this.price = price;
	}
    
	   
    
    

}
