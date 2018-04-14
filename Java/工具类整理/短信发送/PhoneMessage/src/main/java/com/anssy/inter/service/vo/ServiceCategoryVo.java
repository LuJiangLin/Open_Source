/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  ServiceCategoryVo.java
 * PACKAGE      :  com.anssy.inter.service.vo
 * CREATE DATE  :  2016-8-18
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.inter.service.vo;

/**
 * @author make it
 * @version SVN #V1# #2016-8-18#
 *          服务类别辅助类
 */
public class ServiceCategoryVo {

    /**
     * 服务级别(1-一级大类,2-二级小类)
     */
    private int serviceLevel;
    /**
     * 父ID
     */
    private Long fatherId;

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

}