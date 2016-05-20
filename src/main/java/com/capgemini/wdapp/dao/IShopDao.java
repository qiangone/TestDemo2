package com.capgemini.wdapp.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.capgemini.wdapp.model.Shop;


@Repository
public interface IShopDao {
	public int addShop(Shop shop);
	
	
	public int updateShop(Shop shop);
	
	public List<Shop> getShop(Shop shop);	
	
	public List<Shop> getShopByWeChatAppKey(String weChatAppKey);
	
	
}
