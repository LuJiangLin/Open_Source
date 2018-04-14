/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  FieldInfoCacheServer.java
 * PACKAGE      :  com.anssy.venturebar.base.server
 * CREATE DATE  :  2016-8-24
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.venturebar.base.server;

import com.anssy.venturebar.base.dao.FieldInfoDao;
import com.anssy.venturebar.base.entity.FieldInfoEntity;
import com.anssy.webcore.core.cache.CacheOperator;
import com.anssy.webcore.core.cache.CacheServer;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author make it
 * @version SVN #V1# #2016-8-24#
 *          行业信息缓存
 */
@Service("fieldInfoCacheServer")
public class FieldInfoCacheServer extends CacheServer<FieldInfoEntity> implements CacheOperator {

    /**
     * 缓存名称
     */
    private final String cacheName = "FieldInfo";
    @Resource
    private FieldInfoDao fieldInfoDao;

    public void load() {
        try {
            List<FieldInfoEntity> fields = fieldInfoDao.findFieldAll();
            for (FieldInfoEntity entity : fields) {
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
     * 通过field获取对应的行业信息
     */
    public String findFields(String field) {
        StringBuilder fields = new StringBuilder();
        if (field != null && !StringUtils.isBlank(field)) {
            String[] ids = field.split(",");
            int j = 0;
            for (String id : ids) {
                id.replaceAll(" ", "");
                if (id == null || id.equals("null") || id.equals("")) {
                } else if (StringUtils.isNotBlank(id)) {
                    if (j > 0) {
                        fields.append(";");
                    }
                    fields.append(findFieldName(Long.parseLong(id)));
                    j++;
                }
            }
        }
        return fields.toString();
    }

    /**
     * 获取行业名称
     *
     * @param fieldId Id
     */
    private String findFieldName(Long fieldId) {
        String fieldName = "";
        FieldInfoEntity info;
        try {
            info = super.getValue(cacheName, fieldId.toString());
            if (info != null) {
                fieldName = info.getFieldName();
            } else {
                info = fieldInfoDao.findFieldById(fieldId);
                if (info != null) {
                    fieldName = info.getFieldName();
                }
            }
        } catch (Exception e) {
            try {
                info = fieldInfoDao.findFieldById(fieldId);
                if (info != null) {
                    fieldName = info.getFieldName();
                }
            } catch (Exception e1) {
                fieldName = "";
            }
        }
        return fieldName;
    }

}