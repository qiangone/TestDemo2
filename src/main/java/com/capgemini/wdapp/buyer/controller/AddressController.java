/**
 * CAPGEMINI APPLIANCE CHAINS.
 * Copyright (c) 2015-2015 All Rights Reserved.
 */
package com.capgemini.wdapp.buyer.controller;

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

import com.capgemini.wdapp.model.Address;
import com.capgemini.wdapp.model.Area;
import com.capgemini.wdapp.model.ExpressCompany;
import com.capgemini.wdapp.response.ResponseUtil;
import com.capgemini.wdapp.response.WDResponse;
import com.capgemini.wdapp.service.IAddressService;

/**
 * 
 * functional description： Seller Controller
 * 
 * @author zhihuang@capgemini.com
 * @created Dec 18, 2015 10:40:17 AM
 */
@Controller
@RequestMapping("/api/user/address")
public class AddressController {

	private final Log logger = LogFactory.getLog(AddressController.class);

	@Autowired
	private IAddressService addrService;
	
	

	private static final String ADDADDR_SUCCESS_MSG = "add address successfully";
	private static final String ADDADDR_EXCEPTION_MSG = "add address exception";
	private static final String UPDATEADDR_SUCCESS_MSG = "UPDATEADDR successfully";
	private static final String UPDATEADDR_EXCEPTION_MSG = "UPDATEADDR exception";
	
	
	private static final String GETADDR_SUCCESS_MSG = "get address successfully";
	private static final String GETADDR_EXCEPTION_MSG = "get address exception";
	
	private static final String SETTODEFAULTADDRESS_SUCCESS_MSG = "SETTODEFAULTADDRESS successfully";
	private static final String SETTODEFAULTADDRESS_EXCEPTION_MSG = "SETTODEFAULTADDRESS exception";
	
	private static final String GETDEFAULTADDR_SUCCESS_MSG = "GETDEFAULTADDR successfully";
	private static final String GETDEFAULTADDR_EXCEPTION_MSG = "GETDEFAULTADDR exception";
	
	private static final String GETALLPROVINCE_SUCCESS_MSG = "get all province successfully";
	private static final String GETALLPROVINCE_EXCEPTION_MSG = "get all province exception";
	
	private static final String GETSUBAREABYID_SUCCESS_MSG = "getSubAreaById successfully";
	private static final String GETSUBAREABYID_EXCEPTION_MSG = "getSubAreaById exception";
	
	

	
	@RequestMapping(value = "/addAddress", method = RequestMethod.POST)
	public @ResponseBody WDResponse addAddress(@RequestBody Address addr) {
		
		if (StringUtils.isEmpty(addr)) {
			return ResponseUtil.apiError("1001", "缺少必要参数");
		}
		if (StringUtils.isEmpty(addr.getUserId())) {
			return ResponseUtil.apiError("1002", "userId is null");
		}
		if (StringUtils.isEmpty(addr.getProvince())) {
			return ResponseUtil.apiError("1003", "province is null");
		}
		if (StringUtils.isEmpty(addr.getCity())) {
			//return ResponseUtil.apiError("1004", "city is null");
		}
		if (StringUtils.isEmpty(addr.getDistrict())) {
			//return ResponseUtil.apiError("1005", "district is null");
		}
		if (StringUtils.isEmpty(addr.getAddressDetail())) {
			return ResponseUtil.apiError("1006", "addressDetail is null");
		}
		if (StringUtils.isEmpty(addr.getContactName())) {
			return ResponseUtil.apiError("1007", "contactName is null");
		}
		if (StringUtils.isEmpty(addr.getContactNumber())) {
			return ResponseUtil.apiError("1008", "contactNumber is null");
		}
				
		try {
			
			addrService.addAddress(addr);
			
			logger.info(ADDADDR_SUCCESS_MSG);
			return ResponseUtil.apiSuccess(addr, ADDADDR_SUCCESS_MSG);
		} catch (Exception e) {
			logger.error(ADDADDR_EXCEPTION_MSG , e);
			return ResponseUtil.apiError("1100", "未知错误");
		}
	

	}
	
	@RequestMapping(value = "/updateAddress", method = RequestMethod.POST)
	public @ResponseBody WDResponse updateAddress(@RequestBody Address addr) {
		
		if (StringUtils.isEmpty(addr)) {
			return ResponseUtil.apiError("1001", "缺少必要参数");
		}
		if (StringUtils.isEmpty(addr.getId())) {
			return ResponseUtil.apiError("1002", "id is null");
		}
		
				
		try {
			
			addrService.updateAddress(addr);
			
			logger.info(UPDATEADDR_SUCCESS_MSG);
			return ResponseUtil.apiSuccess(addr, UPDATEADDR_SUCCESS_MSG);
		} catch (Exception e) {
			logger.error(UPDATEADDR_EXCEPTION_MSG , e);
			return ResponseUtil.apiError("1100", "未知错误");
		}
	

	}
	
	@RequestMapping(value = "/setToDefault", method = RequestMethod.POST)
	public @ResponseBody WDResponse setToDefault(@RequestBody Address addr) {
		
		
		if (StringUtils.isEmpty(addr.getId())) {
			return ResponseUtil.apiError("1002", "id is null");
		}if (StringUtils.isEmpty(addr.getUserId())) {
			return ResponseUtil.apiError("1003", "userId is null");
		}
				
		try {
			addrService.setToDefault(addr.getUserId(), addr.getId());
			
			logger.info(SETTODEFAULTADDRESS_SUCCESS_MSG);
			return ResponseUtil.apiSuccess("set to default address succ", SETTODEFAULTADDRESS_SUCCESS_MSG);
		} catch (Exception e) {
			logger.error(SETTODEFAULTADDRESS_EXCEPTION_MSG , e);
			return ResponseUtil.apiError("1100", "未知错误");
		}
	

	}
	
	@RequestMapping(value = "/getAddress", method = RequestMethod.GET)
	public @ResponseBody WDResponse getAddress(@RequestParam Integer userId) {
		
		
		if (StringUtils.isEmpty(userId)) {
			return ResponseUtil.apiError("1002", "userId is null");
		}
				
		try {
			List<Address> list = addrService.getAddressByUserId(userId);
			logger.info(GETADDR_SUCCESS_MSG);
			return ResponseUtil.apiSuccess(list, GETADDR_SUCCESS_MSG);
		} catch (Exception e) {
			logger.error(GETADDR_EXCEPTION_MSG , e);
			return ResponseUtil.apiError("1100", "未知错误");
		}
	

	}
	
	
	@RequestMapping(value = "/getDefaultAddress", method = RequestMethod.GET)
	public @ResponseBody WDResponse getDefaultAddress(@RequestParam Integer userId) {
		
		
		if (StringUtils.isEmpty(userId)) {
			return ResponseUtil.apiError("1002", "userId is null");
		}
				
		try {
			Address addr = addrService.getDefaultAddressByUserId(userId);
			logger.info(GETDEFAULTADDR_SUCCESS_MSG);
			return ResponseUtil.apiSuccess(addr, GETDEFAULTADDR_SUCCESS_MSG);
		} catch (Exception e) {
			logger.error(GETDEFAULTADDR_EXCEPTION_MSG , e);
			return ResponseUtil.apiError("1100", "未知错误");
		}
	

	}
	
	
	
	
	
	@RequestMapping(value = "/getAllProvince", method = RequestMethod.GET)
	public @ResponseBody WDResponse getAllProvince() {
		try {
			List<Area> list = addrService.getAllProvince();
			logger.info(GETALLPROVINCE_SUCCESS_MSG);
			return ResponseUtil.apiSuccess(list, GETALLPROVINCE_SUCCESS_MSG);
		} catch (Exception e) {
			logger.error(GETALLPROVINCE_EXCEPTION_MSG , e);
			return ResponseUtil.apiError("1100", "未知错误");
		}
	

	}
	
	@RequestMapping(value = "/getSubAreaById", method = RequestMethod.GET)
	public @ResponseBody WDResponse getSubAreaById(@RequestParam Integer id) {
		try {
			if (StringUtils.isEmpty(id)) {
				return ResponseUtil.apiError("1002", "id is null");
			}
			List<Area> list = addrService.getAreaByParentId(id);
			logger.info(GETSUBAREABYID_SUCCESS_MSG);
			return ResponseUtil.apiSuccess(list, GETSUBAREABYID_SUCCESS_MSG);
		} catch (Exception e) {
			logger.error(GETSUBAREABYID_EXCEPTION_MSG , e);
			return ResponseUtil.apiError("1100", "未知错误");
		}
	

	}
	
	
	
}


