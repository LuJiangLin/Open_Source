package com.anssy.phone;

import java.util.Date;

import com.anssy.utils.SmsUtil;

/**
 * @author Mr.Ѧ
 *
 */
public class MessageTest {

//	���ŷ��Ͳ���
	public static void main(String[] args) {
		String sms = new SmsUtil().sendSms("18772101110","���ã�����ԤԼ2017-8-12 14:25:23���ι�����԰������Ӵ����������ʽ������޹�˾��");
      System.out.println(sms+new Date());
	}
	
	
}
