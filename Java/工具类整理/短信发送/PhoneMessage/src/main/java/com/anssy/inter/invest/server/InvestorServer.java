/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  InvestorServer.java
 * PACKAGE      :  com.anssy.inter.invest.server
 * CREATE DATE  :  2016-8-21
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.inter.invest.server;

import com.anssy.venturebar.base.dao.DatadictDao;
import com.anssy.venturebar.base.server.AreaCacheServer;
import com.anssy.venturebar.base.server.DatadictCacheServer;
import com.anssy.venturebar.base.server.FieldInfoCacheServer;
import com.anssy.venturebar.base.server.UserInfoCacheServer;
import com.anssy.venturebar.base.vo.DatadictVo;
import com.anssy.venturebar.invest.dao.InvestorInfoDao;
import com.anssy.venturebar.invest.dao.InvestorItemDao;
import com.anssy.venturebar.invest.entity.InvestorInfoEntity;
import com.anssy.venturebar.invest.entity.InvestorItemEntity;
import com.anssy.venturebar.invest.vo.FieldVo;
import com.anssy.venturebar.invest.vo.PvVo;
import com.anssy.inter.base.server.BaseServer;
import com.anssy.inter.invest.vo.InvestorVo;
import com.anssy.inter.invest.vo.SearchVo;
import com.anssy.inter.picture.server.PictureServer;
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
 * @version SVN #V1# #2016-8-21#
 *          投资_投资人信息
 */
@Service("investorServer")
public class InvestorServer {

    @Resource
    private DatadictDao datadictDao;
    @Resource
    private InvestorInfoDao investorInfoDao;
    @Resource
    private InvestorItemDao investorItemDao;
    @Resource
    private BaseServer baseServer;
    @Resource
    private PictureServer pictureServer;
    @Resource
    private FieldInfoCacheServer fieldInfoCacheServer;
    @Resource
    private AreaCacheServer areaCacheServer;
    @Resource
    private DatadictCacheServer datadictCacheServer;
    @Resource
    private UserInfoCacheServer userInfoCacheServer;


    /**
     * 获取投资主体
     */
    public List<DatadictVo> findInvestorBody() throws Exception {
        return datadictDao.findDictByCategory("BODY");
    }

    /**
     * 获取投资方式
     */
    public List<DatadictVo> findInvestWay() throws Exception {
        return datadictDao.findDictByCategory("INVEST_WAY");
    }

    public List<DatadictVo> findWay() throws Exception {
        return datadictDao.findDictByCategory("WAY");
    }

    /**
     * 获取投资类型
     */
    public List<DatadictVo> findInvestType() throws Exception {
        return datadictDao.findDictByCategory("INVEST_TYPE");
    }

    /**
     * 修改浏览量
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public int updatePV(Long id) throws Exception {
        return investorInfoDao.updatePV(id);
    }

    /**
     * 点赞
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public int updatePraise(Long id) throws Exception {
        return investorInfoDao.updatePraise(id);
    }

    /**
     * 权重
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public int updateWeight(Long id) throws Exception {
        return investorInfoDao.updateWeight(id, new Date().getTime());
    }

    /**
     * 删除投资人信息
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean deleteInvestor(Long investorId) throws Exception {
        boolean flag = false;
        InvestorInfoEntity info = findInvestorInfoById(investorId);
        if (info != null) {
            if (StringUtils.isNotBlank(info.getImage())) {
                pictureServer.deletePicture(info.getImage());
            }
            if (investorInfoDao.deleteInvestorInfo(investorId) > 0) {
                InvestorItemEntity item = findInvestorItem(investorId);
                if (item != null) {
                    if (StringUtils.isNotBlank(item.getCallingCard())) {
                        pictureServer.deletePicture(item.getCallingCard());
                    }
                }
                if (investorItemDao.deleteInvestorItem(investorId) > 0) {
                    flag = true;
                }
            }
        }
        return flag;
    }

    /**
     * 通过主键ID查询投资人信息
     */
    public InvestorInfoEntity findInvestorInfoById(Long id) throws Exception {
        return investorInfoDao.findInvestorInfoById(id);
    }

    /**
     * 通过投资人ID查询投资人明细
     */
    public InvestorItemEntity findInvestorItem(Long investorId) throws Exception {
        return investorItemDao.findInvestorItem(investorId);
    }

    /**
     * 行业搜索
     */
    public List<InvestorInfoEntity> findInvestorByField(SearchVo vo) throws Exception {
        FieldVo fieldVo = new FieldVo();
        fieldVo.setBeing((vo.getPage() - 1) * BaseConstants.PAGE_SIZE + 1);
        fieldVo.setEnd((vo.getPage() - 1) * BaseConstants.PAGE_SIZE + BaseConstants.PAGE_SIZE);
        if (StringUtils.isNotBlank(vo.getSearch())) {
            fieldVo.setSearch(vo.getSearch());
        }
        if (vo.getProvinceId() > 0) {
            fieldVo.setProvinceId(vo.getProvinceId());
            if (vo.getCityId() > 0) {
                fieldVo.setCityId(vo.getCityId());
                if (vo.getAreaId() > 0) {
                    fieldVo.setAreaId(vo.getAreaId());
                }
            }
        }
        Long[] ids = baseServer.findFieldIds(vo.getField());
        fieldVo.setFields(ids);
        return replenish(investorInfoDao.findListByField(fieldVo));
    }

    /**
     * 智能搜索
     */
    public List<InvestorInfoEntity> findInvestorByPv(SearchVo vo) throws Exception {
        PvVo pVo = new PvVo();
        pVo.setCapacityType(vo.getCapacityType());
        pVo.setBeing((vo.getPage() - 1) * BaseConstants.PAGE_SIZE + 1);
        pVo.setEnd((vo.getPage() - 1) * BaseConstants.PAGE_SIZE + BaseConstants.PAGE_SIZE);
        if (StringUtils.isNotBlank(vo.getSearch())) {
            pVo.setSearch(vo.getSearch());
        }
        if (vo.getProvinceId() > 0) {
            pVo.setProvinceId(vo.getProvinceId());
            if (vo.getCityId() > 0) {
                pVo.setCityId(vo.getCityId());
                if (vo.getAreaId() > 0) {
                    pVo.setAreaId(vo.getAreaId());
                }
            }
        }
        return replenish(investorInfoDao.findListByPv(pVo));
    }

    /**
     * 推荐投资人
     */
    public List<InvestorInfoEntity> referrals(ReferralsVo vo) throws Exception {
        if (vo.getProvinceId() == 0) {
            vo.setProvinceId(null);
        }
        if (vo.getCityId() == 0) {
            vo.setCityId(null);
        }
        if (vo.getAreaId() == 0) {
            vo.setAreaId(null);
        }
        return replenish(investorInfoDao.referrals(vo));
    }

    /**
     * 我发布的信息
     */
    public List<InvestorInfoEntity> findMyPublish(PublishVo vo, Long userId) throws Exception {
        MyVo mv = new MyVo();
        mv.setBeing((vo.getPage() - 1) * BaseConstants.PAGE_SIZE + 1);
        mv.setEnd((vo.getPage() - 1) * BaseConstants.PAGE_SIZE + BaseConstants.PAGE_SIZE);
        if (StringUtils.isNotBlank(vo.getSearch())) {
            mv.setSearch(vo.getSearch());
        }
        mv.setPublishId(userId);
        return replenish(investorInfoDao.findMyPublish(mv));
    }

    /**
     * 我的收藏
     */
    public List<InvestorInfoEntity> findCollect(MyCollectVo vo, Long userId) throws Exception {
        CollectVo cVo = new CollectVo();
        cVo.setBeing((vo.getPage() - 1) * BaseConstants.PAGE_SIZE + 1);
        cVo.setEnd((vo.getPage() - 1) * BaseConstants.PAGE_SIZE + BaseConstants.PAGE_SIZE);
        if (StringUtils.isNotBlank(vo.getSearch())) {
            cVo.setSearch(vo.getSearch());
        }
        cVo.setCollectId(userId);
        cVo.setType(vo.getType());
        return replenish(investorInfoDao.findCollect(cVo));
    }

    /**
     * 按GPS进行附近搜索,
     */
    public List<InvestorInfoEntity> findListByGPS(SearchVo vo) throws Exception {
        GPSVo gpsVo = new GPSVo();
        if (StringUtils.isNotBlank(vo.getSearch())) {
            gpsVo.setSearch(vo.getSearch());
        }
        Geohash e = new Geohash();
        String geohash = e.encode(Double.parseDouble(vo.getLongitude()), Double.parseDouble(vo.getLatitude()));
        List<InvestorInfoEntity> temps = null;
        int len = 5;
        do {
            //前5位编码相同表示10平方千米的范围，前4位编码相同表示60平方千米的范围
            if (geohash != null && geohash.length() > len) {
                gpsVo.setGeohash(geohash.substring(0, len));
                len--;
            }
            temps = investorInfoDao.findListByGPS(gpsVo);
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
    private List<InvestorInfoEntity> distanceSort(List<InvestorInfoEntity> temps, double longitude, double latitude, int page) throws Exception {
        int being = (page - 1) * BaseConstants.PAGE_SIZE + 1;
        int end = (page - 1) * BaseConstants.PAGE_SIZE + BaseConstants.PAGE_SIZE;
        int len = temps.size();
        if (len >= being) {
            for (int i = 0; i < len; i++) {
                temps.get(i).setDistance(latitude, longitude);
            }
            Collections.sort(temps, new Comparator<InvestorInfoEntity>() {
                public int compare(InvestorInfoEntity arg0, InvestorInfoEntity arg1) {
                    return arg0.getSort().compareTo(arg1.getSort());
                }
            });
        }
        List<InvestorInfoEntity> infos = new ArrayList<InvestorInfoEntity>();
        if (len >= being) {
            being--;//下标从0开始
            for (int i = being; (i < len && i < end); i++) {
                infos.add(temps.get(i));
            }
        }
        return replenish(infos);
    }


    /**
     * 补充省市区县信息
     */
    private List<InvestorInfoEntity> replenish(List<InvestorInfoEntity> infos) throws Exception {
        for (InvestorInfoEntity info : infos) {
            if (StringUtils.isNotBlank(info.getImage())) {
                info.setImage(pictureServer.findTinyURL(info.getImage()));
            }
            if (info.getBody() != null) {
                info.setBodyName(datadictCacheServer.findDict("BODY", info.getBody()));
            }
//            if (info.getProvinceId() != null) {
//                info.setProvince(areaCacheServer.findArea(info.getProvinceId()));
//            }
//            if (info.getCityId() != null) {
//                info.setCity(areaCacheServer.findArea(info.getCityId()));
//            }
//            if (info.getAreaId() != null) {
//                info.setArea(areaCacheServer.findArea(info.getAreaId()));
//            }
            info.setFieldName(fieldInfoCacheServer.findFields(info.getField()));
        }
        return infos;
    }

    /**
     * 补充省市区县信息
     */
    public InvestorInfoEntity replenish(InvestorInfoEntity info) throws Exception {
        if (info != null && info.getId() > 0) {
            if (StringUtils.isNotBlank(info.getImage())) {
                List<String> urls = pictureServer.findURL(info.getImage());
                info.setUrls(urls);
            }
            if (info.getBody() != null) {
                info.setBodyName(datadictCacheServer.findDict("BODY", info.getBody()));
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
        }
        return info;
    }

    public InvestorItemEntity replenish(InvestorItemEntity item) throws Exception {
        if (item != null && item.getId() > 0) {
            if (StringUtils.isNotBlank(item.getCallingCard())) {
                List<String> urls = pictureServer.findURL(item.getCallingCard());
                item.setUrls(urls);
            }
            item.setPublishName(userInfoCacheServer.findNickname(item.getPublishId()));
            item.setInvestWayName(datadictCacheServer.findDict("INVEST_WAY", Long.parseLong(item.getInvestWay() + "")));
            item.setInvestTypeName(datadictCacheServer.findDict("INVEST_TYPE", Long.parseLong(item.getInvestType() + "")));
        }
        return item;
    }

    /**
     * 发布投资人信息
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean releaseInvestor(InvestorVo vo, Long userId) throws Exception {
        boolean flag = false;
        Long id = investorInfoDao.findId();
        if (insertInvestorInfo(vo, id) > 0) {
            if (insertInvestorItem(vo, userId, id) > 0) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 修改投资人信息
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean updateInvestor(InvestorVo vo) throws Exception {
        boolean flag = false;
        try {
            if (updateInvestorInfo(vo) > 0) {
                if (updateInvestorItem(vo) > 0) {
                    flag = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 添加投资人信息
     */
    private int insertInvestorInfo(InvestorVo vo, Long id) throws Exception {
        InvestorInfoEntity info = new InvestorInfoEntity();
        info.setId(id);
        info = investorInfo(info, vo);
        info.setPv(0L);
        info.setPraise(0L);
        info.setState(1);//状态 (STATE 0-待审核,1-正常,2-过期,3-锁定)
        return investorInfoDao.insertInvestorInfo(info);
    }

    /**
     * 修改投资人信息
     */
    private int updateInvestorInfo(InvestorVo vo) throws Exception {
        InvestorInfoEntity info = investorInfoDao.findInvestorInfoById(vo.getId());
        info = investorInfo(info, vo);
        return investorInfoDao.updateInvestorInfo(info);
    }

    private InvestorInfoEntity investorInfo(InvestorInfoEntity info, InvestorVo vo) {
        if (StringUtils.isNotBlank(vo.getImage())) {
            info.setImage(vo.getImage());
        } else {
            info.setImage(null);
        }
        info.setName(vo.getName());
        info.setCompanyName(vo.getCompanyName());
        info.setPost(vo.getPost());
        info.setBody(vo.getBody());
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
        if (StringUtils.isNotBlank(vo.getCompanySite())) {
            String area = areaCacheServer.findArea(vo.getAreaId());
            Map<String, String> gps = Geocoder.getGeocoder(vo.getCompanySite(), area);
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
     * 投资人明细
     */
    private int insertInvestorItem(InvestorVo vo, Long userId, Long id) throws Exception {
        InvestorItemEntity item = new InvestorItemEntity();
        item.setInvestorId(id);
        item = investorItem(item, vo);
        item.setPublishId(userId);
        item.setLeaveNumber(0L);
        item.setCollectNumber(0L);
        return investorItemDao.insertInvestorItem(item);
    }

    /**
     * 投资人明细
     */
    private int updateInvestorItem(InvestorVo vo) throws Exception {
        InvestorItemEntity item = investorItemDao.findInvestorItem(vo.getId());
        item = investorItem(item, vo);
        return investorItemDao.updateInvestorItem(item);
    }

    private InvestorItemEntity investorItem(InvestorItemEntity item, InvestorVo vo) throws Exception {
        if (StringUtils.isNotBlank(vo.getCallingCard())) {
            item.setCallingCard(vo.getCallingCard());
        } else {
            item.setCallingCard(null);
        }
        if (StringUtils.isNotBlank(vo.getInvestIntro())) {
            item.setInvestIntro(vo.getInvestIntro());
        } else {
            item.setInvestIntro(null);
        }
        if (StringUtils.isNotBlank(vo.getEmail())) {
            item.setEmail(vo.getEmail());
        } else {
            item.setEmail(null);
        }
        item.setCompanySite(vo.getCompanySite());
        if (StringUtils.isNotBlank(vo.getSuccessfulCase())) {
            item.setSuccessfulCase(vo.getSuccessfulCase());
        } else {
            item.setSuccessfulCase(null);
        }
        item.setInvestWay(vo.getInvestWay());
        if (StringUtils.isNotBlank(vo.getWay())) {
            item.setWay(vo.getWay());
        } else {
            item.setWay(null);
        }
        item.setInvestType(vo.getInvestType());
        if (StringUtils.isNotBlank(vo.getInvestSum())) {
            item.setInvestSum(vo.getInvestSum());
        } else {
            item.setInvestSum(null);
        }
        if (StringUtils.isNotBlank(vo.getDeadline())) {
            item.setDeadline(vo.getDeadline());
        } else {
            item.setDeadline(null);
        }
        if (StringUtils.isNotBlank(vo.getFocus())) {
            item.setFocus(vo.getFocus());
        } else {
            item.setFocus(null);
        }
        if (StringUtils.isNotBlank(vo.getRemark())) {
            item.setRemark(vo.getRemark());
        } else {
            item.setRemark(null);
        }
        if (StringUtils.isNotBlank(vo.getOther())) {
            item.setOther(vo.getOther());
        }
        item.setLinkman(vo.getLinkman());
        item.setPhone(vo.getPhone());
        if (StringUtils.isNotBlank(vo.getUrl())) {
            item.setUrl(vo.getUrl());
        } else {
            item.setUrl(null);
        }
        if (item.getPublishTime() == null) {
            item.setPublishTime(new Date());
        }
        return item;
    }

}