/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  DatadictCacheServer.java
 * PACKAGE      :  com.anssy.venturebar.base.server
 * CREATE DATE  :  2016-8-13
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.venturebar.base.server;

import com.anssy.venturebar.base.dao.DatadictDao;
import com.anssy.venturebar.base.entity.DatadictEntity;
import com.anssy.webcore.core.cache.CacheOperator;
import com.anssy.webcore.core.cache.CacheServer;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author make it
 * @version SVN #V1# #2016-8-13#
 *          数据字典缓存服务
 */
@Service("datadictCacheServer")
public class DatadictCacheServer extends CacheServer<DatadictEntity> implements CacheOperator {

    @Resource
    private DatadictDao datadictDao;

    public void load() {
        try {
            List<DatadictEntity> dicts = datadictDao.findAllDict();
            for (DatadictEntity entity : dicts) {
                super.put(entity.getCategory(), entity.getLiteral().toString(), entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String findDict(String cacheName, Long id) {
        String dict = "";
        if (id != null) {
            try {
                DatadictEntity data = super.getValue(cacheName, id.toString());
                if (data != null) {
                    dict = data.getDictDesc();
                } else {
                    dict = datadictDao.findfindDictByLiteral(cacheName, id);
                }
            } catch (Exception e) {
                try {
                    dict = datadictDao.findfindDictByLiteral(cacheName, id);
                } catch (Exception e1) {
                    dict = "";
                }
            }
        }
        if (StringUtils.isBlank(dict)) {
            dict = "";
        }
        return dict;
    }

}