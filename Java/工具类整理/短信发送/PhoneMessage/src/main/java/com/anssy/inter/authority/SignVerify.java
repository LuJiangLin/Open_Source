/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  SignVerify.java
 * PACKAGE      :  com.anssy.inter.authority
 * CREATE DATE  :  2016-8-11
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.inter.authority;

import com.anssy.venturebar.base.entity.UserInfoEntity;
import com.anssy.venturebar.base.server.UserInfoCacheServer;
import com.anssy.webcore.core.context.ApplicationUtil;

/**
 * @author make it
 * @version SVN #V1# #2016-8-11#
 *          sign验证(单例模式)
 */
public class SignVerify {

    private static SignVerify s = new SignVerify();

    private UserInfoCacheServer userInfoCacheServer;

    private SignVerify() {
        userInfoCacheServer = (UserInfoCacheServer) ApplicationUtil.getBean("userInfoCacheServer");
    }

    public static boolean getInstance(String key, String sign) {
        return s.verifySing(key, sign);
    }

    /**
     * 验证sign参数是否有效
     */
    private boolean verifySing(String key, String sing) {
        boolean flag = false;
        try {
            UserInfoEntity user = userInfoCacheServer.findUserInfo(Long.parseLong(key));
            if (user != null && user.getState() == 0 && user.getSign().equals(sing)) {
                flag = true;
            }
        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
        }
        return flag;
    }

}