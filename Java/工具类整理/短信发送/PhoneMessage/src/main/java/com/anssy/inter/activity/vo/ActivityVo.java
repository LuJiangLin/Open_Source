/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  ActivityVo.java
 * PACKAGE      :  com.anssy.inter.activity.vo
 * CREATE DATE  :  2016-8-27
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.inter.activity.vo;

import java.util.Date;

/**
 * @author make it
 * @version SVN #V1# #2016-8-27#
 *          活动辅助类
 */
public class ActivityVo {

    private Long id;
    /**
     * 图片
     */
    private String image;
    /**
     * 活动名称
     */
    private String activityName;
    /**
     * 活动类型
     */
    private Long activityType;
    /**
     * 主办方
     */
    private String sponsor;
    /**
     * 开始时间
     */
    private Date beginTime;
    /**
     * 结束时间
     */
    private Date endTime;
    /**
     * 省ID
     */
    private Long provinceId;
    /**
     * 市ID
     */
    private Long cityId;
    /**
     * 区县ID
     */
    private Long areaId;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public Long getActivityType() {
        return activityType;
    }

    public void setActivityType(Long activityType) {
        this.activityType = activityType;
    }

    public String getSponsor() {
        return sponsor;
    }

    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
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

}