/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  ServiceCategoryDao.java
 * PACKAGE      :  com.anssy.inter.service.server
 * CREATE DATE  :  2016-8-18
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.inter.service.server;

import com.anssy.venturebar.base.dao.ServiceCategoryDao;
import com.anssy.venturebar.base.entity.ServiceCategoryEntity;
import com.anssy.venturebar.base.server.AreaCacheServer;
import com.anssy.venturebar.base.server.ServiceCategoryCacheServer;
import com.anssy.venturebar.base.server.UserInfoCacheServer;
import com.anssy.venturebar.service.dao.ServiceInfoDao;
import com.anssy.venturebar.service.dao.ServiceItemDao;
import com.anssy.venturebar.service.entity.ServiceInfoEntity;
import com.anssy.venturebar.service.entity.ServiceItemEntity;
import com.anssy.venturebar.service.vo.FieldVo;
import com.anssy.venturebar.service.vo.GPSVo;
import com.anssy.venturebar.service.vo.PvVo;
import com.anssy.inter.picture.server.PictureServer;
import com.anssy.inter.service.vo.SearchVo;
import com.anssy.inter.service.vo.ServiceCategoryVo;
import com.anssy.inter.service.vo.ServiceVo;
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
 * @version SVN #V1# #2016-8-18#
 *          服务
 */
@Service("serviceServer")
public class ServiceServer {

    @Resource
    private ServiceCategoryDao serviceCategoryDao;
    @Resource
    private ServiceInfoDao serviceInfoDao;
    @Resource
    private ServiceItemDao serviceItemDao;
    @Resource
    private PictureServer pictureServer;
    @Resource
    private AreaCacheServer areaCacheServer;
    @Resource
    private ServiceCategoryCacheServer serviceCategoryCacheServer;
    @Resource
    private UserInfoCacheServer userInfoCacheServer;

    /**
     * 按GPS进行附近搜索,
     */
    public List<ServiceInfoEntity> findListByGPS(SearchVo vo) throws Exception {
        GPSVo gpsVo = new GPSVo();
        if (StringUtils.isNotBlank(vo.getSearch())) {
            gpsVo.setSearch(vo.getSearch());
        }
        if (vo.getServiceType() != null && vo.getServiceType() != 0) {
            gpsVo.setServiceType("," + vo.getServiceType() + ",");
        } else {
            gpsVo.setServiceType(null);
        }
        Geohash e = new Geohash();
        String geohash = e.encode(Double.parseDouble(vo.getLongitude()), Double.parseDouble(vo.getLatitude()));
        List<ServiceInfoEntity> temps;
        int len = 5;
        do {
            //前5位编码相同表示10平方千米的范围，前4位编码相同表示60平方千米的范围
            if (geohash != null && geohash.length() > len) {
                gpsVo.setGeohash(geohash.substring(0, len));
                len--;
            }
            temps = serviceInfoDao.findListByGPS(gpsVo);
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
    private List<ServiceInfoEntity> distanceSort(List<ServiceInfoEntity> temps, double longitude, double latitude, int page) throws Exception {
        int being = (page - 1) * BaseConstants.PAGE_SIZE + 1;
        int end = (page - 1) * BaseConstants.PAGE_SIZE + BaseConstants.PAGE_SIZE;
        int len = temps.size();
        if (len >= being) {
            for (ServiceInfoEntity temp : temps) {
                temp.setDistance(latitude, longitude);
            }
            Collections.sort(temps, new Comparator<ServiceInfoEntity>() {
                public int compare(ServiceInfoEntity arg0, ServiceInfoEntity arg1) {
                    return arg0.getSort().compareTo(arg1.getSort());
                }
            });
        }
        List<ServiceInfoEntity> infos = new ArrayList<ServiceInfoEntity>();
        if (len >= being) {
            being--;//下标从0开始
            for (int i = being; (i < len && i < end); i++) {
                infos.add(temps.get(i));
            }
        }
        return replenish(infos);
    }

    /**
     * 按类型进行场地类型搜索
     */
    public List<ServiceInfoEntity> findListByType(SearchVo vo) throws Exception {
        FieldVo stVo = new FieldVo();
        stVo.setBeing((vo.getPage() - 1) * BaseConstants.PAGE_SIZE + 1);
        stVo.setEnd((vo.getPage() - 1) * BaseConstants.PAGE_SIZE + BaseConstants.PAGE_SIZE);
        if (StringUtils.isNotBlank(vo.getSearch())) {
            stVo.setSearch(vo.getSearch());
        }
        if (vo.getProvinceId() > 0) {
            stVo.setProvinceId(vo.getProvinceId());
            if (vo.getCityId() > 0) {
                stVo.setCityId(vo.getCityId());
                if (vo.getAreaId() > 0) {
                    stVo.setAreaId(vo.getAreaId());
                }
            }
        }
        if (vo.getServiceType() != null && vo.getServiceType() != 0) {
            stVo.setServiceType("," + vo.getServiceType() + ",");
        } else {
            stVo.setServiceType(null);
        }
        stVo.setField("," + vo.getField().toString() + ",");
        return replenish(serviceInfoDao.findListByType(stVo));
    }

    /**
     * 智能搜索
     */
    public List<ServiceInfoEntity> findListByPv(SearchVo vo) throws Exception {
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
        if (vo.getServiceType() != null && vo.getServiceType() != 0) {
            pVo.setServiceType("," + vo.getServiceType() + ",");
        } else {
            pVo.setServiceType(null);
        }
        return replenish(serviceInfoDao.findListByPv(pVo));
    }

    /**
     * 推荐服务
     */
    public List<ServiceInfoEntity> referrals(ReferralsVo vo) throws Exception {
        if (vo.getProvinceId() == 0) {
            vo.setProvinceId(null);
        }
        if (vo.getCityId() == 0) {
            vo.setCityId(null);
        }
        if (vo.getAreaId() == 0) {
            vo.setAreaId(null);
        }
        if (StringUtils.isNotBlank(vo.getServiceType())
                && !vo.getServiceType().equals("0")) {
            vo.setServiceType("," + vo.getServiceType() + ",");
        } else {
            vo.setServiceType(null);
        }
        return replenish(serviceInfoDao.referrals(vo));
    }

    /**
     * 我发布的信息
     */
    public List<ServiceInfoEntity> findMyPublish(PublishVo vo, Long userId) throws Exception {
        MyVo mv = new MyVo();
        mv.setBeing((vo.getPage() - 1) * BaseConstants.PAGE_SIZE + 1);
        mv.setEnd((vo.getPage() - 1) * BaseConstants.PAGE_SIZE + BaseConstants.PAGE_SIZE);
        if (StringUtils.isNotBlank(vo.getSearch())) {
            mv.setSearch(vo.getSearch());
        }
        mv.setPublishId(userId);
        if (vo.getServiceType() != null && vo.getServiceType() != 0) {
            mv.setServiceType("," + vo.getServiceType() + ",");
        } else {
            mv.setServiceType(null);
        }
        return replenish(serviceInfoDao.findMyPublish(mv));
    }

    /**
     * 我的收藏
     */
    public List<ServiceInfoEntity> findCollect(MyCollectVo vo, Long userId) throws Exception {
        CollectVo cVo = new CollectVo();
        cVo.setBeing((vo.getPage() - 1) * BaseConstants.PAGE_SIZE + 1);
        cVo.setEnd((vo.getPage() - 1) * BaseConstants.PAGE_SIZE + BaseConstants.PAGE_SIZE);
        if (StringUtils.isNotBlank(vo.getSearch())) {
            cVo.setSearch(vo.getSearch());
        }
        cVo.setCollectId(userId);
        cVo.setType(vo.getType());
        if (vo.getServiceType() != null && vo.getServiceType() != 0) {
            cVo.setServiceType("," + vo.getServiceType() + ",");
        } else {
            cVo.setServiceType(null);
        }
        return replenish(serviceInfoDao.findCollect(cVo));
    }

    /**
     * 补充省市区县信息
     */
    private List<ServiceInfoEntity> replenish(List<ServiceInfoEntity> infos) throws Exception {
        for (ServiceInfoEntity info : infos) {
            if (StringUtils.isNotBlank(info.getLogo())) {
                info.setLogo(pictureServer.findTinyURL(info.getLogo()));
            }
            info.setTypeName(serviceCategoryCacheServer.findCategory(info.getServiceType()));
            info.setFieldName(serviceCategoryCacheServer.findCategory(info.getField()));
//            if (info.getProvinceId() != null) {
//                info.setProvince(areaCacheServer.findArea(info.getProvinceId()));
//            }
//            if (info.getCityId() != null) {
//                info.setCity(areaCacheServer.findArea(info.getCityId()));
//            }
//            if (info.getAreaId() != null) {
//                info.setArea(areaCacheServer.findArea(info.getAreaId()));
//            }
        }
        return infos;
    }

    /**
     * 补充省市区县信息
     */
    public ServiceInfoEntity replenish(ServiceInfoEntity info) throws Exception {
        if (info != null && info.getId() > 0) {
            if (StringUtils.isNotBlank(info.getLogo())) {
                List<String> urls = pictureServer.findURL(info.getLogo());
                info.setUrls(urls);
            }
            info.setTypeName(serviceCategoryCacheServer.findCategory(info.getServiceType()));
            info.setFieldName(serviceCategoryCacheServer.findCategory(info.getField()));
            if (info.getProvinceId() != null) {
                info.setProvince(areaCacheServer.findArea(info.getProvinceId()));
            }
            if (info.getCityId() != null) {
                info.setCity(areaCacheServer.findArea(info.getCityId()));
            }
            if (info.getAreaId() != null) {
                info.setArea(areaCacheServer.findArea(info.getAreaId()));
            }
        }
        return info;
    }

    public ServiceItemEntity replenish(ServiceItemEntity item) throws Exception {
        if (item != null) {
            item.setPublishName(userInfoCacheServer.findNickname(item.getPublishId()));
        }
        return item;
    }

    /**
     * 删除服务信息
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean deleteService(Long serviceId) throws Exception {
        boolean flag = false;
        ServiceInfoEntity info = findServiceInfoById(serviceId);
        if (info != null) {
            if (StringUtils.isNotBlank(info.getLogo())) {
                pictureServer.deletePicture(info.getLogo());
            }
            if (serviceInfoDao.deleteServiceInfo(serviceId) > 0) {
                if (serviceItemDao.deleteServiceItem(serviceId) > 0) {
                    flag = true;
                }
            }
        }
        return flag;
    }

    /**
     * 通过服务ID查询服务信息
     */
    public ServiceInfoEntity findServiceInfoById(Long id) throws Exception {
        return serviceInfoDao.findServiceInfoById(id);
    }

    /**
     * 通过服务ID查询服务明细
     */
    public ServiceItemEntity findServiceItem(Long serviceId) throws Exception {
        return serviceItemDao.findServiceItem(serviceId);
    }

    /**
     * 修改浏览量
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public int updatePV(Long id) throws Exception {
        return serviceInfoDao.updatePV(id);
    }

    /**
     * 点赞
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public int updatePraise(Long id) throws Exception {
        return serviceInfoDao.updatePraise(id);
    }

    /**
     * 权重
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public int updateWeight(Long id) throws Exception {
        return serviceInfoDao.updateWeight(id, new Date().getTime());
    }

    /**
     * 发布服务信息
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean releaseService(ServiceVo vo, Long userId) throws Exception {
        boolean flag = false;
        Long id = serviceInfoDao.findId();
        if (insertServiceInfo(vo, id) > 0) {
            if (insertServiceItem(vo, id, userId) > 0) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 修改服务信息
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean updateService(ServiceVo vo) throws Exception {
        boolean flag = false;
        if (updateServiceInfo(vo) > 0) {
            if (updateServiceItem(vo) > 0) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 添加服务信息
     */
    private int insertServiceInfo(ServiceVo vo, Long id) throws Exception {
        ServiceInfoEntity info = new ServiceInfoEntity();
        info.setId(id);
        info = serviceInfo(info, vo);
        info.setPv(0L);
        info.setState(1);// 状态 (STATE 0-待审核,1-正常,2-过期,3-锁定)
        if (info.getPublishTime() == null) {
            info.setPublishTime(new Date());
        }
        return serviceInfoDao.insertServiceInfo(info);
    }

    /**
     * 修改服务信息
     */
    private int updateServiceInfo(ServiceVo vo) throws Exception {
        ServiceInfoEntity info = serviceInfoDao.findServiceInfoById(vo.getId());
        info = serviceInfo(info, vo);
        return serviceInfoDao.updateServiceInfo(info);
    }

    private ServiceInfoEntity serviceInfo(ServiceInfoEntity info, ServiceVo vo) {
        if (StringUtils.isNotBlank(vo.getLogo())) {
            info.setLogo(vo.getLogo());
        } else {
            info.setLogo(null);
        }
        if (StringUtils.isNotBlank(vo.getServiceType())) {
            if (!vo.getServiceType().startsWith(",")) {
                vo.setServiceType("," + vo.getServiceType());
            }
            if (!vo.getServiceType().endsWith(",")) {
                vo.setServiceType(vo.getServiceType() + ",");
            }
            info.setServiceType(vo.getServiceType());
        } else {
            info.setServiceType("");
        }
        if (!vo.getField().startsWith(",")) {
            vo.setField("," + vo.getField());
        }
        if (!vo.getField().endsWith(",")) {
            vo.setField(vo.getField() + ",");
        }
        info.setField(vo.getField());
        info.setOrganization(vo.getOrganization());
        info.setProvinceId(vo.getProvinceId());
        info.setCityId(vo.getCityId());
        info.setAreaId(vo.getAreaId());
        if (StringUtils.isNotBlank(vo.getServiceSite())) {
            String area = areaCacheServer.findArea(vo.getAreaId());
            Map<String, String> gps = Geocoder.getGeocoder(vo.getServiceSite(), area);
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
     * 添加服务明细
     */
    private int insertServiceItem(ServiceVo vo, Long id, Long userId) throws Exception {
        ServiceItemEntity item = new ServiceItemEntity();
        item.setServiceId(id);
        item = serviceItem(item, vo);
        item.setPublishId(userId);
        item.setLeaveNumber(0L);
        item.setCollectNumber(0L);
        return serviceItemDao.insertServiceItem(item);
    }

    /**
     * 修改服务明细
     */
    private int updateServiceItem(ServiceVo vo) throws Exception {
        ServiceItemEntity item = serviceItemDao.findServiceItem(vo.getId());
        item = serviceItem(item, vo);
        return serviceItemDao.updateServiceItem(item);
    }

    private ServiceItemEntity serviceItem(ServiceItemEntity item, ServiceVo vo) {
        if (StringUtils.isNotBlank(vo.getDescribe())) {
            item.setDescribe(vo.getDescribe());
        } else {
            item.setDescribe(null);
        }
        if (StringUtils.isNotBlank(vo.getOther())) {
            item.setOther(vo.getOther());
        } else {
            item.setOther(null);
        }
        item.setServiceSite(vo.getServiceSite());
        if (StringUtils.isNotBlank(vo.getServiceTime())) {
            item.setServiceTime(vo.getServiceTime());
        } else {
            item.setServiceTime(null);
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
        if (item.getPublishTime() == null) {
            item.setPublishTime(new Date());
        }
        return item;
    }

    /**
     * 获取服务类别
     */
    public List<ServiceCategoryEntity> findServiceCategory(ServiceCategoryVo vo) throws Exception {
        return serviceCategoryDao.findServiceCategory(vo);
    }

    /**
     * 获取服务类别
     */
    public List<ServiceCategoryEntity> findServiceCategoryAll() throws Exception {
        return serviceCategoryDao.findServiceCategoryAll();
    }

}