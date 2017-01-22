package com.dzdp.rs.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.spi.LoggerFactory;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

import com.dzdp.rs.conf.ConfigManager;

public class JPushUtils {
		private static Log LOG = LogFactory.getLog(JPushUtils.class);
		private static final String appKey = ConfigManager.getInstance().getText("/Mobile/jpush/appKey");
		private static final String masterSecret = ConfigManager.getInstance().getText("/Mobile/jpush/masterSecret");
		private static JPushClient client; 
		private static PushPayload payload;
		private static PushResult result;
		
		
		public static JPushClient init(){
			if(client==null){
				client = new JPushClient(masterSecret,appKey,3);
			}
			return client;
		}
		
		public static boolean push(PushPayload payload){
			boolean sendResult = false;
			try {
				init();
				result = client.sendPush(payload);
			    System.out.println("success");
		        LOG.info("Got result - " + result);
		        sendResult = true;
			} catch (APIConnectionException e) {
				sendResult = false;
				LOG.error("Connection error. Should retry later. ", e);
				e.printStackTrace();
			} catch (APIRequestException e) {
				sendResult = false;
				LOG.error("Error response from JPush server. Should review and fix it. ", e);
	            LOG.info("HTTP Status: " + e.getStatus());
	            LOG.info("Error Code: " + e.getErrorCode());
	            LOG.info("Error Message: " + e.getErrorMessage());
	            LOG.info("Msg ID: " + e.getMsgId());
				e.printStackTrace();
			}
			return sendResult;
		}
		
		public static boolean buildPushForAllAlert(String alert) {
			payload = PushPayload.alertAll(alert);
		    return  push(payload) ;
		}
		
		public static boolean buildPushObject_android_and_ios(String tag,String content,String title,String key,String value) {
	         payload = PushPayload.newBuilder()
	                .setPlatform(Platform.android_ios())
	                .setAudience(Audience.tag(tag))
	                .setNotification(Notification.newBuilder().setAlert(content)
	                .addPlatformNotification(AndroidNotification.newBuilder().setTitle(title).build())
	                .addPlatformNotification(IosNotification.newBuilder().incrBadge(1).addExtra(key, value).build()).build()).build();
	        return push(payload);
	    }
		
		public static void main(String[] args) {
			JPushUtils.buildPushForAllAlert("For Test");
			//System.out.println(2%6);
		}
}
