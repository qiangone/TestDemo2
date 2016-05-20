/**
 * CAPGEMINI APPLIANCE CHAINS.
 * Copyright (c) 2015-2015 All Rights Reserved.
 */
package com.capgemini.wdapp.controller;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.capgemini.wdapp.common.WDConstant;
import com.capgemini.wdapp.model.Device;
import com.capgemini.wdapp.model.Shop;
import com.capgemini.wdapp.model.User;
import com.capgemini.wdapp.response.ResponseUtil;
import com.capgemini.wdapp.response.WDResponse;
import com.capgemini.wdapp.service.IDeviceService;
import com.capgemini.wdapp.service.IPushService;
import com.capgemini.wdapp.service.IShopService;
import com.capgemini.wdapp.service.IUserService;
import com.capgemini.wdapp.util.ConfigUtil;
import com.capgemini.wdapp.vendor.push.PushConstants;
import com.capgemini.wdapp.vendor.wechat.WeChatApiUtil;
import com.capgemini.wdapp.vendor.wechat.WechatConstants;

/**
 * 
 * functional description： Seller Controller
 * @author  perry.hu@capgemini.com
 * @created Dec 18, 2015 10:40:17 AM
 */
@Controller
@RequestMapping("/api/wechat")
public class WechatController {
    
    private final Log logger = LogFactory.getLog(WechatController.class);
    
    @Autowired
    private IUserService userService;
    
    @Autowired
    private IDeviceService deviceService;
    @Autowired
    private IPushService pushService;
    @Autowired
    private IShopService shopService;
    
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public void index(HttpServletResponse response,String action, Integer shopid) {
    	Shop shop = shopService.getShopById(shopid);
    	if (shop == null) {
    		return;
    	}
    	Map<String, Object> params = new HashMap<String, Object>();
    	params.put(WechatConstants.ACTION_KEY, action);
    	params.put(WechatConstants.SHOPID_KEY, shopid);
    	
    	String url = WeChatApiUtil.requestToWeixin(shop.getWeChatAppKey(), shop.getWeChatAppSecret(), params);
    	response.setHeader("Location", url);
    	response.setStatus(302);
    }
    @RequestMapping(value = "/callback", method = RequestMethod.GET)
    public void callback(String code,HttpServletResponse response, String action, Integer shopid) {
    	Shop shop = shopService.getShopById(shopid);
    	if (shop == null) {
    		return;
    	}
    	JSONObject accessToken = WeChatApiUtil.getWebAccessTokenAndOpenID(shop.getWeChatAppKey(), shop.getWeChatAppSecret(),code);
    	String accessTokenStr = accessToken.getString("access_token");
    	String openid = accessToken.getString(WechatConstants.OPENID);
    	JSONObject userInfo = WeChatApiUtil.getUserInfo(accessTokenStr,openid);
    	User user = userService.getUserInfo(openid);
    	if (user != null ) {
    		user.setLastLoginTime(new Date());
    		user.setIcon(userInfo.getString(WechatConstants.USERINFO_HEADIMGURL));
    		user.setNickname(userInfo.getString(WechatConstants.USERINFO_NICKNAME));
    		userService.updateUser(user);
    	} else {
    		user = new User();
    		user.setRegisterTime(new Date());
    		user.setLastLoginTime(new Date());
    		user.setIcon(userInfo.getString(WechatConstants.USERINFO_HEADIMGURL));
    		user.setNickname(userInfo.getString(WechatConstants.USERINFO_NICKNAME));
    		user.setOpenId(openid);
    		user.setShopId(shopid);
    		userService.saveUser(user);
    		Device device = new Device();
        	device.setDeviceType(Device.DEVICE_TYPE_WECHAT);
        	device.setName(user.getNickname());
        	device.setOsVersion(WechatConstants.WECHAT_OSVERSION);
        	device.setChannelType(PushConstants.CHANNEL_WECHAT);
        	device.setStatus(1);
        	device.setToken(user.getOpenId());
        	device.setUserId(user.getId());
        	device.setMpKey(String.valueOf(shopid));
        	deviceService.register(PushConstants.DEVICE_USER,device);
        	pushService.newUser(user);
    	}
    	
    	String url = ConfigUtil.getPropertyValue("buyer.root") + "view/goods/index.html?openId=" + openid + "&shopId=" + shopid;
    	response.setHeader("Location", url);
    	response.setStatus(302);
    }
    
    @RequestMapping(value = "/tokenauth", method = RequestMethod.GET)
    public @ResponseBody String tokenAuth(String echostr) {
    	
    	return echostr;
    }
}
