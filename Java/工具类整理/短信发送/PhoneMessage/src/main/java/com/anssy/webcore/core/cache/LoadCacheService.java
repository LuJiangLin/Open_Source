/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  LoadCacheService.java
 * PACKAGE      :  com.anssy.webcore.core.cache
 * CREATE DATE  :  2016-8-11
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.webcore.core.cache;

import com.anssy.venturebar.base.server.*;
import com.anssy.venturebar.log.server.SystemLogServer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author make it
 * @version SVN #V1# #2016-8-11#
 *          redis缓存服务类(加载)
 */
@Service("loadCacheService")
public class LoadCacheService {

    @Resource
    private UserInfoCacheServer userInfoCacheServer;
    @Resource
    private DatadictCacheServer datadictCacheServer;
    @Resource
    private FieldInfoCacheServer fieldInfoCacheServer;
    @Resource
    private ServiceCategoryCacheServer serviceCategoryCacheServer;
    @Resource
    private SiteTypeCacheServer siteTypeCacheServer;
    @Resource
    private ActivityTypeCacheServer activityTypeCacheServer;
    @Resource
    private AreaCacheServer areaCacheServer;
    @Resource
    private SystemLogServer systemLogServer;

    public void loadCacheAll() {
        try {
            userInfoCacheServer.load();
            datadictCacheServer.load();
            fieldInfoCacheServer.load();
            serviceCategoryCacheServer.load();
            siteTypeCacheServer.load();
            activityTypeCacheServer.load();
            areaCacheServer.load();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            systemLogServer.putInfo("loadCache");
        }
    }

}