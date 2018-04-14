/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  EpibolyItemEntity.java
 * PACKAGE      :  com.anssy.venturebar.team.entity
 * CREATE DATE  :  2016-8-10
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.venturebar.team.entity;

import com.anssy.webcore.core.entity.IdEntity;

import java.util.Date;

/**
 * @author make it
 * @version SVN #V1# #2016-8-10#
 *          团队_众包明细
 */
public class EpibolyItemEntity extends IdEntity {

    private static final long serialVersionUID = -3785787698522911023L;

    /**
     * 众包ID
     */
    private Long epibolyId;
    /**
     * 描述
     */
    private String describe;
    /**
     * 联系人
     */
    private String linkman;
    /**
     * 联系电话
     */
    private String phone;
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

    public Long getEpibolyId() {
        return epibolyId;
    }

    public void setEpibolyId(Long epibolyId) {
        this.epibolyId = epibolyId;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
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