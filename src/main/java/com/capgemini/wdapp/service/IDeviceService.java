/**
 * CAPGEMINI APPLIANCE CHAINS.
 * Copyright (c) 2015-2015 All Rights Reserved.
 */
package com.capgemini.wdapp.service;

import java.util.List;

import com.capgemini.wdapp.model.Device;


public interface IDeviceService {
    public int register(int type,Device device);
    public void unregister(Device device);
    
    public void deleteByToken(String token);
    
    public List<Device> getSellerDevice(Long sellerId);


	
    
}
