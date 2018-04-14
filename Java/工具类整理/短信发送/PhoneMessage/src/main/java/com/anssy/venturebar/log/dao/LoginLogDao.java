/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  LoginLogDao.java
 * PACKAGE      :  com.anssy.venturebar.log.dao
 * CREATE DATE  :  2016-8-25
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.venturebar.log.dao;

import com.anssy.venturebar.log.entity.LoginLogEntity;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * @author make it
 * @version SVN #V1# #2016-8-25#
 *          日志_登录日志
 */
@Repository("loginLogDao")
public interface LoginLogDao {

    /**
     * 添加日志
     */
    int insertLoginLog(LoginLogEntity log);

    /**
     * 查询日志(用户id)
     */
    LoginLogEntity findLoginLogByUserId(Long userId);

    /**
     * 修改日志(登出操作)
     */
    int updateLoginLog(LoginLogEntity log);

    /**
     * 删除日志
     */
    int deleteLoginLog(Date time);

    /**
     * 查询某天的登录数量
     */
    Long findLoginNum(Date begin, Date end);

}