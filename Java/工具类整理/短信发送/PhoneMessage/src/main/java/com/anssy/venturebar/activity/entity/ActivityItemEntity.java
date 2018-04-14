/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  ActivityItemEntity.java
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
 *          活动_活动明细
 */
public class ActivityItemEntity extends IdEntity {

    private static final long serialVersionUID = 6144563135502288398L;

    /**
     * 活动ID
     */
    private Long activityId;
    /**
     * 活动描述
     */
    private String describe;
    /**
     * 活动地点
     */
    private String activitySite;
    /**
     * 上限人数
     */
    private String upperNumber;
    /**
     * 联系人
     */
    private String linkman;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 报名人数
     */
    private int joinNumber;
    /**
     * 关注人数
     */
    private int careNumber;
    /**
     * 活动状态(0-未开始,1-进行中,2已结束)
     */
    private int activityState;
    /**
     * 发布人
     */
    private Long publishId;
    private String publishName;
    /**
     * 发布时间
     */
    private Date publishTime;
    /**
     * 留言量
     */
    private Long leaveNumber;
    /**
     * 收藏量
     */
    private Long collectNumber;

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getActivitySite() {
        return activitySite;
    }

    public void setActivitySite(String activitySite) {
        this.activitySite = activitySite;
    }

    public String getUpperNumber() {
        return upperNumber;
    }

    public void setUpperNumber(String upperNumber) {
        this.upperNumber = upperNumber;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getJoinNumber() {
        return joinNumber;
    }

    public void setJoinNumber(int joinNumber) {
        this.joinNumber = joinNumber;
    }

    public int getCareNumber() {
        return careNumber;
    }

    public void setCareNumber(int careNumber) {
        this.careNumber = careNumber;
    }

    public int getActivityState() {
        return activityState;
    }

    public void setActivityState(int activityState) {
        this.activityState = activityState;
    }

    public Long getPublishId() {
        return publishId;
    }

    public void setPublishId(Long publishId) {
        this.publishId = publishId;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public Long getLeaveNumber() {
        return leaveNumber;
    }

    public void setLeaveNumber(Long leaveNumber) {
        this.leaveNumber = leaveNumber;
    }

    public Long getCollectNumber() {
        return collectNumber;
    }

    public void setCollectNumber(Long collectNumber) {
        this.collectNumber = collectNumber;
    }

    public String getPublishName() {
        return publishName;
    }

    public void setPublishName(String publishName) {
        this.publishName = publishName;
    }

}