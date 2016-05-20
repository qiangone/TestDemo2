/**
 * CAPGEMINI APPLIANCE CHAINS.
 * Copyright (c) 2015-2015 All Rights Reserved.
 */
package com.capgemini.wdapp.service;

import java.util.List;

import com.capgemini.wdapp.model.Address;
import com.capgemini.wdapp.model.Area;
import com.capgemini.wdapp.model.ExpressCompany;

/**
 * 
 * functional description： seller interface
 * @author  zhihuang@capgemini.com
 * @created Dec 18, 2015 10:37:13 AM
 * @date Dec 18, 2015 10:37:13 AM
 */

public interface IAddressService {
	public int addAddress(Address addr);
	
	public int updateAddress(Address addr);
	
	public List<Address> getAddressByUserId(Integer userId);
	
	public Address getAddressById(Integer id);
	
	public int setToDefault(Integer userId, Integer addressId);
	
	
	public Address getDefaultAddressByUserId(Integer userId);
	
	public List<Area> getAllProvince();
	
	public List<Area> getAreaByParentId(Integer parentId);
	
	public List<ExpressCompany> getExpressCompList();
}
