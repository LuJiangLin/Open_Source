/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  PolicyElucidationDao.java
 * PACKAGE      :  com.anssy.venturebar.service.dao
 * CREATE DATE  :  2016-8-3
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.venturebar.base.dao;

import com.anssy.inter.base.vo.NewsPageVo;
import com.anssy.inter.base.vo.NewsVo;
import com.anssy.venturebar.base.entity.NewsEntity;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author make it
 * @version SVN #V1# #2016-8-3#
 *          资讯_政策解读
 */
@Repository("newsPolicyDao")
public interface NewsPolicyDao {

    /**
     * 添加政策解读
     */
    int insertPolicy(NewsEntity entity);

    /**
     * 查询政策解读信息
     */
    List<NewsEntity> findList(NewsPageVo pageVo);

    /**
     *  清除全部表数据
     */
    int deleteAllData();

    /**
     * 查询第一条数据
     */
    Date findBeginUpdate();

}