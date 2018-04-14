/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  PvInfoCacheServer.java
 * PACKAGE      :  com.anssy.venturebar.app.server
 * CREATE DATE  :  2016-8-30
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.venturebar.app.server;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author make it
 * @version SVN #V1# #2016-8-30#
 *          pv缓存服务
 */
@Service("pvInfoCacheServer")
public class PvInfoCacheServer {

    @Resource
    RedisTemplate<String, Long> redisTemplate;

    private final String KEY = "PV_NUM";

    /**
     * 每次将PV加1
     */
    public void addPVNum() {
        try {
            redisTemplate.setKeySerializer(new StringRedisSerializer());
            redisTemplate.setValueSerializer(new GenericToStringSerializer<Long>(Long.class));
            redisTemplate.opsForValue().increment(KEY, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取PV
     */
    public Long findPVNum() {
        Long pvNum;
        try {
            pvNum = redisTemplate.opsForValue().get(KEY);
        } catch (Exception e) {
            pvNum = 0l;
            e.printStackTrace();
        }
        return pvNum;
    }

    /**
     * 清空PV
     */
    public void clearPVNum() {
        try {
            redisTemplate.setKeySerializer(new StringRedisSerializer());
            redisTemplate.setValueSerializer(new GenericToStringSerializer<Long>(Long.class));
            redisTemplate.opsForValue().set(KEY, 0L);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}