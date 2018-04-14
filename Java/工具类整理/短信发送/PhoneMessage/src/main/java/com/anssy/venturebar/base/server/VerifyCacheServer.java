/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  VerifyCacheServer.java
 * PACKAGE      :  com.anssy.venturebar.base.server
 * CREATE DATE  :  2016-8-27
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.venturebar.base.server;

import com.anssy.webcore.common.BaseConstants;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author make it
 * @version SVN #V1# #2016-8-27#
 *          手机验证码redis时间缓存
 */
@Service("verifyCacheServer")
public class VerifyCacheServer {

    @Resource
    RedisTemplate<String, Long> redisTemplate;

    /**
     * 将号码和验证码加入缓存
     */
    public void setCode(String phone, Long code) {
        try {
            redisTemplate.setKeySerializer(new StringRedisSerializer());
            redisTemplate.setValueSerializer(new GenericToStringSerializer<Long>(Long.class));
            redisTemplate.opsForValue().set(phone, code, BaseConstants.SECONDS, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 验证号码和验证码
     */
    public Long findCode(String phone) {
        Long code = null;
        try {
            code = redisTemplate.opsForValue().get(phone);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return code;
    }

}