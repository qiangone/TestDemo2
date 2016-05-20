/**
 * CAPGEMINI APPLIANCE CHAINS.
 * Copyright (c) 2015-2015 All Rights Reserved.
 */
package com.capgemini.wdapp.service;

import java.util.List;

import com.capgemini.wdapp.model.Category;
import com.capgemini.wdapp.model.Seller;

/**
 * 
 * functional description： seller interface
 * @author  zhihuang@capgemini.com
 * @created Dec 18, 2015 10:37:13 AM
 * @date Dec 18, 2015 10:37:13 AM
 */

public interface ICategoryService {
	public int addCategory(Category cat);
	
	public int updateCategory(Category cat);
	
	public Category getCategoryById(Integer id);
	
	public List<Category> getCategoryListByParentId(Integer parentId);
	
	public List<Category> getAllCategoryList(Integer shopId);
	
	public List<Category> getBuyerCategoryList(Integer shopId);
	
	public int deleteCategory(Integer id);
	
	public List<Category> getCategoryByName(String name, Integer shopId);
}
