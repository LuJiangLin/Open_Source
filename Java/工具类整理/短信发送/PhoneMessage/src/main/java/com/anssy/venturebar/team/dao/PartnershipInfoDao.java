/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  PartnershipInfoDao.java
 * PACKAGE      :  com.anssy.venturebar.team.dao
 * CREATE DATE  :  2016-8-10
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.venturebar.team.dao;

import com.anssy.venturebar.team.entity.PartnershipInfoEntity;
import com.anssy.venturebar.team.vo.FieldVo;
import com.anssy.venturebar.team.vo.PvVo;
import com.anssy.webcore.vo.CollectVo;
import com.anssy.webcore.vo.GPSVo;
import com.anssy.webcore.vo.MyVo;
import com.anssy.webcore.vo.ReferralsVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author make it
 * @version SVN #V1# #2016-8-10#
 *          团队_合伙信息
 */
@Repository("partnershipInfoDao")
public interface PartnershipInfoDao {

    /**
     * 添加合伙信息
     */
    int insertPartnershipInfo(PartnershipInfoEntity info);

    /**
     * 修改合伙信息
     */
    int updatePartnershipInfo(PartnershipInfoEntity info);

    /**
     * 获取主键
     */
    Long findId();

    /**
     * 通过合伙ID查询合伙信息
     */
    PartnershipInfoEntity findPartnershipInfoById(Long id);

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
     * 删除合伙信息
     */
    int deletePartnership(Long id);

    /**
     * 附近搜索
     */
    List<PartnershipInfoEntity> findPartnershipByGPS(GPSVo vo);

    /**
     * 行业搜索
     */
    List<PartnershipInfoEntity> findPartnershipByField(FieldVo vo);

    /**
     * 智能搜索
     */
    List<PartnershipInfoEntity> findPartnershipByPv(PvVo vo);

    /**
     * 推荐合伙
     */
    List<PartnershipInfoEntity> referrals(ReferralsVo vo);

    /**
     * 我发布的信息
     */
    List<PartnershipInfoEntity> findMyPublish(MyVo vo);

    /**
     * 查看我的收藏
     */
    List<PartnershipInfoEntity> findCollect(CollectVo vo);

}