/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  ActivityTypeDao.java
 * PACKAGE      :  com.anssy.venturebar.base.dao
 * CREATE DATE  :  2016-8-14
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.venturebar.base.dao;

import com.anssy.venturebar.base.entity.ActivityTypeEntity;
import com.anssy.inter.activity.vo.TypeVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author make it
 * @version SVN #V1# #2016-8-14#
 *          活动类型
 */
@Repository("activityTypeDao")
public interface ActivityTypeDao {

    /**
     * 获取活动类型
     */
    List<ActivityTypeEntity> findActivityType(TypeVo vo);

    /**
     * 获取活动类型
     */
    List<ActivityTypeEntity> findActivityTypeAll();

    /**
     * 通过ID查询活动类型
     */
    ActivityTypeEntity findActivityTypeById(Long Id);

}