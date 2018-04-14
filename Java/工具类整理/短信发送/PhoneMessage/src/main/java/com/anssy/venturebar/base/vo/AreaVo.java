/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  AreaVo.java
 * PACKAGE      :  com.anssy.venturebar.base.vo
 * CREATE DATE  :  2016-8-24
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.venturebar.base.vo;

import java.io.Serializable;

/**
 * @author make it
 * @version SVN #V1# #2016-8-24#
 *          省市区县辅助类
 */
public class AreaVo implements Serializable {

    private static final long serialVersionUID = -816112140561548646L;

    /**
     * 省市区县ID
     */
    private Long areaId;
    /**
     * 省市区县名称
     */
    private String areaName;

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

}