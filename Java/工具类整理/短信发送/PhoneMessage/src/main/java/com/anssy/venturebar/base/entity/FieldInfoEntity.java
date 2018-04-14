/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  FieldInfoEntity.java
 * PACKAGE      :  com.anssy.venturebar.base.entity
 * CREATE DATE  :  2016-8-10
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.venturebar.base.entity;

import com.anssy.webcore.core.entity.IdEntity;

/**
 * @author make it
 * @version SVN #V1# #2016-8-10#
 *          基础_行业信息
 */
public class FieldInfoEntity extends IdEntity {

    private static final long serialVersionUID = 1538626882863065219L;

    /**
     * 类型(1-投资人,2-导师)
     */
    private int type;
    /**
     * 行业名称
     */
    private String fieldName;
    /**
     * 行业级别(1-一级行业,2-二级行业)
     */
    private int fieldLevel;
    /**
     * 父ID
     */
    private Long fatherId;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public int getFieldLevel() {
        return fieldLevel;
    }

    public void setFieldLevel(int fieldLevel) {
        this.fieldLevel = fieldLevel;
    }

    public Long getFatherId() {
        return fatherId;
    }

    public void setFatherId(Long fatherId) {
        this.fatherId = fatherId;
    }

}