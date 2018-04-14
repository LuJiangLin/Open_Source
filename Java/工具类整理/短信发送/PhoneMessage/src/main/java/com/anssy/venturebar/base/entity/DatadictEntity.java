/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  DatadictEntity.java
 * PACKAGE      :  com.anssy.venturebar.base.entity
 * CREATE DATE  :  2016-8-13
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.venturebar.base.entity;

import com.anssy.webcore.core.entity.IdEntity;

/**
 * @author make it
 * @version SVN #V1# #2016-8-13#
 *          基础_数据字典
 */
public class DatadictEntity extends IdEntity {

    private static final long serialVersionUID = -6593114801733153454L;

    /**
     * 类别
     */
    private String category;
    /**
     * 顺序
     */
    private int showIndex;
    /**
     * 常量
     */
    private Long literal;
    /**
     * 描述
     */
    private String dictDesc;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getShowIndex() {
        return showIndex;
    }

    public void setShowIndex(int showIndex) {
        this.showIndex = showIndex;
    }

    public Long getLiteral() {
        return literal;
    }

    public void setLiteral(Long literal) {
        this.literal = literal;
    }

    public String getDictDesc() {
        return dictDesc;
    }

    public void setDictDesc(String dictDesc) {
        this.dictDesc = dictDesc;
    }

}