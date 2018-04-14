/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  SiteTypeEntity.java
 * PACKAGE      :  com.anssy.venturebar.base.entity
 * CREATE DATE  :  2016-8-17
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.venturebar.base.entity;

import com.anssy.webcore.core.entity.IdEntity;

/**
 * @author make it
 * @version SVN #V1# #2016-8-17#
 *          基础_场地类型
 */
public class SiteTypeEntity extends IdEntity {

    private static final long serialVersionUID = -2448978426956620560L;

    /**
     * 场地类型
     */
    private String siteName;
    /**
     * 场地级别(1-一级,2-二级)
     */
    private int siteLevel;
    /**
     * 父ID
     */
    private Long fatherId;

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public int getSiteLevel() {
        return siteLevel;
    }

    public void setSiteLevel(int siteLevel) {
        this.siteLevel = siteLevel;
    }

    public Long getFatherId() {
        return fatherId;
    }

    public void setFatherId(Long fatherId) {
        this.fatherId = fatherId;
    }

}