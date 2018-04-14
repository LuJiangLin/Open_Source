/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  StatisticsInfoDao.java
 * PACKAGE      :  com.anssy.venturebar.app.dao
 * CREATE DATE  :  2016-8-30
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.venturebar.app.dao;

import com.anssy.venturebar.app.entity.StatisticsInfoEntity;
import org.springframework.stereotype.Repository;

/**
 * @author make it
 * @version SVN #V1# #2016-8-30#
 *          APP_统计信息
 */
@Repository("statisticsInfoDao")
public interface StatisticsInfoDao {

    /**
     * 查询
     */
    StatisticsInfoEntity findStatistics(String appDate);

    /**
     * 添加统计信息
     */
    int insertStatistics(StatisticsInfoEntity info);

}