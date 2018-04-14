/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  UserItemEntity.java
 * PACKAGE      :  com.anssy.venturebar.base.entity
 * CREATE DATE  :  2016-8-9
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.venturebar.base.entity;

import com.anssy.webcore.core.entity.IdEntity;

import java.util.Date;

/**
 * @author make it
 * @version SVN #V1# #2016-8-9#
 *          基础_用户明细
 */
public class UserItemEntity extends IdEntity {

    private static final long serialVersionUID = -2023334573425105196L;

    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 姓名
     */
    private String userName;
    /**
     * 性别 (SEX)
     */
    private int sex;
    private String sexName;
    /**
     * 公司
     */
    private String company;
    /**
     * 职位
     */
    private String post;
    /**
     * 关注领域
     */
    private String concern;
    private String concernName;
    /**
     * 注册时间
     */
    private Date registerTime;
    /**
     * 激活时间
     */
    private Date activateTime;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getSexName() {
        return sexName;
    }

    public void setSexName(String sexName) {
        this.sexName = sexName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getConcern() {
        return concern;
    }

    public void setConcern(String concern) {
        this.concern = concern;
    }

    public String getConcernName() {
        return concernName;
    }

    public void setConcernName(String concernName) {
        this.concernName = concernName;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public Date getActivateTime() {
        return activateTime;
    }

    public void setActivateTime(Date activateTime) {
        this.activateTime = activateTime;
    }

}