/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  UserInfoCacheServer.java
 * PACKAGE      :  com.anssy.venturebar.base.server
 * CREATE DATE  :  2016-8-11
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.venturebar.base.server;

import com.anssy.venturebar.base.dao.UserInfoDao;
import com.anssy.venturebar.base.entity.UserInfoEntity;
import com.anssy.webcore.core.cache.CacheOperator;
import com.anssy.webcore.core.cache.CacheServer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author make it
 * @version SVN #V1# #2016-8-11#
 *          基础_用户信息缓存服务
 */
@Service("userInfoCacheServer")
public class UserInfoCacheServer extends CacheServer<UserInfoEntity> implements CacheOperator {

    /**
     * 缓存名称
     */
    private final String cacheName = "userInfo";

    @Resource
    private UserInfoDao userInfoDao;

    public void load() {
        try {
            List<UserInfoEntity> users = userInfoDao.findAll();
            for (UserInfoEntity entity : users) {
                super.put(cacheName, entity.getId().toString(), entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getCacheName() {
        return cacheName;
    }

    public UserInfoEntity findUserInfo(Long userId) {
        UserInfoEntity info;
        try {
            info = super.getValue(cacheName, userId.toString());
            if (info == null) {
                info = userInfoDao.findUserById(userId);
            }
        } catch (Exception e) {
            try {
                info = userInfoDao.findUserById(userId);
            } catch (Exception e1) {
                info = null;
            }
        }
        return info;
    }

    /**
     * 获取用户昵称
     *
     * @param publishId 用户id
     */
    public String findNickname(Long publishId) {
        String nickname = "";
        if (publishId != null) {
            UserInfoEntity user;
            try {
                user = super.getValue(cacheName, publishId.toString());
                if (user != null) {
                    nickname = user.getNickname();
                } else {
                    user = userInfoDao.findUserById(publishId);
                    if (user != null) {
                        nickname = user.getNickname();
                    }
                }
            } catch (Exception e) {
                try {
                    user = userInfoDao.findUserById(publishId);
                    if (user != null) {
                        nickname = user.getNickname();
                    }
                } catch (Exception e1) {
                    nickname = "";
                }
            }
        }
        return nickname;
    }

}