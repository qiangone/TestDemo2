/**
 * CAPGEMINI APPLIANCE CHAINS.
 * Copyright (c) 2015-2015 All Rights Reserved.
 */
package com.capgemini.wdapp.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.capgemini.wdapp.model.Seller;
import com.capgemini.wdapp.model.Shop;
import com.capgemini.wdapp.model.Sms;
import com.capgemini.wdapp.response.ResponseUtil;
import com.capgemini.wdapp.response.WDResponse;
import com.capgemini.wdapp.service.ISellerService;
import com.capgemini.wdapp.service.IShopService;
import com.capgemini.wdapp.service.ISmsService;
import com.capgemini.wdapp.util.JsonUtil;

/**
 * 
 * functional description： Seller Controller
 * 
 * @author zhihuang@capgemini.com
 * @created Dec 18, 2015 10:40:17 AM
 */
@Controller
@RequestMapping("/api/seller")
public class SellerController {

	private final Log logger = LogFactory.getLog(SellerController.class);

	@Autowired
	private ISellerService sellerService;

	@Autowired
	private ISmsService smsService;
	
	@Autowired
	private IShopService shopService;
	
	@Autowired
	private TokenEndpoint endPoint;
	
	
	private ResponseEntity<WDResponse> getResponse(WDResponse accessToken) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Cache-Control", "no-store");
		headers.set("Pragma", "no-cache");
		return new ResponseEntity<WDResponse>(accessToken, headers, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public @ResponseBody WDResponse registerSeller(@RequestBody Seller seller) {
		try {

			if (seller == null) {
				return ResponseUtil.apiError("1001", "Missing parameter!");
			}

			if (seller != null && StringUtils.isEmpty(seller.getPhoneNumber())) {
				return ResponseUtil.apiError("1002", "phone number is null!");
			}
//			if (seller != null && StringUtils.isEmpty(seller.getUserName())) {
//				return ResponseUtil.apiError("1003", "username is null!");
//			}

			if (StringUtils.isEmpty(seller.getVarifyNumber())) {
				return ResponseUtil.apiError("1004", "verify num is null!");
			}
			
			String phoneNumber = seller.getPhoneNumber();
//			String userName = seller.getUserName();
			Seller sell = new Seller();
			sell.setPhoneNumber(phoneNumber);
			List<Seller> list = sellerService.getSeller(sell);
			if (list != null && list.size() > 0) {
				return ResponseUtil.apiError("1007", "phone number exist!");
			}

			Sms sms = new Sms();
			sms.setPhoneNumber(seller.getPhoneNumber());
			sms.setType(1);// register
			Sms ret = smsService.getSmsByPhoneNumberAndOrType(sms);
			if (ret == null) {
				return ResponseUtil
						.apiError("1005", "get verify num exception");
			} else {
				String verifyNum = ret.getSecurityCode();
				if (!verifyNum.equalsIgnoreCase(seller.getVarifyNumber() + "")) {
					return ResponseUtil.apiError("1006",
							"verify num is not correct");
				}

			}



			int tag = sellerService.registerSeller(seller);
			
			return ResponseUtil.apiSuccess(seller, "register shop successful");
		} catch (Exception e) {
			logger.error("register shop successful",e);
			return ResponseUtil.apiError("1101", "未知错误");
		}

		

	}
	
	
	@RequestMapping(value = "/getSellerInfo", method = RequestMethod.GET)
	public @ResponseBody WDResponse getSellerInfo() {

		Seller self = null;
		try {
			if(SecurityContextHolder.getContext() == null){
				return ResponseUtil.apiError("1002", "该用户信息没有授权");
			}
			
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String phoneNumber = userDetails.getUsername();
			self = sellerService.getSellerByPhoneNum(phoneNumber);
			if (self == null) {
				return ResponseUtil.apiError("1003", "seller 在数据库不存在");
			}
			// get seller shop info
			Shop shop =shopService.getShopBySellerId(self.getId());
			self.setShop(shop);
			return ResponseUtil.apiSuccess(self, "get seller info successful");
			
			

		} catch (Exception e) {
			logger.error("get seller info successful",e);
			return ResponseUtil.apiError("1101", "未知错误");
		}

		

	}
	

	@RequestMapping(value = "/updateSellerInfo", method = RequestMethod.POST)
	public @ResponseBody WDResponse updateSellerInfo(
			@RequestBody Seller seller) {

		Seller self = null;
		try {
			boolean ret = false;
			List<Seller> list = null;

			if (seller == null) {
				return ResponseUtil.apiError("1001", "Missing parameter!");
			}

			if (seller.getId() == null) {
				return ResponseUtil.apiError("1002", "Id参数缺失");
			}

			Seller query = new Seller();

			self = sellerService.getSellerById(seller.getId());
			if (self == null) {
				return ResponseUtil.apiError("1003", "Id 在数据库不存在");
			}

			// self = (Seller)list.get(0);//self

			// phone number is change
			if (!StringUtils.isEmpty(seller.getPhoneNumber())
					&& !seller.getPhoneNumber().equalsIgnoreCase(
							self.getPhoneNumber())) {
				query = new Seller();
				query.setPhoneNumber(seller.getPhoneNumber());
				list = sellerService.getSeller(query);
				if (list != null && list.size() > 0) {
					return ResponseUtil.apiError("1004", "phone number exist");
				}

			}

			// if(!StringUtils.isEmpty(seller.getUserName())){
			// return ResponseUtil.apiError("1005", "username不能修改");
			// }

			sellerService.UpdateSeller(seller);

			self = sellerService.getSellerById(seller.getId());
			
			return ResponseUtil.apiSuccess(self, "update seller successful");

		} catch (Exception e) {
			logger.error("update seller successful",e);
			return ResponseUtil.apiError("1101", "未知错误");
		}

		

	}

}
