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

import com.capgemini.wdapp.api.exception.DataAccessDeniedException;
import com.capgemini.wdapp.dao.ISellerDao;
import com.capgemini.wdapp.model.Seller;
import com.capgemini.wdapp.service.ISellerService;

/**
 * 
 * functional description： seller interface
 * @author  zhihuang@capgemini.com
 * @created Dec 18, 2015 10:37:13 AM
 * @date Dec 18, 2015 10:37:13 AM
 */

@Service(value = "sellerService")
public class SellerServiceImpl implements ISellerService {

	@Autowired
	private ISellerDao sellerDao;
	

	private final static Log logger = LogFactory.getLog(SellerServiceImpl.class);

	public int registerSeller(Seller seller){
	    
	    if(seller == null){
	        return 0;
	    }
	    return sellerDao.registerSeller(seller);
	    
	}
	
    public int UpdateSeller(Seller seller){
    	 return sellerDao.updateSeller(seller);
    }
    
    public Seller getSellerById(int id){
    	return sellerDao.getSellerById(id);
    }
	
	public boolean sellerLogin(String userName, String password){
	    Seller seller = new Seller();
	    seller.setUsername(userName);
	    seller.setPassword(password);
	    
	    List<Seller> sellerList = sellerDao.getSeller(seller);
	    if(sellerList != null){
	        return true;
	    }else{
	        return false;
	    }
	    
	}
	
	public Seller getSellerByPhoneNum(String phoneNum){
	    Seller seller = new Seller();
        seller.setPhoneNumber(phoneNum);
        
        List<Seller> sellerList = sellerDao.getSeller(seller);
        
        if(sellerList == null || sellerList.size()!=1){
           
        	throw new DataAccessDeniedException("seller数据异常");
        }
       
        return (Seller)sellerList.get(0);
	}
	
	 public List<Seller> getSeller(Seller seller){
	     List<Seller> sellerList = sellerDao.getSeller(seller);
	     
	     return sellerList;
	 }

	
}
