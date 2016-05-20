package com.capgemini.wdapp.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.capgemini.wdapp.model.Address;
import com.capgemini.wdapp.model.Area;
import com.capgemini.wdapp.model.ExpressCompany;
import com.capgemini.wdapp.model.OrderAddress;


@Repository
public interface IAddressDao {
	public int addAddress(Address addr);
	
	public int addOrderAddress(OrderAddress addr);
	
	public int updateAddress(Address addr);
	
	public List<Address> getAddress(Map map);
	
	public List<Area> getAreaList(Map map);
	
	
	public List<ExpressCompany> getExpressCompList();
	
	
	public ExpressCompany getExpressCompByName(String name);
	
}
