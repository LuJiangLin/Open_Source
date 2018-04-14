/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  MD5.java
 * PACKAGE      :  com.anssy.webcore.core.encryption
 * CREATE DATE  :  2016-8-11
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.webcore.core.encryption;

import java.security.MessageDigest;

/**
 * @author make it
 * @version SVN #V1# #2016-8-11#
 */
public class MD5 {

    private String inStr;
    private MessageDigest md5;

    public MD5(String inStr) {
        this.inStr = inStr;
        try {
            this.md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String compute() {
        char[] charArray = this.inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];
        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];
        byte[] md5Bytes = this.md5.digest(byteArray);

        StringBuilder hexValue = new StringBuilder();

        for (byte md5Byte : md5Bytes) {
            int val = ((int) md5Byte) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }
}