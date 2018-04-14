/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  EpibolyServer.java
 * PACKAGE      :  com.anssy.inter.team.server
 * CREATE DATE  :  2016-8-23
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.inter.team.server;

import com.anssy.venturebar.base.server.AreaCacheServer;
import com.anssy.venturebar.base.server.FieldInfoCacheServer;
import com.anssy.venturebar.base.server.UserInfoCacheServer;
import com.anssy.venturebar.team.dao.EpibolyInfoDao;
import com.anssy.venturebar.team.dao.EpibolyItemDao;
import com.anssy.venturebar.team.entity.EpibolyInfoEntity;
import com.anssy.venturebar.team.entity.EpibolyItemEntity;
import com.anssy.venturebar.team.vo.FieldVo;
import com.anssy.venturebar.team.vo.PvVo;
import com.anssy.inter.base.server.BaseServer;
import com.anssy.inter.team.vo.EpibolyVo;
import com.anssy.inter.team.vo.SearchVo;
import com.anssy.webcore.common.BaseConstants;
import com.anssy.webcore.vo.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author make it
 * @version SVN #V1# #2016-8-23#
 */
@Service("epibolyServer")
public class EpibolyServer {

    @Resource
    private EpibolyInfoDao epibolyInfoDao;
    @Resource
    private EpibolyItemDao epibolyItemDao;
    @Resource
    private BaseServer baseServer;
    @Resource
    private FieldInfoCacheServer fieldInfoCacheServer;
    @Resource
    private AreaCacheServer areaCacheServer;
    @Resource
    private UserInfoCacheServer userInfoCacheServer;

    /**
     * 修改浏览量
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public int updatePV(Long id) throws Exception {
        return epibolyInfoDao.updatePV(id);
    }

    /**
     * 点赞
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public int updatePraise(Long id) throws Exception {
        return epibolyInfoDao.updatePraise(id);
    }

    /**
     * 权重
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public int updateWeight(Long id) throws Exception {
        return epibolyInfoDao.updateWeight(id, new Date().getTime());
    }

    /**
     * 通过众包ID查询众包信息
     */
    public EpibolyInfoEntity findEpibolyInfoById(Long id) throws Exception {
        return epibolyInfoDao.findEpibolyInfoById(id);
    }

    /**
     * 通过众包ID查询众包明细
     */
    public EpibolyItemEntity findEpibolyItem(Long epibolyId) throws Exception {
        return epibolyItemDao.findEpibolyItem(epibolyId);
    }

    /**
     * 删除众包信息
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean deleteEpiboly(Long epibolyId) throws Exception {
        boolean flag = false;
        EpibolyInfoEntity info = findEpibolyInfoById(epibolyId);
        if (info != null) {
            if (epibolyInfoDao.deleteEpiboly(epibolyId) > 0) {
                if (epibolyItemDao.deleteEpibolyItem(epibolyId) > 0) {
                    flag = true;
                }
            }
        }
        return flag;
    }

    /**
     * 附近搜索
     */
    public List<EpibolyInfoEntity> findEpibolyByNearby(SearchVo vo) throws Exception {
        PvVo pv = new PvVo();
        pv.setBeing((vo.getPage() - 1) * BaseConstants.PAGE_SIZE + 1);
        pv.setEnd((vo.getPage() - 1) * BaseConstants.PAGE_SIZE + BaseConstants.PAGE_SIZE);
        if (StringUtils.isNotBlank(vo.getSearch())) {
            pv.setSearch(vo.getSearch());
        }
        if (vo.getProvinceId() > 0) {
            pv.setProvinceId(vo.getProvinceId());
            if (vo.getCityId() > 0) {
                pv.setCityId(vo.getCityId());
                if (vo.getAreaId() > 0) {
                    pv.setAreaId(vo.getAreaId());
                }
            }
        }
        return replenish(epibolyInfoDao.findEpibolyByNearby(pv));
    }

    /**
     * 行业搜索
     */
    public List<EpibolyInfoEntity> findEpibolyByField(SearchVo vo) throws Exception {
        FieldVo fv = new FieldVo();
        fv.setBeing((vo.getPage() - 1) * BaseConstants.PAGE_SIZE + 1);
        fv.setEnd((vo.getPage() - 1) * BaseConstants.PAGE_SIZE + BaseConstants.PAGE_SIZE);
        if (StringUtils.isNotBlank(vo.getSearch())) {
            fv.setSearch(vo.getSearch());
        }
        if (vo.getProvinceId() > 0) {
            fv.setProvinceId(vo.getProvinceId());
            if (vo.getCityId() > 0) {
                fv.setCityId(vo.getCityId());
                if (vo.getAreaId() > 0) {
                    fv.setAreaId(vo.getAreaId());
                }
            }
        }
        Long[] ids = baseServer.findFieldIds(vo.getField());
        fv.setFields(ids);
        return replenish(epibolyInfoDao.findEpibolyByField(fv));
    }

    /**
     * 智能搜索
     */
    public List<EpibolyInfoEntity> findEpibolyByPv(SearchVo vo) throws Exception {
        PvVo pv = new PvVo();
        pv.setCapacityType(vo.getCapacityType());
        pv.setBeing((vo.getPage() - 1) * BaseConstants.PAGE_SIZE + 1);
        pv.setEnd((vo.getPage() - 1) * BaseConstants.PAGE_SIZE + BaseConstants.PAGE_SIZE);
        if (StringUtils.isNotBlank(vo.getSearch())) {
            pv.setSearch(vo.getSearch());
        }
        if (vo.getProvinceId() > 0) {
            pv.setProvinceId(vo.getProvinceId());
            if (vo.getCityId() > 0) {
                pv.setCityId(vo.getCityId());
                if (vo.getAreaId() > 0) {
                    pv.setAreaId(vo.getAreaId());
                }
            }
        }
        return replenish(epibolyInfoDao.findEpibolyByPv(pv));
    }

    /**
     * 推荐众包
     */
    public List<EpibolyInfoEntity> referrals(ReferralsVo vo) throws Exception {
        if (vo.getProvinceId() == 0) {
            vo.setProvinceId(null);
        }
        if (vo.getCityId() == 0) {
            vo.setCityId(null);
        }
        if (vo.getAreaId() == 0) {
            vo.setAreaId(null);
        }
        return replenish(epibolyInfoDao.referrals(vo));
    }

    /**
     * 我发布的信息
     */
    public List<EpibolyInfoEntity> findMyPublish(PublishVo vo, Long userId) throws Exception {
        MyVo mv = new MyVo();
        mv.setBeing((vo.getPage() - 1) * BaseConstants.PAGE_SIZE + 1);
        mv.setEnd((vo.getPage() - 1) * BaseConstants.PAGE_SIZE + BaseConstants.PAGE_SIZE);
        if (StringUtils.isNotBlank(vo.getSearch())) {
            mv.setSearch(vo.getSearch());
        }
        mv.setPublishId(userId);
        return replenish(epibolyInfoDao.findMyPublish(mv));
    }

    /**
     * 我的收藏
     */
    public List<EpibolyInfoEntity> findCollect(MyCollectVo vo, Long userId) throws Exception {
        CollectVo cVo = new CollectVo();
        cVo.setBeing((vo.getPage() - 1) * BaseConstants.PAGE_SIZE + 1);
        cVo.setEnd((vo.getPage() - 1) * BaseConstants.PAGE_SIZE + BaseConstants.PAGE_SIZE);
        if (StringUtils.isNotBlank(vo.getSearch())) {
            cVo.setSearch(vo.getSearch());
        }
        cVo.setCollectId(userId);
        cVo.setType(vo.getType());
        return replenish(epibolyInfoDao.findCollect(cVo));
    }

    /**
     * 补充省市区县信息
     */
    private List<EpibolyInfoEntity> replenish(List<EpibolyInfoEntity> infos) throws Exception {
        for (EpibolyInfoEntity info : infos) {
            if (info.getProvinceId() != null) {
                info.setProvince(areaCacheServer.findArea(info.getProvinceId()));
            }
            if (info.getCityId() != null) {
                info.setCity(areaCacheServer.findArea(info.getCityId()));
            }
            if (info.getAreaId() != null) {
                info.setArea(areaCacheServer.findArea(info.getAreaId()));
            }
            info.setFieldName(fieldInfoCacheServer.findFields(info.getField()));
        }
        return infos;
    }

    /**
     * 补充省市区县信息
     */
    public EpibolyInfoEntity replenish(EpibolyInfoEntity info) throws Exception {
        if (info != null && info.getId() > 0) {
            if (info.getProvinceId() != null) {
                info.setProvince(areaCacheServer.findArea(info.getProvinceId()));
            }
            if (info.getCityId() != null) {
                info.setCity(areaCacheServer.findArea(info.getCityId()));
            }
            if (info.getAreaId() != null) {
                info.setArea(areaCacheServer.findArea(info.getAreaId()));
            }
            info.setFieldName(fieldInfoCacheServer.findFields(info.getField()));
        }
        return info;
    }

    public EpibolyItemEntity replenish(EpibolyItemEntity item) throws Exception {
        if (item != null) {
            item.setPublishName(userInfoCacheServer.findNickname(item.getPublishId()));
        }
        return item;
    }

    /**
     * 发布众包信息接口
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean releaseEpiboly(EpibolyVo vo, Long userId) throws Exception {
        boolean flag = false;
        Long id = epibolyInfoDao.findId();
        if (insertEpibolyInfo(vo, id) > 0) {
            if (insertEpibolyItem(vo, userId, id) > 0) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 修改众包信息接口
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean updateEpiboly(EpibolyVo vo) throws Exception {
        boolean flag = false;
        if (updateEpibolyInfo(vo) > 0) {
            if (updateEpibolyItem(vo) > 0) {
                flag = true;
            }
        }
        return flag;
    }


    /**
     * 添加众包信息
     */
    private int insertEpibolyInfo(EpibolyVo vo, Long id) throws Exception {
        EpibolyInfoEntity info = new EpibolyInfoEntity();
        info.setId(id);
        info = epibolyInfo(info, vo);
        info.setPv(0L);
        info.setPraise(0L);
        info.setState(1);//状态 (STATE 0-待审核,1-正常,2-过期,3-锁定)
        return epibolyInfoDao.insertEpibolyInfo(info);
    }

    /**
     * 修改众包信息
     */
    private int updateEpibolyInfo(EpibolyVo vo) throws Exception {
        EpibolyInfoEntity info = epibolyInfoDao.findEpibolyInfoById(vo.getId());
        info = epibolyInfo(info, vo);
        return epibolyInfoDao.updateEpibolyInfo(info);
    }

    private EpibolyInfoEntity epibolyInfo(EpibolyInfoEntity info, EpibolyVo vo) {
        info.setTitle(vo.getTitle());
        if (!vo.getField().startsWith(",")) {
            vo.setField("," + vo.getField());
        }
        if (!vo.getField().endsWith(",")) {
            vo.setField(vo.getField() + ",");
        }
        info.setField(vo.getField());
        info.setProvinceId(vo.getProvinceId());
        info.setCityId(vo.getCityId());
        info.setAreaId(vo.getAreaId());
        info.setWeight(new Date().getTime());
        return info;
    }

    /**
     * 添加众包明细
     */
    private int insertEpibolyItem(EpibolyVo vo, Long userId, Long id) throws Exception {
        EpibolyItemEntity item = new EpibolyItemEntity();
        item.setEpibolyId(id);
        item = epibolyItem(item, vo);
        item.setPublishId(userId);
        item.setLeaveNumber(0L);
        item.setCollectNumber(0L);
        return epibolyItemDao.insertEpibolyItem(item);
    }

    /**
     * 修改众包明细
     */
    private int updateEpibolyItem(EpibolyVo vo) throws Exception {
        EpibolyItemEntity item = epibolyItemDao.findEpibolyItem(vo.getId());
        item = epibolyItem(item, vo);
        return epibolyItemDao.updateEpibolyItem(item);
    }

    private EpibolyItemEntity epibolyItem(EpibolyItemEntity item, EpibolyVo vo) {
        item.setDescribe(vo.getDescribe());
        item.setLinkman(vo.getLinkman());
        item.setPhone(vo.getPhone());
        if (item.getPublishTime() == null) {
            item.setPublishTime(new Date());
        }
        return item;
    }

}