package com.capgemini.wdapp.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.capgemini.wdapp.model.Device;


@Repository
public interface IDeviceDao {
	public int saveDevice(Device device);
	public int updateDevice(Device device);
	public List<Device> findByToken(String token);
	public List<Device> findByUserId(Long userId); //for weidian app ,this userId means sellerId
}
