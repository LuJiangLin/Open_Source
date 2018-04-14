/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  CacheServer.java
 * PACKAGE      :  com.anssy.webcore.core.cache
 * CREATE DATE  :  2016-8-11
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.webcore.core.cache;

import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

/**
 * @author make it
 * @version SVN #V1# #2016-8-11#
 *          缓存服务类
 */
public class CacheServer<T> {

    @Resource
    RedisTemplate<String, T> redisTemplate;

    /**
     * 通过cacheName获取缓存值
     */
    public T getValue(String cacheName, String key) {
        return (T) redisTemplate.opsForHash().get(cacheName, key);
    }

    /**
     * 加入缓存
     */
    public void put(String cacheName, String key, T t) {
        redisTemplate.opsForHash().put(cacheName, key, t);
    }

    /**
     * 删除缓存
     */
    public void delCache(String cacheName, String key) {
        redisTemplate.opsForHash().delete(cacheName, key);
    }

}