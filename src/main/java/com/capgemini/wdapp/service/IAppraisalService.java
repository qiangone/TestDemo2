/**
 * CAPGEMINI APPLIANCE CHAINS.
 * Copyright (c) 2015-2015 All Rights Reserved.
 */
package com.capgemini.wdapp.service;

import java.util.List;
import java.util.Map;

import com.capgemini.wdapp.model.Appraisal;
import com.capgemini.wdapp.model.Cart;
import com.capgemini.wdapp.model.Goods;
import com.capgemini.wdapp.model.OrderMaster;
import com.capgemini.wdapp.model.PageResults;
import com.capgemini.wdapp.model.Pagination;

/**
 * 
 * functional description： seller interface
 * @author  zhihuang@capgemini.com
 * @created Dec 18, 2015 10:37:13 AM
 * @date Dec 18, 2015 10:37:13 AM
 */

public interface IAppraisalService {
	
	public int addAppraisal(List<Appraisal> list);
	
   
	public List<Appraisal> getAppraisalByGoodsId(Integer userId);
	
	public PageResults<Appraisal> getAppraisalListByPage(Map<String, Object> conditions, Pagination page) ;
	
}
