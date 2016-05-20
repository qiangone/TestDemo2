package com.capgemini.wdapp.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.capgemini.wdapp.model.Category;


@Repository
public interface ICategoryDao {
	public int addCategory(Category cat);
	
	public int updateCategory(Category cat);
	
	public int deleteCategory(Integer id);
	
	public Category getCategoryById(Integer id);
	
	public List<Category> getCategoryListByParentId(Integer parentId);
	
	public List<Category> getAllCategoryList(Integer shopId);
	
	public List<Category> getCategoryByName(Map map);
	
	public List<Category> getBuyerCategoryList(Integer shopId);
	
	
}
