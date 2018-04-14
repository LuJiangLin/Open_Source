/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  ServiceCategoryEntity.java
 * PACKAGE      :  com.anssy.venturebar.base.entity
 * CREATE DATE  :  2016-8-10
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.venturebar.base.entity;

import com.anssy.webcore.core.entity.IdEntity;

/**
 * @author make it
 * @version SVN #V1# #2016-8-10#
 *          基础_服务类别信息
 */
public class ServiceCategoryEntity extends IdEntity {

    private static final long serialVersionUID = -5507800512628342146L;

    /**
     * 服务类别
     */
    private String serviceName;
    /**
     * 服务级别(1-一级大类,2-二级小类)
     */
    private int serviceLevel;
    /**
     * 父ID
     */
    private Long fatherId;
    /**
     * 样式
     */
    private String style;
    /**
     * 标志
     */
    private int mark;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public int getServiceLevel() {
        return serviceLevel;
    }

    public void setServiceLevel(int serviceLevel) {
        this.serviceLevel = serviceLevel;
    }

    public Long getFatherId() {
        return fatherId;
    }

    public void setFatherId(Long fatherId) {
        this.fatherId = fatherId;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

}