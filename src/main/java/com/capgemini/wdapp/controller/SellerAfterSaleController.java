/**
 * CAPGEMINI APPLIANCE CHAINS.
 * Copyright (c) 2015-2015 All Rights Reserved.
 */
package com.capgemini.wdapp.controller;

import java.util.Date;
import java.util.HashMap;
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
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.capgemini.wdapp.common.WDConstant;
import com.capgemini.wdapp.model.Address;
import com.capgemini.wdapp.model.AfterSale;
import com.capgemini.wdapp.model.Shop;
import com.capgemini.wdapp.response.ResponseUtil;
import com.capgemini.wdapp.response.WDResponse;
import com.capgemini.wdapp.service.IAfterSaleService;
import com.capgemini.wdapp.util.ImageUtil;

/**
 * 
 * functional description： Seller Controller
 * 
 * @author zhihuang@capgemini.com
 * @created Dec 18, 2015 10:40:17 AM
 */
@Controller
@RequestMapping("/api/seller/aftersale")
public class SellerAfterSaleController {

	private final Log logger = LogFactory.getLog(SellerAfterSaleController.class);

	@Autowired
	private IAfterSaleService afterSaleService;

	private static final String APPLYAFTERSALE_SUCCESS_MSG = "APPLYAFTERSALE successfully";
	private static final String APPLYAFTERSALE_EXCEPTION_MSG = "APPLYAFTERSALE exception";
	
	private static final String FILLLOGISTICS_SUCCESS_MSG = "FILLLOGISTICS successfully";
	private static final String FILLLOGISTICS_EXCEPTION_MSG = "FILLLOGISTICS exception";
	
	private static final String HANDLERFUNDS_SUCCESS_MSG = "HANDLERFUNDS successfully";
	private static final String HANDLERFUNDS_EXCEPTION_MSG = "HANDLERFUNDS exception";

	private static final String CONFIRMRECVANDREFUNDS_SUCCESS_MSG = "CONFIRMRECVANDREFUNDS successfully";
	private static final String CONFIRMRECVANDREFUNDS_EXCEPTION_MSG = "CONFIRMRECVANDREFUNDS exception";

	@RequestMapping(value = "/getAfterSale", method = RequestMethod.GET)
	public @ResponseBody WDResponse getAfterSale(
			@RequestParam(required = false) Integer orderDetailId) {
		try {
			if (StringUtils.isEmpty(orderDetailId)) {
				return ResponseUtil.apiError("1002", "orderDetailId is null!");
			}

			AfterSale as = afterSaleService.getAfterSaleByDetailId(orderDetailId);
			return ResponseUtil.apiSuccess(as, "getAfterSale successful");

		} catch (Exception e) {
			logger.error(APPLYAFTERSALE_EXCEPTION_MSG, e);
			return ResponseUtil.apiError("1100", "未知错误");
		}

		

	}
	
	@RequestMapping(value = "/handleRefunds", method = RequestMethod.POST)
	public @ResponseBody WDResponse handleRefunds(@RequestBody AfterSale as) {
		
		if (as == null) {
			return ResponseUtil.apiError("1001", "Missing parameter!");
		}
		
		
		
		if (StringUtils.isEmpty(as.getHandleResult())) {
			return ResponseUtil.apiError("1002", "handleResult is null");
		}
//		if (StringUtils.isEmpty(as.getHandleReason())) {
//			return ResponseUtil.apiError("1003", "handleReason is null");
//		}
		if (StringUtils.isEmpty(as.getOrderDetailId())) {
			return ResponseUtil.apiError("1004", "orderDetailId is null");
		}
		
				
		try {
			afterSaleService.handleRefunds(as);
			
			logger.info(HANDLERFUNDS_SUCCESS_MSG);
			return ResponseUtil.apiSuccess(HANDLERFUNDS_SUCCESS_MSG, HANDLERFUNDS_SUCCESS_MSG);
		} catch (Exception e) {
			logger.error(HANDLERFUNDS_EXCEPTION_MSG , e);
			return ResponseUtil.apiError("1100", "未知错误");
		}
	

	}
	
	@RequestMapping(value = "/confirmRecvAndRefunds", method = RequestMethod.POST)
	public @ResponseBody WDResponse confirmRecvAndRefunds(@RequestBody AfterSale as) {
		
		if (as == null) {
			return ResponseUtil.apiError("1001", "Missing parameter!");
		}
		
		if (StringUtils.isEmpty(as.getOrderDetailId())) {
			return ResponseUtil.apiError("1002", "orderDetailId is null");
		}
		
				
		try {
			afterSaleService.confirmRecvAndRefunds(as);
			
			logger.info(CONFIRMRECVANDREFUNDS_SUCCESS_MSG);
			return ResponseUtil.apiSuccess(CONFIRMRECVANDREFUNDS_SUCCESS_MSG, CONFIRMRECVANDREFUNDS_SUCCESS_MSG);
		} catch (Exception e) {
			logger.error(CONFIRMRECVANDREFUNDS_EXCEPTION_MSG , e);
			return ResponseUtil.apiError("1100", "未知错误");
		}
	

	}

	
	

}
