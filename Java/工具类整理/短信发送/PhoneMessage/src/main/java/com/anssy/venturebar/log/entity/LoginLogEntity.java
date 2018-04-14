/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  LoginLogEntity.java
 * PACKAGE      :  com.anssy.venturebar.log.entity
 * CREATE DATE  :  2016-8-25
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.venturebar.log.entity;

import com.anssy.webcore.core.entity.IdEntity;

import java.util.Date;

/**
 * @author make it
 * @version SVN #V1# #2016-8-25#
 *          日志_登录日志
 */
public class LoginLogEntity extends IdEntity {

    private static final long serialVersionUID = -5422083490160393994L;

    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 登录时间
     */
    private Date loginTime;
    /**
     * 登出时间
     */
    private Date logoutTime;
    /**
     * 手机平台
     */
    private String phoneTerrace;
    /**
     * 手机版本
     */
    private String phoneVersion;
    /**
     * app版本
     */
    private String appVersion;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public Date getLogoutTime() {
        return logoutTime;
    }

    public void setLogoutTime(Date logoutTime) {
        this.logoutTime = logoutTime;
    }

    public String getPhoneTerrace() {
        return phoneTerrace;
    }

    public void setPhoneTerrace(String phoneTerrace) {
        this.phoneTerrace = phoneTerrace;
    }

    public String getPhoneVersion() {
        return phoneVersion;
    }

    public void setPhoneVersion(String phoneVersion) {
        this.phoneVersion = phoneVersion;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

}