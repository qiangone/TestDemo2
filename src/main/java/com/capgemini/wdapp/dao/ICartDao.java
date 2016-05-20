package com.capgemini.wdapp.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.capgemini.wdapp.model.Cart;


@Repository
public interface ICartDao {
	public int addCart(Cart addr);
	
	public int updateCart(Cart addr);
	
	public int addCardList(List<Cart> list);
	
	public int deleteCartbyId(Integer id);
	
	public int deleteCart(Map map);
	
	public int deleteCartbyUserId(Integer userId);

	public List<Cart> getCart(Map map);
	
}
