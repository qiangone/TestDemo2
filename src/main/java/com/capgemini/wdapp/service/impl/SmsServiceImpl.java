/**
 * CAPGEMINI APPLIANCE CHAINS.
 * Copyright (c) 2015-2015 All Rights Reserved.
 */
package com.capgemini.wdapp.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.wdapp.dao.ISellerDao;
import com.capgemini.wdapp.dao.ISmsDao;
import com.capgemini.wdapp.model.Seller;
import com.capgemini.wdapp.model.Sms;
import com.capgemini.wdapp.response.ResponseUtil;
import com.capgemini.wdapp.service.ISellerService;
import com.capgemini.wdapp.service.ISmsService;
import com.capgemini.wdapp.util.SmsUtil;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

/**
 * 
 * functional description： seller interface
 * @author  zhihuang@capgemini.com
 * @created Dec 18, 2015 10:37:13 AM
 * @date Dec 18, 2015 10:37:13 AM
 */

@Service(value = "smsService")
public class SmsServiceImpl implements ISmsService {

	@Autowired
	private ISmsDao smsDao;
	
	private TaobaoClient mTaobaoClient;
	

	private final static Log logger = LogFactory.getLog(SmsServiceImpl.class);

	public int saveSms(Sms sms){
	    
	    if(sms == null){
	        return 0;
	    }
	    return smsDao.saveSms(sms);
	    
	}
	
	public Sms getSmsByPhoneNumberAndOrType(Sms sms){
        
        List<Sms> smsList = smsDao.getSmsByPhoneNumberAndOrType(sms);
        if(smsList != null && smsList.size()>0){
            return (Sms)smsList.get(0);
        }
        
        return null;
	}

	@Override
	public int updateSms(Sms sms) {
		if(sms == null){
	        return 0;
	    }
	    return smsDao.updateSms(sms);
	}
	
	
	public void getSmsVarifyNum(Sms sms) throws Exception{
    	mTaobaoClient = new DefaultTaobaoClient(SmsUtil.URL, SmsUtil.APPKEY,SmsUtil.APPSECRET);
    	String securityCode = SmsUtil.createSecurityCode();
    	AlibabaAliqinFcSmsNumSendRequest req = SmsUtil.buildSmsRequest(sms.getPhoneNumber(), securityCode, sms.getType());
    	try {
    		AlibabaAliqinFcSmsNumSendResponse rsp = mTaobaoClient.execute(req);
    		if (rsp.getResult().getErrCode().equals("0") && rsp.getResult().getSuccess()) {//sucess
    			sms.setCreatedTime(new Date());
    			sms.setSecurityCode(securityCode);
    			List<Sms> smsList = smsDao.getSmsByPhoneNumberAndOrType(sms);
    			if(smsList != null && smsList.size()>0){
    				Sms existSms = smsList.get(0);
    				existSms.setCreatedTime(new Date());
    				existSms.setSecurityCode(securityCode);
    				smsDao.updateSms(existSms);
    			}else{
    				smsDao.saveSms(sms);
    			}
    			
    		} else {//fail
    			throw new Exception("发送短信失败" + rsp.getMsg());
    		}
    	} catch (Exception e) {
    		logger.error("getSmsVarifyNum error:" +e);
    		throw new Exception("getSmsVarifyNum error" +e);
    	}
	}
}
