/**
 * CAPGEMINI APPLIANCE CHAINS.
 * Copyright (c) 2015-2015 All Rights Reserved.
 */
package com.capgemini.wdapp.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.wdapp.dao.IFindPwdDao;
import com.capgemini.wdapp.model.FindPassword;
import com.capgemini.wdapp.service.IFindPwdService;

/**
 * 
 * functional description： seller interface
 * @author  zhihuang@capgemini.com
 * @created Dec 18, 2015 10:37:13 AM
 * @date Dec 18, 2015 10:37:13 AM
 */

@Service(value = "findPwdService")
public class FindPwdServiceImpl implements IFindPwdService {

	@Autowired
	private IFindPwdDao findPwdDao;
	

	private final static Log logger = LogFactory.getLog(FindPwdServiceImpl.class);
	
	
	public int addFinwpwd(FindPassword fp){
		return findPwdDao.addFinwpwd(fp);
	}
	
	public int saveFinwpwd(FindPassword fp){
		return findPwdDao.saveFinwpwd(fp);
	}
	
	public FindPassword getFindPwdByPhoneNumber(String phoneNumber){
		return findPwdDao.getFindPwdByPhoneNumber(phoneNumber);
	}

	
	
}
