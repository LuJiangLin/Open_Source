/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  TutorItemDao.java
 * PACKAGE      :  com.anssy.venturebar.technology.dao
 * CREATE DATE  :  2016-8-10
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.venturebar.technology.dao;

import com.anssy.venturebar.technology.entity.TutorItemEntity;
import org.springframework.stereotype.Repository;

/**
 * @author make it
 * @version SVN #V1# #2016-8-10#
 *          技术_导师明细
 */
@Repository("tutorItemDao")
public interface TutorItemDao {

    /**
     * 添加导师明细
     */
    int insertTutorItem(TutorItemEntity item);

    /**
     * 修改导师明细
     */
    int updateTutorItem(TutorItemEntity item);

    /**
     * 通过导师ID查询导师明细
     */
    TutorItemEntity findTutorItem(Long tutorId);

    /**
     * 通过导师ID删除导师明细
     */
    int deleteTutorItem(Long tutorId);

    /**
     * 修改留言量
     */
    int updateLeaveNumber(Long tutorId);

    /**
     * 修改收藏量
     */
    int updateCollectNumber(Long tutorId, Long number);

}