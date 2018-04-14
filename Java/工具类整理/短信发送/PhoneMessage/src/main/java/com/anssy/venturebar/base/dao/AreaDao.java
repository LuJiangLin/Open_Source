/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  AreaDao.java
 * PACKAGE      :  com.anssy.venturebar.base.dao
 * CREATE DATE  :  2016-8-9
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.venturebar.base.dao;

import com.anssy.venturebar.base.entity.AreaEntity;
import com.anssy.venturebar.base.vo.AreaVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author make it
 * @version SVN #V1# #2016-8-9#
 *          基础_区县信息
 */
@Repository("areaDao")
public interface AreaDao {

    /**
     * 通过市ID查询区县信息
     *
     * @param cityId 市ID
     */
    List<AreaEntity> findAreaByCityId(Long cityId);

    /**
     * 查询所有的区县信息
     */
    List<AreaVo> findAreaAreaVoAll();

    /**
     * 通过区县ID查询区县名称
     */
    String findAreaByAreaId(Long areaId);

    /**
     * 通过name反查区县ID
     */
    Long findAreaIdByName(String name);

    Long findCityId(Long areaId);
}