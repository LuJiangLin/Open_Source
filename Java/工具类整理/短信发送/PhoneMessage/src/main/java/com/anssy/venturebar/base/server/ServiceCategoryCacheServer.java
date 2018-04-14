/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  ServiceCategoryCacheServer.java
 * PACKAGE      :  com.anssy.venturebar.base.server
 * CREATE DATE  :  2016-8-24
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.venturebar.base.server;

import com.anssy.venturebar.base.dao.ServiceCategoryDao;
import com.anssy.venturebar.base.entity.ServiceCategoryEntity;
import com.anssy.webcore.core.cache.CacheOperator;
import com.anssy.webcore.core.cache.CacheServer;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author make it
 * @version SVN #V1# #2016-8-24#
 *          服务类别缓存
 */
@Service("serviceCategoryCacheServer")
public class ServiceCategoryCacheServer extends CacheServer<ServiceCategoryEntity> implements CacheOperator {

    /**
     * 缓存名称
     */
    private final String cacheName = "ServiceCategory";
    @Resource
    private ServiceCategoryDao serviceCategoryDao;

    public void load() {
        try {
            List<ServiceCategoryEntity> categorys = serviceCategoryDao.findServiceCategoryAll();
            for (ServiceCategoryEntity entity : categorys) {
                super.put(cacheName, entity.getId().toString(), entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getCacheName() {
        return cacheName;
    }

    /**
     * 获取服务类别(单个)
     */
    public String findCategory(Long serviceType) {
        return findServiceName(serviceType);
    }

    /**
     * 获取服务类别(多个)
     */
    public String findCategory(String field) {
        StringBuilder fields = new StringBuilder();
        if (field != null && !StringUtils.isBlank(field)) {
            String[] ids = field.split(",");
            int j = 0;
            String category;
            for (String id : ids) {
                if (StringUtils.isNotBlank(id)) {
                    if (j > 0) {
                        fields.append(",");
                    }
                    category = findServiceName(Long.parseLong(id));
                    if (StringUtils.isNotBlank(category)) {
                        fields.append(category);
                        j++;
                    }
                }
            }
        }
        return fields.toString();
    }


    /**
     * 获取服务类别
     *
     * @param serviceType type
     */
    private String findServiceName(Long serviceType) {
        String category = "";
        ServiceCategoryEntity sc;
        try {
            sc = super.getValue(cacheName, serviceType.toString());
            if (sc != null) {
                category = sc.getServiceName();
            } else {
                sc = serviceCategoryDao.findServiceCategoryById(serviceType);
                if (sc != null) {
                    category = sc.getServiceName();
                }
            }
        } catch (Exception e) {
            try {
                sc = serviceCategoryDao.findServiceCategoryById(serviceType);
                if (sc != null) {
                    category = sc.getServiceName();
                }
            } catch (Exception e1) {
                category = "";
            }
        }
        return category;
    }

}