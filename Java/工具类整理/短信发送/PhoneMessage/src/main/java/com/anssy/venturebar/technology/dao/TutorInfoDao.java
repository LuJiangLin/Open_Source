/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  TutorInfoDao.java
 * PACKAGE      :  com.anssy.venturebar.technology.dao
 * CREATE DATE  :  2016-8-10
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.venturebar.technology.dao;

import com.anssy.venturebar.technology.entity.TutorInfoEntity;
import com.anssy.venturebar.technology.vo.FieldVo;
import com.anssy.venturebar.technology.vo.PvVo;
import com.anssy.webcore.vo.CollectVo;
import com.anssy.webcore.vo.GPSVo;
import com.anssy.webcore.vo.MyVo;
import com.anssy.webcore.vo.ReferralsVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author make it
 * @version SVN #V1# #2016-8-10#
 *          技术_导师信息
 */
@Repository("tutorInfoDao")
public interface TutorInfoDao {

    /**
     * 添加导师信息
     */
    int insertTutorInfo(TutorInfoEntity info);

    /**
     * 修改导师信息
     */
    int updateTutorInfo(TutorInfoEntity info);

    /**
     * 获取主键
     */
    Long findId();

    /**
     * 通过导师ID查询导师信息
     */
    TutorInfoEntity findTutorInfoById(Long id);

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
     * 删除导师信息
     */
    int deleteTutorInfo(Long id);

    /**
     * 按GPS进行附近搜索
     */
    List<TutorInfoEntity> findTutorByGPS(GPSVo vo);

    /**
     * 行业搜索
     */
    List<TutorInfoEntity> findTutorByField(FieldVo vo);

    /**
     * 智能搜索
     */
    List<TutorInfoEntity> findTutorByPv(PvVo vo);

    /**
     * 推荐导师
     */
    List<TutorInfoEntity> referrals(ReferralsVo vo);

    /**
     * 我发布的信息
     */
    List<TutorInfoEntity> findMyPublish(MyVo vo);

    /**
     * 查看我的收藏
     */
    List<TutorInfoEntity> findCollect(CollectVo vo);

}