/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  GPSVo.java
 * PACKAGE      :  com.anssy.venturebar.service.vo
 * CREATE DATE  :  2016-8-26
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.venturebar.service.vo;

/**
 * @author make it
 * @version SVN #V1# #2016-8-26#
 */
public class GPSVo {

    /**
     * geohash(GPS坐标附近位置检索)
     */
    private String geohash;
    /**
     * 服务类型
     */
    private String serviceType;
    /**
     * 搜索词组
     */
    private String search;

    public String getGeohash() {
        return geohash;
    }

    public void setGeohash(String geohash) {
        this.geohash = geohash;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

}