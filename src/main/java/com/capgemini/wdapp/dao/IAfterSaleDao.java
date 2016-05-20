package com.capgemini.wdapp.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.capgemini.wdapp.model.AfterSale;
import com.capgemini.wdapp.model.OrderDetail;


@Repository
public interface IAfterSaleDao {
	public int addAfterSale(AfterSale addr);
	
	public int updateAfterSale(AfterSale addr);
	
	
	public List<AfterSale> getAfterSale(Map map);
	
	public List<Map> getAllRejectReasonByType(int type);
	
	
	
}
