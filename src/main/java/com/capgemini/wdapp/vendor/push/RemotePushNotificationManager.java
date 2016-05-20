package com.capgemini.wdapp.vendor.push;

import java.io.IOException;

import com.capgemini.wdapp.model.Channel;
import com.capgemini.wdapp.model.Device;
import com.capgemini.wdapp.response.WDResponse;

public class RemotePushNotificationManager implements IPushNotificationManager{
	private String baseUrl;
	private String appId;
	private String appToken;
	
	public static final String REG_DEVICE_PATH = "device/register";
	public static final String SEND_MESSAGE_PATH = "push/send";
	public static final String CREATE_CHANNEL_PATH = "channel/create";
	public static final int DEFAULT_PUSH_TYPE = PushConstants.CHANNEL_APNS_IOS;
	private RESTHttpClient restClient;
	public RemotePushNotificationManager(String baseUrl, String appId, String appToken) {
		super();
		this.baseUrl = baseUrl;
		this.appId = appId;
		this.appToken = appToken;
		restClient = new RESTHttpClient(baseUrl);
		restClient.setToken("token", appToken);
	}
	
	@Override
	public void registerDevice(Device device) throws PushMessageException {
		WDResponse res;
		device.setAppId(appId);
		try {
			res = restClient.doPost(REG_DEVICE_PATH,device);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void send(PushMessage msg) throws PushMessageException {
		WDResponse res;
		try {
			msg.setAppId(appId);
			res = restClient.doPost(SEND_MESSAGE_PATH, msg);
		} catch (IOException e) {
			throw new PushMessageException(e.getMessage());
		}
	}

	@Override
	public void remoteDevice(Long id) throws PushMessageException {
		WDResponse res;
		try {
			res = restClient.doPost(REG_DEVICE_PATH + "/" + String.valueOf(id));
		} catch (IOException e) {
			throw new PushMessageException(e.getMessage());
		}
	}

	@Override
	public void cleanupTokens() throws PushMessageException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createChannel(Channel channel) throws PushMessageException {
		WDResponse res;
		try {
			res = restClient.doPost(CREATE_CHANNEL_PATH,channel);
		} catch (IOException e) {
			throw new PushMessageException(e.getMessage());
		}
		
	}
}
