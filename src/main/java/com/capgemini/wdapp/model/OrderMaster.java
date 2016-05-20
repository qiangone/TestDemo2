/**
 * CAPGEMINI APPLIANCE CHAINS.
 * Copyright (c) 2015-2015 All Rights Reserved.
 */
package com.capgemini.wdapp.model;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.capgemini.wdapp.util.DateUtil;


/**
 * 
 * functional description： Category
 * @author  zhihuang@capgemini.com
 * @created Dec 18, 2015 10:22:27 AM
 * @date Dec 18, 2015 10:22:27 AM
 */

public class OrderMaster {
    
    private Integer id;
    private String orderCode;
    private Integer userId;
    private Integer shopId;
    private Float amount;
    private Integer status;
    private String comments;
    private Date createTime;
    private String createTimeStr;
    private Date payTime;//订单支付时间
    private Date deliveryTime;//订单发货时间
    private Date dealTime;//订单成交时间
    private String payTransactionNo;
    private String expressNumber;
    private String expressCompany;
    private Float transportExpense;
    
    private List<OrderDetail> orderDetailList;
    private OrderAddress orderAddress;
    private User user;
    
    
    
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
	 * @return the dealTime
	 */
	public Date getDealTime() {
		return dealTime;
	}
	/**
	 * @param dealTime the dealTime to set
	 */
	public void setDealTime(Date dealTime) {
		this.dealTime = dealTime;
	}
	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
	/**
	 * @return the createTimeStr
	 */
	public String getCreateTimeStr() {
		if(createTime != null){
			return DateUtil.dateFormatWithSec(createTime);
		}
		
		return createTimeStr;
	}
	/**
	 * @param createTimeStr the createTimeStr to set
	 */
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	/**
	 * @return the orderDetailList
	 */
	public List<OrderDetail> getOrderDetailList() {
		return orderDetailList;
	}
	/**
	 * @param orderDetailList the orderDetailList to set
	 */
	public void setOrderDetailList(List<OrderDetail> orderDetailList) {
		this.orderDetailList = orderDetailList;
	}
	/**
	 * @return the orderAddress
	 */
	public OrderAddress getOrderAddress() {
		return orderAddress;
	}
	/**
	 * @param orderAddress the orderAddress to set
	 */
	public void setOrderAddress(OrderAddress orderAddress) {
		this.orderAddress = orderAddress;
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
	 * @return the orderCode
	 */
	public String getOrderCode() {
		return orderCode;
	}
	/**
	 * @param orderCode the orderCode to set
	 */
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
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
	 * @return the amount
	 */
	public Float getAmount() {
		return amount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(Float amount) {
		this.amount = amount;
	}
	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
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
	 * @return the payTime
	 */
	public Date getPayTime() {
		return payTime;
	}
	/**
	 * @param payTime the payTime to set
	 */
	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	/**
	 * @return the deliveryTime
	 */
	public Date getDeliveryTime() {
		return deliveryTime;
	}
	/**
	 * @param deliveryTime the deliveryTime to set
	 */
	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
	/**
	 * @return the payTransactionNo
	 */
	public String getPayTransactionNo() {
		return payTransactionNo;
	}
	/**
	 * @param payTransactionNo the payTransactionNo to set
	 */
	public void setPayTransactionNo(String payTransactionNo) {
		this.payTransactionNo = payTransactionNo;
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
	 * @return the expressCompany
	 */
	public String getExpressCompany() {
		return expressCompany;
	}
	/**
	 * @param expressCompany the expressCompany to set
	 */
	public void setExpressCompany(String expressCompany) {
		this.expressCompany = expressCompany;
	}
	/**
	 * @return the transportExpense
	 */
	public Float getTransportExpense() {
		return transportExpense;
	}
	/**
	 * @param transportExpense the transportExpense to set
	 */
	public void setTransportExpense(Float transportExpense) {
		this.transportExpense = transportExpense;
	}
	
    
   
    
    

}
