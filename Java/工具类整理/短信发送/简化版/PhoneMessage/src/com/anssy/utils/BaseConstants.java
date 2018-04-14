/**
 * ������ʽ������޹�˾
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  BaseConstants.java
 * PACKAGE      :  com.anssy.webcore.common
 * CREATE DATE  :  2016-8-17
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.utils;


/**
 * @author make it
 * @version SVN #V1# #2016-8-17#
 *          ϵͳ����������
 */
public class BaseConstants {

    /**
     * �ֻ���֤�뱣�������ʱ�� ��
     */
    public static final long SECONDS = 600;
    /**
     * ��־����ʱ�����
     */
    public static final int LOG_DAYS = 90;
    /***/
    public static final int VALIDITY_DAYS = 90;
    /**
     * ���ڸ�ʽ **
     */
    public final static String DATE_PATTERN = "yyyy-MM-dd";
    /**
     * ���ڸ�ʽ **
     */
    public final static String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    /**
     * ÿҳ�ļ�¼��
     */
    public final static int PAGE_SIZE = 20;
    /**
     * ͼƬURL·��
     */
    public final static String PICTURE_URL = "http://114.55.254.168/app/picture/pictureAction/findImageStreams.action?path=";
    /**
     * ���������ַ
     */
    public final static String ACTIVATE_URL = "http://114.55.254.168/";
    /**
     * ���Ų���
     */
    public final static String CORP_ACCOUNT = "kcjk";
    public final static String USER_ACCOUNT = "admin";
    public final static String SMS_PWD = "191515";
    /**
     * �������
     */
    public final static String EMAIL_HOST = "smtp.qq.com";
    public final static String EMAIL_USER = "qcy-app@qq.com";
    public final static String EMAIL_PASSWD = "gaotou123";

    /**
     * ͼƬ�����λ��
     * APP default.png
     */
    public static String findPictureDir() {
        String dir;
        String os = System.getProperty("os.name");
        if (os.startsWith("win") || os.startsWith("Windows")) {
            dir = "D:/upload/picture/";
        } else {
            dir = "/upload/picture/";
        }
        return dir;
    }

}