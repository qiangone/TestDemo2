package com.capgemini.wdapp.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.capgemini.wdapp.model.Seller;


@Repository
public interface ISellerDao {
	public int registerSeller(Seller seller);
	
	public int updateSeller(Seller seller);
	
	 public List<Seller> getSeller(Seller seller);
	 
	 public Seller getSellerById(int id);
}
