/**
 * CAPGEMINI APPLIANCE CHAINS.
 * Copyright (c) 2015-2015 All Rights Reserved.
 */
package com.capgemini.wdapp.controller;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.capgemini.wdapp.model.Seller;
import com.capgemini.wdapp.model.Sms;
import com.capgemini.wdapp.response.ResponseUtil;
import com.capgemini.wdapp.response.WDResponse;
import com.capgemini.wdapp.service.ISellerService;
import com.capgemini.wdapp.service.ISmsService;
import com.capgemini.wdapp.util.ConfigUtil;
import com.capgemini.wdapp.util.SmsUtil;
import com.capgemini.wdapp.util.ValidateUtil;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

/**
 * 
 * functional description： SMS Controller
 * 
 * @author zhihuang@capgemini.com
 * @created Dec 18, 2015 10:40:17 AM
 */
@Controller
@RequestMapping("/api/sms")
public class SmsController {

	private final Log logger = LogFactory.getLog(SmsController.class);

	@Autowired
	private ISmsService smsService;
	@Autowired
	private ISellerService sellerService;
	private TaobaoClient mTaobaoClient;

	@RequestMapping(value = "/sendVerifyNum", method = RequestMethod.POST)
	public @ResponseBody WDResponse registerSeller(@RequestBody Sms sms) {
		try {
			if (!ValidateUtil.isPhoneNum(sms.getPhoneNumber())) {
				return ResponseUtil.apiError("1001",
						"phone number is not correct");
			}
			if (sms.getType() != SmsUtil.TYPE_REGISTER
					&& sms.getType() != SmsUtil.TYPE_FIND_PASSWORD
					&& sms.getType() != SmsUtil.TYPE_UPDATE_PASSWORD) {
				return ResponseUtil.apiError("1002", "type is not correct");
			}
			if (mTaobaoClient == null) {
				mTaobaoClient = new DefaultTaobaoClient(SmsUtil.URL,
						SmsUtil.APPKEY, SmsUtil.APPSECRET);
			}

			if (sms.getType() == SmsUtil.TYPE_REGISTER) {// register
				// Sms esms = smsService.getSmsByPhoneNumberAndOrType(sms);
				Seller query = new Seller();
				query.setPhoneNumber(sms.getPhoneNumber());
				List<Seller> list = sellerService.getSeller(query);
				if (list != null && list.size() > 0) {
					return ResponseUtil.apiError("1003",
							"phone number already exist!");
				}

			}

			String securityCode = SmsUtil.createSecurityCode();
			
			
			String tag = ConfigUtil.getPropertyValue("network.tag");
			if(StringUtils.isNotEmpty(tag) && tag.equals("1")){
				AlibabaAliqinFcSmsNumSendRequest req = SmsUtil.buildSmsRequest(
						sms.getPhoneNumber(), securityCode, sms.getType());
				try {
					AlibabaAliqinFcSmsNumSendResponse rsp = mTaobaoClient
							.execute(req);
					if (rsp.getResult().getErrCode().equals("0")
							&& rsp.getResult().getSuccess()) {
						sms.setCreatedTime(new Date());
						sms.setSecurityCode(securityCode);
						Sms existSms = smsService.getSmsByPhoneNumberAndOrType(sms);
						if (existSms != null) {
							existSms.setCreatedTime(sms.getCreatedTime());
							existSms.setSecurityCode(sms.getSecurityCode());
							smsService.updateSms(existSms);
						} else {
							smsService.saveSms(sms);
						}
					} else {
						return ResponseUtil.apiError(rsp.getResult().getErrCode(),
								rsp.getResult().getMsg());
					}
				} catch (Exception e) {
					logger.error("send sms fail", e);
					return ResponseUtil.apiError("1004", "send sms fail");
				}
			}else{//just for testing
				// ////////test begin
				 sms.setCreatedTime(new Date());
				 sms.setSecurityCode(securityCode);
				 Sms existSms = smsService.getSmsByPhoneNumberAndOrType(sms);
				 if (existSms != null) {
				 existSms.setCreatedTime(sms.getCreatedTime());
				 existSms.setSecurityCode(sms.getSecurityCode());
				 smsService.updateSms(existSms);
				 } else {
				 smsService.saveSms(sms);
				 }//////test end
			}

			return ResponseUtil.apiSuccess(sms, "send sms successful");
		} catch (Exception e) {
			logger.error("send sms exception", e);
			return ResponseUtil.apiError("1101", "未知错误");
		}
	}

}
