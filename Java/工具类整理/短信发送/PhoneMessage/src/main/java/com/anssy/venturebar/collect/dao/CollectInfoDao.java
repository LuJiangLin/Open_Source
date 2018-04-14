/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  CollectInfoDao.java
 * PACKAGE      :  com.anssy.venturebar.collect.dao
 * CREATE DATE  :  2016-8-3
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.venturebar.collect.dao;

import com.anssy.venturebar.collect.entity.CollectInfoEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author make it
 * @version SVN #V1# #2016-8-3#
 *          收藏_收藏信息
 */
@Repository("collectInfoDao")
public interface CollectInfoDao {

    /**
     * 添加收藏信息
     */
    int insertCollectInfo(CollectInfoEntity collect);

    /**
     * 删除收藏信息
     */
    int deleteCollectInfo(Long id);

    /**
     * 查询收藏信息
     */
    CollectInfoEntity findCollectByType(int type, Long fid, Long collectId);

    /**
     * 查询我的收藏信息(某个模块)
     */
    List<CollectInfoEntity> findCollect(int type, Long collectId);

}