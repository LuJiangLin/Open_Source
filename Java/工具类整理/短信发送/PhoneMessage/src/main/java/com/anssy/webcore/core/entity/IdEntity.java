/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  IdEntity.java
 * PACKAGE      :  com.anssy.webcore.core.entity
 * CREATE DATE  :  2016-8-9
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.webcore.core.entity;

import java.io.Serializable;

/**
 * @author make it
 * @version SVN #V1# #2016-8-9#
 *          扩展Entity类，其他的Entity可以从此继承扩展.
 */
public abstract class IdEntity implements Serializable {

    private static final long serialVersionUID = -6880096765847802483L;

    /**
     * 主键
     */
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}