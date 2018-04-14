/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  SiteItemDao.java
 * PACKAGE      :  com.anssy.venturebar.site.dao
 * CREATE DATE  :  2016-8-10
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.venturebar.site.dao;

import com.anssy.venturebar.site.entity.SiteItemEntity;
import org.springframework.stereotype.Repository;

/**
 * @author make it
 * @version SVN #V1# #2016-8-10#
 *          场地_场地明细
 */
@Repository("siteItemDao")
public interface SiteItemDao {

    /**
     * 添加场地明细
     */
    int insertSiteItem(SiteItemEntity item);

    /**
     * 修改场地明细
     */
    int updateSiteItem(SiteItemEntity item);

    /**
     * 通过场地ID查询信息
     */
    SiteItemEntity findSiteItemById(Long siteId);

    /**
     * 删除
     */
    int deleteSiteItem(Long siteId);

    /**
     * 删除全部
     */
    int deleteAllSiteItem();

    /**
     * 修改留言量
     */
    int updateLeaveNumber(Long siteId);

    /**
     * 修改收藏量
     */
    int updateCollectNumber(Long siteId, Long number);

}