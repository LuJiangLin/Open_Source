/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  NoteVo.java
 * PACKAGE      :  com.anssy.inter.leave.vo
 * CREATE DATE  :  2016-8-20
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.inter.leave.vo;

/**
 * @author make it
 * @version SVN #V1# #2016-8-20#
 *          留言辅助类
 */
public class NoteVo {

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
    /**
     * 留言内容
     */
    private String leaveDetails;
    /**
     * 父ID(回复留言时留言的ID)
     */
    private Long fatherId;

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

}