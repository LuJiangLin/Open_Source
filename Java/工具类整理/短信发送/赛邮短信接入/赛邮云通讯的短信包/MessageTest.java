package com.anssy.test;


import lib.MessageSend;
import config.AppConfig;
import config.MessageConfig;

/**
 * @author Mr.薛
 * 短信发送通知 测试主方法
 */
public class MessageTest {

	private static AppConfig createMessageConfig() {
		AppConfig config = new MessageConfig();
//		APP ID
		config.setAppId("17448");
//		APP key
		config.setAppKey("342657c8be92fec6ba8c06b59b4b9401");
//		加密方式   常规 无加密
		config.setSignType("normal");
		
		return config;
	}
	
	
	
	public static void main(String[] args) {
//		AppConfig config = ConfigLoader.load(ConfigLoader.ConfigType.Message);
		AppConfig config = new MessageTest().createMessageConfig();
		
		MessageSend submail = new MessageSend(config);
		submail.addTo("15072278077");
		submail.addContent("【香港升學咨詢中心】親，您有未處理的日程，請及時處理喲...");
		submail.send();
	}

}
