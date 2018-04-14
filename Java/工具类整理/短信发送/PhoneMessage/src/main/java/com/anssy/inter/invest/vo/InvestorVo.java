/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  InvestorVo.java
 * PACKAGE      :  com.anssy.inter.invest.vo
 * CREATE DATE  :  2016-8-21
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.inter.invest.vo;

/**
 * @author make it
 * @version SVN #V1# #2016-8-21#
 *          投资人信息辅助类
 */
public class InvestorVo {

    private Long id;
    /**
     * 图片
     */
    private String image;
    /**
     * 姓名
     */
    private String name;
    /**
     * 公司名称
     */
    private String companyName;
    /**
     * 职位
     */
    private String post;
    /**
     * 投资主体 (BODY)
     */
    private Long body;
    /**
     * 投资领域
     */
    private String field;
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
     * 名片
     */
    private String callingCard;
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
    /**
     * 投资方式1 (WAY)
     */
    private String way;
    /**
     * 投资类型 (INVEST_TYPE)
     */
    private int investType;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getBody() {
        return body;
    }

    public void setBody(Long body) {
        this.body = body;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
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

    public String getCallingCard() {
        return callingCard;
    }

    public void setCallingCard(String callingCard) {
        this.callingCard = callingCard;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getInvestIntro() {
        return investIntro;
    }

    public void setInvestIntro(String investIntro) {
        this.investIntro = investIntro;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
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

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

}