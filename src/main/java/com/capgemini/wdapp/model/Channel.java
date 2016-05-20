package com.capgemini.wdapp.model;


public class Channel {
	private int channelType;
	private String channelAppSecret;
	private String channelAppKey;
	private String channelMasterKey;
	private String channelName;
	private String appId;
	private String mpKey; //公众号key，微店里用的是shopId的值；
	public int getChannelType() {
		return channelType;
	}
	public void setChannelType(int channelType) {
		this.channelType = channelType;
	}
	public String getChannelAppSecret() {
		return channelAppSecret;
	}
	public void setChannelAppSecret(String channelAppSecret) {
		this.channelAppSecret = channelAppSecret;
	}
	public String getChannelAppKey() {
		return channelAppKey;
	}
	public void setChannelAppKey(String channelAppKey) {
		this.channelAppKey = channelAppKey;
	}
	public String getChannelMasterKey() {
		return channelMasterKey;
	}
	public void setChannelMasterKey(String channelMasterKey) {
		this.channelMasterKey = channelMasterKey;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getMpKey() {
		return mpKey;
	}
	public void setMpKey(String mpKey) {
		this.mpKey = mpKey;
	}
	
	
}
