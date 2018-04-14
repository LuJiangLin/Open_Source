/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  UserVo.java
 * PACKAGE      :  com.anssy.inter.base.vo
 * CREATE DATE  :  2016-8-27
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.inter.base.vo;

/**
 * @author make it
 * @version SVN #V1# #2016-8-27#
 *          注册辅助类
 */
public class UserVo {

    /**
     * 手机号码
     */
    private String phone;
    /**
     * Email
     */
    private String email;
    /**
     * 密码
     */
    private String passwd;
    /**
     * 角色
     */
    private int roles;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        try {
            //	passwd=BASE64.decryptBASE64(passwd).toString();
        } catch (Exception e) {
        }
        this.passwd = passwd;
    }

    public int getRoles() {
        return roles;
    }

    public void setRoles(int roles) {
        this.roles = roles;
    }

}