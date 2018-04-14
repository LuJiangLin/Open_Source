/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  StatisticsInfoEntity.java
 * PACKAGE      :  com.anssy.venturebar.app.entity
 * CREATE DATE  :  2016-8-30
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.venturebar.app.entity;

import com.anssy.webcore.core.entity.IdEntity;

/**
 * @author make it
 * @version SVN #V1# #2016-8-30#
 *          APP_统计信息
 */
public class StatisticsInfoEntity extends IdEntity {

    private static final long serialVersionUID = 7084734465501828803L;

    /**
     * 日期
     */
    private String appDate;
    /**
     * 登录数
     */
    private Long loginNumber;
    /**
     * 注册数
     */
    private Long registerNumber;
    /**
     * 激活数
     */
    private Long activateNumber;
    /**
     * PV
     */
    private Long pv;

    public String getAppDate() {
        return appDate;
    }

    public void setAppDate(String appDate) {
        this.appDate = appDate;
    }

    public Long getLoginNumber() {
        return loginNumber;
    }

    public void setLoginNumber(Long loginNumber) {
        this.loginNumber = loginNumber;
    }

    public Long getRegisterNumber() {
        return registerNumber;
    }

    public void setRegisterNumber(Long registerNumber) {
        this.registerNumber = registerNumber;
    }

    public Long getActivateNumber() {
        return activateNumber;
    }

    public void setActivateNumber(Long activateNumber) {
        this.activateNumber = activateNumber;
    }

    public Long getPv() {
        return pv;
    }

    public void setPv(Long pv) {
        this.pv = pv;
    }

}