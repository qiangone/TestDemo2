/**
 * CAPGEMINI APPLIANCE CHAINS.
 * Copyright (c) 2015-2015 All Rights Reserved.
 */
package com.capgemini.wdapp.controller;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.capgemini.wdapp.model.Sms;
import com.capgemini.wdapp.model.User;
import com.capgemini.wdapp.response.ResponseUtil;
import com.capgemini.wdapp.response.WDResponse;
import com.capgemini.wdapp.service.ISmsService;
import com.capgemini.wdapp.service.IUserService;
import com.capgemini.wdapp.util.SmsUtil;
import com.capgemini.wdapp.util.ValidateUtil;
import com.mysql.jdbc.StringUtils;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

/**
 * 
 * functional description： Seller Controller
 * @author  zhihuang@capgemini.com
 * @created Dec 18, 2015 10:40:17 AM
 */
@Controller
@RequestMapping("/api/user")
public class UserController {
    
    private final Log logger = LogFactory.getLog(UserController.class);
    
    @Autowired
    private IUserService userService;
    
    @RequestMapping(value = "/getUserInfo", method = RequestMethod.GET)
    public @ResponseBody WDResponse getUserInfo(@RequestParam String openId) {
    	if (StringUtils.isNullOrEmpty(openId)){
    		return ResponseUtil.apiError("1001", "openid can't be null or empty");
    	}
    	User user = userService.getUserInfo(openId);
    	if (user != null) {
    		return ResponseUtil.apiSuccess(user, "get user successful");
    	} else {
    		return ResponseUtil.apiError("1002", "can't get the user,please check the openid");
    	}
    }
}
