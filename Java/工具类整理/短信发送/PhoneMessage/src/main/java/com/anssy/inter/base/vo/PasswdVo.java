/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  PasswdVo.java
 * PACKAGE      :  com.anssy.inter.base.vo
 * CREATE DATE  :  2016-8-13
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.inter.base.vo;

/**
 * @author make it
 * @version SVN #V1# #2016-8-13#
 *          用户信息接口参数辅助类
 */
public class PasswdVo {

    /**
     * 老密码
     */
    private String oldPasswd;
    /**
     * 新密码
     */
    private String newPasswd;

    public String getOldPasswd() {
        return oldPasswd;
    }

    public void setOldPasswd(String oldPasswd) {
        try {
            //	oldPasswd=BASE64.decryptBASE64(oldPasswd).toString();
        } catch (Exception e) {
        }
        this.oldPasswd = oldPasswd;
    }

    public String getNewPasswd() {
        return newPasswd;
    }

    public void setNewPasswd(String newPasswd) {
        try {
            //	newPasswd=BASE64.decryptBASE64(newPasswd).toString();
        } catch (Exception e) {
        }
        this.newPasswd = newPasswd;
    }

}