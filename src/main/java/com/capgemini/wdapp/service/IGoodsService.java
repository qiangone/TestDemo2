/**
 * CAPGEMINI APPLIANCE CHAINS.
 * Copyright (c) 2015-2015 All Rights Reserved.
 */
package com.capgemini.wdapp.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.capgemini.wdapp.model.Goods;
import com.capgemini.wdapp.model.PageResults;
import com.capgemini.wdapp.model.Pagination;
import com.capgemini.wdapp.model.StockPrice;

/**
 * 
 * functional description： seller interface
 * @author  zhihuang@capgemini.com
 * @created Dec 18, 2015 10:37:13 AM
 * @date Dec 18, 2015 10:37:13 AM
 */

public interface IGoodsService {
	public int addGoods(Goods cat, CommonsMultipartFile[] files);
	
	public int updateGoods(Goods origi, Goods newGood, CommonsMultipartFile[] files);
	
	public int updateGoods(Goods goods);
	
	public int deleteGoods(Integer id);
	
	public List<Goods> getGoods(Goods goods);
	
	public Goods getGoodsById(Integer id);
	
   public PageResults<Goods> getGoodsListByPage(Map<String, Object> map, Pagination page) ;
   
   public int updateStockPrice(StockPrice sp);
   
   public StockPrice getStockPriceById(Integer id);

	
	
	
	
}
