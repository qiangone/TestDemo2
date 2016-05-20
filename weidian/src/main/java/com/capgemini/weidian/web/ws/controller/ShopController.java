package com.capgemini.weidian.web.ws.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.capgemini.weidian.model.Response;
import com.capgemini.weidian.model.Info;
import com.capgemini.weidian.model.Result;
import com.capgemini.weidian.model.Seller;
import com.capgemini.weidian.model.Shop;

@Controller
@RequestMapping("/api/shop")
public class ShopController {

	// private static Shop shop = null;

	//dddd
	
	
	private final Log logger = LogFactory.getLog(ShopController.class);

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public @ResponseBody Response addShop(@RequestBody Shop shop,HttpServletRequest request) {
		logger.info("add shop  " + shop);

		//add the id for new shop
		shop.setId((new Date()).getTime());
		
		//response info
		Response response = new Response();
		Result result = new Result();
		result.setContent(shop);
		response.setResult(result);
		
		//access code info
		Info info = new Info();
		info.setCode("200");
		info.setMsg("add shop successful");
		response.setInfo(info);

		return response;
	}

	@RequestMapping(value = "/getShopInfo", method = RequestMethod.GET)
	public @ResponseBody Response getShopInfo(@RequestParam long sellerId) {

		//temp test shop
		Shop shop = new Shop();
		shop.setId((new Date()).getTime());
		shop.setDescription("Test Description");
		shop.setName("Test Name");
		shop.setToken("Test Token");
		
		//seller
		Seller seller = new Seller();
		seller.setId(sellerId);
		seller.setUsername("test seller name");
		shop.setSeller(seller);
			
		//response info
		Response response = new Response();
		Result result = new Result();
		result.setContent(shop);
		response.setResult(result);
		
		//access code info
		Info info = new Info();
		info.setCode("200");
		info.setMsg("add shop successful");
		response.setInfo(info);
		return response;
	}

	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody Response update(@ModelAttribute Shop shop) {
		logger.info("update shop  " + shop);

		//result info
		Response response = new Response();
		Result result = new Result();
		result.setContent(shop);
		response.setResult(result);
		
		//access code info
		Info info = new Info();
		info.setCode("200");
		info.setMsg("add shop successful");
		response.setInfo(info);

		return response;
	}
}
