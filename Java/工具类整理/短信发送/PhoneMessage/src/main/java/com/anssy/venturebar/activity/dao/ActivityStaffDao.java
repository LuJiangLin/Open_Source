/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  ActivityStaffDao.java
 * PACKAGE      :  com.anssy.venturebar.activity.dao
 * CREATE DATE  :  2016-8-10
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.venturebar.activity.dao;

import com.anssy.venturebar.activity.entity.ActivityStaffEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author make it
 * @version SVN #V1# #2016-8-10#
 *          活动_活动人员
 */
@Repository("activityStaffDao")
public interface ActivityStaffDao {

    /**
     * 添加活动人员
     */
    int insertActivityStaff(ActivityStaffEntity staff);

    /**
     * 查询活动人员(不能重复参与/关注)
     */
    List<ActivityStaffEntity> findStaff(ActivityStaffEntity staff);

    /**
     * 查询参与活动人员
     */
    List<ActivityStaffEntity> findJoinStaffs(Long activityId);

    /**
     * 查询关注活动人员
     */
    List<ActivityStaffEntity> findCareStaffs(Long activityId);

    /**
     * 删除活动人员
     */
    int deleteCareStaffs(Long activityId);

}