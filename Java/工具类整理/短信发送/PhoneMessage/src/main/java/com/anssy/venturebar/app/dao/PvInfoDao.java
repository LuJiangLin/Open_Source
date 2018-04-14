/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  PvInfoDao.java
 * PACKAGE      :  com.anssy.venturebar.app.dao
 * CREATE DATE  :  2016-8-30
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.venturebar.app.dao;

import com.anssy.venturebar.app.entity.PvInfoEntity;
import org.springframework.stereotype.Repository;

/**
 * @author make it
 * @version SVN #V1# #2016-8-30#
 *          APP_PV信息
 */
@Repository("pvInfoDao")
public interface PvInfoDao {

    /**
     * 查询
     */
    PvInfoEntity findPvInfo(PvInfoEntity info);

    /**
     * 添加PV信息
     */
    int insertPvInfo(PvInfoEntity info);

    /**
     * 修改PV信息
     */
    int updatePvInfo(PvInfoEntity info);

    /**
     * 查询某天的PV量
     */
    Long findPvByAppDate(String appDate);

}