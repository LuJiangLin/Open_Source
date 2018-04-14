/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  SiteInfoDao.java
 * PACKAGE      :  com.anssy.venturebar.site.dao
 * CREATE DATE  :  2016-8-10
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.venturebar.site.dao;

import com.anssy.venturebar.site.entity.SiteInfoEntity;
import com.anssy.venturebar.site.vo.PvVo;
import com.anssy.venturebar.site.vo.SiteTypeVo;
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
 *          场地_场地信息
 */
@Repository("siteInfoDao")
public interface SiteInfoDao {

    /**
     * 获取主键
     */
    Long findId();

    /**
     * 添加场地信息
     */
    int insertSiteInfo(SiteInfoEntity site);

    /**
     * 修改场地信息
     */
    int updateSiteInfo(SiteInfoEntity site);

    /**
     * 通过场地ID查询信息
     */
    SiteInfoEntity findSiteInfoById(Long id);

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
     * 删除
     */
    int deleteSiteInfo(Long id);

    /**
     * 删除全部
     */
    int deleteAllSiteInfo();

    /**
     * 按GPS进行附近搜索
     */
    List<SiteInfoEntity> findListByGPS(GPSVo vo);

    /**
     * 按类型进行场地类型搜索
     */
    List<SiteInfoEntity> findListByType(SiteTypeVo vo);

    /**
     * 智能搜索
     */
    List<SiteInfoEntity> findListByPv(PvVo vo);

    /**
     * 推荐场地
     */
    List<SiteInfoEntity> referrals(ReferralsVo vo);

    /**
     * 我发布的信息
     */
    List<SiteInfoEntity> findMyPublish(MyVo vo);

    /**
     * 过期
     */
    int overdue(Date time);

    /**
     * 查看我的收藏
     */
    List<SiteInfoEntity> findCollect(CollectVo vo);

}