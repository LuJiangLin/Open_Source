package com.anssy.phone;

import java.util.Date;

import com.anssy.utils.SmsUtil;

/**
 * @author Mr.薛
 *
 */
public class MessageTest {

//	短信发送测试
	public static void main(String[] args) {
		String sms = new SmsUtil().sendSms("18772101110","您好，张三预约2017-8-12 14:25:23来参观您的园区场地哟！【湖北安式软件有限公司】");
      System.out.println(sms+new Date());
	}
	
	
}
