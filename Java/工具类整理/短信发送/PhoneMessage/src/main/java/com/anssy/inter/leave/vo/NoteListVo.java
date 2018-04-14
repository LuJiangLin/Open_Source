/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  NoteListVo.java
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
public class NoteListVo {

    /**
     * 类型 LEAVE_TYPE(1-场地,2-投资人,3-投资项目,4-团队合伙,5-团队众包,6-技术导师)
     */
    private int type;
    /**
     * 外键ID
     */
    private Long fid;

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

}