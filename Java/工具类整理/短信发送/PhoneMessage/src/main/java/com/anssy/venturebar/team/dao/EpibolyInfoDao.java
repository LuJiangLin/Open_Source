/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  EpibolyInfoDao.java
 * PACKAGE      :  com.anssy.venturebar.team.dao
 * CREATE DATE  :  2016-8-10
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.venturebar.team.dao;

import com.anssy.venturebar.team.entity.EpibolyInfoEntity;
import com.anssy.venturebar.team.vo.FieldVo;
import com.anssy.venturebar.team.vo.PvVo;
import com.anssy.webcore.vo.CollectVo;
import com.anssy.webcore.vo.MyVo;
import com.anssy.webcore.vo.ReferralsVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author make it
 * @version SVN #V1# #2016-8-10#
 *          团队_众包信息
 */
@Repository("epibolyInfoDao")
public interface EpibolyInfoDao {

    /**
     * 添加众包信息
     */
    int insertEpibolyInfo(EpibolyInfoEntity info);

    /**
     * 修改众包信息
     */
    int updateEpibolyInfo(EpibolyInfoEntity info);

    /**
     * 获取主键
     */
    Long findId();

    /**
     * 通过众包ID查询众包信息
     */
    EpibolyInfoEntity findEpibolyInfoById(Long id);

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
     * 删除众包信息
     */
    int deleteEpiboly(Long id);

    /**
     * 附近搜索
     */
    List<EpibolyInfoEntity> findEpibolyByNearby(PvVo vo);

    /**
     * 行业搜索
     */
    List<EpibolyInfoEntity> findEpibolyByField(FieldVo vo);

    /**
     * 智能搜索
     */
    List<EpibolyInfoEntity> findEpibolyByPv(PvVo vo);

    /**
     * 推荐众包
     */
    List<EpibolyInfoEntity> referrals(ReferralsVo vo);

    /**
     * 我发布的信息
     */
    List<EpibolyInfoEntity> findMyPublish(MyVo vo);

    /**
     * 查看我的收藏
     */
    List<EpibolyInfoEntity> findCollect(CollectVo vo);
}