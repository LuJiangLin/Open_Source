/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  SHA.java
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
 *          SHA(Secure Hash Algorithm，安全散列算法） 数字签名等密码学应用中重要的工具，被广泛地应用于电子商务等信息安全领域
 */
public class SHA {

    /**
     * SHA加密
     *
     * @param data data
     * @throws Exception
     */
    public static byte[] encryptSHA(byte[] data) throws Exception {
        MessageDigest sha = MessageDigest.getInstance("SHA");
        sha.update(data);
        return sha.digest();
    }

}