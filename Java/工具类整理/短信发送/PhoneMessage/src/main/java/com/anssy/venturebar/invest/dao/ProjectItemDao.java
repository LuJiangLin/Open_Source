/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  ProjectItemDao.java
 * PACKAGE      :  com.anssy.venturebar.invest.dao
 * CREATE DATE  :  2016-8-10
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.venturebar.invest.dao;

import com.anssy.venturebar.invest.entity.ProjectItemEntity;
import org.springframework.stereotype.Repository;

/**
 * @author make it
 * @version SVN #V1# #2016-8-10#
 *          投资_项目明细
 */
@Repository("projectItemDao")
public interface ProjectItemDao {

    /**
     * 添加项目明细
     */
    int insertProjectItem(ProjectItemEntity item);

    /**
     * 修改项目明细
     */
    int updateProjectItem(ProjectItemEntity item);

    /**
     * 通过项目ID查询项目明细
     */
    ProjectItemEntity findProjectItem(Long projectId);

    /**
     * 删除项目明细
     */
    int deleteProjectItem(Long projectId);

    /**
     * 删除全部项目明细
     */
    int deleteAllProjectItem();

    /**
     * 修改留言量
     */
    int updateLeaveNumber(Long projectId);

    /**
     * 修改收藏量
     */
    int updateCollectNumber(Long projectId, Long number);

}