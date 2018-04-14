/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  PEVo.java
 * PACKAGE      :  com.anssy.venturebar.service.vo
 * CREATE DATE  :  2016-8-7
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.venturebar.service.vo;

import com.anssy.webcore.core.entity.IdEntity;

import java.util.Date;
import java.util.List;

/**
 * @author make it
 * @version SVN #V1# #2016-8-7#
 */
public class PEVo extends IdEntity {

    private static final long serialVersionUID = 3075384919490466404L;

    /**
     * 图片
     */
    private String image;
    private List<String> urls;
    /**
     * 标题
     */
    private String title;
    /**
     * 发布时间
     */
    private Date publishTime;
    /**
     * 来源
     */
    private String source;
    /**
     * 浏览量
     */
    private Long pv;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Long getPv() {
        return pv;
    }

    public void setPv(Long pv) {
        this.pv = pv;
    }

}