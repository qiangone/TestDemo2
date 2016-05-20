/**
 * CAPGEMINI APPLIANCE CHAINS.
 * Copyright (c) 2015-2015 All Rights Reserved.
 */
package com.capgemini.wdapp.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.capgemini.wdapp.common.JsonParamObj;
import com.capgemini.wdapp.model.Seller;
import com.capgemini.wdapp.model.Sms;
import com.capgemini.wdapp.response.ResponseUtil;
import com.capgemini.wdapp.response.WDResponse;
import com.capgemini.wdapp.service.ISellerService;
import com.capgemini.wdapp.service.ISmsService;

/**
 * 
 * functional description： Seller Controller
 * 
 * @author zhihuang@capgemini.com
 * @created Dec 18, 2015 10:40:17 AM
 */
@Controller
@RequestMapping("/api/password")
public class PasswordController {

	private final Log logger = LogFactory.getLog(PasswordController.class);

	//@Autowired
	//private IFindPwdService findPwdService;

	@Autowired
	private ISellerService sellerService;
	
    @Autowired
    private ISmsService smsService;
	
	private static final String FINDPWD_SUCCESS_MSG = "find password successfully";
	private static final String FINDPWD_EXCEPTION_MSG = "find password exception";
	private static final String UPDATE_SUCCESS_MSG = "update password successfully";
	private static final String UPDATE_EXCEPTION_MSG = "update password exception";
	

//	@RequestMapping(value = "/getVerifyNum", method = RequestMethod.POST)
//	public @ResponseBody WDResponse getVerifyNum(@RequestBody Sms sms) {
//		if (sms == null) {
//			return ResponseUtil.apiError("1001", "Missing parameter!");
//		}
//
//		if (StringUtils.isEmpty(sms.getPhoneNumber())) {
//			return ResponseUtil.apiError("1002", "phone number is null!");
//		}
//		
//		if (sms.getType() != SmsUtil.TYPE_FIND_PASSWORD) {
//			return ResponseUtil.apiError("1003", "type is not correct");
//		}
//
//		try {
//			
//			smsService.getSmsVarifyNum(sms);
//			
//			logger.info(GETVERY_SUCCESS_MSG);
//			return ResponseUtil.apiSuccess(sms, GETVERY_SUCCESS_MSG);
//		} catch (Exception e) {
//			logger.error(GETVERY_EXCEPTION_MSG , e);
//			return ResponseUtil.apiError("1100", "未知错误");
//		}
//
//		
//
//	}

	@RequestMapping(value = "/findPassword", method = RequestMethod.POST)
	public @ResponseBody WDResponse findPassword(@RequestBody Seller sel) {
		if (sel == null) {
			return ResponseUtil.apiError("1001", "Missing parameter!");
		}

		if (StringUtils.isEmpty(sel.getPhoneNumber())) {
			return ResponseUtil.apiError("1002", "phone number is null!");
		}

		if (StringUtils.isEmpty(sel.getPassword())) {
			return ResponseUtil.apiError("1003", "password is null!");
		}

		if (StringUtils.isEmpty(sel.getVarifyNumber())) {
			return ResponseUtil.apiError("1004", "verify num is null!");
		}
		
		Sms sms = new Sms();
		sms.setPhoneNumber(sel.getPhoneNumber());
		sms.setType(2);
		Sms ret = smsService.getSmsByPhoneNumberAndOrType(sms);
		if(ret == null){
			return ResponseUtil.apiError("1006", "get verify num exception");
		}else{
			String verifyNum = ret.getSecurityCode();
			if(!verifyNum.equalsIgnoreCase(sel.getVarifyNumber()+"")){
				return ResponseUtil.apiError("1005", "verify num is not correct");
			}
			
		}
		

		try {
			Seller seller = sellerService.getSellerByPhoneNum(sel
					.getPhoneNumber());
			seller.setPassword(sel.getPassword());
			sellerService.UpdateSeller(seller);
			logger.info(FINDPWD_SUCCESS_MSG);
			return ResponseUtil.apiSuccess(seller, FINDPWD_SUCCESS_MSG);
		} catch (Exception e) {
			logger.error(FINDPWD_EXCEPTION_MSG , e);
			return ResponseUtil.apiError("1100", "未知错误");
		}

		

	}
	
	@RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
	public @ResponseBody WDResponse updatePassword(@RequestBody JsonParamObj json) {
		if (json == null) {
			return ResponseUtil.apiError("1001", "Missing parameter!");
		}

		if (StringUtils.isEmpty(json.getSellerId())) {
			return ResponseUtil.apiError("1002", "seller id is null!");
		}

		if (StringUtils.isEmpty(json.getVerifyNumber())) {
			return ResponseUtil.apiError("1003", "verify num is null!");
		}

		if (StringUtils.isEmpty(json.getOldPassword())) {
			return ResponseUtil.apiError("1004", "old passwordis null!");
		}
		if (StringUtils.isEmpty(json.getNewPassword())) {
			return ResponseUtil.apiError("1005", "new passwordis null!");
		}
		
		Seller seller = sellerService.getSellerById(json.getSellerId());
		if(seller == null){
			return ResponseUtil.apiError("1006", "seller not exist");
		}
		
		// verify num
		Sms sms = new Sms();
		sms.setPhoneNumber(seller.getPhoneNumber());
		sms.setType(3);//update pwd
		Sms ret = smsService.getSmsByPhoneNumberAndOrType(sms);
		if(ret == null){
			return ResponseUtil.apiError("1007", "can not get verify num");
		}else{
			String verifyNum = ret.getSecurityCode();
			if(!verifyNum.equalsIgnoreCase(json.getVerifyNumber())){
				return ResponseUtil.apiError("1008", "verify num is not correct");
			}
			
		}
		
		
		// old password
		if(!json.getOldPassword().equalsIgnoreCase(seller.getPassword())){
			return ResponseUtil.apiError("1009", "password is not correct");
		}
		

		try {
			
			seller.setPassword(json.getNewPassword());
			sellerService.UpdateSeller(seller);
			logger.info(UPDATE_SUCCESS_MSG);
			return ResponseUtil.apiSuccess(seller, UPDATE_SUCCESS_MSG);
		} catch (Exception e) {
			logger.error(UPDATE_EXCEPTION_MSG , e);
			return ResponseUtil.apiError("1100", "未知错误");
		}

		

	}

}
