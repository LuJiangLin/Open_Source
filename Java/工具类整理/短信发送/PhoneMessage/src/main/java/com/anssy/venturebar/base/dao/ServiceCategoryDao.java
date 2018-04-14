/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  ServiceCategoryDao.java
 * PACKAGE      :  com.anssy.venturebar.base.dao
 * CREATE DATE  :  2016-8-18
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.venturebar.base.dao;

import com.anssy.venturebar.base.entity.ServiceCategoryEntity;
import com.anssy.inter.service.vo.ServiceCategoryVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author make it
 * @version SVN #V1# #2016-8-18#
 *          基础_服务类别信息
 */
@Repository("serviceCategoryDao")
public interface ServiceCategoryDao {

    /**
     * 获取服务类别
     */
    List<ServiceCategoryEntity> findServiceCategory(ServiceCategoryVo vo);

    /**
     * 获取服务类别
     */
    List<ServiceCategoryEntity> findServiceCategoryAll();

    /**
     * 通过ID查询服务类别
     */
    ServiceCategoryEntity findServiceCategoryById(Long Id);

    /**
     * 通过ID查询二级的信息
     */
    List<ServiceCategoryEntity> findServiceCategoryByFatherId(Long fatherId);

}