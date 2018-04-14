/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  PolicyVo.java
 * PACKAGE      :  com.anssy.inter.service.vo
 * CREATE DATE  :  2016-8-3
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.inter.service.vo;

/**
 * @author make it
 * @version SVN #V1# #2016-8-3#
 *          政策解读辅助类
 */
public class PolicyVo {

    private Long id;
    /**
     * 图片
     */
    private String image;
    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    private String details;
    /**
     * 来源
     */
    private String source;
    /**
     * URL
     */
    private String url;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}