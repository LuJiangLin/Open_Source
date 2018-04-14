/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  EpibolyItemDao.java
 * PACKAGE      :  com.anssy.venturebar.team.dao
 * CREATE DATE  :  2016-8-10
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.venturebar.team.dao;

import com.anssy.venturebar.team.entity.EpibolyItemEntity;
import org.springframework.stereotype.Repository;

/**
 * @author make it
 * @version SVN #V1# #2016-8-10#
 *          团队_众包明细
 */
@Repository("epibolyItemDao")
public interface EpibolyItemDao {

    /**
     * 添加众包明细
     */
    int insertEpibolyItem(EpibolyItemEntity item);

    /**
     * 修改众包明细
     */
    int updateEpibolyItem(EpibolyItemEntity item);

    /**
     * 通过众包ID查询众包明细
     */
    EpibolyItemEntity findEpibolyItem(Long epibolyId);

    /**
     * 通过众包ID删除众包明细
     */
    int deleteEpibolyItem(Long epibolyId);

    /**
     * 修改留言量
     */
    int updateLeaveNumber(Long epibolyId);

    /**
     * 修改收藏量
     */
    int updateCollectNumber(Long epibolyId, Long number);

}