/**
 * CAPGEMINI APPLIANCE CHAINS.
 * Copyright (c) 2015-2015 All Rights Reserved.
 */
package com.capgemini.wdapp.service;

import com.capgemini.wdapp.api.exception.DataAccessDeniedException;
import com.capgemini.wdapp.model.Sms;

/**
 * 
 * functional description： seller interface
 * @author  zhihuang@capgemini.com
 * @created Dec 18, 2015 10:37:13 AM
 * @date Dec 18, 2015 10:37:13 AM
 */

public interface ISmsService {
    public int saveSms(Sms sms);
    
    public int updateSms(Sms sms);
    
    public Sms getSmsByPhoneNumberAndOrType(Sms sms);
    
    public void getSmsVarifyNum(Sms sms) throws Exception;
    
    
}

