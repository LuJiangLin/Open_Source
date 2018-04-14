/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  ActivityTypeEntity.java
 * PACKAGE      :  com.anssy.venturebar.base.entity
 * CREATE DATE  :  2016-8-14
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.venturebar.base.entity;

import java.util.List;

/**
 * @author make it
 * @version SVN #V1# #2016-8-14#
 *          活动类型
 */
public class ActivityTypeAllEntity extends ActivityTypeEntity {

    private static final long serialVersionUID = -4331830100355800864L;

    private List<ActivityTypeEntity> subType;

    public void setSubType(List<ActivityTypeEntity> subType) {
        this.subType = subType;
    }

    public List<ActivityTypeEntity> getSubType() {
        return subType;
    }

}