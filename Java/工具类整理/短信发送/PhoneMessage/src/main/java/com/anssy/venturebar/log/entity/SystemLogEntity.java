/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  SystemLogEntity.java
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
 *          日志_系统日志
 */
public class SystemLogEntity extends IdEntity {

    private static final long serialVersionUID = 2795603834380736633L;

    /**
     * 记录时间
     */
    private Date logTime;
    /**
     * 日志内容
     */
    private String logDesc;

    public Date getLogTime() {
        return logTime;
    }

    public void setLogTime(Date logTime) {
        this.logTime = logTime;
    }

    public String getLogDesc() {
        return logDesc;
    }

    public void setLogDesc(String logDesc) {
        this.logDesc = logDesc;
    }

}