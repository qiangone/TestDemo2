package com.capgemini.wdapp.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.capgemini.wdapp.model.Address;
import com.capgemini.wdapp.model.Appraisal;
import com.capgemini.wdapp.model.Area;
import com.capgemini.wdapp.model.OrderAddress;


@Repository
public interface IAppraisalDao {
	public int addAppraisal(List<Appraisal> list);
	
	
	public List<Appraisal> getAppraisal(Map map);
	
	
	
}
