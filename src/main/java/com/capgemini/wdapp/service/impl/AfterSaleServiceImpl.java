/**
 * CAPGEMINI APPLIANCE CHAINS.
 * Copyright (c) 2015-2015 All Rights Reserved.
 */
package com.capgemini.wdapp.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.wdapp.common.WDConstant;
import com.capgemini.wdapp.dao.IAfterSaleDao;
import com.capgemini.wdapp.model.Address;
import com.capgemini.wdapp.model.AfterSale;
import com.capgemini.wdapp.model.Area;
import com.capgemini.wdapp.model.ExpressCompany;
import com.capgemini.wdapp.model.OrderDetail;
import com.capgemini.wdapp.service.IAfterSaleService;

/**
 * 
 * functional description： seller interface
 * 
 * @author zhihuang@capgemini.com
 * @created Dec 18, 2015 10:37:13 AM
 * @date Dec 18, 2015 10:37:13 AM
 */

@Service(value = "afterSaleService")
public class AfterSaleServiceImpl implements IAfterSaleService {


	@Autowired
	private IAfterSaleDao afterSaleDao;
	
	
	public int addAfterSale(AfterSale addr){
		return afterSaleDao.addAfterSale(addr);
	}
	
	public int updateAfterSale(AfterSale addr){
		return afterSaleDao.updateAfterSale(addr);
	}
	
	public int fillLogistics(AfterSale as){
		as.setExpressTime(new Date());
		as.setStatus(WDConstant.AFTERSALE_STATUS_CONFIRM_RECV);
		return afterSaleDao.updateAfterSale(as);
	}
	
	public int submitAppeal(AfterSale as){
		as.setAppealTime(new Date());
		return afterSaleDao.updateAfterSale(as);
	}
	
	public int confirmRecvAndRefunds(AfterSale as){
		as.setRefundTime(new Date());
		as.setStatus(WDConstant.AFTERSALE_STATUS_CONFIRM_REFUND);
		return afterSaleDao.updateAfterSale(as);
	}
	
	public int handleRefunds(AfterSale as){
		as.setHandleTime(new Date());
		int result = as.getHandleResult();
		
		int detailId = as.getOrderDetailId();
		AfterSale tmpAs = getAfterSaleByDetailId(detailId);
		if(tmpAs != null){
			int type = tmpAs.getType();
			if(result == 0){//同意
				if(type == 1){//退款
					as.setRefundTime(new Date());
					as.setStatus(WDConstant.AFTERSALE_STATUS_CONFIRM_REFUND);
				}else if(type == 2){//退货
					as.setStatus(WDConstant.AFTERSALE_STATUS_REFUNDING);
				}
			}else{//拒绝
				as.setStatus(WDConstant.AFTERSALE_STATUS_REJECT_REFUND);
				
			}
		}
		
		return afterSaleDao.updateAfterSale(as);
	}
	
	public AfterSale getAfterSaleByDetailId(Integer detailId){
		
		Map map = new HashMap();
		map.put("orderDetailId", detailId);
		List<AfterSale> list = afterSaleDao.getAfterSale(map);
		if(list != null && list.size()>0){
			return list.get(0);
		}
		
		return null;
	}
	
	public List<Map> getAllRejectReasonByType(int type){
		return afterSaleDao.getAllRejectReasonByType(type);
	}
	
	public List<AfterSale> getAfterSaleList(List<OrderDetail> list, List<Integer> status){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("statusList", status);
		map.put("detailList", list);
		return afterSaleDao.getAfterSale(map);
	}
}
