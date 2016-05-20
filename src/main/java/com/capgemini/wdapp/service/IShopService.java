package com.capgemini.wdapp.service;

import java.util.List;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.capgemini.wdapp.model.Shop;

public interface IShopService {
//    public int saveShop(Shop shop) throws Exception;
	
	
//	public String uploadShopLogoImage(CommonsMultipartFile logoImg) ;
//	
//	public String uploadBackgroundImage(CommonsMultipartFile bcgImg) ;
	
	
	public int addShop(Shop shop, CommonsMultipartFile logoImg, CommonsMultipartFile bcgImg);
	
	public int updateShop(Shop shop, CommonsMultipartFile logoImg, CommonsMultipartFile bcgImg);
	public List<Shop> getShop(Shop shop);
	
	public Shop getShopBySellerId(Integer sellerId);
	
	public Shop getShopById(Integer shopId);

	public int updateShopWechatKey(Shop shop);
	
	public int unbindWechatKey(Shop shop);

	public Shop getShopByWeChatAppKey(String weChatAppKey);
	
}
