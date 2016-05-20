/**

 * CAPGEMINI APPLIANCE CHAINS.
 * Copyright (c) 2015-2015 All Rights Reserved.
 */
package com.capgemini.wdapp.service.impl;


import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.wdapp.dao.IDeviceDao;
import com.capgemini.wdapp.model.Channel;
import com.capgemini.wdapp.model.OrderMaster;
import com.capgemini.wdapp.model.Shop;
import com.capgemini.wdapp.model.User;
import com.capgemini.wdapp.service.IOrderService;
import com.capgemini.wdapp.service.IPushService;
import com.capgemini.wdapp.service.ISellerService;
import com.capgemini.wdapp.service.IShopService;
import com.capgemini.wdapp.util.ConfigUtil;
import com.capgemini.wdapp.vendor.push.PushConstants;
import com.capgemini.wdapp.vendor.push.PushMessage;
import com.capgemini.wdapp.vendor.push.PushMessageException;
import com.capgemini.wdapp.vendor.push.IPushNotificationManager;
import com.capgemini.wdapp.vendor.push.RemotePushNotificationManager;



@Service(value = "pushService")
public class PushServiceImpl implements IPushService {
	private IPushNotificationManager userPushSender;
	private IPushNotificationManager sellerPushSender;
	@Autowired
	private IDeviceDao deviceDao;
	@Autowired
	private IShopService shopService;
	@Autowired
	private ISellerService sellerService;
	@Autowired
	private IOrderService orderService;
	
	
	private final static Log logger = LogFactory.getLog(PushServiceImpl.class);
	private void initSender() {
		if (userPushSender == null) {
			userPushSender = new RemotePushNotificationManager(ConfigUtil.getPropertyValue("push.baseurl"),ConfigUtil.getPropertyValue("push.user.appid"),ConfigUtil.getPropertyValue("push.user.apptoken"));
		}
		if (sellerPushSender == null) {
			sellerPushSender = new RemotePushNotificationManager(ConfigUtil.getPropertyValue("push.baseurl"),ConfigUtil.getPropertyValue("push.seller.appid"),ConfigUtil.getPropertyValue("push.seller.apptoken"));
		}
		
	}
	@Override
	public int newOrder(OrderMaster orderMaster) {
		newOrderToUser(orderMaster);
		newOrderToSeller(orderMaster);
		return 0;
	}

	

	@Override
	public int newOrderConfirmed(int sellerId) {
		initSender();
    	PushMessage msg = new PushMessage();
		msg.setRecipient(String.valueOf(sellerId));
		msg.setBadge(1);
		msg.addCustomField(PushMessage.KEY_PAYLOAD, "{\"action:\"}" + "2");
		msg.setMessage("您有一个订单已被确认收货，点击查看");
		try {
			userPushSender.send(msg);
		} catch (PushMessageException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	@Override
	public int addWechatChannelForShop(int shopId) {
		initSender();
    	Shop shop = shopService.getShopById(shopId);
		Channel channel = new Channel();
    	channel.setAppId(ConfigUtil.getPropertyValue("push.user.appid"));
    	channel.setChannelAppKey(shop.getWeChatAppKey());
    	channel.setChannelAppSecret(shop.getWeChatAppSecret());
    	channel.setChannelName("wechat for shop:" + shop.getName());
    	channel.setChannelType(PushConstants.CHANNEL_WECHAT);
    	channel.setMpKey(String.valueOf(shopId));
		try {
			userPushSender.createChannel(channel);
		} catch (PushMessageException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int newUser(User user) {
		initSender();
    	PushMessage msg = new PushMessage();
		Shop shop = shopService.getShopById(user.getShopId());
    	msg.setRecipient(String.valueOf(user.getId()));
		msg.setBadge(1);
		msg.addCustomField("tpl_id", "TMTEST");
		msg.addCustomField("first", "亲爱的"+user.getNickname()+":感谢您光临"+shop.getName());
		msg.addCustomField("nickname", user.getNickname());
		msg.addCustomField("time", "2015-02-01");
		msg.addCustomField("remark", "多来逛逛~");
		try {
			userPushSender.send(msg);
		} catch (PushMessageException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public int newOrderToUser(OrderMaster orderMaster) {
		initSender();
    	PushMessage msg = new PushMessage();
		msg.setRecipient(String.valueOf(orderMaster.getUserId()));
		msg.setBadge(1);
		msg.addCustomField("tpl_id", "TM00016"); //订单提交成功
		msg.addCustomField("first", "您的订单已经提交成功，请尽快支付。"); //订单提交成功
		msg.addCustomField("orderID", orderMaster.getOrderCode());
		msg.addCustomField("orderMoneySum", orderMaster.getAmount()+"元");
		msg.addCustomField("time", "13");
		msg.addCustomField("remark", "如有问题，请直接联系商家电话：0571-83382828");
		try {
			userPushSender.send(msg);
		} catch (PushMessageException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public int newOrderToSeller(OrderMaster orderMaster) {
		initSender();
		PushMessage msg = new PushMessage();
		int shopId = orderMaster.getShopId();
		Shop shop = shopService.getShopById(shopId);
		if (shop == null ) {
			return -1;
		}
		msg.setRecipient(String.valueOf(shop.getSellerId()));
		msg.setBadge(1);
		msg.setMessage("You have new order,please check");
		msg.addCustomField("action", "newOrder"); //订单提交成功
		try {
			sellerPushSender.send(msg);
		} catch (PushMessageException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	@Override
	public int orderConfirmReceive(Integer masterId) {
		initSender();
		OrderMaster orderMaster = orderService.getOrderById(masterId);
		
		PushMessage msg = new PushMessage();
		int shopId = orderMaster.getShopId();
		Shop shop = shopService.getShopById(shopId);
		if (shop == null ) {
			return -1;
		}
		msg.setRecipient(String.valueOf(shop.getSellerId()));
		msg.setBadge(1);
		msg.setMessage("You have an order been receive confirmed,please check");
		msg.addCustomField("action", "orderConfirm"); //订单确认收货
		try {
			sellerPushSender.send(msg);
		} catch (PushMessageException e) {
			e.printStackTrace();
		}
		return 0;
	}
	@Override
	public int orderPay(Integer masterId) {
		initSender();
		OrderMaster orderMaster = orderService.getOrderById(masterId);
		
		PushMessage msg = new PushMessage();
		int shopId = orderMaster.getShopId();
		Shop shop = shopService.getShopById(shopId);
		if (shop == null ) {
			return -1;
		}
		msg.setRecipient(String.valueOf(shop.getSellerId()));
		msg.setBadge(1);
		msg.setMessage("您有一笔订单用户已付款，请及时发货");
		msg.addCustomField("action", "orderPay"); //订单确认收货
		try {
			sellerPushSender.send(msg);
		} catch (PushMessageException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public int deliverGoods(OrderMaster orderMaster) {
		initSender();
    	PushMessage msg = new PushMessage();
		msg.setRecipient(String.valueOf(orderMaster.getUserId()));
		msg.setBadge(1);
		msg.addCustomField("tpl_id", "TM00303"); //订单提交成功
		msg.addCustomField("first", "您的订单卖家已发货。"); //订单提交成功
		msg.addCustomField("delivername", orderMaster.getExpressCompany());
		msg.addCustomField("ordername", orderMaster.getExpressNumber());
		msg.addCustomField("remark", "如有问题，请直接联系商家电话：0571-83382828");
		try {
			userPushSender.send(msg);
		} catch (PushMessageException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
}
