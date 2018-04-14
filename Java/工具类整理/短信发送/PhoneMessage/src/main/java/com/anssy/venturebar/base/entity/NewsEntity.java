/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  PolicyElucidationEntity.java
 * PACKAGE      :  com.anssy.venturebar.service.entity
 * CREATE DATE  :  2016-8-3
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.venturebar.base.entity;

import com.anssy.webcore.core.entity.IdEntity;

import java.util.Date;
import java.util.List;

/**
 * @author make it
 * @version SVN #V1# #2016-8-3#
 *          资讯_政策解读
 */
public class NewsEntity extends IdEntity {

    private static final long serialVersionUID = 273720877715081735L;

    /**
     * 标题
     */
    private String title;
    /**
     * 发布时间
     */
    private Date updateTime;
    /**
     * URL
     */
    private String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}