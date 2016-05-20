/**
 * CAPGEMINI APPLIANCE CHAINS.
 * Copyright (c) 2015-2015 All Rights Reserved.
 */
package com.capgemini.wdapp.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.wdapp.dao.ISellerDao;
import com.capgemini.wdapp.dao.ISmsDao;
import com.capgemini.wdapp.dao.IUserDao;
import com.capgemini.wdapp.model.Seller;
import com.capgemini.wdapp.model.Sms;
import com.capgemini.wdapp.model.User;
import com.capgemini.wdapp.service.ISellerService;
import com.capgemini.wdapp.service.ISmsService;
import com.capgemini.wdapp.service.IUserService;

/**
 * 
 * functional description： user interface
 * @author  zhihuang@capgemini.com
 * @created Dec 18, 2015 10:37:13 AM
 * @date Dec 18, 2015 10:37:13 AM
 */

@Service(value = "userService")
public class UserServiceImpl implements IUserService {

	@Autowired
	private IUserDao userDao;
	

	private final static Log logger = LogFactory.getLog(UserServiceImpl.class);

	public int saveUser(User user){
	    
	    if(user == null){
	        return 0;
	    }
	    return userDao.saveUser(user);
	    
	}
	
	public User getUserInfo(String openId){
        
        List<User> userList = userDao.getUserInfo(openId);
        if(userList != null && userList.size()>0){
            return (User)userList.get(0);
        }
        
        return null;
	}

	@Override
	public int updateUser(User user) {
		if(user == null){
	        return 0;
	    }
	    return userDao.updateUser(user);
	}
	
}
