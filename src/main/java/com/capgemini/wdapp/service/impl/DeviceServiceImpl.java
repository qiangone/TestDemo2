/**
 * CAPGEMINI APPLIANCE CHAINS.
 * Copyright (c) 2015-2015 All Rights Reserved.
 */
package com.capgemini.wdapp.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.wdapp.dao.IDeviceDao;
import com.capgemini.wdapp.dao.ISmsDao;
import com.capgemini.wdapp.model.Device;
import com.capgemini.wdapp.service.IDeviceService;
import com.capgemini.wdapp.util.ConfigUtil;
import com.capgemini.wdapp.vendor.push.PushConstants;
import com.capgemini.wdapp.vendor.push.PushMessageException;
import com.capgemini.wdapp.vendor.push.IPushNotificationManager;
import com.capgemini.wdapp.vendor.push.RemotePushNotificationManager;

/**
 * 
 * functional description： user interface
 * @author  zhihuang@capgemini.com
 * @created Dec 18, 2015 10:37:13 AM
 * @date Dec 18, 2015 10:37:13 AM
 */

@Service(value = "deviceService")
public class DeviceServiceImpl implements IDeviceService {
	private IPushNotificationManager pushNotificationManager;
	@Autowired
	private IDeviceDao deviceDao;
	

	private final static Log logger = LogFactory.getLog(DeviceServiceImpl.class);

	@Override
	public void deleteByToken(String token) {
		
		
	}


	@Override
	public List<Device> getSellerDevice(Long sellerId) {
		return null;
	}


	@Override
	public int register(int type,Device entity) {
		if(entity == null){
	        return 0;
	    }
	    
	    try {
	    	if (type == PushConstants.DEVICE_SELLER) {
	    		pushNotificationManager = new RemotePushNotificationManager(ConfigUtil.getPropertyValue("push.baseurl"),ConfigUtil.getPropertyValue("push.seller.appid"),ConfigUtil.getPropertyValue("push.seller.apptoken"));
	    		
	    	} else if (type == PushConstants.DEVICE_USER) {
	    		pushNotificationManager = new RemotePushNotificationManager(ConfigUtil.getPropertyValue("push.baseurl"),ConfigUtil.getPropertyValue("push.user.appid"),ConfigUtil.getPropertyValue("push.user.apptoken"));
	    		
	    	}  
	    	pushNotificationManager.registerDevice(entity);
	    	
		} catch (PushMessageException e) {
			e.printStackTrace();
		}
	    //entity.setUpdateTime(new Date());
	    List<Device> devices = deviceDao.findByToken(entity.getToken());
	    if (devices != null && devices.size() == 1) {
	    	Device existingDevice = devices.get(0);
	    	entity.setId(existingDevice.getId());
	    	return deviceDao.updateDevice(entity);
	    }
	    return deviceDao.saveDevice(entity);
	}


	@Override
	public void unregister(Device device) {
		// TODO Auto-generated method stub
		
	}
	
}
