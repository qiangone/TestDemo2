/**
 * CAPGEMINI APPLIANCE CHAINS.
 * Copyright (c) 2015-2015 All Rights Reserved.
 */
package com.capgemini.wdapp.buyer.controller;

import java.util.Date;
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
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.capgemini.wdapp.common.WDConstant;
import com.capgemini.wdapp.model.AfterSale;
import com.capgemini.wdapp.model.OrderMaster;
import com.capgemini.wdapp.response.ResponseUtil;
import com.capgemini.wdapp.response.WDResponse;
import com.capgemini.wdapp.service.IAfterSaleService;
import com.capgemini.wdapp.service.IOrderService;
import com.capgemini.wdapp.util.ConfigUtil;
import com.capgemini.wdapp.util.DateUtil;
import com.capgemini.wdapp.util.ImageUtil;

/**
 * 
 * functional description： Seller Controller
 * 
 * @author zhihuang@capgemini.com
 * @created Dec 18, 2015 10:40:17 AM
 */
@Controller
@RequestMapping("/api/user/aftersale")
public class AfterSaleController {

	private final Log logger = LogFactory.getLog(AfterSaleController.class);

	@Autowired
	private IAfterSaleService afterSaleService;
	
	@Autowired
	private IOrderService orderService;

	private static final String APPLYAFTERSALE_SUCCESS_MSG = "APPLYAFTERSALE successfully";
	private static final String APPLYAFTERSALE_EXCEPTION_MSG = "APPLYAFTERSALE exception";
	
	private static final String GETAPPEALSUCCESS_MSG = "GETAPPEAL successfully";
	private static final String GETAPPEAL_EXCEPTION_MSG = "GETAPPEAL exception";
	
	private static final String FILLLOGISTICS_SUCCESS_MSG = "FILLLOGISTICS successfully";
	private static final String FILLLOGISTICS_EXCEPTION_MSG = "FILLLOGISTICS exception";
	
	private static final String SUBMITAPPEAL_SUCCESS_MSG = "SUBMITAPPEAL successfully";
	private static final String SUBMITAPPEAL_EXCEPTION_MSG = "SUBMITAPPEAL exception";
	

	@RequestMapping(value = "/applyAfterSale", method = RequestMethod.POST)
	public @ResponseBody WDResponse applyAfterSale(
			@RequestParam(required = true) Integer orderDetailId,
			@RequestParam(required = true) Integer type,
			@RequestParam(required = false) Integer isReceived,
			@RequestParam(required = true) String reason,
			@RequestParam(required = false) String detail,
			@RequestParam(required = true) Float money,
			@RequestParam(value = "attach", required = false) CommonsMultipartFile attach) {

		try {

			if (StringUtils.isEmpty(orderDetailId)) {
				return ResponseUtil.apiError("1002", "orderDetailId is null!");
			}
			if (StringUtils.isEmpty(type)) {
				return ResponseUtil.apiError("1003", "type Id is null!");
			}
			if (StringUtils.isEmpty(reason)) {
				return ResponseUtil.apiError("1004", "reason is null!");
			}
			if (StringUtils.isEmpty(money)) {
				return ResponseUtil.apiError("1005", "money is null!");
			}
			
			 String confirmDay = ConfigUtil.getPropertyValue("confirmDay");
			 String appraisalDay = ConfigUtil.getPropertyValue("appraisalDay");
			 String mayApplyRefundAfterPayDay = ConfigUtil.getPropertyValue("mayApplyRefundAfterPayDay");
			 
			 OrderMaster master= orderService.getOrderByDetailId(orderDetailId);
			 if(master != null){
				 int status = master.getStatus();
				 Date createTime = master.getCreateTime();
				 Date deliveryTime = master.getDeliveryTime();
				 Date dealTime = master.getDealTime();
					
				 if(status == WDConstant.ORDERMASTER_STATUS_PAYED){
					 
					 if(DateUtil.getDiffDay(createTime, new Date())>Integer.parseInt(mayApplyRefundAfterPayDay)){
						 return ResponseUtil.apiError("1006", "refund must be in "+ mayApplyRefundAfterPayDay + " days after payed!");
					 }
					 
				 }
				 
				 if(status == WDConstant.ORDERMASTER_STATUS_DELIVERYED){
					 
					 if(DateUtil.getDiffDay(deliveryTime, new Date())>Integer.parseInt(confirmDay)){
						 return ResponseUtil.apiError("1006", "refund must be in "+ confirmDay + " days after delivery!");
					 }
					 
				 }
				 
				 if(status == WDConstant.ORDERMASTER_STATUS_CONFIRMED){
					 
					 if(DateUtil.getDiffDay(dealTime, new Date())>Integer.parseInt(appraisalDay)){
						 return ResponseUtil.apiError("1006", "refund must be in "+ appraisalDay + " days after confirm!");
					 }
					 
				 }
			 }

			AfterSale as = new AfterSale();
			as.setOrderMasterId(master.getId());

			if (attach != null) {
				String ret = uploadAfterSaleImage(attach, orderDetailId);
				as.setAttach(ret);
			}
			as.setOrderDetailId(orderDetailId);
			as.setType(type);
			as.setStatus(type);	
			as.setIsReceived(isReceived);
			as.setReason(reason);
			as.setDetail(detail);
			as.setMoney(money);
			as.setApplyTime(new Date());

			afterSaleService.addAfterSale(as);

		} catch (Exception e) {
			logger.error(APPLYAFTERSALE_EXCEPTION_MSG, e);
			return ResponseUtil.apiError("1100", "未知错误");
		}

		return ResponseUtil.apiSuccess("APPLYAFTERSALE successfully",
				APPLYAFTERSALE_SUCCESS_MSG);
	}

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
	
	@RequestMapping(value = "/getRejectReason", method = RequestMethod.GET)
	public @ResponseBody WDResponse getRejectReason(@RequestParam(required = false) Integer type) {
		try {
			if (StringUtils.isEmpty(type)) {
				return ResponseUtil.apiError("1002", "type is null!");
			}
			List<Map> list = afterSaleService.getAllRejectReasonByType(type);
			return ResponseUtil.apiSuccess(list, "getRejectReason successful");

		} catch (Exception e) {
			logger.error(APPLYAFTERSALE_EXCEPTION_MSG, e);
			return ResponseUtil.apiError("1100", "未知错误");
		}

		

	}
	
	
	@RequestMapping(value = "/fillLogistics", method = RequestMethod.POST)
	public @ResponseBody WDResponse fillLogistics(@RequestBody AfterSale as) {
		
		if (as == null) {
			return ResponseUtil.apiError("1001", "Missing parameter!");
		}
		
		if (StringUtils.isEmpty(as.getOrderDetailId())) {
			return ResponseUtil.apiError("1002", "orderDetailId is null");
		}
		if (StringUtils.isEmpty(as.getExpressNumber())) {
			return ResponseUtil.apiError("1003", "expressNumber is null");
		}
		if (StringUtils.isEmpty(as.getExpressCompany())) {
			return ResponseUtil.apiError("1004", "expressCompany is null");
		}
				
		try {
			afterSaleService.fillLogistics(as);
			
			logger.info(FILLLOGISTICS_SUCCESS_MSG);
			return ResponseUtil.apiSuccess(FILLLOGISTICS_SUCCESS_MSG, FILLLOGISTICS_SUCCESS_MSG);
		} catch (Exception e) {
			logger.error(FILLLOGISTICS_EXCEPTION_MSG , e);
			return ResponseUtil.apiError("1100", "未知错误");
		}
	

	}
	
	@RequestMapping(value = "/submitAppeal", method = RequestMethod.POST)
	public @ResponseBody WDResponse submitAppeal(@RequestBody AfterSale as) {
		
		if (as == null) {
			return ResponseUtil.apiError("1001", "Missing parameter!");
		}
		
		if (StringUtils.isEmpty(as.getOrderDetailId())) {
			return ResponseUtil.apiError("1002", "orderDetailId is null");
		}
		if (StringUtils.isEmpty(as.getAppealTitle())) {
			return ResponseUtil.apiError("1003", "appealTitle is null");
		}
		if (StringUtils.isEmpty(as.getAppealContent())) {
			return ResponseUtil.apiError("1004", "appealContent is null");
		}
				
		try {
			afterSaleService.submitAppeal(as);
			
			logger.info(SUBMITAPPEAL_SUCCESS_MSG);
			return ResponseUtil.apiSuccess(SUBMITAPPEAL_SUCCESS_MSG, SUBMITAPPEAL_SUCCESS_MSG);
		} catch (Exception e) {
			logger.error(SUBMITAPPEAL_EXCEPTION_MSG , e);
			return ResponseUtil.apiError("1100", "未知错误");
		}
	

	}
	
	@RequestMapping(value = "/getAppeal", method = RequestMethod.GET)
	public @ResponseBody WDResponse getAppeal(
			@RequestParam(required = false) Integer orderDetailId) {
		try {
			if (StringUtils.isEmpty(orderDetailId)) {
				return ResponseUtil.apiError("1002", "orderDetailId is null!");
			}

			AfterSale as = afterSaleService.getAfterSaleByDetailId(orderDetailId);
			return ResponseUtil.apiSuccess(as, GETAPPEALSUCCESS_MSG);

		} catch (Exception e) {
			logger.error(GETAPPEAL_EXCEPTION_MSG, e);
			return ResponseUtil.apiError("1100", "未知错误");
		}

		

	}
	

	private String uploadAfterSaleImage(CommonsMultipartFile logoImg,
			Integer orderDetailId) {

		int i = logoImg.getOriginalFilename().indexOf(".");
		if (i == -1) {
			return null;
		}
		String imgType = logoImg.getOriginalFilename().substring(i);
		String filename = "afterSale_" + orderDetailId + imgType;

		String relativePath = WDConstant.ORDER_AFTER_SALE_IMAGE + filename;

		// upload image
		ImageUtil.uploadStaticResource(logoImg, relativePath);

		return relativePath;

	}

}
