/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  ProvinceDao.java
 * PACKAGE      :  com.anssy.venturebar.base.dao
 * CREATE DATE  :  2016-8-9
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.venturebar.base.dao;

import com.anssy.venturebar.base.entity.ProvinceEntity;
import com.anssy.venturebar.base.vo.AreaVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author make it
 * @version SVN #V1# #2016-8-9#
 *          基础_省信息
 */
@Repository("provinceDao")
public interface ProvinceDao {

    /**
     * 查询显示省信息
     */
    List<ProvinceEntity> findProvince();

    /**
     * 查询所有的省信息
     */
    List<AreaVo> findProvinceAreaVoAll();

    /**
     * 通过name反查省ID
     */
    Long findProvinceIdByName(String name);

    /**
     * 通过省ID查询省名称
     */
    String findProvinceByProvinceId(Long provinceId);

}