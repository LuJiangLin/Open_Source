/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  SiteInfoEntity.java
 * PACKAGE      :  com.anssy.venturebar.site.entity
 * CREATE DATE  :  2016-8-10
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.venturebar.site.entity;

import com.anssy.webcore.core.entity.IdEntity;

import java.util.Date;
import java.util.List;

/**
 * @author make it
 * @version SVN #V1# #2016-8-10#
 *          场地_场地信息
 */
public class SiteInfoEntity extends IdEntity {

    private static final long serialVersionUID = -2964070765214180061L;

    /**
     * 图片
     */
    private String siteImage;
    private List<String> urls;
    /**
     * 类型(基础_场地类型)
     */
    private Long type;
    private String typeName;
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
    private String province;
    /**
     * 市ID
     */
    private Long cityId;
    private String city;
    /**
     * 区县ID
     */
    private Long areaId;
    private String area;
    /**
     * 经度
     */
    private String longitude;
    /**
     * 纬度
     */
    private String latitude;
    /**
     * geohash(GPS坐标附近位置检索)
     */
    private String geohash;
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
     * 开始时间
     */
    private Date beginTime;
    /**
     * 结束时间
     */
    private Date endTime;
    /**
     * 浏览量
     */
    private Long pv;
    /**
     * 点赞数
     */
    private Long praise;
    /**
     * 权重 默认排序
     */
    private Long weight;
    /**
     * 状态 (STATE 0-待审核,1-正常,2-过期,3-锁定)
     */
    private int state;
    /**
     * 距离
     */
    private double distance;

    private Integer sort;

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

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getGeohash() {
        return geohash;
    }

    public void setGeohash(String geohash) {
        this.geohash = geohash;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Long getPv() {
        return pv;
    }

    public void setPv(Long pv) {
        this.pv = pv;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double lat1, double lng1) {
        double dis = getDistance();
        if (dis == 0) {
            double lat2 = Double.parseDouble(getLatitude());
            double lng2 = Double.parseDouble(getLongitude());
            dis = calculateDistance(lat1, lng1, lat2, lng2);
        }
        this.distance = dis;
    }

    /**
     * 排序方法
     */
    public Integer getSort() {
        sort = Integer.parseInt(new java.text.DecimalFormat("0")
                .format(getDistance()));
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * 计算距离的方法(单位m)
     */
    private double calculateDistance(double lat1, double lng1, double lat2,
                                     double lng2) {
        double theta = lng1 - lng2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        return dist * 60 * 1.1515 * 1.609344 * 1000;
    }

    // 将角度转换为弧度
    private static double deg2rad(double degree) {
        return degree / 180 * Math.PI;
    }

    // 将弧度转换为角度
    private static double rad2deg(double radian) {
        return radian * 180 / Math.PI;
    }

    public Long getPraise() {
        return praise;
    }

    public void setPraise(Long praise) {
        this.praise = praise;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
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

    public Long getWeight() {
        return weight;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }

}