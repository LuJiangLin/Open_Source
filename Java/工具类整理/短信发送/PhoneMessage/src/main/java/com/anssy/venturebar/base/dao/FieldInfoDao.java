/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  FieldInfoDao.java
 * PACKAGE      :  com.anssy.venturebar.base.dao
 * CREATE DATE  :  2016-8-10
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.venturebar.base.dao;

import com.anssy.venturebar.base.entity.FieldInfoEntity;
import com.anssy.inter.base.vo.FieldVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author make it
 * @version SVN #V1# #2016-8-10#
 *          基础_行业信息
 */
@Repository("fieldInfoDao")
public interface FieldInfoDao {

    /**
     * 查询某一级的行业
     */
    List<FieldInfoEntity> findField(FieldVo vo);

    /**
     * 查询类型全部行业
     */
    List<FieldInfoEntity> findFieldByType(int type);

    /**
     * 查询全部行业
     */
    List<FieldInfoEntity> findFieldAll();

    /**
     * 通过ID查询行业信息
     */
    FieldInfoEntity findFieldById(Long Id);

    /**
     * 通过ID查询二级的信息
     */
    List<FieldInfoEntity> findFieldByFatherId(Long fatherId);

}