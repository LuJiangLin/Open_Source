/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  StringRandomUtil.java
 * PACKAGE      :  com.anssy.webcore.common
 * CREATE DATE  :  2016-8-19
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.webcore.common;

import java.util.Random;

/**
 * @author make it
 * @version SVN #V1# #2016-8-19#
 *          生成随机字符串
 */
public class StringRandomUtil {

    /**
     * 生成随机字符串
     *
     * @param length 长度
     */
    public static String RandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int num = random.nextInt(62);
            sb.append(str.charAt(num));
        }
        return sb.toString();
    }

    /**
     * 生成随机验证码(首位不能为0)
     *
     * @param length 长度
     */
    public static Long RandomCode(int length) {
        String first = "123456789";
        String str = "0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        sb.append(first.charAt(random.nextInt(9)));
        for (int i = 1; i < length; i++) {
            int num = random.nextInt(10);
            sb.append(str.charAt(num));
        }
        return Long.parseLong(sb.toString());
    }

}