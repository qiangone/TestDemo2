package com.capgemini.wdapp.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.capgemini.wdapp.model.Seller;
import com.capgemini.wdapp.model.Sms;


@Repository
public interface ISmsDao {
	public int saveSms(Sms sms);
	public int updateSms(Sms sms);
	public List<Sms> getSmsByPhoneNumberAndOrType(Sms sms);
}
