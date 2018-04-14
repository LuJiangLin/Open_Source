/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  AreaEntity.java
 * PACKAGE      :  com.anssy.venturebar.base.entity
 * CREATE DATE  :  2016-8-9
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.venturebar.base.entity;

import com.anssy.webcore.core.entity.IdEntity;

/**
 * @author make it
 * @version SVN #V1# #2016-8-9#
 *          基础_区县信息
 */
public class AreaEntity extends IdEntity {

    private static final long serialVersionUID = 2449666240469668024L;

    /**
     * 区县ID
     */
    private Long areaId;
    /**
     * 区县名称
     */
    private String area;
    /**
     * 市ID
     */
    private Long cityId;

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

}