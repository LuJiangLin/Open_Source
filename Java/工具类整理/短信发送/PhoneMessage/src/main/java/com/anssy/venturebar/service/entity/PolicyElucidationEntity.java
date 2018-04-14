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
package com.anssy.venturebar.service.entity;

import com.anssy.webcore.core.entity.IdEntity;

import java.util.Date;
import java.util.List;

/**
 * @author make it
 * @version SVN #V1# #2016-8-3#
 *          服务_政策解读
 */
public class PolicyElucidationEntity extends IdEntity {

    private static final long serialVersionUID = 1192958638922188412L;

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
     * 发布人
     */
    private Long publishId;
    private String publishName;
    /**
     * 发布时间
     */
    private Date publishTime;
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
    /**
     * 浏览量
     */
    private Long pv;
    /**
     * 点赞数
     */
    private Long praise;
    /**
     * 权重 默认排序
     */
    private Long weight;
    /**
     * 留言量
     */
    private Long leaveNumber;
    /**
     * 收藏量
     */
    private Long collectNumber;
    /**
     * 状态 (STATE 0-待审核,1-正常,2-过期,3-锁定)
     */
    private int state;

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

    public Long getPublishId() {
        return publishId;
    }

    public void setPublishId(Long publishId) {
        this.publishId = publishId;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
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

    public Long getPv() {
        return pv;
    }

    public void setPv(Long pv) {
        this.pv = pv;
    }

    public Long getPraise() {
        return praise;
    }

    public void setPraise(Long praise) {
        this.praise = praise;
    }

    public Long getLeaveNumber() {
        return leaveNumber;
    }

    public void setLeaveNumber(Long leaveNumber) {
        this.leaveNumber = leaveNumber;
    }

    public Long getCollectNumber() {
        return collectNumber;
    }

    public void setCollectNumber(Long collectNumber) {
        this.collectNumber = collectNumber;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Long getWeight() {
        return weight;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }

    public String getPublishName() {
        return publishName;
    }

    public void setPublishName(String publishName) {
        this.publishName = publishName;
    }

}