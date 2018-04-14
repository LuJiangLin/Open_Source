/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  ActivityInfoDao.java
 * PACKAGE      :  com.anssy.venturebar.activity.dao
 * CREATE DATE  :  2016-8-10
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.venturebar.activity.dao;

import com.anssy.venturebar.activity.entity.ActivityInfoEntity;
import com.anssy.venturebar.activity.vo.ActivityTypeVo;
import com.anssy.venturebar.activity.vo.PvVo;
import com.anssy.webcore.vo.CollectVo;
import com.anssy.webcore.vo.GPSVo;
import com.anssy.webcore.vo.MyVo;
import com.anssy.webcore.vo.ReferralsVo;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


/**
 * @author make it
 * @version SVN #V1# #2016-8-10#
 *          活动_活动信息
 */
@Repository("activityInfoDao")
public interface ActivityInfoDao {

    /**
     * 添加活动信息
     */
    int insertActivityInfo(ActivityInfoEntity info);

    /**
     * 修改活动信息
     */
    int updateActivityInfo(ActivityInfoEntity info);

    /**
     * 获取主键
     */
    Long findId();

    /**
     * 通过活动ID查询活动信息
     */
    ActivityInfoEntity findActivityInfoById(Long id);

    /**
     * 修改浏览量
     */
    int updatePV(Long id);

    /**
     * 点赞
     */
    int updatePraise(Long id);

    /**
     * 修改权重
     */
    int updateWeight(Long id, Long weight);

    /**
     * 删除活动信息
     */
    int deleteActivityInfo(Long id);

    /**
     * 删除全部同步活动信息
     */
    int deleteAllByComefrom();

    /**
     * 按GPS进行附近搜索
     */
    List<ActivityInfoEntity> findListByGPS(GPSVo vo);

    /**
     * 按活动类型搜索
     */
    List<ActivityInfoEntity> findListByType(ActivityTypeVo vo);

    /**
     * 智能搜索
     */
    List<ActivityInfoEntity> findListByPv(PvVo vo);

    /**
     * 推荐活动
     */
    List<ActivityInfoEntity> referrals(ReferralsVo vo);

    /**
     * 我发布的信息
     */
    List<ActivityInfoEntity> findMyPublish(MyVo vo);

    /**
     * 过期
     */
    int overdue(Date time);

    /**
     * 查看我的收藏
     */
    List<ActivityInfoEntity> findCollect(CollectVo vo);

}