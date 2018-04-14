/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  ServiceItemDao.java
 * PACKAGE      :  com.anssy.venturebar.service.dao
 * CREATE DATE  :  2016-8-10
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.venturebar.service.dao;

import com.anssy.venturebar.service.entity.ServiceItemEntity;
import org.springframework.stereotype.Repository;

/**
 * @author make it
 * @version SVN #V1# #2016-8-10#
 *          服务_服务明细
 */
@Repository("serviceItemDao")
public interface ServiceItemDao {

    /**
     * 添加服务明细
     */
    int insertServiceItem(ServiceItemEntity item);

    /**
     * 修改服务明细
     */
    int updateServiceItem(ServiceItemEntity item);

    /**
     * 通过服务ID查询服务明细
     */
    ServiceItemEntity findServiceItem(Long serviceId);

    /**
     * 删除服务明细
     */
    int deleteServiceItem(Long serviceId);

    /**
     * 修改留言量
     */
    int updateLeaveNumber(Long serviceId);

    /**
     * 修改收藏量
     */
    int updateCollectNumber(Long serviceId, Long number);

}