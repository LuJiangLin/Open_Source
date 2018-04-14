/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  PartnershipServer.java
 * PACKAGE      :  com.anssy.inter.team.server
 * CREATE DATE  :  2016-8-23
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.inter.team.server;

import com.anssy.venturebar.base.dao.DatadictDao;
import com.anssy.venturebar.base.server.AreaCacheServer;
import com.anssy.venturebar.base.server.DatadictCacheServer;
import com.anssy.venturebar.base.server.FieldInfoCacheServer;
import com.anssy.venturebar.base.server.UserInfoCacheServer;
import com.anssy.venturebar.base.vo.DatadictVo;
import com.anssy.venturebar.team.dao.PartnershipInfoDao;
import com.anssy.venturebar.team.dao.PartnershipItemDao;
import com.anssy.venturebar.team.entity.PartnershipInfoEntity;
import com.anssy.venturebar.team.entity.PartnershipItemEntity;
import com.anssy.venturebar.team.vo.FieldVo;
import com.anssy.venturebar.team.vo.PvVo;
import com.anssy.inter.base.server.BaseServer;
import com.anssy.inter.picture.server.PictureServer;
import com.anssy.inter.team.vo.PartnershipVo;
import com.anssy.inter.team.vo.SearchVo;
import com.anssy.webcore.common.BaseConstants;
import com.anssy.webcore.common.Geocoder;
import com.anssy.webcore.common.Geohash;
import com.anssy.webcore.vo.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author make it
 * @version SVN #V1# #2016-8-23#
 *          团队_合伙信息
 */
@Service("partnershipServer")
public class PartnershipServer {

    @Resource
    private DatadictDao datadictDao;
    @Resource
    private PictureServer pictureServer;
    @Resource
    private PartnershipInfoDao partnershipInfoDao;
    @Resource
    private PartnershipItemDao partnershipItemDao;
    @Resource
    private BaseServer baseServer;
    @Resource
    private FieldInfoCacheServer fieldInfoCacheServer;
    @Resource
    private AreaCacheServer areaCacheServer;
    @Resource
    private DatadictCacheServer datadictCacheServer;
    @Resource
    private UserInfoCacheServer userInfoCacheServer;

    /**
     * 获取合伙人要求信息
     */
    public List<DatadictVo> findAskFor() throws Exception {
        return datadictDao.findDictByCategory("ASK_FOR");
    }

    /**
     * 获取项目阶段信息
     */
    public List<DatadictVo> findStage() throws Exception {
        return datadictDao.findDictByCategory("STAGE");
    }

    /**
     * 获取初始资金信息
     */
    public List<DatadictVo> findFund() throws Exception {
        return datadictDao.findDictByCategory("FUND");
    }

    /**
     * 删除合伙信息
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean deletePartnershipInfo(Long partnershipId) throws Exception {
        boolean flag = false;
        PartnershipInfoEntity info = findPartnershipInfoById(partnershipId);
        if (info != null) {
            if (StringUtils.isNotBlank(info.getImage())) {
                pictureServer.deletePicture(info.getImage());
            }
            if (partnershipInfoDao.deletePartnership(partnershipId) > 0) {
                if (partnershipItemDao.deletePartnership(partnershipId) > 0) {
                    flag = true;
                }
            }
        }
        return flag;
    }

    /**
     * 修改浏览量
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public int updatePV(Long id) throws Exception {
        return partnershipInfoDao.updatePV(id);
    }

    /**
     * 点赞
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public int updatePraise(Long id) throws Exception {
        return partnershipInfoDao.updatePraise(id);
    }

    /**
     * 权重
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public int updateWeight(Long id) throws Exception {
        return partnershipInfoDao.updateWeight(id, new Date().getTime());
    }

    /**
     * 通过合伙ID查询合伙信息
     */
    public PartnershipInfoEntity findPartnershipInfoById(Long id) throws Exception {
        return partnershipInfoDao.findPartnershipInfoById(id);
    }

    /**
     * 通过合伙ID查询合伙明细信息
     */
    public PartnershipItemEntity findPartnershipItem(Long partnershipId) throws Exception {
        return partnershipItemDao.findPartnershipItem(partnershipId);
    }

    /**
     * 按GPS进行附近搜索,
     */
    public List<PartnershipInfoEntity> findPartnershipByGPS(SearchVo vo) throws Exception {
        GPSVo gpsVo = new GPSVo();
        if (StringUtils.isNotBlank(vo.getSearch())) {
            gpsVo.setSearch(vo.getSearch());
        }
        Geohash e = new Geohash();
        String geohash = e.encode(Double.parseDouble(vo.getLongitude()), Double.parseDouble(vo.getLatitude()));
        List<PartnershipInfoEntity> temps;
        int len = 5;
        do {
            //前5位编码相同表示10平方千米的范围，前4位编码相同表示60平方千米的范围
            if (geohash != null && geohash.length() > len) {
                gpsVo.setGeohash(geohash.substring(0, len));
                len--;
            }
            temps = partnershipInfoDao.findPartnershipByGPS(gpsVo);
        } while (!temps.isEmpty() && len > 2);
        return distanceSort(temps, Double.parseDouble(vo.getLongitude()), Double.parseDouble(vo.getLatitude()), vo.getPage());
    }

    /**
     * 按照距离排序后再分页
     *
     * @param temps     查询出的集合
     * @param longitude 经度
     * @param latitude  纬度
     * @param page      第几页
     */
    private List<PartnershipInfoEntity> distanceSort(List<PartnershipInfoEntity> temps, double longitude, double latitude, int page) throws Exception {
        int being = (page - 1) * BaseConstants.PAGE_SIZE + 1;
        int end = (page - 1) * BaseConstants.PAGE_SIZE + BaseConstants.PAGE_SIZE;
        int len = temps.size();
        if (len >= being) {
            for (PartnershipInfoEntity temp : temps) {
                temp.setDistance(latitude, longitude);
            }
            Collections.sort(temps, new Comparator<PartnershipInfoEntity>() {
                public int compare(PartnershipInfoEntity arg0, PartnershipInfoEntity arg1) {
                    return arg0.getSort().compareTo(arg1.getSort());
                }
            });
        }
        List<PartnershipInfoEntity> infos = new ArrayList<PartnershipInfoEntity>();
        if (len >= being) {
            being--;//下标从0开始
            for (int i = being; (i < len && i < end); i++) {
                infos.add(temps.get(i));
            }
        }
        return replenish(infos);
    }

    /**
     * 行业搜索
     */
    public List<PartnershipInfoEntity> findPartnershipByField(SearchVo vo) throws Exception {
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
        return replenish(partnershipInfoDao.findPartnershipByField(fv));
    }

    /**
     * 智能搜索
     */
    public List<PartnershipInfoEntity> findPartnershipByPv(SearchVo vo) throws Exception {
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
        return replenish(partnershipInfoDao.findPartnershipByPv(pv));
    }

    /**
     * 推荐合伙
     */
    public List<PartnershipInfoEntity> referrals(ReferralsVo vo) throws Exception {
        if (vo.getProvinceId() == 0) {
            vo.setProvinceId(null);
        }
        if (vo.getCityId() == 0) {
            vo.setCityId(null);
        }
        if (vo.getAreaId() == 0) {
            vo.setAreaId(null);
        }
        return replenish(partnershipInfoDao.referrals(vo));
    }

    /**
     * 我发布的信息
     */
    public List<PartnershipInfoEntity> findMyPublish(PublishVo vo, Long userId) throws Exception {
        MyVo mv = new MyVo();
        mv.setBeing((vo.getPage() - 1) * BaseConstants.PAGE_SIZE + 1);
        mv.setEnd((vo.getPage() - 1) * BaseConstants.PAGE_SIZE + BaseConstants.PAGE_SIZE);
        if (StringUtils.isNotBlank(vo.getSearch())) {
            mv.setSearch(vo.getSearch());
        }
        mv.setPublishId(userId);
        return replenish(partnershipInfoDao.findMyPublish(mv));
    }

    /**
     * 我的收藏
     */
    public List<PartnershipInfoEntity> findCollect(MyCollectVo vo, Long userId) throws Exception {
        CollectVo cVo = new CollectVo();
        cVo.setBeing((vo.getPage() - 1) * BaseConstants.PAGE_SIZE + 1);
        cVo.setEnd((vo.getPage() - 1) * BaseConstants.PAGE_SIZE + BaseConstants.PAGE_SIZE);
        if (StringUtils.isNotBlank(vo.getSearch())) {
            cVo.setSearch(vo.getSearch());
        }
        cVo.setCollectId(userId);
        cVo.setType(vo.getType());
        return replenish(partnershipInfoDao.findCollect(cVo));
    }

    /**
     * 补充省市区县信息
     */
    private List<PartnershipInfoEntity> replenish(List<PartnershipInfoEntity> infos) throws Exception {
        for (PartnershipInfoEntity info : infos) {
            if (StringUtils.isNotBlank(info.getImage())) {
                info.setImage(pictureServer.findTinyURL(info.getImage()));
            }
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
            info.setAskForName(datadictCacheServer.findDict("ASK_FOR", info.getAskFor()));
        }
        return infos;
    }

    /**
     * 补充省市区县信息
     */
    public PartnershipInfoEntity replenish(PartnershipInfoEntity info) throws Exception {
        if (info != null && info.getId() > 0) {
            if (StringUtils.isNotBlank(info.getImage())) {
                List<String> urls = pictureServer.findURL(info.getImage());
                info.setUrls(urls);
            }
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
            info.setAskForName(datadictCacheServer.findDict("ASK_FOR", info.getAskFor()));
        }
        return info;
    }

    public PartnershipItemEntity replenish(PartnershipItemEntity item) throws Exception {
        if (item != null && item.getId() > 0) {
            item.setStageName(datadictCacheServer.findDict("STAGE", item.getStage()));
            StringBuilder fundName = new StringBuilder();
            if (StringUtils.isNotBlank(item.getFund())) {
                String[] ids = item.getFund().split(",");
                for (String id : ids) {
                    if (StringUtils.isNotBlank(id)) {
                        fundName.append(datadictCacheServer.findDict("FUND", Long.parseLong(id))).append(" ");
                    }
                }
            }
            item.setFundName(fundName.toString());
        }
        return item;
    }

    /**
     * 发布合伙信息
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean releasePartnership(PartnershipVo vo, Long userId) throws Exception {
        boolean flag = false;
        Long id = partnershipInfoDao.findId();
        if (insertPartnershipInfo(vo, id) > 0) {
            if (insertPartnershipItem(vo, id, userId) > 0) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 修改合伙信息
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean updatePartnership(PartnershipVo vo) throws Exception {
        boolean flag = false;
        if (updatePartnershipInfo(vo) > 0) {
            if (updatePartnershipItem(vo) > 0) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 添加合伙信息
     */
    private int insertPartnershipInfo(PartnershipVo vo, Long id) throws Exception {
        PartnershipInfoEntity info = new PartnershipInfoEntity();
        info.setId(id);
        info = partnershipInfo(info, vo);
        info.setPv(0L);
        info.setPraise(0L);
        info.setState(1);//状态 (STATE 0-待审核,1-正常,2-过期,3-锁定)
        return partnershipInfoDao.insertPartnershipInfo(info);
    }

    /**
     * 修改合伙信息
     */
    private int updatePartnershipInfo(PartnershipVo vo) throws Exception {
        PartnershipInfoEntity info = partnershipInfoDao.findPartnershipInfoById(vo.getId());
        info = partnershipInfo(info, vo);
        return partnershipInfoDao.updatePartnershipInfo(info);
    }

    private PartnershipInfoEntity partnershipInfo(PartnershipInfoEntity info, PartnershipVo vo) {
        if (StringUtils.isNotBlank(vo.getImage())) {
            info.setImage(vo.getImage());
        } else {
            info.setImage(null);
        }
        info.setTitle(vo.getTitle());
        if (!vo.getField().startsWith(",")) {
            vo.setField("," + vo.getField());
        }
        if (!vo.getField().endsWith(",")) {
            vo.setField(vo.getField() + ",");
        }
        info.setField(vo.getField());
        info.setPost(vo.getPost());
        info.setAskFor(vo.getAskFor());
        if (StringUtils.isNotBlank(vo.getSketch())) {
            info.setSketch(vo.getSketch());
        } else {
            info.setSketch(null);
        }
        info.setProvinceId(vo.getProvinceId());
        info.setCityId(vo.getCityId());
        info.setAreaId(vo.getAreaId());
        if (StringUtils.isNotBlank(vo.getSite())) {
            String area = areaCacheServer.findArea(vo.getAreaId());
            Map<String, String> gps = Geocoder.getGeocoder(vo.getSite(), area);
            String lng = gps.get("lng");
            String lat = gps.get("lat");
            if (StringUtils.isNotBlank(lng) && StringUtils.isNotBlank(lat)) {
                info.setLongitude(lng);
                info.setLatitude(lat);
                Geohash e = new Geohash();
                String geohash = e.encode(Double.parseDouble(lng), Double.parseDouble(lat));
                if (StringUtils.isNotBlank(geohash)) {
                    info.setGeohash(geohash);
                }
            }
        }
        info.setWeight(new Date().getTime());
        return info;
    }

    /**
     * 添加合伙明细
     */
    private int insertPartnershipItem(PartnershipVo vo, Long id, Long userId) throws Exception {
        PartnershipItemEntity item = new PartnershipItemEntity();
        item.setPartnershipId(id);
        item = partnershipItem(item, vo);
        item.setPublishId(userId);
        item.setLeaveNumber(0L);
        item.setCollectNumber(0L);
        return partnershipItemDao.insertPartnershipItem(item);
    }

    /**
     * 修改合伙明细
     */
    private int updatePartnershipItem(PartnershipVo vo) throws Exception {
        PartnershipItemEntity item = partnershipItemDao.findPartnershipItem(vo.getId());
        item = partnershipItem(item, vo);
        return partnershipItemDao.updatePartnershipItem(item);
    }

    private PartnershipItemEntity partnershipItem(PartnershipItemEntity item, PartnershipVo vo) {
        item.setDescribe(vo.getDescribe());
        item.setLinkman(vo.getLinkman());
        item.setPhone(vo.getPhone());
        if (StringUtils.isNotBlank(vo.getDirection())) {
            item.setDirection(vo.getDirection());
        } else {
            item.setDirection(null);
        }
        if (StringUtils.isNotBlank(vo.getTeamCase())) {
            item.setTeamCase(vo.getTeamCase());
        } else {
            item.setTeamCase(null);
        }
        if (StringUtils.isNotBlank(vo.getSite())) {
            item.setSite(vo.getSite());
        } else {
            item.setSite(null);
        }
        if (StringUtils.isNotBlank(vo.getLightspot())) {
            item.setLightspot(vo.getLightspot());
        } else {
            item.setLightspot(null);
        }
        if (StringUtils.isNotBlank(vo.getProspect())) {
            item.setProspect(vo.getProspect());
        } else {
            item.setProspect(null);
        }
        if (vo.getStage() != null) {
            item.setStage(vo.getStage());
        } else {
            item.setStage(null);
        }
        if (StringUtils.isNotBlank(vo.getFund())) {
            if (!vo.getFund().startsWith(",")) {
                vo.setFund("," + vo.getFund());
            }
            if (!vo.getFund().endsWith(",")) {
                vo.setFund(vo.getFund() + ",");
            }
            item.setFund(vo.getFund());
        } else {
            item.setFund(null);
        }
        if (StringUtils.isNotBlank(vo.getAppURL())) {
            item.setAppURL(vo.getAppURL());
        } else {
            item.setAppURL(null);
        }
        if (StringUtils.isNotBlank(vo.getEmail())) {
            item.setEmail(vo.getEmail());
        } else {
            item.setEmail(null);
        }
        if (item.getPublishTime() == null) {
            item.setPublishTime(new Date());
        }
        return item;
    }

}