/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  SmsInfoDao.java
 * PACKAGE      :  com.anssy.venturebar.app.dao
 * CREATE DATE  :  2016-8-28
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.venturebar.app.dao;

import com.anssy.venturebar.app.entity.SmsInfoEntity;

/**
 * @author make it
 * @version SVN #V1# #2016-8-28#
 *          APP_短信信息
 */
public interface SmsInfoDao {

    /**
     * 添加短信信息
     */
    int insertSMSInfo(SmsInfoEntity info);

}