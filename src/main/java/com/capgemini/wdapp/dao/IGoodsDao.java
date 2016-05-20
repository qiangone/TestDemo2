package com.capgemini.wdapp.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.capgemini.wdapp.model.Goods;
import com.capgemini.wdapp.model.GoodsCategory;
import com.capgemini.wdapp.model.StockPrice;


@Repository
public interface IGoodsDao {
	public int addGoods(Goods cat);
	public int updateGoods(Goods cat);
	public int deleteGoods(Integer id);
	
	public List<Goods> getGoods(Goods goods);
	
	public List<Goods> getGoodsListByPage(Map<String, Object> conditions);
	
	public int addGoodsCategory(List<GoodsCategory> list);
	public List<GoodsCategory> getGoodsCategory(Integer goodsId);

	public int deleteGoodsCategory(Integer goodsId);
	


	
	
	public int addStockPrice(List<StockPrice> list);
	
	public int updateStockPrice(StockPrice sp);
	
	public int deleteStockPrice(Integer goodsId);
	
	public List<StockPrice> getStockPrice(StockPrice sp);
	
}
