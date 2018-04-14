/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  SiteTypeVo.java
 * PACKAGE      :  com.anssy.venturebar.site.vo
 * CREATE DATE  :  2016-8-17
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.venturebar.site.vo;

/**
 * @author make it
 * @version SVN #V1# #2016-8-17#
 */
public class SiteTypeVo {

    /**
     * 类型(场地)
     */
    private Long type;
    private Long[] types;
    /**
     * 搜索词组
     */
    private String search;
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
    private int being;
    private int end;

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public Long[] getTypes() {
        return types;
    }

    public void setTypes(Long[] types) {
        this.types = types;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
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

    public int getBeing() {
        return being;
    }

    public void setBeing(int being) {
        this.being = being;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

}