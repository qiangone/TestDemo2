package com.capgemini.wdapp.model;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

public class Goods {

	private Integer id;
	private String name;
	private Integer shopId;
	private String specNameFirst;
	private String specNameSecond;
	private String specNameThird;
	private Float price;
    private Integer quantity;
	
	private String images;
	private String thumbImages;
	private String description;
	
	private Integer excellentNum;
	private Integer mediumNum;
	private Integer badNum;
	
	private Integer IsOnSale;
	private Date createTime;
	
	//@JsonIgnore
	private List<Category> categoryList;
	
	@JsonIgnore
	private List<StockPrice> stockPriceList;
	
	private String deleteImages;
	
	
	

    
	/**
	 * @return the deleteImages
	 */
	public String getDeleteImages() {
		return deleteImages;
	}

	/**
	 * @param deleteImages the deleteImages to set
	 */
	public void setDeleteImages(String deleteImages) {
		this.deleteImages = deleteImages;
	}

	/**
	 * @return the thumbImages
	 */
	public String getThumbImages() {
		return thumbImages;
	}

	/**
	 * @param thumbImages the thumbImages to set
	 */
	public void setThumbImages(String thumbImages) {
		this.thumbImages = thumbImages;
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
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the stockPriceList
	 */
	public List<StockPrice> getStockPriceList() {
		return stockPriceList;
	}

	/**
	 * @param stockPriceList the stockPriceList to set
	 */
	public void setStockPriceList(List<StockPrice> stockPriceList) {
		this.stockPriceList = stockPriceList;
	}

	/**
	 * @return the categoryList
	 */
	public List<Category> getCategoryList() {
		return categoryList;
	}

	/**
	 * @param categoryList the categoryList to set
	 */
	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the shopId
	 */
	public Integer getShopId() {
		return shopId;
	}

	/**
	 * @param shopId the shopId to set
	 */
	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	/**
	 * @return the specNameFirst
	 */
	public String getSpecNameFirst() {
		return specNameFirst;
	}

	/**
	 * @param specNameFirst the specNameFirst to set
	 */
	public void setSpecNameFirst(String specNameFirst) {
		this.specNameFirst = specNameFirst;
	}

	/**
	 * @return the specNameSecond
	 */
	public String getSpecNameSecond() {
		return specNameSecond;
	}

	/**
	 * @param specNameSecond the specNameSecond to set
	 */
	public void setSpecNameSecond(String specNameSecond) {
		this.specNameSecond = specNameSecond;
	}

	/**
	 * @return the specNameThird
	 */
	public String getSpecNameThird() {
		return specNameThird;
	}

	/**
	 * @param specNameThird the specNameThird to set
	 */
	public void setSpecNameThird(String specNameThird) {
		this.specNameThird = specNameThird;
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
	 * @return the excellentNum
	 */
	public Integer getExcellentNum() {
		return excellentNum;
	}

	/**
	 * @param excellentNum the excellentNum to set
	 */
	public void setExcellentNum(Integer excellentNum) {
		this.excellentNum = excellentNum;
	}

	/**
	 * @return the mediumNum
	 */
	public Integer getMediumNum() {
		return mediumNum;
	}

	/**
	 * @param mediumNum the mediumNum to set
	 */
	public void setMediumNum(Integer mediumNum) {
		this.mediumNum = mediumNum;
	}

	/**
	 * @return the badNum
	 */
	public Integer getBadNum() {
		return badNum;
	}

	/**
	 * @param badNum the badNum to set
	 */
	public void setBadNum(Integer badNum) {
		this.badNum = badNum;
	}

	/**
	 * @return the isOnSale
	 */
	public Integer getIsOnSale() {
		return IsOnSale;
	}

	/**
	 * @param isOnSale the isOnSale to set
	 */
	public void setIsOnSale(Integer isOnSale) {
		IsOnSale = isOnSale;
	}

	
	
	
}
