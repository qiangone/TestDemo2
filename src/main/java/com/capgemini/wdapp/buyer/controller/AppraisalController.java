/**
 * CAPGEMINI APPLIANCE CHAINS.
 * Copyright (c) 2015-2015 All Rights Reserved.
 */
package com.capgemini.wdapp.buyer.controller;

import java.util.ArrayList;
import java.util.Date;
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
import com.capgemini.wdapp.model.Appraisal;
import com.capgemini.wdapp.model.OrderMaster;
import com.capgemini.wdapp.model.PageResults;
import com.capgemini.wdapp.model.Pagination;
import com.capgemini.wdapp.response.ResponseUtil;
import com.capgemini.wdapp.response.WDResponse;
import com.capgemini.wdapp.service.IAppraisalService;

/**
 * 
 * functional description： Seller Controller
 * 
 * @author zhihuang@capgemini.com
 * @created Dec 18, 2015 10:40:17 AM
 */
@Controller
@RequestMapping("/api/user/appraisal")
public class AppraisalController {

	private final Log logger = LogFactory.getLog(AppraisalController.class);

	@Autowired
	private IAppraisalService appraisalService;
	
	

	private static final String GETAPPRAISALLIST_SUCCESS_MSG = "GETAPPRAISAL successfully";
	private static final String GETAPPRAISALLIST_EXCEPTION_MSG = "GETAPPRAISAL exception";
	private static final String ADDAPPRAISAL_SUCCESS_MSG = "ADDAPPRAISAL successfully";
	private static final String ADDAPPRAISAL_EXCEPTION_MSG = "ADDAPPRAISAL exception";
	private static final String CANCELORDER_SUCCESS_MSG = "cancel order successfully";
	private static final String CANCELORDER_EXCEPTION_MSG = "cancel order exception";
	
	private static final String CONFIRMRECEIVE_SUCCESS_MSG = "confirm receviing successfully";
	private static final String CONFIRMRECEIVE_EXCEPTION_MSG = "confirm receviing exception";
	
	private static final String GETORDER_SUCCESS_MSG = "get order successfully";
	private static final String GETORDER_EXCEPTION_MSG = "get order exception";
	

	
	@RequestMapping(value = "/addAppraisal", method = RequestMethod.POST)
	public @ResponseBody WDResponse makeOrder(@RequestBody JsonParamObj cart) {
		
		if (StringUtils.isEmpty(cart)) {
			return ResponseUtil.apiError("1001", "缺少必要参数");
		}
//		if (StringUtils.isEmpty(cart.getOrderDetailId())) {
//			return ResponseUtil.apiError("1002", "orderDetailId is null");
//		}
//		
//		if (StringUtils.isEmpty(cart.getLevel())) {
//			return ResponseUtil.apiError("1003", "level is null");
//		}
//		if (StringUtils.isEmpty(cart.getContents())) {
//			return ResponseUtil.apiError("1004", "Contents is null");
//		}
//		if (StringUtils.isEmpty(cart.getUserId())) {
//			return ResponseUtil.apiError("1005", "userId is null");
//		}
//		if (StringUtils.isEmpty(cart.getGoodsId())) {
//			return ResponseUtil.apiError("1006", "goodsId is null");
//		}
		try {
			List<Appraisal> appList = cart.getAppraisalList();
			if(appList != null && appList.size()>0){
				for(Appraisal app: appList){
					app.setCreateTime(new Date());
				}
				appraisalService.addAppraisal(appList);
			}
			
			logger.info(ADDAPPRAISAL_SUCCESS_MSG);
			return ResponseUtil.apiSuccess("ADDAPPRAISAL successfully", ADDAPPRAISAL_SUCCESS_MSG);
		} catch (Exception e) {
			logger.error(ADDAPPRAISAL_EXCEPTION_MSG , e);
			return ResponseUtil.apiError("1100", "未知错误");
		}
	

	}
	
	
	@RequestMapping(value = "/getAppraisal", method = RequestMethod.POST)
	public @ResponseBody WDResponse getAppraisal(@RequestBody JsonParamObj query) {
		
		if (StringUtils.isEmpty(query)) {
			return ResponseUtil.apiError("1001", "缺少必要参数");
		}
		if (StringUtils.isEmpty(query.getGoodsId())) {
			return ResponseUtil.apiError("1002", "goodsId is null");
		}
		
		Integer currentPage = query.getCurrentPage();
		Integer pageSize = query.getPageSize();
		
		try {
			Pagination page = new Pagination();
			if(currentPage != null){
				page.setCurrentPage(currentPage);
			}
			if(pageSize != null){
				page.setPageSize(pageSize);
			}
			
			Map<String, Object> conditions = new HashMap<String, Object>();
			conditions.put("goodsId", query.getGoodsId());
			PageResults<Appraisal> result = appraisalService.getAppraisalListByPage(conditions, page);
			
			logger.info(GETAPPRAISALLIST_SUCCESS_MSG);
			return ResponseUtil.apiSuccess(result, GETAPPRAISALLIST_SUCCESS_MSG);
		} catch (Exception e) {
			logger.error(GETAPPRAISALLIST_EXCEPTION_MSG , e);
			return ResponseUtil.apiError("1100", "未知错误");
		}
	

	}
	
	
	
	
	
}


