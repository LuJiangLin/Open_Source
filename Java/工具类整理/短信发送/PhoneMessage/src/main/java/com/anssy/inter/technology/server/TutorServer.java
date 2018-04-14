/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  TutorServer.java
 * PACKAGE      :  com.anssy.inter.technology.server
 * CREATE DATE  :  2016-8-25
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.inter.technology.server;

import com.anssy.venturebar.base.server.AreaCacheServer;
import com.anssy.venturebar.base.server.DatadictCacheServer;
import com.anssy.venturebar.base.server.FieldInfoCacheServer;
import com.anssy.venturebar.base.server.UserInfoCacheServer;
import com.anssy.venturebar.technology.dao.TutorInfoDao;
import com.anssy.venturebar.technology.dao.TutorItemDao;
import com.anssy.venturebar.technology.entity.TutorInfoEntity;
import com.anssy.venturebar.technology.entity.TutorItemEntity;
import com.anssy.venturebar.technology.vo.FieldVo;
import com.anssy.venturebar.technology.vo.PvVo;
import com.anssy.inter.base.server.BaseServer;
import com.anssy.inter.picture.server.PictureServer;
import com.anssy.inter.technology.vo.SearchVo;
import com.anssy.inter.technology.vo.TutorVo;
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
 * @version SVN #V1# #2016-8-25#
 *          导师
 */
@Service("tutorServer")
public class TutorServer {

    @Resource
    private TutorInfoDao tutorInfoDao;
    @Resource
    private TutorItemDao tutorItemDao;
    @Resource
    private PictureServer pictureServer;
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
     * 按GPS进行附近搜索,
     */
    public List<TutorInfoEntity> findTutorByGPS(SearchVo vo) throws Exception {
        GPSVo gpsVo = new GPSVo();
        if (StringUtils.isNotBlank(vo.getSearch())) {
            gpsVo.setSearch(vo.getSearch());
        }
        Geohash e = new Geohash();
        String geohash = e.encode(Double.parseDouble(vo.getLongitude()), Double.parseDouble(vo.getLatitude()));
        List<TutorInfoEntity> temps;
        int len = 5;
        do {
            //前5位编码相同表示10平方千米的范围，前4位编码相同表示60平方千米的范围
            if (geohash != null && geohash.length() > len) {
                gpsVo.setGeohash(geohash.substring(0, len));
                len--;
            }
            temps = tutorInfoDao.findTutorByGPS(gpsVo);
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
    private List<TutorInfoEntity> distanceSort(List<TutorInfoEntity> temps, double longitude, double latitude, int page) throws Exception {
        int being = (page - 1) * BaseConstants.PAGE_SIZE + 1;
        int end = (page - 1) * BaseConstants.PAGE_SIZE + BaseConstants.PAGE_SIZE;
        int len = temps.size();
        if (len >= being) {
            for (TutorInfoEntity temp : temps) {
                temp.setDistance(latitude, longitude);
            }
            Collections.sort(temps, new Comparator<TutorInfoEntity>() {
                public int compare(TutorInfoEntity arg0, TutorInfoEntity arg1) {
                    return arg0.getSort().compareTo(arg1.getSort());
                }
            });
        }
        List<TutorInfoEntity> infos = new ArrayList<TutorInfoEntity>();
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
    public List<TutorInfoEntity> findTutorByField(SearchVo vo) throws Exception {
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
        return replenish(tutorInfoDao.findTutorByField(fv));
    }

    /**
     * 智能搜索
     */
    public List<TutorInfoEntity> findTutorByPv(SearchVo vo) throws Exception {
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
        return replenish(tutorInfoDao.findTutorByPv(pv));
    }

    /**
     * 推荐导师
     */
    public List<TutorInfoEntity> referrals(ReferralsVo vo) throws Exception {
        if (vo.getProvinceId() == 0) {
            vo.setProvinceId(null);
        }
        if (vo.getCityId() == 0) {
            vo.setCityId(null);
        }
        if (vo.getAreaId() == 0) {
            vo.setAreaId(null);
        }
        return replenish(tutorInfoDao.referrals(vo));
    }

    /**
     * 我发布的信息
     */
    public List<TutorInfoEntity> findMyPublish(PublishVo vo, Long userId) throws Exception {
        MyVo mv = new MyVo();
        mv.setBeing((vo.getPage() - 1) * BaseConstants.PAGE_SIZE + 1);
        mv.setEnd((vo.getPage() - 1) * BaseConstants.PAGE_SIZE + BaseConstants.PAGE_SIZE);
        if (StringUtils.isNotBlank(vo.getSearch())) {
            mv.setSearch(vo.getSearch());
        }
        mv.setPublishId(userId);
        return replenish(tutorInfoDao.findMyPublish(mv));
    }

    /**
     * 我的收藏
     */
    public List<TutorInfoEntity> findCollect(MyCollectVo vo, Long userId) throws Exception {
        CollectVo cVo = new CollectVo();
        cVo.setBeing((vo.getPage() - 1) * BaseConstants.PAGE_SIZE + 1);
        cVo.setEnd((vo.getPage() - 1) * BaseConstants.PAGE_SIZE + BaseConstants.PAGE_SIZE);
        if (StringUtils.isNotBlank(vo.getSearch())) {
            cVo.setSearch(vo.getSearch());
        }
        cVo.setCollectId(userId);
        cVo.setType(vo.getType());
        return replenish(tutorInfoDao.findCollect(cVo));
    }

    /**
     * 补充省市区县信息
     */
    private List<TutorInfoEntity> replenish(List<TutorInfoEntity> infos) throws Exception {
        for (TutorInfoEntity info : infos) {
            if (StringUtils.isNotBlank(info.getHeadImage())) {
                info.setHeadImage(pictureServer.findTinyURL(info.getHeadImage()));
            }
//            info.setSexName(datadictCacheServer.findDict("SEX", Long.parseLong(info.getSex() + "")));
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
    public TutorInfoEntity replenish(TutorInfoEntity info) throws Exception {
        if (info != null && info.getId() > 0) {
            if (StringUtils.isNotBlank(info.getHeadImage())) {
                info.setHeadImage(pictureServer.findTinyURL(info.getHeadImage()));
            }
//            if (StringUtils.isNotBlank(info.getHeadImage())) {
//                List<String> urls = pictureServer.findURL(info.getHeadImage());
//                info.setUrls(urls);
//            }
            info.setSexName(datadictCacheServer.findDict("SEX", Long.parseLong(info.getSex() + "")));
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

    public TutorItemEntity replenish(TutorItemEntity item) throws Exception {
        if (item != null) {
            item.setPublishName(userInfoCacheServer.findNickname(item.getPublishId()));
        }
        return item;
    }

    /**
     * 通过导师ID查询导师信息
     */
    public TutorInfoEntity findTutorInfoById(Long id) throws Exception {
        return tutorInfoDao.findTutorInfoById(id);
    }

    /**
     * 通过导师ID查询导师明细
     */
    public TutorItemEntity findTutorItem(Long tutorId) throws Exception {
        return tutorItemDao.findTutorItem(tutorId);
    }

    /**
     * 修改浏览量
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public int updatePV(Long id) throws Exception {
        return tutorInfoDao.updatePV(id);
    }

    /**
     * 点赞
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public int updatePraise(Long id) throws Exception {
        return tutorInfoDao.updatePraise(id);
    }

    /**
     * 权重
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public int updateWeight(Long id) throws Exception {
        return tutorInfoDao.updateWeight(id, new Date().getTime());
    }

    /**
     * 删除导师信息
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean deleteTutor(Long tutorId) throws Exception {
        boolean flag = false;
        TutorInfoEntity info = findTutorInfoById(tutorId);
        if (info != null) {
            if (StringUtils.isNotBlank(info.getHeadImage())) {
                pictureServer.deletePicture(info.getHeadImage());
            }
            if (tutorInfoDao.deleteTutorInfo(tutorId) > 0) {
                if (tutorItemDao.deleteTutorItem(tutorId) > 0) {
                    flag = true;
                }
            }
        }
        return flag;
    }

    /**
     * 发布导师信息
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean releaseTutor(TutorVo vo, Long userId) throws Exception {
        boolean flag = false;
        Long id = tutorInfoDao.findId();
        if (insertTutorInfo(vo, id) > 0) {
            if (insertTutorItem(vo, id, userId) > 0) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 修改导师信息
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean updateTutor(TutorVo vo) throws Exception {
        boolean flag = false;
        if (updateTutorInfo(vo) > 0) {
            if (updateTutorItem(vo) > 0) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 添加导师信息
     */
    private int insertTutorInfo(TutorVo vo, Long id) throws Exception {
        TutorInfoEntity info = new TutorInfoEntity();
        info.setId(id);
        info = tutorInfo(info, vo);
        info.setPv(0L);
        info.setPraise(0L);
        info.setState(1);//状态 (STATE 0-待审核,1-正常,2-过期,3-锁定)
        return tutorInfoDao.insertTutorInfo(info);
    }

    /**
     * 修改导师信息
     */
    private int updateTutorInfo(TutorVo vo) throws Exception {
        TutorInfoEntity info = tutorInfoDao.findTutorInfoById(vo.getId());
        info = tutorInfo(info, vo);
        return tutorInfoDao.updateTutorInfo(info);
    }

    private TutorInfoEntity tutorInfo(TutorInfoEntity info, TutorVo vo) {
        if (StringUtils.isNotBlank(vo.getHeadImage())) {
            info.setHeadImage(vo.getHeadImage());
        } else {
            info.setHeadImage(null);
        }
        info.setName(vo.getName());
        info.setSex(vo.getSex());
        if (StringUtils.isNotBlank(vo.getTitle())) {
            info.setTitle(vo.getTitle());
        } else {
            info.setTitle(null);
        }
        if (StringUtils.isNotBlank(vo.getPost())) {
            info.setPost(vo.getPost());
        } else {
            info.setPost(null);
        }
        if (StringUtils.isNotBlank(vo.getLabel())) {
            info.setLabel(vo.getLabel());
        } else {
            info.setLabel(null);
        }
        info.setProvinceId(vo.getProvinceId());
        info.setCityId(vo.getCityId());
        info.setAreaId(vo.getAreaId());
        if (!vo.getField().startsWith(",")) {
            vo.setField("," + vo.getField());
        }
        if (!vo.getField().endsWith(",")) {
            vo.setField(vo.getField() + ",");
        }
        info.setField(vo.getField());
        if (StringUtils.isNotBlank(vo.getOrganization())) {
            info.setOrganization(vo.getOrganization());
        } else {
            info.setOrganization(null);
        }
        if (StringUtils.isNotBlank(vo.getOrganizationSite())) {
            String area = areaCacheServer.findArea(vo.getAreaId());
            Map<String, String> gps = Geocoder.getGeocoder(vo.getOrganizationSite(), area);
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
     * 添加导师明细
     */
    private int insertTutorItem(TutorVo vo, Long id, Long userId) throws Exception {
        TutorItemEntity item = new TutorItemEntity();
        item.setTutorId(id);
        item = tutorItem(item, vo);
        item.setPublishId(userId);
        item.setLeaveNumber(0L);
        item.setCollectNumber(0L);
        return tutorItemDao.insertTutorItem(item);
    }

    /**
     * 修改导师明细
     */
    private int updateTutorItem(TutorVo vo) throws Exception {
        TutorItemEntity item = tutorItemDao.findTutorItem(vo.getId());
        item = tutorItem(item, vo);
        return tutorItemDao.updateTutorItem(item);
    }

    private TutorItemEntity tutorItem(TutorItemEntity item, TutorVo vo) {
        item.setAge(vo.getAge());
        if (StringUtils.isNotBlank(vo.getBgInfo())) {
            item.setBgInfo(vo.getBgInfo());
        } else {
            item.setBgInfo(null);
        }
        if (StringUtils.isNotBlank(vo.getUrl())) {
            item.setUrl(vo.getUrl());
        } else {
            item.setUrl(null);
        }
        item.setLinkman(vo.getLinkman());
        item.setPhone(vo.getPhone());
        if (StringUtils.isNotBlank(vo.getEmail())) {
            item.setEmail(vo.getEmail());
        } else {
            item.setEmail(null);
        }
        if (StringUtils.isNotBlank(vo.getOrganizationSite())) {
            item.setOrganizationSite(vo.getOrganizationSite());
        } else {
            item.setOrganizationSite(null);
        }
        if (StringUtils.isNotBlank(vo.getWill())) {
            item.setWill(vo.getWill());
        } else {
            item.setWill(null);
        }
        if (StringUtils.isNotBlank(vo.getCooperation())) {
            item.setCooperation(vo.getCooperation());
        } else {
            item.setCooperation(null);
        }
        if (item.getPublishTime() == null) {
            item.setPublishTime(new Date());
        }
        return item;
    }

}