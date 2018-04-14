/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  PolicyElucidationDao.java
 * PACKAGE      :  com.anssy.venturebar.service.dao
 * CREATE DATE  :  2016-8-3
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.venturebar.service.dao;

import com.anssy.venturebar.service.entity.PolicyElucidationEntity;
import com.anssy.venturebar.service.vo.PEVo;
import com.anssy.venturebar.service.vo.PvVo;
import com.anssy.webcore.vo.CollectVo;
import com.anssy.webcore.vo.MyVo;
import com.anssy.webcore.vo.ReferralsVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author make it
 * @version SVN #V1# #2016-8-3#
 *          服务_政策解读
 */
@Repository("policyElucidationDao")
public interface PolicyElucidationDao {

    /**
     * 添加政策解读
     */
    int insertPolicyElucidation(PolicyElucidationEntity info);

    /**
     * 修改政策解读
     */
    int updatePolicyElucidation(PolicyElucidationEntity info);

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
     * 查询政策解读信息
     */
    PolicyElucidationEntity findPolicyElucidationById(Long id);

    /**
     * 删除政策解读信息
     */
    int deletePolicy(Long id);

    /**
     * 修改留言量
     */
    int updateLeaveNumber(Long id);

    /**
     * 修改收藏量
     */
    int updateCollectNumber(Long id, Long number);

    /**
     * 查询政策解读信息
     */
    List<PEVo> findList(PvVo vo);

    /**
     * 推荐政策解读
     */
    List<PEVo> referrals(ReferralsVo vo);

    /**
     * 我发布的信息
     */
    List<PEVo> findMyPublish(MyVo vo);

    /**
     * 查看我的收藏
     */
    List<PEVo> findCollect(CollectVo vo);
}