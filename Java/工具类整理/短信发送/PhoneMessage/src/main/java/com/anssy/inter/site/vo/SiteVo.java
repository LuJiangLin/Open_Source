/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  SiteVo.java
 * PACKAGE      :  com.anssy.inter.site.vo
 * CREATE DATE  :  2016-8-13
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.inter.site.vo;

import java.util.Date;

/**
 * @author make it
 * @version SVN #V1# #2016-8-13#
 *          场地接口辅助类
 */
public class SiteVo {

    private Long id;
    /**
     * 图片
     */
    private String siteImage;
    /**
     * 类型(基础_场地类型)
     */
    private Long type;
    /**
     * 场地名
     */
    private String siteName;
    /**
     * 标题
     */
    private String title;
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
     * 价格
     */
    private String price;
    /**
     * 卡位租金
     */
    private String rent;
    /**
     * 面积
     */
    private String acreage;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 联系人
     */
    private String linkman;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 描述
     */
    private String describe;
    /**
     * 场地地址
     */
    private String siteAddress;
    /**
     * 装修情况
     */
    private String fitment;
    /**
     * 朝向
     */
    private String direction;
    /**
     * 楼层
     */
    private String storey;
    /**
     * 产权年限
     */
    private String property;
    /**
     * 建筑年代
     */
    private String years;
    /**
     * 介绍网址
     */
    private String url;
    /**
     * 结束时间
     */
    private Date endTime;
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

    public String getSiteImage() {
        return siteImage;
    }

    public void setSiteImage(String siteImage) {
        this.siteImage = siteImage;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getSiteAddress() {
        return siteAddress;
    }

    public void setSiteAddress(String siteAddress) {
        this.siteAddress = siteAddress;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFitment() {
        return fitment;
    }

    public void setFitment(String fitment) {
        this.fitment = fitment;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getStorey() {
        return storey;
    }

    public void setStorey(String storey) {
        this.storey = storey;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getYears() {
        return years;
    }

    public void setYears(String years) {
        this.years = years;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getRent() {
        return rent;
    }

    public void setRent(String rent) {
        this.rent = rent;
    }

    public String getAcreage() {
        return acreage;
    }

    public void setAcreage(String acreage) {
        this.acreage = acreage;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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