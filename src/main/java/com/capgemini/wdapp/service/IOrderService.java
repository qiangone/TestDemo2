/**
 * CAPGEMINI APPLIANCE CHAINS.
 * Copyright (c) 2015-2015 All Rights Reserved.
 */
package com.capgemini.wdapp.service;

import java.util.List;
import java.util.Map;

import com.capgemini.wdapp.common.LogisticsJsonObj;
import com.capgemini.wdapp.model.Cart;
import com.capgemini.wdapp.model.OrderMaster;
import com.capgemini.wdapp.model.PageResults;
import com.capgemini.wdapp.model.Pagination;

/**
 * 
 * functional description： seller interface
 * @author  zhihuang@capgemini.com
 * @created Dec 18, 2015 10:37:13 AM
 * @date Dec 18, 2015 10:37:13 AM
 */

public interface IOrderService {
	
	
	public void makeOrders(List<Cart> list, Integer userId, Integer shopId, Integer addressId,String comments, Float charge);
	
	public void deleteCart(List<Cart> list, Integer userId);
	
	public OrderMaster getOrderById(Integer masterId);
	public OrderMaster getOrderByDetailId(Integer detailId);
	
	public OrderMaster getOrderByOrderCode(String orderCode);
	
	
	
	public PageResults<OrderMaster> getOrderListByPage(Map<String, Object> map, Pagination page);
	
	public void cancelOrderMaster(Integer masterId);
	
	public void confirmRecevie(Integer masterId);
	
	public void deliverGoods(Integer masterId, String expressNumber, String expressCorporation);
	
	
	public LogisticsJsonObj getLogisticsInfo(Integer masterId);
	
	public LogisticsJsonObj getLogisticsInfo(String expressNo, String expressComp);
	
	public void orderPay(String orderCode);
	
	public void orderPay(Integer masterId);
	 
	public int countOrderNumbers(Map<String, Object> map);
	
	public void autoHandleAllOrderStatus(int confirmDay, int appraisalDay,int finishDay, int asHandleDay, int asConfirmDay, int asApplyAppealDay);
	
	public void autoHandleSingleOrderStatus(int confirmDay, int appraisalDay,int finishDay, int asHandleDay, int asConfirmDay, int asApplyAppealDay, int orderMasterId);
	
	
}
