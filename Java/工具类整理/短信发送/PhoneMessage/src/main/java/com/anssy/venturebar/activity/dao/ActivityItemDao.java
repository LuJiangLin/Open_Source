/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  ActivityItemDao.java
 * PACKAGE      :  com.anssy.venturebar.activity.dao
 * CREATE DATE  :  2016-8-10
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.venturebar.activity.dao;

import com.anssy.venturebar.activity.entity.ActivityItemEntity;
import org.springframework.stereotype.Repository;

/**
 * @author make it
 * @version SVN #V1# #2016-8-10#
 *          活动_活动明细
 */
@Repository("activityItemDao")
public interface ActivityItemDao {

    /**
     * 添加活动明细
     */
    int insertActivityItem(ActivityItemEntity item);

    /**
     * 修改活动明细
     */
    int updateActivityItem(ActivityItemEntity item);

    /**
     * 通过活动ID查询活动明细
     */
    ActivityItemEntity findActivityItem(Long activityId);

    /**
     * 删除活动明细
     */
    int deleteActivityItem(Long activityId);

    /**
     * 清理主表中没有的数据
     */
    int deleteAllByNotidComefrom();

    /**
     * 修改报名人数
     */
    int updateJoinNumber(Long activityId);

    /**
     * 修改关注人数
     */
    int updateCareNumber(Long activityId);

    /**
     * 修改留言量
     */
    int updateLeaveNumber(Long activityId);

    /**
     * 修改收藏量
     */
    int updateCollectNumber(Long activityId, Long number);

}