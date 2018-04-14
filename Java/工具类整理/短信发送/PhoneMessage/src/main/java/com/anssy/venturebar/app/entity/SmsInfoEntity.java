/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  SmsInfoEntity.java
 * PACKAGE      :  com.anssy.venturebar.app.entity
 * CREATE DATE  :  2016-8-28
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.venturebar.app.entity;

import com.anssy.webcore.core.entity.IdEntity;

import java.util.Date;

/**
 * @author make it
 * @version SVN #V1# #2016-8-28#
 *          APP_短信信息
 */
public class SmsInfoEntity extends IdEntity {

    private static final long serialVersionUID = 4552454810474634833L;

    /**
     * 发送时间
     */
    private Date sendTime;
    /**
     * 接收号码
     */
    private String mobile;
    /**
     * 短信内容
     */
    private String content;
    /**
     * 余额
     */
    private Long balance;
    /**
     * 状态
     */
    private String state;

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}