/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  PvInfoEntity.java
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
 *          APP_PV信息
 */
public class PvInfoEntity extends IdEntity {

    private static final long serialVersionUID = -5524180106538641775L;

    /**
     * 日期
     */
    private String appDate;
    /**
     * 小时
     */
    private int pvHour;
    /**
     * PV数量
     */
    private Long pvNum;

    public String getAppDate() {
        return appDate;
    }

    public void setAppDate(String appDate) {
        this.appDate = appDate;
    }

    public int getPvHour() {
        return pvHour;
    }

    public void setPvHour(int pvHour) {
        this.pvHour = pvHour;
    }

    public Long getPvNum() {
        return pvNum;
    }

    public void setPvNum(Long pvNum) {
        this.pvNum = pvNum;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}