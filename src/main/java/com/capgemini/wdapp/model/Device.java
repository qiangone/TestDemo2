package com.capgemini.wdapp.model;

import java.util.Date;

public class Device {
	public static final int DEVICE_TYPE_ANDROID = 1;
	public static final int DEVICE_TYPE_IOS = 2;
	public static final int DEVICE_TYPE_WECHAT = 3;
	
	
	private Integer id;
    
	private String appId;
	
    private Integer userId; //for weidian project, it's relation to sellerId of seller table. or userId for user table.
    
    private String name; //device name ""
    
    private String osVersion; //os version of device,"9.1.2"
    
    private Integer deviceType; //device type, "1 - Android" "2 - iOS"
    
    private Integer channelType; //push channel, "1 - Getui for android" "2 - APNS" ,6:"wechat"
    
    private String token; //token for push notification
    
    private Integer status; //status of the device.
    
    private String mpKey;
    
    private Date updateTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOsVersion() {
		return osVersion;
	}

	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}

	public Integer getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}

	public Integer getChannelType() {
		return channelType;
	}

	public void setChannelType(Integer channelType) {
		this.channelType = channelType;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public static byte getDeviceTypeAndroid() {
		return DEVICE_TYPE_ANDROID;
	}

	public static byte getDeviceTypeIos() {
		return DEVICE_TYPE_IOS;
	}

	public String getMpKey() {
		return mpKey;
	}

	public void setMpKey(String mpKey) {
		this.mpKey = mpKey;
	}
    
}
