/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  InvestorItemEntity.java
 * PACKAGE      :  com.anssy.venturebar.invest.entity
 * CREATE DATE  :  2016-8-10
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.venturebar.invest.entity;

import com.anssy.webcore.core.entity.IdEntity;

import java.util.Date;
import java.util.List;

/**
 * @author make it
 * @version SVN #V1# #2016-8-10#
 *          投资_投资人明细
 */
public class InvestorItemEntity extends IdEntity {

    private static final long serialVersionUID = 7531166921463168214L;

    /**
     * 投资人ID
     */
    private Long investorId;
    /**
     * 名片
     */
    private String callingCard;
    private List<String> urls;
    /**
     * 投资人简介
     */
    private String investIntro;
    /**
     * Email
     */
    private String email;
    /**
     * 公司地址
     */
    private String companySite;
    /**
     * 成功案例
     */
    private String successfulCase;
    /**
     * 投资方式 (INVEST_WAY)
     */
    private int investWay;
    private String investWayName;
    /**
     * 投资方式1 (WAY)
     */
    private String way;
    /**
     * 投资类型 (INVEST_TYPE)
     */
    private int investType;
    private String investTypeName;
    /**
     * 投资金额
     */
    private String investSum;
    /**
     * 投资期限
     */
    private String deadline;
    /**
     * 关注地区
     */
    private String focus;
    /**
     * 备注
     */
    private String remark;
    /**
     * 其它
     */
    private String other;
    /**
     * 联系人
     */
    private String linkman;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 介绍网址
     */
    private String url;
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

    public Long getInvestorId() {
        return investorId;
    }

    public void setInvestorId(Long investorId) {
        this.investorId = investorId;
    }

    public String getCallingCard() {
        return callingCard;
    }

    public void setCallingCard(String callingCard) {
        this.callingCard = callingCard;
    }

    public String getInvestIntro() {
        return investIntro;
    }

    public void setInvestIntro(String investIntro) {
        this.investIntro = investIntro;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompanySite() {
        return companySite;
    }

    public void setCompanySite(String companySite) {
        this.companySite = companySite;
    }

    public String getSuccessfulCase() {
        return successfulCase;
    }

    public void setSuccessfulCase(String successfulCase) {
        this.successfulCase = successfulCase;
    }

    public int getInvestWay() {
        return investWay;
    }

    public void setInvestWay(int investWay) {
        this.investWay = investWay;
    }

    public String getInvestWayName() {
        return investWayName;
    }

    public void setInvestWayName(String investWayName) {
        this.investWayName = investWayName;
    }

    public String getWay() {
        return way;
    }

    public void setWay(String way) {
        this.way = way;
    }

    public int getInvestType() {
        return investType;
    }

    public void setInvestType(int investType) {
        this.investType = investType;
    }

    public String getInvestTypeName() {
        return investTypeName;
    }

    public void setInvestTypeName(String investTypeName) {
        this.investTypeName = investTypeName;
    }

    public String getInvestSum() {
        return investSum;
    }

    public void setInvestSum(String investSum) {
        this.investSum = investSum;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getFocus() {
        return focus;
    }

    public void setFocus(String focus) {
        this.focus = focus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getPublishName() {
        return publishName;
    }

    public void setPublishName(String publishName) {
        this.publishName = publishName;
    }

}