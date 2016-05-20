/**
 * CAPGEMINI APPLIANCE CHAINS.
 * Copyright (c) 2015-2015 All Rights Reserved.
 */
package com.capgemini.wdapp.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.wdapp.dao.ICategoryDao;
import com.capgemini.wdapp.model.Category;
import com.capgemini.wdapp.service.ICategoryService;

/**
 * 
 * functional description： seller interface
 * @author  zhihuang@capgemini.com
 * @created Dec 18, 2015 10:37:13 AM
 * @date Dec 18, 2015 10:37:13 AM
 */

@Service(value = "categoryService")
public class CategoryServiceImpl implements ICategoryService {

	private final static Log logger = LogFactory.getLog(CategoryServiceImpl.class);
	
	@Autowired
	private ICategoryDao catDao;
	
	public List<Category> getCategoryByName(String name,Integer shopId){
		Map map = new HashMap();
		map.put("name", name);
		map.put("shopId", shopId);
		return catDao.getCategoryByName(map);
	}
	
	
	public int addCategory(Category cat){
		return catDao.addCategory(cat);
		
	}
	public int updateCategory(Category cat){
		return catDao.updateCategory(cat);
		
	}
	
	public int deleteCategory(Integer id){
		return catDao.deleteCategory(id);
	}
	
	
	public Category getCategoryById(Integer id){
		Category self = catDao.getCategoryById(id);
		List<Category> list = catDao.getCategoryListByParentId(id);
		if(list != null && list.size()>0){
			List<Category> list2 = new ArrayList<Category>();
			for(Category c : list){
				Category c2 = getCategoryById(c.getId());
				list2.add(c2);
			}
			self.setSubCategoryList(list2);//set sub
		}
		
		
		return self;
	}
	
	public List<Category> getAllCategoryList(Integer shopId){
		List<Category> list = catDao.getAllCategoryList(shopId);
//		if(list == null){
//			return null;
//		}
		
//		List<Category> retList = new ArrayList<Category>();
//		for(Category cat : list){
//			Category c = getCategoryById(cat.getId());
//			retList.add(c);
//		}
//		
//		return retList;
		return list;
	}
	
	public List<Category> getBuyerCategoryList(Integer shopId){
		return catDao.getBuyerCategoryList(shopId);
	}
	
	

	public List<Category> getCategoryListByParentId(Integer parentId){
		return catDao.getCategoryListByParentId(parentId);
	}
	
	
}
