/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  SiteTypeEntity.java
 * PACKAGE      :  com.anssy.venturebar.base.entity
 * CREATE DATE  :  2016-8-17
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.venturebar.base.entity;

import com.anssy.webcore.core.entity.IdEntity;

import java.util.List;

/**
 * @author make it
 * @version SVN #V1# #2016-8-17#
 *          基础_场地类型
 */
public class SiteTypeAllEntity extends SiteTypeEntity {

    private static final long serialVersionUID = -4390837562870474103L;

    private List<SiteTypeEntity> subType;

    public void setSubType(List<SiteTypeEntity> subType) {
        this.subType = subType;
    }

    public List<SiteTypeEntity> getSubType() {
        return subType;
    }
}