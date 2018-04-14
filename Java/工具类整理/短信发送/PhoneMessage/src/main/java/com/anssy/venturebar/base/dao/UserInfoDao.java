/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  UserInfoDao.java
 * PACKAGE      :  com.anssy.venturebar.base.dao
 * CREATE DATE  :  2016-8-9
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.venturebar.base.dao;

import com.anssy.venturebar.base.entity.UserInfoEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author make it
 * @version SVN #V1# #2016-8-9#
 *          基础_用户信息
 */
@Repository("userInfoDao")
public interface UserInfoDao {

    /**
     * 查询全部信息
     */
    List<UserInfoEntity> findAll();

    /**
     * 通过手机号码查询用户信息
     */
    UserInfoEntity findUserByPhone(String phone);

    /**
     * 通过Email查询用户信息
     */
    UserInfoEntity findUserByEmail(String email);

    /**
     * 通过主键ID查询用户信息
     */
    UserInfoEntity findUserById(Long id);

    /**
     * 获取主键
     */
    Long findId();

    /**
     * 注册方法
     */
    int register(UserInfoEntity user);

    /**
     * 修改用户信息
     */
    int updateUser(UserInfoEntity user);

}