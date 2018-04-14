/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  TypeVo.java
 * PACKAGE      :  com.anssy.inter.activity.vo
 * CREATE DATE  :  2016-8-14
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.inter.activity.vo;

/**
 * @author make it
 * @version SVN #V1# #2016-8-14#
 *          活动类型辅助类
 */
public class TypeVo {

    /**
     * 活动级别(1-一级,2-二级)
     */
    private int activityLevel;
    /**
     * 父ID(活动级别为一级时，父ID值为0)
     */
    private Long fatherId;

    public int getActivityLevel() {
        return activityLevel;
    }

    public void setActivityLevel(int activityLevel) {
        this.activityLevel = activityLevel;
    }

    public Long getFatherId() {
        return fatherId;
    }

    public void setFatherId(Long fatherId) {
        this.fatherId = fatherId;
    }

}
