/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  DatadictDao.java
 * PACKAGE      :  com.anssy.venturebar.base.dao
 * CREATE DATE  :  2016-8-13
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.venturebar.base.dao;

import com.anssy.venturebar.base.entity.DatadictEntity;
import com.anssy.venturebar.base.vo.DatadictVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author make it
 * @version SVN #V1# #2016-8-13#
 *          基础_数据字典
 */
@Repository("datadictDao")
public interface DatadictDao {

    /**
     * 查询所有的字典信息
     */
    List<DatadictEntity> findAllDict();

    /**
     * 通过类别查询字典信息
     */
    List<DatadictVo> findDictByCategory(String category);

    /**
     * 获取某一个字典
     */
    String findfindDictByLiteral(String category, Long literal);

}