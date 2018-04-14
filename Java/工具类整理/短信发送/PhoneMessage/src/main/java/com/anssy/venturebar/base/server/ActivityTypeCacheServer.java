/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  ActivityTypeCacheServer.java
 * PACKAGE      :  com.anssy.venturebar.base.server
 * CREATE DATE  :  2016-8-14
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.venturebar.base.server;

import com.anssy.venturebar.base.dao.ActivityTypeDao;
import com.anssy.venturebar.base.entity.ActivityTypeEntity;
import com.anssy.webcore.core.cache.CacheOperator;
import com.anssy.webcore.core.cache.CacheServer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author make it
 * @version SVN #V1# #2016-8-14#
 *          活动类型缓存
 */
@Service("activityTypeCacheServer")
public class ActivityTypeCacheServer extends CacheServer<ActivityTypeEntity> implements CacheOperator {

    /**
     * 缓存名称
     */
    private final String cacheName = "ActivityType";

    @Resource
    private ActivityTypeDao activityTypeDao;

    public void load() {
        try {
            List<ActivityTypeEntity> types = activityTypeDao.findActivityTypeAll();
            for (ActivityTypeEntity type : types) {
                super.put(cacheName, type.getId().toString(), type);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String findActivityType(String typeId) {
        String typeName = "";
        ActivityTypeEntity type;
        try {
            type = super.getValue(cacheName, typeId);
            if (type != null && type.getId() > 0) {
                typeName = type.getActivityName();
            } else {
                type = activityTypeDao.findActivityTypeById(Long.parseLong(typeId));
                if (type != null && type.getId() > 0) {
                    typeName = type.getActivityName();
                }
            }
        } catch (Exception e) {
            try {
                type = activityTypeDao.findActivityTypeById(Long.parseLong(typeId));
                if (type != null && type.getId() > 0) {
                    typeName = type.getActivityName();
                }
            } catch (Exception e1) {
                typeName = "";
            }
        }
        return typeName;
    }

    public String getCacheName() {
        return cacheName;
    }

}
