/**
 * CAPGEMINI APPLIANCE CHAINS.
 * Copyright (c) 2015-2015 All Rights Reserved.
 */
package com.capgemini.wdapp.service;

import java.util.List;

import com.capgemini.wdapp.model.FindPassword;
import com.capgemini.wdapp.model.Seller;

/**
 * 
 * functional description： seller interface
 * @author  zhihuang@capgemini.com
 * @created Dec 18, 2015 10:37:13 AM
 * @date Dec 18, 2015 10:37:13 AM
 */

public interface IFindPwdService {
	 public int addFinwpwd(FindPassword fp);
	 
	 public int saveFinwpwd(FindPassword fp);
	 
	 public FindPassword getFindPwdByPhoneNumber(String phoneNumber);
    
    
}
