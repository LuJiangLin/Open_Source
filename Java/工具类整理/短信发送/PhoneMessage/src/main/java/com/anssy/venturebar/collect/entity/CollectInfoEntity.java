/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  CollectInfoEntity.java
 * PACKAGE      :  com.anssy.venturebar.collect.entity
 * CREATE DATE  :  2016-8-3
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.venturebar.collect.entity;

import com.anssy.webcore.core.entity.IdEntity;

import java.util.Date;

/**
 * @author make it
 * @version SVN #V1# #2016-8-3#
 *          收藏_收藏信息
 */
public class CollectInfoEntity extends IdEntity {

    private static final long serialVersionUID = 6720874728000861966L;

    /**
     * 类型 LEAVE_TYPE(1-场地,2-投资人,3-投资项目,4-团队合伙,5-团队众包,6-技术导师)
     */
    private int type;
    /**
     * 外键ID
     */
    private Long fid;
    /**
     * 收藏人ID
     */
    private Long collectId;
    /**
     * 收藏时间
     */
    private Date collectTime;

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

    public Long getCollectId() {
        return collectId;
    }

    public void setCollectId(Long collectId) {
        this.collectId = collectId;
    }

    public Date getCollectTime() {
        return collectTime;
    }

    public void setCollectTime(Date collectTime) {
        this.collectTime = collectTime;
    }

}