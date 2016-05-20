package com.capgemini.wdapp.vendor.push;

import com.capgemini.wdapp.model.Channel;
import com.capgemini.wdapp.model.Device;

public interface IPushNotificationManager {
	
	void registerDevice(Device device) throws PushMessageException;
	
	void remoteDevice(Long id) throws PushMessageException;
	
	void send(PushMessage msg) throws PushMessageException;
	
	void cleanupTokens() throws PushMessageException;

	void createChannel(Channel channel) throws PushMessageException;
	
}
