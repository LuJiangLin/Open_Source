/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  ServiceItemEntity.java
 * PACKAGE      :  com.anssy.venturebar.service.entity
 * CREATE DATE  :  2016-8-10
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.venturebar.service.entity;

import com.anssy.webcore.core.entity.IdEntity;

import java.util.Date;

/**
 * @author make it
 * @version SVN #V1# #2016-8-10#
 *          服务_服务明细
 */
public class ServiceItemEntity extends IdEntity {

    private static final long serialVersionUID = 6444529732459566212L;

    /**
     * 服务ID
     */
    private Long serviceId;
    /**
     * 服务描述
     */
    private String describe;
    /**
     * 其它
     */
    private String other;
    /**
     * 机构地址
     */
    private String serviceSite;
    /**
     * 服务时间
     */
    private String serviceTime;
    /**
     * 门户网站
     */
    private String url;
    /**
     * 联系人
     */
    private String linkman;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * Email
     */
    private String email;
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
     * 留言量
     */
    private Long leaveNumber;
    /**
     * 收藏量
     */
    private Long collectNumber;

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getServiceSite() {
        return serviceSite;
    }

    public void setServiceSite(String serviceSite) {
        this.serviceSite = serviceSite;
    }

    public String getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(String serviceTime) {
        this.serviceTime = serviceTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getPublishName() {
        return publishName;
    }

    public void setPublishName(String publishName) {
        this.publishName = publishName;
    }

}