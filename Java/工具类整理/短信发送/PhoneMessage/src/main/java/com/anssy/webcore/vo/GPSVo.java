/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  GPSVo.java
 * PACKAGE      :  com.anssy.webcore.vo
 * CREATE DATE  :  2016-8-24
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.webcore.vo;

/**
 * @author make it
 * @version SVN #V1# #2016-8-24#
 */
public class GPSVo {

    /**
     * geohash(GPS坐标附近位置检索)
     */
    private String geohash;
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

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

}