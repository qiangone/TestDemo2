/**
 * CAPGEMINI APPLIANCE CHAINS.
 * Copyright (c) 2015-2015 All Rights Reserved.
 */
package com.capgemini.wdapp.service;

import java.util.List;

import com.capgemini.wdapp.model.Seller;
import com.capgemini.wdapp.model.Sms;
import com.capgemini.wdapp.model.User;

/**
 * 
 * functional description： seller interface
 * @author  zhihuang@capgemini.com
 * @created Dec 18, 2015 10:37:13 AM
 * @date Dec 18, 2015 10:37:13 AM
 */

public interface IUserService {
    public int saveUser(User user);
    
    public int updateUser(User user);
    
    public User getUserInfo(String openid);
}
