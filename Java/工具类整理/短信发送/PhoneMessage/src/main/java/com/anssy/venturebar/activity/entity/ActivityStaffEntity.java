/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  ActivityStaffEntity.java
 * PACKAGE      :  com.anssy.venturebar.activity.entity
 * CREATE DATE  :  2016-8-10
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.venturebar.activity.entity;

import com.anssy.webcore.core.entity.IdEntity;

import java.util.Date;

/**
 * @author make it
 * @version SVN #V1# #2016-8-10#
 *          活动_活动人员
 */
public class ActivityStaffEntity extends IdEntity {

    private static final long serialVersionUID = 1128986181267807451L;

    /**
     * 活动ID
     */
    private Long activityId;
    /**
     * 参与类型(0-参加活动,1-关注活动)
     */
    private int joinType;
    /**
     * 参与人
     */
    private Long jionId;
    private String jionName;
    /**
     * 参与时间
     */
    private Date jionTime;

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public int getJoinType() {
        return joinType;
    }

    public void setJoinType(int joinType) {
        this.joinType = joinType;
    }

    public Long getJionId() {
        return jionId;
    }

    public void setJionId(Long jionId) {
        this.jionId = jionId;
    }

    public String getJionName() {
        return jionName;
    }

    public void setJionName(String jionName) {
        this.jionName = jionName;
    }

    public Date getJionTime() {
        return jionTime;
    }

    public void setJionTime(Date jionTime) {
        this.jionTime = jionTime;
    }

}