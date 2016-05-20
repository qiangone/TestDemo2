/**
 * CAPGEMINI APPLIANCE CHAINS.
 * Copyright (c) 2015-2015 All Rights Reserved.
 */
package com.capgemini.wdapp.controller;

import java.util.ArrayList;
import java.util.List;

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

import com.capgemini.wdapp.model.Shop;
import com.capgemini.wdapp.response.ResponseUtil;
import com.capgemini.wdapp.response.WDResponse;
import com.capgemini.wdapp.service.IPushService;
import com.capgemini.wdapp.service.IShopService;
import com.capgemini.wdapp.util.ConfigUtil;
import com.capgemini.wdapp.vendor.wechat.Button;
import com.capgemini.wdapp.vendor.wechat.Buttons;
import com.capgemini.wdapp.vendor.wechat.Industry;
import com.capgemini.wdapp.vendor.wechat.WeChatApiUtil;
import com.capgemini.wdapp.vendor.wechat.WechatConstants;

/**
 * 
 * functional description： Seller Controller
 * 
 * @created Dec 18, 2015 10:40:17 AM
 */
@Controller
@RequestMapping("/api/shopsetting")
public class ShopSettingController {
	@Autowired
	private IShopService shopService;
	@Autowired
	private IPushService pushService;
	private final Log logger = LogFactory.getLog(ShopSettingController.class);

	@RequestMapping(value = "/wechatbind", method = RequestMethod.POST)
	public @ResponseBody WDResponse wechatbind(@RequestBody Shop entity) {
		try {
			if (StringUtils.isEmpty(entity.getSellerId())) {
				return ResponseUtil.apiError("sellerId不能为空()", "1001");
			}
			if (StringUtils.isEmpty(entity.getWeChatAppKey())
					|| StringUtils.isEmpty(entity.getWeChatAppSecret())) {
				return ResponseUtil.apiError(
						"weChatAppKey和weChatAppSecret不能为空", "1002");
			}
			Shop existShop = shopService.getShopByWeChatAppKey(entity.getWeChatAppKey());
			Shop shop = shopService.getShopBySellerId(entity.getSellerId());
			if (shop != null) {
				entity.setId(shop.getId());
			}
			if (existShop != null) {
				return ResponseUtil.apiError("1003", "该公众号已经被其他店铺绑定");
			}
			int res = shopService.updateShopWechatKey(entity);
			if (res == 0) {
				shop.setWeChatAppKey(entity.getWeChatAppKey());
				shop.setWeChatAppSecret(entity.getWeChatAppSecret());
				pushService.addWechatChannelForShop(entity.getId());
				return ResponseUtil.apiSuccess(shop, "successful");
			} else {
				return ResponseUtil.apiError("fail", "1001");
			}
		} catch (Exception e) {
			logger.error("bind wechat exception", e);
			return ResponseUtil.apiError("1101", "未知错误");
		}
	}

	@RequestMapping(value = "/wechatunbind", method = RequestMethod.POST)
	public @ResponseBody WDResponse wechatunbind(@RequestBody Shop entity) {
		try {
			Shop shop = shopService.getShopBySellerId(entity.getSellerId());
			if (shop != null) {
				shop.setWeChatAppKey("");
				shop.setWeChatAppSecret("");
				shopService.unbindWechatKey(shop);
				return ResponseUtil.apiSuccess(shop, "successful");
			}
			return ResponseUtil.apiError("fail", "1001");
		} catch (Exception e) {
			logger.error("unbind wechat exception", e);
			return ResponseUtil.apiError("1101", "未知错误");
		}
	}

}
