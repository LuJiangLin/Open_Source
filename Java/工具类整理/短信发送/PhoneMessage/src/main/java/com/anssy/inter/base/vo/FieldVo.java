/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  FieldVo.java
 * PACKAGE      :  com.anssy.inter.base.vo
 * CREATE DATE  :  2016-8-21
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.inter.base.vo;

/**
 * @author make it
 * @version SVN #V1# #2016-8-21#
 */
public class FieldVo {

    /**
     * 类型(1-投资人,2-导师)
     */
    private int type;
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