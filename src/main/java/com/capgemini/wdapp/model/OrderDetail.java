/**
 * CAPGEMINI APPLIANCE CHAINS.
 * Copyright (c) 2015-2015 All Rights Reserved.
 */
package com.capgemini.wdapp.model;

import java.util.Date;
import java.util.List;


/**
 * 
 * functional description： Category
 * @author  zhihuang@capgemini.com
 * @created Dec 18, 2015 10:22:27 AM
 * @date Dec 18, 2015 10:22:27 AM
 */

public class OrderDetail {
    
    private Integer id;
    private Integer orderMasterId;
    private Float price;
    private Integer quantity;
    private Integer goodsId;
    private String goodsName;
    private String images;
    private String description;
    private Integer stockPriceId;
    private String specValueFirst;
    private String specValueSecond;
    private String specValueThird;
   // private Integer status; // move to table:after_sale status field
    private Integer asStatus;//after sale status;
    
    
	/**
	 * @return the asStatus
	 */
	public Integer getAsStatus() {
		return asStatus;
	}
	/**
	 * @param asStatus the asStatus to set
	 */
	public void setAsStatus(Integer asStatus) {
		this.asStatus = asStatus;
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
	 * @return the orderMasterId
	 */
	public Integer getOrderMasterId() {
		return orderMasterId;
	}
	/**
	 * @param orderMasterId the orderMasterId to set
	 */
	public void setOrderMasterId(Integer orderMasterId) {
		this.orderMasterId = orderMasterId;
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
	 * @return the goodsName
	 */
	public String getGoodsName() {
		return goodsName;
	}
	/**
	 * @param goodsName the goodsName to set
	 */
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	/**
	 * @return the images
	 */
	public String getImages() {
		return images;
	}
	/**
	 * @param images the images to set
	 */
	public void setImages(String images) {
		this.images = images;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
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
	 * @return the status
	 */
//	public Integer getStatus() {
//		return status;
//	}
//	/**
//	 * @param status the status to set
//	 */
//	public void setStatus(Integer status) {
//		this.status = status;
//	}
   
	
    
   
    
    

}
