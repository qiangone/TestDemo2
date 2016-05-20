/**
 * CAPGEMINI APPLIANCE CHAINS.
 * Copyright (c) 2015-2015 All Rights Reserved.
 */
package com.capgemini.wdapp.service;

import com.capgemini.wdapp.model.OrderMaster;
import com.capgemini.wdapp.model.User;

public interface IPushService {
    public int newOrder(OrderMaster orderMaster);
    
    public int newOrderConfirmed(int sellerId);

	public int addWechatChannelForShop(int shopId);

	public int newUser(User user);

	public int orderConfirmReceive(Integer masterId);
	public int orderPay(Integer masterId);
	
	public int deliverGoods(OrderMaster orderMaster);

}
