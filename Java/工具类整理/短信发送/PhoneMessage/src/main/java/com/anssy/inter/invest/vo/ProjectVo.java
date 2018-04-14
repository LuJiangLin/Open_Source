/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  ProjectVo.java
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
 *          投资项目辅助类
 */
public class ProjectVo {

    private Long id;
    /**
     * 图片
     */
    private String image;
    /**
     * 标题
     */
    private String title;
    /**
     * 一句描述
     */
    private String sketch;
    /**
     * 行业(基础_行业信息)
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
    /**
     * 融资状态 (FINANCING_STATE)
     */
    private int financingState;
    /**
     * 融资金额
     */
    private String financingSum;
    /**
     * 出让股份比例 (SHARE)
     */
    private int sellShare;
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
     * 经度
     */
    private String tbLng;
    /**
     * 纬度
     */
    private String tbLat;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSketch() {
        return sketch;
    }

    public void setSketch(String sketch) {
        this.sketch = sketch;
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

    public int getFinancingState() {
        return financingState;
    }

    public void setFinancingState(int financingState) {
        this.financingState = financingState;
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

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public String getsetTbLng() {
        return tbLng;
    }

    public void setTbLng(String tbLng) {
        this.tbLng = tbLng;
    }

    public String getTbLat() {
        return tbLat;
    }

    public void setTbLat(String tbLat) {
        this.tbLat = tbLat;
    }

}