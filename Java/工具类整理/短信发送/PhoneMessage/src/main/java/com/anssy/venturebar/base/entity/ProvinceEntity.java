/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  ProvinceEntity.java
 * PACKAGE      :  com.anssy.venturebar.base.entity
 * CREATE DATE  :  2016-8-9
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.venturebar.base.entity;

import com.anssy.webcore.core.entity.IdEntity;

/**
 * @author make it
 * @version SVN #V1# #2016-8-9#
 *          基础_省信息
 */
public class ProvinceEntity extends IdEntity {

    private static final long serialVersionUID = -1837877360468995341L;

    /**
     * 省ID
     */
    private Long provinceId;

    /**
     * 省名称
     */
    private String province;

    /**
     * 顺序
     */
    private int showIndex;

    /**
     * 标志(是否显示0-不显示,1-显示)
     */
    private int optFlag;

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public int getShowIndex() {
        return showIndex;
    }

    public void setShowIndex(int showIndex) {
        this.showIndex = showIndex;
    }

    public int getOptFlag() {
        return optFlag;
    }

    public void setOptFlag(int optFlag) {
        this.optFlag = optFlag;
    }

}