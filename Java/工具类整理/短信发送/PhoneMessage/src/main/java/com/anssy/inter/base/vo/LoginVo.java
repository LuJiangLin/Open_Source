/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  LoginVo.java
 * PACKAGE      :  com.anssy.inter.base.vo
 * CREATE DATE  :  2016-8-11
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.inter.base.vo;

/**
 * @author make it
 * @version SVN #V1# #2016-8-11#
 *          登录接口参数辅助类
 */
public class LoginVo {

    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String passwd;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        try {
            //	passwd = BASE64.decryptBASE64(passwd).toString();
        } catch (Exception e) {
        }
        this.passwd = passwd;
    }

}