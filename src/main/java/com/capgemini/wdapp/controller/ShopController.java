package com.capgemini.wdapp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.capgemini.wdapp.model.Shop;
import com.capgemini.wdapp.response.ResponseUtil;
import com.capgemini.wdapp.response.WDResponse;
import com.capgemini.wdapp.service.IShopService;

@Controller
@RequestMapping("/api/shop")
public class ShopController {
	private final Log logger = LogFactory.getLog(ShopController.class);

	@Autowired
	private IShopService shopService;

	@RequestMapping(value = "/addShop", method = RequestMethod.POST)
	public @ResponseBody WDResponse addShop(
			@RequestParam(required = false) String name,
			@RequestParam(required = false) String description,
			@RequestParam(required = false) Integer sellerId,
			@RequestParam(required = false) String token,
			@RequestParam(required = false) Float transportExpense,
			@RequestParam(value = "logoImg", required = false) CommonsMultipartFile logoImg,
			@RequestParam(value = "backgImg", required = false) CommonsMultipartFile backgImg) {

		if (StringUtils.isEmpty(name)) {
			return ResponseUtil.apiError("1002", "name is null!");
		}
		if (StringUtils.isEmpty(sellerId)) {
			return ResponseUtil.apiError("1003", "seller Id is null!");
		}
		
		Shop shop = new Shop();
		try {

			shop.setSellerId(sellerId);
			List<Shop> list = shopService.getShop(shop);
			if (list != null && list.size() > 0) {
				return ResponseUtil.apiError("1005", "seller Id exist");
			}

			// Shop shop = new Shop();
			shop.setName(name);
			shop.setDescription(description);
			// shop.setSellerId(sellerId);
			shop.setToken(token);
			if (StringUtils.isEmpty(transportExpense)) {
				shop.setTransportExpense(0f);
			} else {
				shop.setTransportExpense(transportExpense);
			}

			shopService.addShop(shop, logoImg, backgImg);
		} catch (Exception e) {
			logger.error("addShop exception: " , e);
			return ResponseUtil.apiError("1100", "未知错误");
		}

		return ResponseUtil.apiSuccess(shop, "add shop successful");

	}

	@RequestMapping(value = "/updateShop", method = RequestMethod.POST)
	public @ResponseBody WDResponse updateShop(
			@RequestParam(required = false) String name,
			@RequestParam(required = false) String description,
			@RequestParam(required = true) Integer sellerId,
			@RequestParam(required = false) String token,
			@RequestParam(required = false) Float transportExpense,
			@RequestParam(required = false) String address,
			@RequestParam(required = false) String postCode,
			@RequestParam(required = false) String contact,
			@RequestParam(required = false) String telephone,
			@RequestParam(required = false) String province,
			@RequestParam(required = false) String city,
			@RequestParam(required = false) String region,
			@RequestParam(value = "logoImg", required = false) CommonsMultipartFile logoImg,
			@RequestParam(value = "backgImg", required = false) CommonsMultipartFile backgImg) {

		if (StringUtils.isEmpty(sellerId)) {
			return ResponseUtil.apiError("1002", "seller Id is null!");
		}

		Shop shop = new Shop();
		shop.setSellerId(sellerId);
		List<Shop> list = shopService.getShop(shop);
		if (list == null || list.size() == 0) {
			return ResponseUtil.apiError("1003", "seller Id not exist");
		}

		shop.setName(name);
		shop.setDescription(description);

		shop.setToken(token);
		shop.setTransportExpense(transportExpense);
		shop.setAddress(address);
		shop.setContact(contact);
		shop.setPostCode(postCode);;
		shop.setTelephone(telephone);
		shop.setProvince(province);
		shop.setCity(city);
		shop.setRegion(region);
		try {
			shopService.updateShop(shop, logoImg, backgImg);
			Shop retShop = shopService.getShopBySellerId(sellerId);
			return ResponseUtil.apiSuccess(retShop, "update shop successful");
		} catch (Exception e) {
			logger.error("updateShop exception: " , e);
			return ResponseUtil.apiError("1100", "未知错误");
		}

		

	}

	@RequestMapping(value = "/getShopInfo", method = RequestMethod.GET)
	public @ResponseBody WDResponse getShopInfo(
			@RequestParam(required = false) Integer sellerId) {
		if (StringUtils.isEmpty(sellerId)) {
			return ResponseUtil.apiError("1002", "seller Id is null!");
		}

		Shop shop = shopService.getShopBySellerId(sellerId);
		if (shop == null) {
			return ResponseUtil.apiError("1003", "shop not exist");
		}
		
		Map map = new HashMap();
		map.put("bysl", 0);
		map.put("jrfk", 0);
		map.put("jrcj", 0);
		map.put("zrxz", 0);
		map.put("shop", shop);

		return ResponseUtil.apiSuccess(shop, "get shop info successful");

	}

}
