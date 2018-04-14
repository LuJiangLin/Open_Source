/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  DatadictVo.java
 * PACKAGE      :  com.anssy.venturebar.base.vo
 * CREATE DATE  :  2016-8-27
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.venturebar.base.vo;

import java.io.Serializable;

/**
 * @author make it
 * @version SVN #V1# #2016-8-27#
 */
public class DatadictVo implements Serializable {

    private static final long serialVersionUID = -8578322945373448782L;

    /**
     * 常量
     */
    private Long literal;
    /**
     * 描述
     */
    private String dictDesc;

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