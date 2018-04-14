/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  SearchVo.java
 * PACKAGE      :  com.anssy.inter.activity.vo
 * CREATE DATE  :  2016-8-28
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.inter.activity.vo;

/**
 * @author make it
 * @version SVN #V1# #2016-8-28#
 *          活动辅助类
 */
public class SearchVo {

    /**
     * 搜索方式(1-按GPS进行附近搜索,2-按活动类型搜索,3-智能搜索)
     */
    private int listType;
    /**
     * 第几页(初始化时值为1,加载时每次加1)
     */
    private int page;
    /**
     * 经度(附近)
     */
    private String longitude;
    /**
     * 纬度(附近)
     */
    private String latitude;
    /**
     * 活动类型
     */
    private Long activityType;
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
     * 智能搜索类型(0-按浏览量/1-按点赞数/2-按发布时间)
     */
    private int capacityType;
    /**
     * 搜索词组
     */
    private String search;

    public int getListType() {
        return listType;
    }

    public void setListType(int listType) {
        this.listType = listType;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
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

    public Long getActivityType() {
        return activityType;
    }

    public void setActivityType(Long activityType) {
        this.activityType = activityType;
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

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public int getCapacityType() {
        return capacityType;
    }

    public void setCapacityType(int capacityType) {
        this.capacityType = capacityType;
    }

}