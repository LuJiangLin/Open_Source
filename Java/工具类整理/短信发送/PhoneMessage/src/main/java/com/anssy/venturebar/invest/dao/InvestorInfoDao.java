/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  InvestorInfoDao.java
 * PACKAGE      :  com.anssy.venturebar.invest.dao
 * CREATE DATE  :  2016-8-10
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.venturebar.invest.dao;

import com.anssy.venturebar.invest.entity.InvestorInfoEntity;
import com.anssy.venturebar.invest.vo.FieldVo;
import com.anssy.venturebar.invest.vo.PvVo;
import com.anssy.webcore.vo.CollectVo;
import com.anssy.webcore.vo.GPSVo;
import com.anssy.webcore.vo.MyVo;
import com.anssy.webcore.vo.ReferralsVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author make it
 * @version SVN #V1# #2016-8-10#
 *          投资_投资人信息
 */
@Repository("investorInfoDao")
public interface InvestorInfoDao {

    /**
     * 添加投资人信息
     */
    int insertInvestorInfo(InvestorInfoEntity info);

    /**
     * 修改投资人信息
     */
    int updateInvestorInfo(InvestorInfoEntity info);

    /**
     * 获取主键
     */
    Long findId();

    /**
     * 通过主键ID查询投资人信息
     */
    InvestorInfoEntity findInvestorInfoById(Long id);

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
     * 删除投资人信息
     */
    int deleteInvestorInfo(Long id);

    /**
     * 附近搜索
     */
    List<InvestorInfoEntity> findListByGPS(GPSVo vo);

    /**
     * 行业搜索
     */
    List<InvestorInfoEntity> findListByField(FieldVo vo);

    /**
     * 智能搜索
     */
    List<InvestorInfoEntity> findListByPv(PvVo vo);

    /**
     * 推荐投资人
     */
    List<InvestorInfoEntity> referrals(ReferralsVo vo);

    /**
     * 我发布的信息
     */
    List<InvestorInfoEntity> findMyPublish(MyVo vo);

    /**
     * 查看我的收藏
     */
    List<InvestorInfoEntity> findCollect(CollectVo vo);
}