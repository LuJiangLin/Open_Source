/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  SlideInfoDao.java
 * PACKAGE      :  com.anssy.venturebar.base.dao
 * CREATE DATE  :  2016-8-11
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.venturebar.base.dao;

import com.anssy.venturebar.base.entity.SlideInfoEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author make it
 * @version SVN #V1# #2016-8-11#
 *          基础_幻灯片信息
 */
@Repository("slideInfoDao")
public interface SlideInfoDao {

    /**
     * 查询首页轮播幻灯片信息
     */
    List<SlideInfoEntity> findSlideList(int type);

    /**
     * 修改点击次数
     */
    int updateClickNum(Long id);

}