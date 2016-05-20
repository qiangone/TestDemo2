package com.capgemini.wdapp.vendor.push;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PushMessage {
	
	public static final String KEY_PAYLOAD = "payload";
	public static final String KEY_ACTION = "action";
	private Long id;
	private String appId;
	private String sender; //the display name of sender
	private String recipient; //receiver
	private String message; //content
	private byte status;
	private int badge;
	private String sound; //
	private byte sendCount;
	private Map<String,String> customFields;
	private Date createdTime;
	private Date sendTime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public byte getStatus() {
		return status;
	}
	public void setStatus(byte status) {
		this.status = status;
	}
	public int getBadge() {
		return badge;
	}
	public void setBadge(int badge) {
		this.badge = badge;
	}
	public String getSound() {
		return sound;
	}
	public void setSound(String sound) {
		this.sound = sound;
	}
	public byte getSendCount() {
		return sendCount;
	}
	public void setSendCount(byte sendCount) {
		this.sendCount = sendCount;
	}
	public Map<String, String> getCustomFelds() {
		return customFields;
	}
	public void setCustomFelds(Map<String, String> customFelds) {
		this.customFields = customFelds;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	public String getRecipient() {
		return recipient;
	}
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public void addCustomField(String key, String value) {
		if(null == customFields) {
			customFields = new HashMap<String, String>();
		}
		
		customFields.put(key, value);
	}
	
}
