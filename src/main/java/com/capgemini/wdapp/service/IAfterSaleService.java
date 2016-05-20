/**
 * CAPGEMINI APPLIANCE CHAINS.
 * Copyright (c) 2015-2015 All Rights Reserved.
 */
package com.capgemini.wdapp.service;

import java.util.List;
import java.util.Map;

import com.capgemini.wdapp.model.AfterSale;
import com.capgemini.wdapp.model.OrderDetail;

/**
 * 
 * functional description： seller interface
 * @author  zhihuang@capgemini.com
 * @created Dec 18, 2015 10:37:13 AM
 * @date Dec 18, 2015 10:37:13 AM
 */

public interface IAfterSaleService {
	public int addAfterSale(AfterSale addr);
	
	public int updateAfterSale(AfterSale addr);
	
	public int fillLogistics(AfterSale addr);
	
	public int submitAppeal(AfterSale addr);
	
	public int handleRefunds(AfterSale addr);
	
	public int confirmRecvAndRefunds(AfterSale addr);
	
	
	public AfterSale getAfterSaleByDetailId(Integer detailId);
	
	public List<Map> getAllRejectReasonByType(int type);

	public List<AfterSale> getAfterSaleList(List<OrderDetail> list, List<Integer> status);
}
