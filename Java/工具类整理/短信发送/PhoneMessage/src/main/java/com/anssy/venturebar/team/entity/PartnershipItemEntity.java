/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  PartnershipItemEntity.java
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
 *          团队_合伙明细
 */
public class PartnershipItemEntity extends IdEntity {

    private static final long serialVersionUID = 5197379812022463712L;

    /**
     * 合伙ID
     */
    private Long partnershipId;
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
     * 项目方向
     */
    private String direction;
    /**
     * 团队情况
     */
    private String teamCase;
    /**
     * 创业地址
     */
    private String site;
    /**
     * 项目亮点
     */
    private String lightspot;
    /**
     * 项目前景
     */
    private String prospect;
    /**
     * 项目阶段(STAGE)
     */
    private Long stage;
    private String stageName;
    /**
     * 初始资金(FUND)
     */
    private String fund;
    private String fundName;
    /**
     * 产品链接
     */
    private String appURL;
    /**
     * 邮箱
     */
    private String email;
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

    public Long getPartnershipId() {
        return partnershipId;
    }

    public void setPartnershipId(Long partnershipId) {
        this.partnershipId = partnershipId;
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

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getTeamCase() {
        return teamCase;
    }

    public void setTeamCase(String teamCase) {
        this.teamCase = teamCase;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getLightspot() {
        return lightspot;
    }

    public void setLightspot(String lightspot) {
        this.lightspot = lightspot;
    }

    public String getProspect() {
        return prospect;
    }

    public void setProspect(String prospect) {
        this.prospect = prospect;
    }

    public Long getStage() {
        return stage;
    }

    public void setStage(Long stage) {
        this.stage = stage;
    }

    public String getFund() {
        return fund;
    }

    public void setFund(String fund) {
        this.fund = fund;
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    public String getFundName() {
        return fundName;
    }

    public void setFundName(String fundName) {
        this.fundName = fundName;
    }

    public String getAppURL() {
        return appURL;
    }

    public void setAppURL(String appURL) {
        this.appURL = appURL;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPublishName() {
        return publishName;
    }

    public void setPublishName(String publishName) {
        this.publishName = publishName;
    }

}