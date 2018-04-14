/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  ServiceInfoDao.java
 * PACKAGE      :  com.anssy.venturebar.service.dao
 * CREATE DATE  :  2016-8-10
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.venturebar.service.dao;

import com.anssy.venturebar.service.entity.ServiceInfoEntity;
import com.anssy.venturebar.service.vo.FieldVo;
import com.anssy.venturebar.service.vo.GPSVo;
import com.anssy.venturebar.service.vo.PvVo;
import com.anssy.webcore.vo.CollectVo;
import com.anssy.webcore.vo.MyVo;
import com.anssy.webcore.vo.ReferralsVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author make it
 * @version SVN #V1# #2016-8-10#
 *          服务_服务信息
 */
@Repository("serviceInfoDao")
public interface ServiceInfoDao {

    /**
     * 添加服务信息
     */
    int insertServiceInfo(ServiceInfoEntity info);

    /**
     * 修改服务信息
     */
    int updateServiceInfo(ServiceInfoEntity info);

    /**
     * 获取主键
     */
    Long findId();

    /**
     * 通过服务ID查询服务信息
     */
    ServiceInfoEntity findServiceInfoById(Long id);

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
     * 删除服务信息
     */
    int deleteServiceInfo(Long id);

    /**
     * 按GPS进行附近搜索
     */
    List<ServiceInfoEntity> findListByGPS(GPSVo vo);

    /**
     * 按服务类型搜索
     */
    List<ServiceInfoEntity> findListByType(FieldVo vo);

    /**
     * 智能搜索
     */
    List<ServiceInfoEntity> findListByPv(PvVo vo);

    /**
     * 推荐服务
     */
    List<ServiceInfoEntity> referrals(ReferralsVo vo);

    /**
     * 我发布的信息
     */
    List<ServiceInfoEntity> findMyPublish(MyVo vo);

    /**
     * 查看我的收藏
     */
    List<ServiceInfoEntity> findCollect(CollectVo vo);
}