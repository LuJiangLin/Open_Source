/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  ProjectItemEntity.java
 * PACKAGE      :  com.anssy.venturebar.invest.entity
 * CREATE DATE  :  2016-8-10
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.venturebar.invest.entity;

import com.anssy.webcore.core.entity.IdEntity;

import java.util.Date;

/**
 * @author make it
 * @version SVN #V1# #2016-8-10#
 *          投资_项目明细
 */
public class ProjectItemEntity extends IdEntity {

    private static final long serialVersionUID = 4634033423631376157L;

    /**
     * 项目ID
     */
    private Long projectId;
    /**
     * 项目介绍
     */
    private String describe;
    /**
     * 核心竞争力
     */
    private String advantage;
    /**
     * 融资需求 (IS_FINANCING)
     */
    private int isFinancing;
    private String isFinancingName;
    /**
     * 融资状态 (FINANCING_STATE)
     */
    private int financingState;
    private String financingStateName;
    /**
     * 融资金额
     */
    private String financingSum;
    /**
     * 出让股份比例 (SHARE)
     */
    private int sellShare;
    private String sellShareName;
    /**
     * 创业者介绍
     */
    private String founder;
    /**
     * 公司名称
     */
    private String companyName;
    /**
     * 公司地址
     */
    private String companySite;
    /**
     * EMAIL
     */
    private String email;
    /**
     * 介绍网址
     */
    private String url;
    /**
     * app地址
     */
    private String appUrl;
    /**
     * 联系人
     */
    private String linkman;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 发布人(user_id)
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

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getAdvantage() {
        return advantage;
    }

    public void setAdvantage(String advantage) {
        this.advantage = advantage;
    }

    public int getIsFinancing() {
        return isFinancing;
    }

    public void setIsFinancing(int isFinancing) {
        this.isFinancing = isFinancing;
    }

    public String getIsFinancingName() {
        return isFinancingName;
    }

    public void setIsFinancingName(String isFinancingName) {
        this.isFinancingName = isFinancingName;
    }

    public int getFinancingState() {
        return financingState;
    }

    public void setFinancingState(int financingState) {
        this.financingState = financingState;
    }

    public String getFinancingStateName() {
        return financingStateName;
    }

    public void setFinancingStateName(String financingStateName) {
        this.financingStateName = financingStateName;
    }

    public String getFinancingSum() {
        return financingSum;
    }

    public void setFinancingSum(String financingSum) {
        this.financingSum = financingSum;
    }

    public int getSellShare() {
        return sellShare;
    }

    public void setSellShare(int sellShare) {
        this.sellShare = sellShare;
    }

    public String getSellShareName() {
        return sellShareName;
    }

    public void setSellShareName(String sellShareName) {
        this.sellShareName = sellShareName;
    }

    public String getFounder() {
        return founder;
    }

    public void setFounder(String founder) {
        this.founder = founder;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanySite() {
        return companySite;
    }

    public void setCompanySite(String companySite) {
        this.companySite = companySite;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public String getPublishName() {
        return publishName;
    }

    public void setPublishName(String publishName) {
        this.publishName = publishName;
    }

}