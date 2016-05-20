package com.capgemini.wdapp.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.capgemini.wdapp.model.FindPassword;
import com.capgemini.wdapp.model.Seller;


@Repository
public interface IFindPwdDao {
	 
	 
	 public int addFinwpwd(FindPassword fp);
	 
	 public int saveFinwpwd(FindPassword fp);
	 
	 public FindPassword getFindPwdByPhoneNumber(String phoneNumber);
}
