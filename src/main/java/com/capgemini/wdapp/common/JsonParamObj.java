package com.capgemini.wdapp.common;

import java.util.List;

import com.capgemini.wdapp.model.Appraisal;
import com.capgemini.wdapp.model.Cart;


public class JsonParamObj {
	
	private Integer id;
	private Integer currentPage;
	private Integer pageSize;
	private Integer shopId;
	private Integer sellerId;
	private Integer userId;
	private Integer IsOnSale;
	private Integer categoryId;
	private Integer goodsId;
	
	private Integer sortByStock;
	
	private Integer sortByCreatTime;
	
	private String name;
	
	private String openId;
	
	private String keyword;
	
	private String verifyNumber;
	private String oldPassword;
	private String newPassword;
	
	private String comments;
	
	private Integer addressId;
	
	private Integer orderMasterId;
	private String orderCode;
	private List<Cart> cartList;
	
	
	private List<Integer> status;
	
	private String expressNumber;
	private String expressCorporation;
	
	private String type;//0:全部 1：:待付款 2：待发货 3：待收货 4：待评价 5：已发货 6：退款中　７：已完成
	
	private Float charge;//运费
	
	
	
	
	private List<Appraisal> appraisalList;
	
	
	
	
	
	/**
	 * @return the charge
	 */
	public Float getCharge() {
		return charge;
	}
	/**
	 * @param charge the charge to set
	 */
	public void setCharge(Float charge) {
		this.charge = charge;
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
	 * @return the appraisalList
	 */
	public List<Appraisal> getAppraisalList() {
		return appraisalList;
	}
	/**
	 * @param appraisalList the appraisalList to set
	 */
	public void setAppraisalList(List<Appraisal> appraisalList) {
		this.appraisalList = appraisalList;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the expressNumber
	 */
	public String getExpressNumber() {
		return expressNumber;
	}
	/**
	 * @param expressNumber the expressNumber to set
	 */
	public void setExpressNumber(String expressNumber) {
		this.expressNumber = expressNumber;
	}
	/**
	 * @return the expressCorporation
	 */
	public String getExpressCorporation() {
		return expressCorporation;
	}
	/**
	 * @param expressCorporation the expressCorporation to set
	 */
	public void setExpressCorporation(String expressCorporation) {
		this.expressCorporation = expressCorporation;
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
	 * @return the status
	 */
	public List<Integer> getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(List<Integer> status) {
		this.status = status;
	}
	/**
	 * @return the addressId
	 */
	public Integer getAddressId() {
		return addressId;
	}
	/**
	 * @param addressId the addressId to set
	 */
	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}
	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}
	/**
	 * @param comments the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	/**
	 * @return the cartList
	 */
	public List<Cart> getCartList() {
		return cartList;
	}
	/**
	 * @param cartList the cartList to set
	 */
	public void setCartList(List<Cart> cartList) {
		this.cartList = cartList;
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
	 * @return the sellerId
	 */
	public Integer getSellerId() {
		return sellerId;
	}
	/**
	 * @param sellerId the sellerId to set
	 */
	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}
	/**
	 * @return the verifyNumber
	 */
	public String getVerifyNumber() {
		return verifyNumber;
	}
	/**
	 * @param verifyNumber the verifyNumber to set
	 */
	public void setVerifyNumber(String verifyNumber) {
		this.verifyNumber = verifyNumber;
	}
	/**
	 * @return the oldPassword
	 */
	public String getOldPassword() {
		return oldPassword;
	}
	/**
	 * @param oldPassword the oldPassword to set
	 */
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	/**
	 * @return the newPassword
	 */
	public String getNewPassword() {
		return newPassword;
	}
	/**
	 * @param newPassword the newPassword to set
	 */
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	/**
	 * @return the keyword
	 */
	public String getKeyword() {
		return keyword;
	}
	/**
	 * @param keyword the keyword to set
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	/**
	 * @return the openId
	 */
	public String getOpenId() {
		return openId;
	}
	/**
	 * @param openId the openId to set
	 */
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	/**
	 * @return the sortByCreatTime
	 */
	public Integer getSortByCreatTime() {
		return sortByCreatTime;
	}
	/**
	 * @param sortByCreatTime the sortByCreatTime to set
	 */
	public void setSortByCreatTime(Integer sortByCreatTime) {
		this.sortByCreatTime = sortByCreatTime;
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
	 * @return the sortByStock
	 */
	public Integer getSortByStock() {
		return sortByStock;
	}
	/**
	 * @param sortByStock the sortByStock to set
	 */
	public void setSortByStock(Integer sortByStock) {
		this.sortByStock = sortByStock;
	}
	/**
	 * @return the currentPage
	 */
	public Integer getCurrentPage() {
		return currentPage;
	}
	/**
	 * @param currentPage the currentPage to set
	 */
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	/**
	 * @return the pageSize
	 */
	public Integer getPageSize() {
		return pageSize;
	}
	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
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
	/**
	 * @return the categoryId
	 */
	public Integer getCategoryId() {
		return categoryId;
	}
	/**
	 * @param categoryId the categoryId to set
	 */
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	
		
	



	
}
