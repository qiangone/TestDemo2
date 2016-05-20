/**
 * CAPGEMINI APPLIANCE CHAINS.
 * Copyright (c) 2015-2015 All Rights Reserved.
 */
package com.capgemini.wdapp.model;

import java.util.Date;
import java.util.List;

import com.capgemini.wdapp.util.DateUtil;


/**
 * 
 * functional description： Category
 * @author  zhihuang@capgemini.com
 * @created Dec 18, 2015 10:22:27 AM
 * @date Dec 18, 2015 10:22:27 AM
 */

public class AfterSale {
    
    private Integer id;
    
    private Integer orderDetailId;
    
    private Integer orderMasterId;
    
    private Integer type;
    
    private Integer status;
    private Integer isReceived;
    
    private String reason;
    private String detail;
    private Float money;
    private String attach;
    private Date applyTime;
    private String applyTimeStr;
    
    private Integer handleResult;
    private String handleReason;
    private Date  handleTime;
    
    private String expressNumber;
    private String expressCompany;
    private Date expressTime;
    
    private Date refundTime;
    private String refundCertificate;
    
    private String appealTitle;
    private String appealContent;
    private Date appealTime;
    private String appealTimeStr;
    
    private Date finishTime;
    private String finishTimeStr;
    
    
    private OrderDetail orderDetail;
    
    
    
    
    
    
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
	 * @return the orderDetail
	 */
	public OrderDetail getOrderDetail() {
		return orderDetail;
	}

	/**
	 * @param orderDetail the orderDetail to set
	 */
	public void setOrderDetail(OrderDetail orderDetail) {
		this.orderDetail = orderDetail;
	}

	/**
	 * @return the finishTime
	 */
	public Date getFinishTime() {
		return finishTime;
	}

	/**
	 * @param finishTime the finishTime to set
	 */
	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	/**
	 * @return the finishTimeStr
	 */
	public String getFinishTimeStr() {
		if(finishTime != null){
			return DateUtil.dateFormatWithSec(finishTime);
		}
		
		return finishTimeStr;
	}

	/**
	 * @param finishTimeStr the finishTimeStr to set
	 */
	public void setFinishTimeStr(String finishTimeStr) {
		this.finishTimeStr = finishTimeStr;
	}

	/**
	 * @return the appealTimeStr
	 */
	public String getAppealTimeStr() {
		
		if(appealTime != null){
			return DateUtil.dateFormatWithSec(appealTime);
		}
		
		return appealTimeStr;
	}

	/**
	 * @return the appealTitle
	 */
	public String getAppealTitle() {
		return appealTitle;
	}

	/**
	 * @param appealTitle the appealTitle to set
	 */
	public void setAppealTitle(String appealTitle) {
		this.appealTitle = appealTitle;
	}

	/**
	 * @return the appealContent
	 */
	public String getAppealContent() {
		return appealContent;
	}

	/**
	 * @param appealContent the appealContent to set
	 */
	public void setAppealContent(String appealContent) {
		this.appealContent = appealContent;
	}

	/**
	 * @return the appealTime
	 */
	public Date getAppealTime() {
		return appealTime;
	}

	/**
	 * @param appealTime the appealTime to set
	 */
	public void setAppealTime(Date appealTime) {
		this.appealTime = appealTime;
	}

	/**
	 * @return the handleResult
	 */
	public Integer getHandleResult() {
		return handleResult;
	}

	/**
	 * @param handleResult the handleResult to set
	 */
	public void setHandleResult(Integer handleResult) {
		this.handleResult = handleResult;
	}

	/**
	 * @return the handleReason
	 */
	public String getHandleReason() {
		return handleReason;
	}

	/**
	 * @param handleReason the handleReason to set
	 */
	public void setHandleReason(String handleReason) {
		this.handleReason = handleReason;
	}

	/**
	 * @return the handleTime
	 */
	public Date getHandleTime() {
		return handleTime;
	}

	/**
	 * @param handleTime the handleTime to set
	 */
	public void setHandleTime(Date handleTime) {
		this.handleTime = handleTime;
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
	 * @return the expressTime
	 */
	public Date getExpressTime() {
		return expressTime;
	}

	/**
	 * @param expressTime the expressTime to set
	 */
	public void setExpressTime(Date expressTime) {
		this.expressTime = expressTime;
	}

	/**
	 * @return the refundTime
	 */
	public Date getRefundTime() {
		return refundTime;
	}

	/**
	 * @param refundTime the refundTime to set
	 */
	public void setRefundTime(Date refundTime) {
		this.refundTime = refundTime;
	}

	/**
	 * @return the refundCertificate
	 */
	public String getRefundCertificate() {
		return refundCertificate;
	}

	/**
	 * @param refundCertificate the refundCertificate to set
	 */
	public void setRefundCertificate(String refundCertificate) {
		this.refundCertificate = refundCertificate;
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
	 * @return the orderDetailId
	 */
	public Integer getOrderDetailId() {
		return orderDetailId;
	}
	/**
	 * @param orderDetailId the orderDetailId to set
	 */
	public void setOrderDetailId(Integer orderDetailId) {
		this.orderDetailId = orderDetailId;
	}
	/**
	 * @return the type
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(Integer type) {
		this.type = type;
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
	 * @return the isReceived
	 */
	public Integer getIsReceived() {
		return isReceived;
	}
	/**
	 * @param isReceived the isReceived to set
	 */
	public void setIsReceived(Integer isReceived) {
		this.isReceived = isReceived;
	}
	/**
	 * @return the reason
	 */
	public String getReason() {
		return reason;
	}
	/**
	 * @param reason the reason to set
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}
	/**
	 * @return the detail
	 */
	public String getDetail() {
		return detail;
	}
	/**
	 * @param detail the detail to set
	 */
	public void setDetail(String detail) {
		this.detail = detail;
	}
	/**
	 * @return the money
	 */
	public Float getMoney() {
		return money;
	}
	/**
	 * @param money the money to set
	 */
	public void setMoney(Float money) {
		this.money = money;
	}
	/**
	 * @return the attach
	 */
	public String getAttach() {
		return attach;
	}
	/**
	 * @param attach the attach to set
	 */
	public void setAttach(String attach) {
		this.attach = attach;
	}

	/**
	 * @return the applyTime
	 */
	public Date getApplyTime() {
		return applyTime;
	}

	/**
	 * @param applyTime the applyTime to set
	 */
	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	/**
	 * @return the applyTimeStr
	 */
	public String getApplyTimeStr() {
		if(applyTime != null){
			return DateUtil.dateFormatWithSec(applyTime);
		}
		
		return applyTimeStr;
	}

	/**
	 * @param applyTimeStr the applyTimeStr to set
	 */
	public void setApplyTimeStr(String applyTimeStr) {
		this.applyTimeStr = applyTimeStr;
	}

	/**
	 * @param appealTimeStr the appealTimeStr to set
	 */
	public void setAppealTimeStr(String appealTimeStr) {
		this.appealTimeStr = appealTimeStr;
	}
	
    

}
