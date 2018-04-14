/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  SystemLogDao.java
 * PACKAGE      :  com.anssy.venturebar.log.dao
 * CREATE DATE  :  2016-8-25
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.venturebar.log.dao;

import com.anssy.venturebar.log.entity.SystemLogEntity;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * @author make it
 * @version SVN #V1# #2016-8-25#
 *          日志_系统日志
 */
@Repository("systemLogDao")
public interface SystemLogDao {

    /**
     * 添加日志
     */
    int insertSystemLog(SystemLogEntity log);

    /**
     * 删除日志
     */
    int deleteSystemLog(Date time);

}