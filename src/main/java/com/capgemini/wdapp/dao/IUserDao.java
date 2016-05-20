package com.capgemini.wdapp.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.capgemini.wdapp.model.Cart;
import com.capgemini.wdapp.model.Seller;
import com.capgemini.wdapp.model.Sms;
import com.capgemini.wdapp.model.User;


@Repository
public interface IUserDao {
	public int saveUser(User user);
	public int updateUser(User user);
	public List<User> getUserInfo(String openid);
	
	public List<User> getUser(Map map);
}
