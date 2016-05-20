/**
 * CAPGEMINI APPLIANCE CHAINS.
 * Copyright (c) 2015-2015 All Rights Reserved.
 */
package com.capgemini.wdapp.controller;

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
import com.capgemini.wdapp.common.WDConstant;
import com.capgemini.wdapp.model.AfterSale;
import com.capgemini.wdapp.model.ExpressCompany;
import com.capgemini.wdapp.model.OrderDetail;
import com.capgemini.wdapp.model.OrderMaster;
import com.capgemini.wdapp.model.PageResults;
import com.capgemini.wdapp.model.Pagination;
import com.capgemini.wdapp.response.ResponseUtil;
import com.capgemini.wdapp.response.WDResponse;
import com.capgemini.wdapp.service.IAddressService;
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
@RequestMapping("/api/seller/order")
public class SellerOrderController {

	private final Log logger = LogFactory.getLog(SellerOrderController.class);

	@Autowired
	private IOrderService orderService;
	
	@Autowired
	private IAddressService addrService;
	
	@Autowired
	private IAfterSaleService afterSaleService;
	
	@Autowired
	private AutoHandleOrderStatusService autoService;
	
	

	private static final String GETORDERLIST_SUCCESS_MSG = "get order list successfully";
	private static final String GETORDERLIST_EXCEPTION_MSG = "get order list exception";
	private static final String DELIVERY_SUCCESS_MSG = "DELIVERY successfully";
	private static final String DELIVERY_EXCEPTION_MSG = "DELIVERY exception";
	
	private static final String GETORDER_SUCCESS_MSG = "get order successfully";
	private static final String GETORDER_EXCEPTION_MSG = "get order exception";
	private static final String GETExpressCOMPANY_SUCCESS_MSG = "GETExpressCOMPANY successfully";
	private static final String GETExpressCOMPANY_EXCEPTION_MSG = "GETExpressCOMPANY exception";

	@RequestMapping(value = "/getExpressCompany", method = RequestMethod.GET)
	public @ResponseBody WDResponse getExpressCompany() {
		
		
		
				
		try {
			List<ExpressCompany> list = addrService.getExpressCompList();
			logger.info(GETExpressCOMPANY_SUCCESS_MSG);
			return ResponseUtil.apiSuccess(list, GETExpressCOMPANY_SUCCESS_MSG);
		} catch (Exception e) {
			logger.error(GETExpressCOMPANY_EXCEPTION_MSG , e);
			return ResponseUtil.apiError("1100", "未知错误");
		}
	

	}
	
	
	@RequestMapping(value = "/getOrderList", method = RequestMethod.POST)
	public @ResponseBody WDResponse getOrderList(@RequestBody JsonParamObj query) {
		Map<String, Object> conditions = new HashMap<String, Object>();
		
		//index 小绿点
		Map<String, Object> statusConditions = new HashMap<String, Object>();
		
		if (StringUtils.isEmpty(query)) {
			return ResponseUtil.apiError("1001", "缺少必要参数");
		}
		if (StringUtils.isEmpty(query.getShopId())) {
			return ResponseUtil.apiError("1002", "shopId is null");
		}
		
		if(!StringUtils.isEmpty(query.getType())){//type
			logger.info("query.getType():" + query.getType());
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
				detailStatus.add(WDConstant.AFTERSALE_STATUS_CONFIRM_REFUND);
				detailStatus.add(WDConstant.AFTERSALE_STATUS_CONFIRM_RECV);
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
		
		if(!StringUtils.isEmpty(query.getShopId())){
			conditions.put("shopId", query.getShopId());
			statusConditions.put("shopId", query.getShopId());
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
			
			
			PageResults<OrderMaster> pr = orderService.getOrderListByPage(conditions, page);
			Map<String,Object> map = new HashMap<String, Object>();
			int booked = 0;//待付款
			int appraised = 0;//待评价
			int payed = 0;//待发货
			int deliveryed = 0;//待收货
			int refunds = 0;//退款中
			
			
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
	
	
	@RequestMapping(value = "/deliverGoods", method = RequestMethod.POST)
	public @ResponseBody WDResponse deliverGoods(@RequestBody JsonParamObj cart) {
		
		if (StringUtils.isEmpty(cart)) {
			return ResponseUtil.apiError("1001", "缺少必要参数");
		}
		if (StringUtils.isEmpty(cart.getExpressNumber())) {
			return ResponseUtil.apiError("1002", "expressNumber is null");
		}
		if (StringUtils.isEmpty(cart.getExpressCorporation())) {
			return ResponseUtil.apiError("1003", "expressCorporation is null");
		}
		
		if (StringUtils.isEmpty(cart.getOrderMasterId())) {
			return ResponseUtil.apiError("1004", "orderMasterId is null");
		}
		
		
		
		try {
			orderService.deliverGoods(cart.getOrderMasterId(), cart.getExpressNumber(), cart.getExpressCorporation());
			logger.info(DELIVERY_SUCCESS_MSG);
			return ResponseUtil.apiSuccess("delivery goods successfully", DELIVERY_SUCCESS_MSG);
		} catch (Exception e) {
			logger.error(DELIVERY_EXCEPTION_MSG , e);
			return ResponseUtil.apiError("1100", "未知错误");
		}
	

	}
	
	
	
}


