/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  CacheOperator.java
 * PACKAGE      :  com.anssy.webcore.core.cache
 * CREATE DATE  :  2016-8-11
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.webcore.core.cache;

/**
 * @author make it
 * @version SVN #V1# #2016-8-11#
 *          缓存操作接口
 */
public interface CacheOperator {

    /**
     * 加载数据到redis缓存
     */
    void load();

}