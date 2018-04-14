/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  CollectServer.java
 * PACKAGE      :  com.anssy.inter.collect.server
 * CREATE DATE  :  2016-8-3
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.inter.collect.server;

import com.anssy.venturebar.activity.dao.ActivityItemDao;
import com.anssy.venturebar.base.dao.DatadictDao;
import com.anssy.venturebar.base.vo.DatadictVo;
import com.anssy.venturebar.collect.dao.CollectInfoDao;
import com.anssy.venturebar.collect.entity.CollectInfoEntity;
import com.anssy.venturebar.invest.dao.InvestorItemDao;
import com.anssy.venturebar.invest.dao.ProjectItemDao;
import com.anssy.venturebar.service.dao.PolicyElucidationDao;
import com.anssy.venturebar.service.dao.ServiceItemDao;
import com.anssy.venturebar.site.dao.SiteItemDao;
import com.anssy.venturebar.team.dao.EpibolyItemDao;
import com.anssy.venturebar.team.dao.PartnershipItemDao;
import com.anssy.venturebar.technology.dao.TutorItemDao;
import com.anssy.inter.collect.vo.CollectVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author make it
 * @version SVN #V1# #2016-8-3#
 *          收藏_收藏信息
 */
@Service("collectServer")
public class CollectServer {

    @Resource
    private DatadictDao datadictDao;
    @Resource
    private CollectInfoDao collectInfoDao;
    @Resource
    private SiteItemDao siteItemDao;
    @Resource
    private InvestorItemDao investorItemDao;
    @Resource
    private ProjectItemDao projectItemDao;
    @Resource
    private EpibolyItemDao epibolyItemDao;
    @Resource
    private PartnershipItemDao partnershipItemDao;
    @Resource
    private TutorItemDao tutorItemDao;
    @Resource
    private ServiceItemDao serviceItemDao;
    @Resource
    private ActivityItemDao activityItemDao;
    @Resource
    private PolicyElucidationDao policyElucidationDao;

    /**
     * 查询我的收藏信息(某个模块)
     */
    public List<CollectInfoEntity> findCollect(CollectVo vo, Long userId) throws Exception {
        return collectInfoDao.findCollect(vo.getType(), userId);
    }

    /**
     * 获取类型
     */
    public List<DatadictVo> findCollectType() throws Exception {
        return datadictDao.findDictByCategory("LEAVE_TYPE");
    }

    /**
     * 查询收藏信息是否存在
     */
    public CollectInfoEntity findCollectByType(CollectVo vo, Long userId) throws Exception {
        return collectInfoDao.findCollectByType(vo.getType(), vo.getFid(), userId);
    }

    /**
     * 收藏
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean collect(CollectVo vo, Long userId) throws Exception {
        boolean flag = false;
        CollectInfoEntity collect = findCollectByType(vo, userId);
        if (collect == null) {
            collect = new CollectInfoEntity();
            collect.setType(vo.getType());
            collect.setFid(vo.getFid());
            collect.setCollectId(userId);
            collect.setCollectTime(new Date());
            if (collectInfoDao.insertCollectInfo(collect) > 0) {
                flag = true;
                updateCollectNumber(vo.getType(), vo.getFid(), 1L);
            }
        }
        return flag;
    }

    /**
     * 取消收藏
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean cancelCollect(CollectVo vo, Long userId) throws Exception {
        boolean flag = false;
        CollectInfoEntity collect = findCollectByType(vo, userId);
        if (collect != null && collect.getId() > 0) {
            if (collectInfoDao.deleteCollectInfo(collect.getId()) > 0) {
                flag = true;
                updateCollectNumber(vo.getType(), vo.getFid(), -1L);
            }
        }
        return flag;
    }

    /**
     * 修改收藏量
     */
    private void updateCollectNumber(int type, Long fid, Long number) {
        if (type == 1) {//1-场地
            siteItemDao.updateCollectNumber(fid, number);
        } else if (type == 2) {//2-投资人
            investorItemDao.updateCollectNumber(fid, number);
        } else if (type == 3) {//3-投资项目
            projectItemDao.updateCollectNumber(fid, number);
        } else if (type == 4) {//4-团队合伙
            partnershipItemDao.updateCollectNumber(fid, number);
        } else if (type == 5) {//5-团队众包
            epibolyItemDao.updateCollectNumber(fid, number);
        } else if (type == 6) {//6-技术导师
            tutorItemDao.updateCollectNumber(fid, number);
        } else if (type == 7) {//7 服务
            serviceItemDao.updateCollectNumber(fid, number);
        } else if (type == 8) {//8 活动信息
            activityItemDao.updateCollectNumber(fid, number);
        } else if (type == 9) {//9 政策解读
            policyElucidationDao.updateCollectNumber(fid, number);
        }
    }

}