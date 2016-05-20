package com.capgemini.wdapp.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.capgemini.wdapp.model.OrderDetail;
import com.capgemini.wdapp.model.OrderMaster;


@Repository
public interface IOrderDao {
	public int addOrderMaster(OrderMaster orderMaster);
	public int updateOrderMaster(OrderMaster orderMaster);
	
	public int addOrderDetail(List<OrderDetail> list);
	
	
	public List<OrderMaster> getOrder(Map<String, Object>  map);
	
	public int countOrderNumbers(Map<String, Object> map);
	
	// bg
	public void autoHandleAllOrderStatus(Map<String, Object> map);
	
	public void autoHandleSingleOrderStatus(Map<String, Object> map);
	
}
