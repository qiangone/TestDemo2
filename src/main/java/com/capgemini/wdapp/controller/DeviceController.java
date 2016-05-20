/**
 * CAPGEMINI APPLIANCE CHAINS.
 * Copyright (c) 2015-2015 All Rights Reserved.
 */
package com.capgemini.wdapp.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.capgemini.wdapp.model.Device;
import com.capgemini.wdapp.response.ResponseUtil;
import com.capgemini.wdapp.response.WDResponse;
import com.capgemini.wdapp.service.IDeviceService;
import com.capgemini.wdapp.service.IPushService;
import com.capgemini.wdapp.vendor.push.PushConstants;
import com.mysql.jdbc.StringUtils;

/**
 * 
 * functional description： Device Controller
 * 
 * @author zhihuang@capgemini.com
 * @created Dec 18, 2015 10:40:17 AM
 */
@Controller
@RequestMapping("/api/device")
public class DeviceController {

	private final Log logger = LogFactory.getLog(DeviceController.class);

	@Autowired
	private IDeviceService deviceService;

	@Autowired
	private IPushService pushService;

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public @ResponseBody WDResponse register(@RequestBody Device device) {
		try {
			device.setStatus(1);
			if (StringUtils.isNullOrEmpty(device.getToken())) {
				return ResponseUtil.apiError("1001",
						"token can't be null or empty");
			}
			deviceService.register(PushConstants.DEVICE_SELLER, device);
			return ResponseUtil
					.apiSuccess(device, "register device successful");
		} catch (Exception e) {
			logger.error("resigter device exception", e);
			return ResponseUtil.apiError("1101", "未知错误");
		}
	}

	@RequestMapping(value = "/unregister", method = RequestMethod.POST)
	public @ResponseBody WDResponse unregister(@RequestBody Device device) {
		try {
			device.setStatus(1);
			if (StringUtils.isNullOrEmpty(device.getToken())) {
				return ResponseUtil.apiError("1001",
						"token can't be null or empty");
			}
			deviceService.unregister(device);
			return ResponseUtil
					.apiSuccess(device, "register device successful");
		} catch (Exception e) {
			logger.error("unregister sms fail", e);
			return ResponseUtil.apiError("1101", "未知错误");
		}
	}
}
