/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  CityDao.java
 * PACKAGE      :  com.anssy.venturebar.base.dao
 * CREATE DATE  :  2016-8-9
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.venturebar.base.dao;

import com.anssy.venturebar.base.entity.CityEntity;
import com.anssy.venturebar.base.vo.AreaVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author make it
 * @version SVN #V1# #2016-8-9#
 *          基础_市信息
 */
@Repository("cityDao")
public interface CityDao {

    /**
     * 通过省ID查询市信息
     *
     * @param provinceId 省ID
     */
    List<CityEntity> findCityByProvinceId(Long provinceId);

    /**
     * 查询所有的市信息
     */
    List<AreaVo> findCityAreaVoAll();

    /**
     * 通过市ID查询市名称
     */
    String findCityByCityId(Long cityId);

    /**
     * 通过name反查市ID
     */
    Long findCityIdByName(String name);

    Long findProvinceId(Long cityId);
}