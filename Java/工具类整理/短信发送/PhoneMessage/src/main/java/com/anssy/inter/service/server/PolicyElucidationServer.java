/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  PolicyElucidationServer.java
 * PACKAGE      :  com.anssy.inter.service.server
 * CREATE DATE  :  2016-8-3
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.inter.service.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.anssy.venturebar.base.server.UserInfoCacheServer;
import com.anssy.venturebar.service.dao.PolicyElucidationDao;
import com.anssy.venturebar.service.entity.PolicyElucidationEntity;
import com.anssy.venturebar.service.vo.PEVo;
import com.anssy.inter.picture.server.PictureServer;
import com.anssy.inter.service.vo.PolicyVo;
import com.anssy.inter.service.vo.SearchVo;
import com.anssy.venturebar.service.vo.PvVo;
import com.anssy.webcore.common.BaseConstants;
import com.anssy.webcore.common.HttpClientUtils;
import com.anssy.webcore.vo.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author make it
 * @version SVN #V1# #2016-8-3#
 *          服务_政策解读
 */
@Service("policyElucidationServer")
public class PolicyElucidationServer {


    private static Logger logger = Logger.getLogger(PolicyElucidationServer.class);

    @Resource
    private PolicyElucidationDao policyElucidationDao;
    @Resource
    private PictureServer pictureServer;
    @Resource
    private UserInfoCacheServer userInfoCacheServer;

    /**
     * 查询政策解读信息
     */
    public List<PEVo> findList(SearchVo vo) throws Exception {
        PvVo pVo = new PvVo();
        pVo.setCapacityType(vo.getCapacityType());
        pVo.setBeing((vo.getPage() - 1) * BaseConstants.PAGE_SIZE + 1);
        pVo.setEnd((vo.getPage() - 1) * BaseConstants.PAGE_SIZE + BaseConstants.PAGE_SIZE);
        if (StringUtils.isNotBlank(vo.getSearch())) {
            pVo.setSearch(vo.getSearch());
        }
        return replenish(policyElucidationDao.findList(pVo));
    }

    /**
     * 我发布的信息
     */
    public List<PEVo> findMyPublish(PublishVo vo, Long userId) throws Exception {
        MyVo mv = new MyVo();
        mv.setBeing((vo.getPage() - 1) * BaseConstants.PAGE_SIZE + 1);
        mv.setEnd((vo.getPage() - 1) * BaseConstants.PAGE_SIZE + BaseConstants.PAGE_SIZE);
        if (StringUtils.isNotBlank(vo.getSearch())) {
            mv.setSearch(vo.getSearch());
        }
        mv.setPublishId(userId);
        return replenish(policyElucidationDao.findMyPublish(mv));
    }

    /**
     * 我的收藏
     */
    public List<PEVo> findCollect(MyCollectVo vo, Long userId) throws Exception {
        CollectVo cVo = new CollectVo();
        cVo.setBeing((vo.getPage() - 1) * BaseConstants.PAGE_SIZE + 1);
        cVo.setEnd((vo.getPage() - 1) * BaseConstants.PAGE_SIZE + BaseConstants.PAGE_SIZE);
        if (StringUtils.isNotBlank(vo.getSearch())) {
            cVo.setSearch(vo.getSearch());
        }
        cVo.setCollectId(userId);
        cVo.setType(vo.getType());
        return replenish(policyElucidationDao.findCollect(cVo));
    }

    /**
     * 推荐政策解读信息
     */
    public List<PEVo> referrals(ReferralsVo vo) throws Exception {
        return replenish(policyElucidationDao.referrals(vo));
    }

    /**
     * 补充省市区县信息
     */
    private List<PEVo> replenish(List<PEVo> infos) throws Exception {
        for (PEVo info : infos) {
            if (StringUtils.isNotBlank(info.getImage())) {
                info.setImage(pictureServer.findTinyURL(info.getImage()));
            }
        }
        return infos;
    }

    /**
     * 补充省市区县信息
     */
    public PolicyElucidationEntity replenish(PolicyElucidationEntity info) throws Exception {
        if (StringUtils.isNotBlank(info.getImage())) {
            List<String> urls = pictureServer.findURL(info.getImage());
            info.setUrls(urls);
        }
        info.setPublishName(userInfoCacheServer.findNickname(info.getPublishId()));
        return info;
    }

    /**
     * 删除政策解读信息
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public int deletePolicy(Long id) throws Exception {
        PolicyElucidationEntity info = findPolicyElucidationById(id);
        if (info != null && StringUtils.isNotBlank(info.getImage())) {
            pictureServer.deletePicture(info.getImage());
        }
        return policyElucidationDao.deletePolicy(id);
    }


    /**
     * 修改浏览量
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public int updatePV(Long id) throws Exception {
        return policyElucidationDao.updatePV(id);
    }

    /**
     * 点赞
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public int updatePraise(Long id) throws Exception {
        return policyElucidationDao.updatePraise(id);
    }

    /**
     * 权重
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public int updateWeight(Long id) throws Exception {
        return policyElucidationDao.updateWeight(id, new Date().getTime());
    }

    /**
     * 查询政策解读信息
     */
    public PolicyElucidationEntity findPolicyElucidationById(Long id) throws Exception {
        return policyElucidationDao.findPolicyElucidationById(id);
    }

    /**
     * 发布政策信息
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean releasePolicy(PolicyVo vo, Long userId) throws Exception {
        boolean flag = false;
        PolicyElucidationEntity info = new PolicyElucidationEntity();
        info = policy(info, vo);
        info.setPublishId(userId);
        info.setPv(0L);
        info.setPraise(0L);
        info.setLeaveNumber(0L);
        info.setCollectNumber(0L);
        info.setState(1);//状态 (STATE 0-待审核,1-正常,2-过期,3-锁定)
        if (policyElucidationDao.insertPolicyElucidation(info) > 0) {
            flag = true;
        }
        return flag;
    }

    /**
     * 修改政策信息
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean updatePolicy(PolicyVo vo) throws Exception {
        boolean flag = false;
        PolicyElucidationEntity info = policyElucidationDao.findPolicyElucidationById(vo.getId());
        info = policy(info, vo);
        if (policyElucidationDao.updatePolicyElucidation(info) > 0) {
            flag = true;
        }
        return flag;
    }

    private PolicyElucidationEntity policy(PolicyElucidationEntity info, PolicyVo vo) {
        if (StringUtils.isNotBlank(vo.getImage())) {
            info.setImage(vo.getImage());
        } else {
            info.setImage(null);
        }
        info.setTitle(vo.getTitle());
        info.setDetails(vo.getDetails());
        if (StringUtils.isNotBlank(vo.getSource())) {
            info.setSource(vo.getSource());
        } else {
            info.setSource(null);
        }
        if (StringUtils.isNotBlank(vo.getUrl())) {
            info.setUrl(vo.getUrl());
        } else {
            info.setUrl(null);
        }
        if (info.getPublishTime() == null) {
            info.setPublishTime(new Date());
        }
        info.setWeight(new Date().getTime());
        return info;
    }

    /**
     * 从创业在线拉取政策数据 (定时)
     */
    public void loadPolcyFormStartupOnline() {
//        try {
//            String resultJson = HttpClientUtils.post("http://localhost:8898/venturebar/app/base/baseAction/findProvince.action", null);
//            logger.error("pull result polcy json : " + resultJson);
//            JSONObject jsonObject = JSON.parseObject(resultJson);
//            if (jsonObject.getIntValue("code")!=0) {
//                logger.error("创业在线服务器出错啦! : " + jsonObject.getString("msg"));
//                return;
//            }
//            List<Map> mapList = (List<Map>) jsonObject.get("provinces");
//
//            if (mapList.size() == 0) {
//                logger.error("未拉取到任何数据");
//            } else {
//                int n = mapList.size() > 5 ? 5 : mapList.size();
//                logger.error("拉取到" + n + "条数据");
//                for (int i = 0; i < n; i++) {
//                    logger.error("数据" + i + "============== : " + mapList.get(i));
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

}