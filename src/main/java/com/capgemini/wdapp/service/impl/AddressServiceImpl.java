/**
 * CAPGEMINI APPLIANCE CHAINS.
 * Copyright (c) 2015-2015 All Rights Reserved.
 */
package com.capgemini.wdapp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.wdapp.dao.IAddressDao;
import com.capgemini.wdapp.model.Address;
import com.capgemini.wdapp.model.Area;
import com.capgemini.wdapp.model.ExpressCompany;
import com.capgemini.wdapp.service.IAddressService;

/**
 * 
 * functional description： seller interface
 * 
 * @author zhihuang@capgemini.com
 * @created Dec 18, 2015 10:37:13 AM
 * @date Dec 18, 2015 10:37:13 AM
 */

@Service(value = "addressService")
public class AddressServiceImpl implements IAddressService {

	private final static Log logger = LogFactory
			.getLog(AddressServiceImpl.class);

	@Autowired
	private IAddressDao addrDao;
//	
//	@Autowired
//	private IAddressService addrService;

	public int addAddress(Address addr) {
//		List<Address> list = addrService.getAddressByUserId(addr.getUserId());
//		if(list != null && list.size()>0){
//			for(Address inadd : list){
//				inadd.setIsDefaultAddr("0");
//				addrDao.updateAddress(inadd);
//			}
//		}
		
		Integer userId = addr.getUserId();
		Address param = new Address();
		param.setUserId(userId);
		param.setIsDefaultAddr("0");
		addrDao.updateAddress(param);
		
		addr.setIsDefaultAddr("1");//default addr
		return addrDao.addAddress(addr);
	}
	
	public List<ExpressCompany> getExpressCompList(){
		return addrDao.getExpressCompList();
	}
	
	public int setToDefault(Integer userId, Integer addressId){
		Address param = new Address();
		param.setUserId(userId);
		param.setIsDefaultAddr("0");
		addrDao.updateAddress(param);
		
		param = new Address();
		param.setId(addressId);
		param.setIsDefaultAddr("1");
		addrDao.updateAddress(param);
		
		return 1;
		
	}
	
	public int updateAddress(Address addr){
		return addrDao.updateAddress(addr);
		
	}
	
	public Address getAddressById(Integer id){
		Map map = new HashMap();
		map.put("id", id);
		List<Address> list = addrDao.getAddress(map);
		if(list != null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	public Address getDefaultAddressByUserId(Integer userId){
		Map map = new HashMap();
		map.put("userId", userId);
		map.put("isDefaultAddr", 1);
		List<Address> list = addrDao.getAddress(map);
		if(list != null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	public List<Address> getAddressByUserId(Integer userId) {
		Map map = new HashMap();
		map.put("userId", userId);
		List<Address> list = addrDao.getAddress(map);
		return list;
	}

	public List<Area> getAllProvince(){
		Map map = new HashMap();
		map.put("parentId", "16");
		List<Area> list = addrDao.getAreaList(map);
		return list;
	}

	public List<Area> getAreaByParentId(Integer parentId){
		Map map = new HashMap();
		map.put("parentId", parentId);
		List<Area> list = addrDao.getAreaList(map);
		return list;
	}

}
