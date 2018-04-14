/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  ProjectInfoDao.java
 * PACKAGE      :  com.anssy.venturebar.invest.dao
 * CREATE DATE  :  2016-8-10
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.venturebar.invest.dao;

import com.anssy.venturebar.invest.entity.ProjectInfoEntity;
import com.anssy.venturebar.invest.vo.FieldVo;
import com.anssy.venturebar.invest.vo.PvVo;
import com.anssy.webcore.vo.CollectVo;
import com.anssy.webcore.vo.GPSVo;
import com.anssy.webcore.vo.MyVo;
import com.anssy.webcore.vo.ReferralsVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author make it
 * @version SVN #V1# #2016-8-10#
 *          投资_项目信息
 */
@Repository("projectInfoDao")
public interface ProjectInfoDao {

    /**
     * 添加项目信息
     */
    int insertProjectInfo(ProjectInfoEntity info);

    /**
     * 修改项目信息
     */
    int updateProjectInfo(ProjectInfoEntity info);

    /**
     * 获取主键
     */
    Long findId();

    /**
     * 通过主键ID查询项目信息
     */
    ProjectInfoEntity findProjectInfoById(Long id);

    /**
     * 修改浏览量
     */
    int updatePV(Long id);

    /**
     * 点赞
     */
    int updatePraise(Long id);

    /**
     * 修改权重
     */
    int updateWeight(Long id, Long weight);

    /**
     * 删除项目信息
     */
    int deleteProjectInfo(Long id);

    /**
     * 删除全部项目信息
     */
    int deleteAllProjectInfo();

    /**
     * 附近搜索
     */
    List<ProjectInfoEntity> findListByGPS(GPSVo vo);

    /**
     * 行业搜索
     */
    List<ProjectInfoEntity> findListByField(FieldVo vo);

    /**
     * 智能搜索
     */
    List<ProjectInfoEntity> findListByPv(PvVo vo);

    /**
     * 推荐项目
     */
    List<ProjectInfoEntity> referrals(ReferralsVo vo);

    /**
     * 我发布的信息
     */
    List<ProjectInfoEntity> findMyPublish(MyVo vo);

    /**
     * 查看我的收藏
     */
    List<ProjectInfoEntity> findCollect(CollectVo vo);
}