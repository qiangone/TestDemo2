/**
 * CAPGEMINI APPLIANCE CHAINS.
 * Copyright (c) 2015-2015 All Rights Reserved.
 */
package com.capgemini.wdapp.buyer.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.capgemini.wdapp.common.JsonParamObj;
import com.capgemini.wdapp.common.LogisticsJsonObj;
import com.capgemini.wdapp.common.WDConstant;
import com.capgemini.wdapp.model.OrderMaster;
import com.capgemini.wdapp.model.PageResults;
import com.capgemini.wdapp.model.Pagination;
import com.capgemini.wdapp.response.ResponseUtil;
import com.capgemini.wdapp.response.WDResponse;
import com.capgemini.wdapp.service.IAfterSaleService;
import com.capgemini.wdapp.service.IOrderService;
import com.capgemini.wdapp.service.impl.AutoHandleOrderStatusService;

/**
 * 
 * functional description： Seller Controller
 * 
 * @author zhihuang@capgemini.com
 * @created Dec 18, 2015 10:40:17 AM
 */
@Controller
@RequestMapping("/api/user/order")
public class OrderController {

	private final Log logger = LogFactory.getLog(OrderController.class);

	@Autowired
	private IOrderService orderService;
	
	@Autowired
	private IAfterSaleService afterSaleService;
	
	@Autowired
	private AutoHandleOrderStatusService autoService;
	
	

	private static final String GETORDERLIST_SUCCESS_MSG = "get order list successfully";
	private static final String GETORDERLIST_EXCEPTION_MSG = "get order list exception";
	private static final String MAKEORDER_SUCCESS_MSG = "make order successfully";
	private static final String MAKEORDER_EXCEPTION_MSG = "make order exception";
	private static final String CANCELORDER_SUCCESS_MSG = "cancel order successfully";
	private static final String CANCELORDER_EXCEPTION_MSG = "cancel order exception";
	
	private static final String CONFIRMRECEIVE_SUCCESS_MSG = "confirm receviing successfully";
	private static final String CONFIRMRECEIVE_EXCEPTION_MSG = "confirm receviing exception";
	
	private static final String GETORDER_SUCCESS_MSG = "get order successfully";
	private static final String GETORDER_EXCEPTION_MSG = "get order exception";
	
	private static final String GETLOGISTIC_EXCEPTION_MSG = "GETLOGISTIC exception";
	private static final String GETLOGISTIC_SUCCESS_MSG = "GETLOGISTIC successfully";
	
	
	private static final String ORDERPAY_SUCCESS_MSG = "ORDERPAY successfully";
	private static final String ORDERPAY_EXCEPTION_MSG = "ORDERPAY exception";
	
	@RequestMapping(value = "/makeOrder", method = RequestMethod.POST)
	public @ResponseBody WDResponse makeOrder(@RequestBody JsonParamObj cart) {
		
		if (StringUtils.isEmpty(cart)||cart.getCartList() == null || cart.getCartList().size()==0) {
			return ResponseUtil.apiError("1001", "缺少必要参数");
		}
		if (StringUtils.isEmpty(cart.getUserId())) {
			return ResponseUtil.apiError("1002", "userId is null");
		}
		
		if (StringUtils.isEmpty(cart.getAddressId())) {
			return ResponseUtil.apiError("1003", "addressId is null");
		}
		if (StringUtils.isEmpty(cart.getCharge())) {
			return ResponseUtil.apiError("1004", "charge is null");
		}
		if (StringUtils.isEmpty(cart.getShopId())) {
			return ResponseUtil.apiError("1005", "shopId is null");
		}
		
		try {
			orderService.makeOrders(cart.getCartList(), cart.getUserId(), cart.getShopId(), cart.getAddressId(), cart.getComments(), cart.getCharge());
			logger.info(MAKEORDER_SUCCESS_MSG);
			return ResponseUtil.apiSuccess("make order successfully", MAKEORDER_SUCCESS_MSG);
		} catch (Exception e) {
			logger.error(MAKEORDER_EXCEPTION_MSG , e);
			return ResponseUtil.apiError("1100", "未知错误");
		}
		
	

	}
	
	
	@RequestMapping(value = "/cancelOrder", method = RequestMethod.POST)
	public @ResponseBody WDResponse cancelOrder(@RequestBody JsonParamObj cart) {
		
		if (StringUtils.isEmpty(cart)) {
			return ResponseUtil.apiError("1001", "缺少必要参数");
		}
		if (StringUtils.isEmpty(cart.getUserId())) {
			return ResponseUtil.apiError("1002", "userId is null");
		}
		
		if (StringUtils.isEmpty(cart.getOrderMasterId())) {
			return ResponseUtil.apiError("1003", "orderMasterId is null");
		}
		
		
		
		try {
			orderService.cancelOrderMaster(cart.getOrderMasterId());
			logger.info(CANCELORDER_SUCCESS_MSG);
			return ResponseUtil.apiSuccess("cancel order successfully", CANCELORDER_SUCCESS_MSG);
		} catch (Exception e) {
			logger.error(CANCELORDER_EXCEPTION_MSG , e);
			return ResponseUtil.apiError("1100", "未知错误");
		}
	

	}
	
	@RequestMapping(value = "/confirmReceive", method = RequestMethod.POST)
	public @ResponseBody WDResponse confirmReceive(@RequestBody JsonParamObj cart) {
		
		if (StringUtils.isEmpty(cart)) {
			return ResponseUtil.apiError("1001", "缺少必要参数");
		}
		if (StringUtils.isEmpty(cart.getUserId())) {
			return ResponseUtil.apiError("1002", "userId is null");
		}
		
		if (StringUtils.isEmpty(cart.getOrderMasterId())) {
			return ResponseUtil.apiError("1003", "orderMasterId is null");
		}
		
		
		
		try {
			orderService.confirmRecevie(cart.getOrderMasterId());
			logger.info(CONFIRMRECEIVE_SUCCESS_MSG);
			return ResponseUtil.apiSuccess("confirm order successfully", CONFIRMRECEIVE_SUCCESS_MSG);
		} catch (Exception e) {
			logger.error(CONFIRMRECEIVE_EXCEPTION_MSG , e);
			return ResponseUtil.apiError("1100", "未知错误");
		}
	

	}
	
	
	
	@RequestMapping(value = "/getOrderList", method = RequestMethod.POST)
	public @ResponseBody WDResponse getOrderList(@RequestBody JsonParamObj query) {
		Map<String, Object> conditions = new HashMap<String, Object>();
		if (StringUtils.isEmpty(query)) {
			return ResponseUtil.apiError("1001", "缺少必要参数");
		}
		if (StringUtils.isEmpty(query.getUserId())) {
			return ResponseUtil.apiError("1002", "userId is null");
		}
		
		//index 小绿点
		Map<String, Object> statusConditions = new HashMap<String, Object>();
		
		if(!StringUtils.isEmpty(query.getType())){//type
			List<Integer> status = new ArrayList<Integer>();
			if(query.getType().equals("1")){//待付款
				status.add(WDConstant.ORDERMASTER_STATUS_BOOKED);
				conditions.put("status", status);
			}else if(query.getType().equals("2")){//待发货
				status.add(WDConstant.ORDERMASTER_STATUS_PAYED);
				conditions.put("status", status);
			}else if(query.getType().equals("3")){//待收货
				status.add(WDConstant.ORDERMASTER_STATUS_DELIVERYED);
				conditions.put("status", status);
			}else if(query.getType().equals("4")){//待评价
				status.add(WDConstant.ORDERMASTER_STATUS_CONFIRMED);
				conditions.put("status", status);
			}else if(query.getType().equals("5")){//已发货
				status.add(WDConstant.ORDERMASTER_STATUS_DELIVERYED);
				conditions.put("status", status);
			}else if(query.getType().equals("6")){//退款
				List<Integer> detailStatus = new ArrayList<Integer>();
				detailStatus.add(WDConstant.AFTERSALE_STATUS_APPLY_REFUND);
				detailStatus.add(WDConstant.AFTERSALE_STATUS_APPLY_RETURN);
				detailStatus.add(WDConstant.AFTERSALE_STATUS_REFUNDING);
				detailStatus.add(WDConstant.AFTERSALE_STATUS_CONFIRM_RECV);
				detailStatus.add(WDConstant.AFTERSALE_STATUS_CONFIRM_REFUND);
				detailStatus.add(WDConstant.AFTERSALE_STATUS_REJECT_REFUND);
				detailStatus.add(WDConstant.AFTERSALE_STATUS_REFUND_FINISH);
				conditions.put("detailStatus", detailStatus);// to do 
			}else if(query.getType().equals("7")){//已完成
				status.add(WDConstant.ORDERMASTER_STATUS_CONFIRMED);
				status.add(WDConstant.ORDERMASTER_STATUS_APPRAISED);
				status.add(WDConstant.ORDERMASTER_STATUS_AFTERSALE_COMPLETE);
				conditions.put("status", status);
			}
		}
		

		
		if(!StringUtils.isEmpty(query.getUserId())){
			conditions.put("userId", query.getUserId());
			statusConditions.put("userId", query.getUserId());
		}
		
		if(!StringUtils.isEmpty(query.getKeyword())){
			conditions.put("keyWord", query.getKeyword());
		}
		
		
		Integer currentPage = query.getCurrentPage();
		Integer pageSize = query.getPageSize();
		
		Pagination page = new Pagination();
		if(currentPage != null){
			page.setCurrentPage(currentPage);
		}
		if(pageSize != null){
			page.setPageSize(pageSize);
		}
		
		try {
			Map<String,Object> map = new HashMap<String, Object>();
			int booked = 0;//待付款
			int appraised = 0;//待评价
			int payed = 0;//待发货
			int deliveryed = 0;//待收货
			int refunds = 0;//退款中
			PageResults<OrderMaster> pr = orderService.getOrderListByPage(conditions, page);
			
			
			statusConditions.put("status", WDConstant.ORDERMASTER_STATUS_BOOKED);
			booked = orderService.countOrderNumbers(statusConditions);
			
			statusConditions.put("status", WDConstant.ORDERMASTER_STATUS_CONFIRMED);
			appraised = orderService.countOrderNumbers(statusConditions);
			
			statusConditions.put("status", WDConstant.ORDERMASTER_STATUS_PAYED);
			payed = orderService.countOrderNumbers(statusConditions);
			
			statusConditions.put("status", WDConstant.ORDERMASTER_STATUS_DELIVERYED);
			deliveryed = orderService.countOrderNumbers(statusConditions);
			
			
			statusConditions.put("status", null);
			List<Integer> asList = new ArrayList<Integer>();
			asList.add(WDConstant.AFTERSALE_STATUS_APPLY_REFUND);
			asList.add(WDConstant.AFTERSALE_STATUS_APPLY_RETURN);
			asList.add(WDConstant.AFTERSALE_STATUS_REFUNDING);
			asList.add(WDConstant.AFTERSALE_STATUS_CONFIRM_RECV);
			asList.add(WDConstant.AFTERSALE_STATUS_CONFIRM_REFUND);
			
			statusConditions.put("asStatus", asList);
			refunds = orderService.countOrderNumbers(statusConditions);
			
			
			
			map.put("daifukuan", booked);
			map.put("daipingjia", appraised);
			map.put("daifahuo", payed);
			map.put("daishouhuo", deliveryed);
			map.put("tuikuanzhong", refunds);
			map.put("data", pr);
			logger.info(GETORDERLIST_SUCCESS_MSG);
			return ResponseUtil.apiSuccess(map, GETORDERLIST_SUCCESS_MSG);
		} catch (Exception e) {
			logger.error(GETORDERLIST_EXCEPTION_MSG , e);
			return ResponseUtil.apiError("1100", "未知错误");
		}
	

	}
	
	
	@RequestMapping(value = "/getOrderDetail", method = RequestMethod.GET)
	public @ResponseBody WDResponse getOrderDetail(@RequestParam Integer masterId) {
		
		
		if (StringUtils.isEmpty(masterId)) {
			return ResponseUtil.apiError("1002", "masterId is null");
		}
		
		
		try {
			
			//trigger sp
			autoService.handleSingleStatus(masterId);
			
			OrderMaster master = orderService.getOrderById(masterId);
			logger.info(GETORDER_SUCCESS_MSG);
			return ResponseUtil.apiSuccess(master, GETORDER_SUCCESS_MSG);
		} catch (Exception e) {
			logger.error(GETORDER_EXCEPTION_MSG , e);
			return ResponseUtil.apiError("1100", "未知错误");
		}
	

	}
	
	
	
	@RequestMapping(value = "/getLogisticsInfo", method = RequestMethod.GET)
	public @ResponseBody WDResponse getLogisticsInfo(@RequestParam int masterId) {
		
		if (StringUtils.isEmpty(masterId)) {
			return ResponseUtil.apiError("1001", "masterId is null");
		}
				
		
		try {
			LogisticsJsonObj obj = orderService.getLogisticsInfo(masterId);

			logger.info(GETLOGISTIC_EXCEPTION_MSG);
			return ResponseUtil.apiSuccess(obj, GETLOGISTIC_EXCEPTION_MSG);
		} catch (Exception e) {
			logger.error(GETLOGISTIC_SUCCESS_MSG , e);
			return ResponseUtil.apiError("1100", "未知错误");
		}
	

	}
	
	@RequestMapping(value = "/getLogisticsInfoByNo", method = RequestMethod.POST)
	public @ResponseBody WDResponse getLogisticsInfoByNo(@RequestBody JsonParamObj query) {
		
		if (StringUtils.isEmpty(query.getExpressNumber())) {
			return ResponseUtil.apiError("1001", "expressNumber is null");
		}
		if (StringUtils.isEmpty(query.getExpressCorporation())) {
			return ResponseUtil.apiError("1002", "expressCorporation is null");
		}
				
		
		try {
			LogisticsJsonObj obj = orderService.getLogisticsInfo(query.getExpressNumber(), query.getExpressCorporation());

			logger.info(GETLOGISTIC_EXCEPTION_MSG);
			return ResponseUtil.apiSuccess(obj, GETLOGISTIC_EXCEPTION_MSG);
		} catch (Exception e) {
			logger.error(GETLOGISTIC_SUCCESS_MSG , e);
			return ResponseUtil.apiError("1100", "未知错误");
		}
	

	}
	
	
	
	@RequestMapping(value = "/pay", method = RequestMethod.POST)
	public @ResponseBody WDResponse pay(@RequestBody JsonParamObj query) {
		
		if (StringUtils.isEmpty(query)) {
			return ResponseUtil.apiError("1001", "缺少必要参数");
		}
		if (StringUtils.isEmpty(query.getOrderMasterId())) {
			return ResponseUtil.apiError("1002", "orderMasterId is null");
		}
		
		Map<String, Object> conditions = new HashMap<String, Object>();
		
		
		try {
			String str = "simulate pay successfully";
//			String payTransactionNo= query.getOrderMasterId()+"NO";
			orderService.orderPay(query.getOrderMasterId());
			return ResponseUtil.apiSuccess(str, ORDERPAY_SUCCESS_MSG);
		} catch (Exception e) {
			logger.error(ORDERPAY_EXCEPTION_MSG , e);
			return ResponseUtil.apiError("1100", "未知错误");
		}
	

	}
	
	
}


