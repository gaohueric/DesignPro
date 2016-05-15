package com.design.monitor.imau.biz;

import com.baidu.yun.channel.auth.ChannelKeyPair;
import com.baidu.yun.channel.client.BaiduChannelClient;
import com.baidu.yun.channel.exception.ChannelClientException;
import com.baidu.yun.channel.exception.ChannelServerException;
import com.baidu.yun.channel.model.PushUnicastMessageRequest;
import com.baidu.yun.channel.model.PushUnicastMessageResponse;
import com.design.imau.utils.other.MDLog;
import com.design.imau.utils.other.PushMessage;
import com.design.monitor.imau.request.DataConstant;

public class NotificationBiz {
	private static final String TAG = NotificationBiz.class.getSimpleName();
	private static MDLog log = MDLog.getLog();

	private static NotificationBiz notification;
	private String apiKey = DataConstant.NOTIFICATION_APIKEY;
	private String secretKey = DataConstant.NOTIFICATION_SECRETKEY;
	private BaiduChannelClient channelClient;

	private NotificationBiz() {
		// 初始化
		ChannelKeyPair pair = new ChannelKeyPair(apiKey, secretKey);
		channelClient = new BaiduChannelClient(pair);

		log.debug(TAG, "Notification", "初始化成功");
	}

	public static NotificationBiz getInstance() {
		if (notification == null) {
			notification = new NotificationBiz();
		}
		return notification;
	}

	/**
	 * 发送推送消息
	 * 
	 * @param channelId
	 * @param userId
	 * @return
	 */
	public boolean sendNotification(Long channelId, String userId,
			PushMessage msg) {

		try {
			PushUnicastMessageRequest request = new PushUnicastMessageRequest();
			// 3是android设备
			request.setDeviceType(3);
			request.setChannelId(channelId);
			request.setUserId(userId);
			request.setMessageType(1);

			// 设置推送内容
			request.setMessage("{\"title\":\"" + msg.getTitle()
					+ "\",\"description\":\"" + msg.getDescription() + "\"}");

			PushUnicastMessageResponse response = channelClient
					.pushUnicastMessage(request);

			// 推送成功
			log.debug(TAG, "sendNotification",
					"推送成功：" + response.getSuccessAmount());
			return true;
		} catch (ChannelClientException e) {
			log.error(TAG, "ChannelClientException", e);
		} catch (ChannelServerException e) {
			log.error(TAG, "ChannelServerException", e);
		}
		return false;
	}

}
