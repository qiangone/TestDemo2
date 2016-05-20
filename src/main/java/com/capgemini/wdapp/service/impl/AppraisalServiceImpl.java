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
import org.springframework.util.StringUtils;

import com.capgemini.wdapp.dao.IAppraisalDao;
import com.capgemini.wdapp.model.Appraisal;
import com.capgemini.wdapp.model.Cart;
import com.capgemini.wdapp.model.Goods;
import com.capgemini.wdapp.model.PageResults;
import com.capgemini.wdapp.model.Pagination;
import com.capgemini.wdapp.service.IAppraisalService;

/**
 * 
 * functional description： seller interface
 * @author  zhihuang@capgemini.com
 * @created Dec 18, 2015 10:37:13 AM
 * @date Dec 18, 2015 10:37:13 AM
 */

@Service(value = "appraisalService")
public class AppraisalServiceImpl implements IAppraisalService {

	private final static Log logger = LogFactory.getLog(AppraisalServiceImpl.class);
	
	@Autowired
	private IAppraisalDao apprDao;
	
	
	
	public int addAppraisal(List<Appraisal> list){
		return apprDao.addAppraisal(list);
	}
	
   
	public List<Appraisal> getAppraisalByGoodsId(Integer goodsId){
		Map map = new HashMap();
		map.put("goodsId", goodsId);
		List<Appraisal> list = apprDao.getAppraisal(map);
		return list;
	}
	
	public PageResults<Appraisal> getAppraisalListByPage(Map<String, Object> conditions, Pagination page) {
		if (null == page || !page.isValidPage()) {
			return null;
		}
		
		// get counts
		List<Appraisal> list = apprDao.getAppraisal(conditions);
		if(list == null || list.size()==0){
			return null;
		}
		
		int totalCount = list.size();
		page.setTotalCount(totalCount);
		
		conditions.put("showpage", 1);
		conditions.put(Pagination.STARTINDEX, page.getStartIndex());
        conditions.put(Pagination.PAGESIZE, page.getPageSize());
		
        List<Appraisal> list2 = apprDao.getAppraisal(conditions);
		
		PageResults<Appraisal> result = new PageResults<Appraisal>(page, list2);
		
		return result;
	}
		
	
	
	
	
}
