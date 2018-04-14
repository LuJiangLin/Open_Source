/**
 * ������ʽ������޹�˾
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  SmsUtil.java
 * PACKAGE      :  com.lt.core.utils
 * CREATE DATE  :  2016-8-27
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * @author make it
 * @version SVN #V1# #2016-8-27#
 */
public class SmsUtil {


//    public static void main(String[] args) {
////        String sms = new SmsUtil().sendSms("18772101110","���ã����ڲ��Զ���Ӵ����������ʽ������޹�˾��");
//        String sms = new SmsUtil().sendSms("18772101110","���ã�����ԤԼ2017-8-12 14:25:23���ι�����԰������Ӵ����������ʽ������޹�˾��");
//        System.out.println(sms+new Date());
//    }





    /**
     * ���Ͷ���
     */
    public String sendSms(String mobile, String content) {
        String url = "http://oa-sms.com/sendSms.action";
        String corpAccount = BaseConstants.CORP_ACCOUNT;
        String userAccount = BaseConstants.USER_ACCOUNT;
        String pwd = BaseConstants.SMS_PWD;
        try {
            content = URLEncoder(content, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        pwd = SmsMD5.MD5(pwd);
        return SendToHttp(url, "corpAccount=" + corpAccount + "&userAccount=" + userAccount + "&pwd=" + pwd + "&mobile=" + mobile + "&content=" + content);
    }

    /**
     * �鿴���
     */
    public String balance() {
        String url = "http://oa-sms.com/balance.action";
        String corpAccount = BaseConstants.CORP_ACCOUNT;
        String userAccount = BaseConstants.USER_ACCOUNT;
        String pwd = BaseConstants.SMS_PWD;
        pwd = SmsMD5.MD5(pwd);
        return SendToHttp(url, "corpAccount=" + corpAccount + "&userAccount=" + userAccount + "&pwd=" + pwd);
    }

    /**
     * @param url       ���� URL
     * @param parameter �����б�
     * @return sting
     */
    private static String SendToHttp(String url, String parameter) {
        StringBuilder sb = new StringBuilder();
        HttpURLConnection connection;
        String connect_err = "error";
        try {
            connection = (HttpURLConnection) (new URL(url)).openConnection();
        } catch (Exception ex) {
            return connect_err;
        }
        if (connection != null) {
            try {
                connection.setRequestMethod("POST"); // ע��POSTΪ��д�ķ�ʽ�ſ��ԣ���ʵ���в��Թ�����������
                connection.setRequestProperty("content-type", "application/x-www-form-urlencoded");
                System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// ���ó�ʱʱ��
                System.setProperty("sun.net.client.defaultReadTimeout", "30000");
                connection.setDoOutput(true);
                OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                writer.write(parameter);
                writer.flush();
                writer.close();
                BufferedReader read;
                read = new BufferedReader(new InputStreamReader(
                        connection.getInputStream()));
                String str;
                while ((str = read.readLine()) != null) {
                    sb.append(str.trim());
                }
                read.close();// �رն�ȡ��
            } catch (Exception ex) {
                ex.printStackTrace();
                return "timeout";
            } finally {
                connection.disconnect();
            }
            return sb.toString();
        } else {
            return connect_err;
        }
    }

    /**
     * @param str  ����
     * @param type �����ʽ utf-8 ISO8859_1��gbk,gb2312
     * @return string
     * @throws UnsupportedEncodingException
     */
    public static String URLEncoder(String str, String type) throws UnsupportedEncodingException {
        if (str == null) {
            return null;
        }
        return java.net.URLEncoder.encode(str, type);
    }

    public Long findBalance() {
        Long balance = 0L;
        try {
            String res = balance();
            if (StringUtils.isNotBlank(res)) {
                String[] mes = res.split("#");
                if (mes.length >= 2) {
                    if (NumberUtils.isNumber(mes[1])) {
                        balance = NumberUtils.toLong(mes[1]);
                    }
                }
            }
        } catch (Exception e) {
            balance = -1L;
            e.printStackTrace();
        }
        return balance;
    }

}