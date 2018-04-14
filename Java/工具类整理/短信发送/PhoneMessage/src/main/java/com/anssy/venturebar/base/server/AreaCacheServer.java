/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  AreaCacheServer.java
 * PACKAGE      :  com.anssy.venturebar.base.server
 * CREATE DATE  :  2016-8-24
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.venturebar.base.server;

import com.anssy.venturebar.base.dao.AreaDao;
import com.anssy.venturebar.base.dao.CityDao;
import com.anssy.venturebar.base.dao.ProvinceDao;
import com.anssy.venturebar.base.vo.AreaVo;
import com.anssy.webcore.core.cache.CacheOperator;
import com.anssy.webcore.core.cache.CacheServer;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author make it
 * @version SVN #V1# #2016-8-24#
 *          省市区县缓存
 */
@Service("areaCacheServer")
public class AreaCacheServer extends CacheServer<AreaVo> implements CacheOperator {

    /**
     * 缓存名称
     */
    private final String cacheName = "Area";
    @Resource
    private ProvinceDao provinceDao;
    @Resource
    private CityDao cityDao;
    @Resource
    private AreaDao areaDao;

    public void load() {
        try {
            List<AreaVo> vos = provinceDao.findProvinceAreaVoAll();
            put(vos);
            vos = cityDao.findCityAreaVoAll();
            put(vos);
            vos = areaDao.findAreaAreaVoAll();
            put(vos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 加入到redis缓存中
     */
    private void put(List<AreaVo> vos) {
        for (AreaVo vo : vos) {
            super.put(cacheName, vo.getAreaId().toString(), vo);
        }
    }

    public String getCacheName() {
        return cacheName;
    }

    public String findArea(Long areaId) {
        String areaName;
        try {
            AreaVo vo = super.getValue(cacheName, areaId.toString());
            if (vo != null) {
                areaName = vo.getAreaName();
            } else {
                areaName = findfindAreafromDB(areaId);
            }
        } catch (Exception e) {
            areaName = findfindAreafromDB(areaId);
        }
        return areaName;
    }

    /**
     * 从DB查询省市区县名称
     */
    private String findfindAreafromDB(Long areaId) {
        String areaName;
        try {
            areaName = provinceDao.findProvinceByProvinceId(areaId);
            if (StringUtils.isBlank(areaName)) {
                areaName = cityDao.findCityByCityId(areaId);
            }
            if (StringUtils.isBlank(areaName)) {
                areaName = areaDao.findAreaByAreaId(areaId);
            }
            if (StringUtils.isBlank(areaName)) {
                areaName = "";
            }
        } catch (Exception e) {
            areaName = "";
        }
        return areaName;
    }

}