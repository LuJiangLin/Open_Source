/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  UserItemDao.java
 * PACKAGE      :  com.anssy.venturebar.base.dao
 * CREATE DATE  :  2016-8-9
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.venturebar.base.dao;

import com.anssy.venturebar.base.entity.UserItemEntity;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * @author make it
 * @version SVN #V1# #2016-8-9#
 *          基础_用户明细
 */
@Repository("userItemDao")
public interface UserItemDao {

    /**
     * 添加
     */
    int insertUserItem(UserItemEntity userItem);

    /**
     * 通过用户ID查询用户明细信息
     */
    UserItemEntity findUserItemByUserId(Long userId);

    /**
     * 修改
     */
    int updateUserItem(UserItemEntity item);

    /**
     * 查询某天注册数量
     */
    Long findRegisterNum(Date begin, Date end);

    /**
     * 查询某天激活数量
     */
    Long findActivateNum(Date begin, Date end);

}