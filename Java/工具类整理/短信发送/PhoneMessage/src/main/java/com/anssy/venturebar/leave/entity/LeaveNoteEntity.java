/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  LeaveNoteEntity.java
 * PACKAGE      :  com.anssy.venturebar.leave.entity
 * CREATE DATE  :  2016-8-10
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.venturebar.leave.entity;

import com.anssy.webcore.core.entity.IdEntity;

import java.util.Date;

/**
 * @author make it
 * @version SVN #V1# #2016-8-10#
 *          留言_留言本
 */
public class LeaveNoteEntity extends IdEntity {

    private static final long serialVersionUID = 1281862372933139498L;

    /**
     * 类型 LEAVE_TYPE(1-场地,2-投资人,3-投资项目,4-团队合伙,5-团队众包,6-技术导师)
     */
    private int type;
    /**
     * 外键ID
     */
    private Long fid;
    /**
     * 发布人(user_id)
     */
    private Long publishId;
    private String publishName;
    /**
     * 留言人(user_id)
     */
    private Long leaveId;
    /**
     * 留言时间
     */
    private Date leaveTime;
    /**
     * 留言内容
     */
    private String leaveDetails;
    /**
     * 父ID
     */
    private Long fatherId;
    /**
     * 是否阅读(0-未阅/1-已阅)
     */
    private int isRead;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Long getFid() {
        return fid;
    }

    public void setFid(Long fid) {
        this.fid = fid;
    }

    public Long getPublishId() {
        return publishId;
    }

    public void setPublishId(Long publishId) {
        this.publishId = publishId;
    }

    public Long getLeaveId() {
        return leaveId;
    }

    public void setLeaveId(Long leaveId) {
        this.leaveId = leaveId;
    }

    public Date getLeaveTime() {
        return leaveTime;
    }

    public void setLeaveTime(Date leaveTime) {
        this.leaveTime = leaveTime;
    }

    public String getLeaveDetails() {
        return leaveDetails;
    }

    public void setLeaveDetails(String leaveDetails) {
        this.leaveDetails = leaveDetails;
    }

    public Long getFatherId() {
        return fatherId;
    }

    public void setFatherId(Long fatherId) {
        this.fatherId = fatherId;
    }

    public int getIsRead() {
        return isRead;
    }

    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }

    public String getPublishName() {
        return publishName;
    }

    public void setPublishName(String publishName) {
        this.publishName = publishName;
    }

}