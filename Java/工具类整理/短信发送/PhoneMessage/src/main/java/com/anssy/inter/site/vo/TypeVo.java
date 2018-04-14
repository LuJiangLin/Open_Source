/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  TypeVo.java
 * PACKAGE      :  com.anssy.inter.site.vo
 * CREATE DATE  :  2016-8-17
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.inter.site.vo;

/**
 * @author make it
 * @version SVN #V1# #2016-8-17#
 *          场地类型辅助类
 */
public class TypeVo {

    /**
     * 场地级别(1-一级,2-二级)
     */
    private int siteLevel;
    /**
     * 父ID(场地级别为一级时，父ID值为0)
     */
    private Long fatherId;

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