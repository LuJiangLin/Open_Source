/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  SiteTypeCacheServer.java
 * PACKAGE      :  com.anssy.venturebar.base.server
 * CREATE DATE  :  2016-8-24
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.venturebar.base.server;

import com.anssy.venturebar.base.dao.SiteTypeDao;
import com.anssy.venturebar.base.entity.SiteTypeEntity;
import com.anssy.webcore.core.cache.CacheOperator;
import com.anssy.webcore.core.cache.CacheServer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author make it
 * @version SVN #V1# #2016-8-24#
 *          场地类型缓存
 */
@Service("siteTypeCacheServer")
public class SiteTypeCacheServer extends CacheServer<SiteTypeEntity> implements CacheOperator {

    /**
     * 缓存名称
     */
    private final String cacheName = "SiteType";

    @Resource
    private SiteTypeDao siteTypeDao;

    public void load() {
        try {
            List<SiteTypeEntity> sts = siteTypeDao.findSiteTypeAll();
            for (SiteTypeEntity st : sts) {
                super.put(cacheName, st.getId().toString(), st);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取场地类型
     *
     * @param typeId id
     */
    public String findSiteType(String typeId) {
        String typeName = "";
        SiteTypeEntity siteType;
        try {
            siteType = super.getValue(cacheName, typeId);
            if (siteType != null && siteType.getId() > 0) {
                typeName = siteType.getSiteName();
            } else {
                siteType = siteTypeDao.findSiteTypeById(Long.parseLong(typeId));
                if (siteType != null && siteType.getId() > 0) {
                    typeName = siteType.getSiteName();
                }
            }
        } catch (Exception e) {
            try {
                siteType = siteTypeDao.findSiteTypeById(Long.parseLong(typeId));
                if (siteType != null && siteType.getId() > 0) {
                    typeName = siteType.getSiteName();
                }
            } catch (NumberFormatException e1) {
                typeName = "";
            }
        }
        return typeName;
    }

    public String getCacheName() {
        return cacheName;
    }

}