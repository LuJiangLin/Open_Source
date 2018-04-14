/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  SiteTypeDao.java
 * PACKAGE      :  com.anssy.venturebar.base.dao
 * CREATE DATE  :  2016-8-17
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.venturebar.base.dao;

import com.anssy.venturebar.base.entity.SiteTypeEntity;
import com.anssy.inter.site.vo.TypeVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author make it
 * @version SVN #V1# #2016-8-17#
 *          基础_场地类型
 */
@Repository("siteTypeDao")
public interface SiteTypeDao {

    /**
     * 获取场地类型
     */
    List<SiteTypeEntity> findSiteType(TypeVo vo);

    /**
     * 获取场地类型
     */
    List<SiteTypeEntity> findSiteTypeAll();

    /**
     * 通过ID查询场地类型
     */
    SiteTypeEntity findSiteTypeById(Long Id);

    /**
     * 通过ID查询二级的信息
     */
    List<SiteTypeEntity> findSiteTypeByFatherId(Long fatherId);

}