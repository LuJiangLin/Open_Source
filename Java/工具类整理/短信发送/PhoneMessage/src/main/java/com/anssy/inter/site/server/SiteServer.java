/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  SiteServer.java
 * PACKAGE      :  com.anssy.inter.site.server
 * CREATE DATE  :  2016-8-13
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.inter.site.server;

import com.anssy.inter.base.server.BaseServer;
import com.anssy.inter.base.server.NewsPolicyServer;
import com.anssy.inter.base.vo.NewsVo;
import com.anssy.venturebar.base.dao.SiteTypeDao;
import com.anssy.venturebar.base.entity.SiteTypeEntity;
import com.anssy.venturebar.base.server.AreaCacheServer;
import com.anssy.venturebar.base.server.SiteTypeCacheServer;
import com.anssy.venturebar.base.server.UserInfoCacheServer;
import com.anssy.venturebar.site.dao.SiteInfoDao;
import com.anssy.venturebar.site.dao.SiteItemDao;
import com.anssy.venturebar.site.entity.SiteInfoEntity;
import com.anssy.venturebar.site.entity.SiteItemEntity;
import com.anssy.venturebar.site.vo.PvVo;
import com.anssy.venturebar.site.vo.SiteTypeVo;
import com.anssy.inter.picture.server.PictureServer;
import com.anssy.inter.site.vo.SearchVo;
import com.anssy.inter.site.vo.SiteVo;
import com.anssy.inter.site.vo.TypeVo;
import com.anssy.webcore.common.*;
import com.anssy.webcore.vo.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * @author make it
 * @version SVN #V1# #2016-8-13#
 *          场地
 */
@Service("siteServer")
public class SiteServer {
    @Resource
    private BaseServer baseServer;

    private static Logger logger = Logger.getLogger(SiteServer.class);

    /**
     * 经度偏移值
     */
    private final static Double LONGITUDE_DEVIANT = 0.0065D;
    /**
     * 经度偏移值
     */
    private final static Double LATITUDE_DEVIANT = 0.0065D;

    @Resource
    private SiteInfoDao siteInfoDao;
    @Resource
    private SiteItemDao siteItemDao;
    @Resource
    private SiteTypeDao siteTypeDao;
    @Resource
    private PictureServer pictureServer;
    @Resource
    private SiteTypeCacheServer siteTypeCacheServer;
    @Resource
    private AreaCacheServer areaCacheServer;
    @Resource
    private UserInfoCacheServer userInfoCacheServer;


    /**
     * 获取场地类型
     */
    public List<SiteTypeEntity> findSiteType(TypeVo vo) throws Exception {
        return siteTypeDao.findSiteType(vo);
    }

    /**
     * 获取场地类型
     */
    public List<SiteTypeEntity> findSiteTypeAll() throws Exception {
        return siteTypeDao.findSiteTypeAll();
    }

    /**
     * 推荐场地
     */
    public List<SiteInfoEntity> referrals(ReferralsVo vo) throws Exception {
        if (vo.getProvinceId() == 0) {
            vo.setProvinceId(null);
        }
        if (vo.getCityId() == 0) {
            vo.setCityId(null);
        }
        if (vo.getAreaId() == 0) {
            vo.setAreaId(null);
        }
        return replenish(siteInfoDao.referrals(vo));
    }

    /**
     * 按GPS进行附近搜索,
     */
    public List<SiteInfoEntity> findListByGPS(SearchVo vo) throws Exception {
        GPSVo gpsVo = new GPSVo();
        if (StringUtils.isNotBlank(vo.getSearch())) {
            gpsVo.setSearch(vo.getSearch());
        }
        Geohash e = new Geohash();
        String geohash = e.encode(Double.parseDouble(vo.getLongitude()), Double.parseDouble(vo.getLatitude()));
        List<SiteInfoEntity> temps;
        int len = 5;
        do {
            // 前5位编码相同表示10平方千米的范围，前4位编码相同表示60平方千米的范围
            if (geohash != null && geohash.length() > len) {
                gpsVo.setGeohash(geohash.substring(0, len));
                len--;
            }
            temps = siteInfoDao.findListByGPS(gpsVo);
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
    private List<SiteInfoEntity> distanceSort(List<SiteInfoEntity> temps, double longitude, double latitude, int page) throws Exception {
        int being = (page - 1) * BaseConstants.PAGE_SIZE + 1;
        int end = (page - 1) * BaseConstants.PAGE_SIZE + BaseConstants.PAGE_SIZE;
        int len = temps.size();
        if (len >= being) {
            for (SiteInfoEntity temp : temps) {
                temp.setDistance(latitude, longitude);
            }
            Collections.sort(temps, new Comparator<SiteInfoEntity>() {
                public int compare(SiteInfoEntity arg0, SiteInfoEntity arg1) {
                    return arg0.getSort().compareTo(arg1.getSort());
                }
            });
        }
        List<SiteInfoEntity> infos = new ArrayList<SiteInfoEntity>();
        if (len >= being) {
            being--;//下标从0开始
            for (int i = being; (i < len && i < end); i++) {
                infos.add(temps.get(i));
            }
        }
        return replenish(infos);
    }

    /**
     * 智能搜索
     */
    public List<SiteInfoEntity> findListByPv(SearchVo vo) throws Exception {
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
        return replenish(siteInfoDao.findListByPv(pVo));
    }

    /**
     * 按类型进行场地类型搜索
     */
    public List<SiteInfoEntity> findListByType(SearchVo vo) throws Exception {
        SiteTypeVo stVo = new SiteTypeVo();
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
        //查询一级还是查询二级
        Long[] ids = findSiteTypeIds(vo.getType());
        if (ids != null && ids.length > 0) {
            stVo.setTypes(ids);
        } else {
            stVo.setType(vo.getType());
        }
        return replenish(siteInfoDao.findListByType(stVo));
    }

    /**
     * 我发布的信息
     */
    public List<SiteInfoEntity> findMyPublish(PublishVo vo, Long userId) throws Exception {
        MyVo mv = new MyVo();
        mv.setBeing((vo.getPage() - 1) * BaseConstants.PAGE_SIZE + 1);
        mv.setEnd((vo.getPage() - 1) * BaseConstants.PAGE_SIZE + BaseConstants.PAGE_SIZE);
        if (StringUtils.isNotBlank(vo.getSearch())) {
            mv.setSearch(vo.getSearch());
        }
        mv.setPublishId(userId);
        return replenish(siteInfoDao.findMyPublish(mv));
    }

    /**
     * 我的收藏
     */
    public List<SiteInfoEntity> findCollect(MyCollectVo vo, Long userId) throws Exception {
        CollectVo cVo = new CollectVo();
        cVo.setBeing((vo.getPage() - 1) * BaseConstants.PAGE_SIZE + 1);
        cVo.setEnd((vo.getPage() - 1) * BaseConstants.PAGE_SIZE + BaseConstants.PAGE_SIZE);
        if (StringUtils.isNotBlank(vo.getSearch())) {
            cVo.setSearch(vo.getSearch());
        }
        cVo.setCollectId(userId);
        cVo.setType(vo.getType());
        return replenish(siteInfoDao.findCollect(cVo));
    }

    /**
     * 补充省市区县信息
     */
    private List<SiteInfoEntity> replenish(List<SiteInfoEntity> sites) throws Exception {
        for (SiteInfoEntity site : sites) {
            if (StringUtils.isNotBlank(site.getSiteImage())) {
                site.setSiteImage(pictureServer.findTinyURL(site.getSiteImage()));
            }
            site.setTypeName(siteTypeCacheServer.findSiteType(site.getType().toString()));
//            if (site.getProvinceId() != null) {
//                site.setProvince(areaCacheServer.findArea(site.getProvinceId()));
//            }
//            if (site.getCityId() != null) {
//                site.setCity(areaCacheServer.findArea(site.getCityId()));
//            }
//            if (site.getAreaId() != null) {
//                site.setArea(areaCacheServer.findArea(site.getAreaId()));
//            }
        }
        return sites;
    }

    /**
     * 补充省市区县信息
     */
    public SiteInfoEntity replenish(SiteInfoEntity site) throws Exception {
        if (site != null && site.getId() > 0) {
            if (StringUtils.isNotBlank(site.getSiteImage())) {
                List<String> urls = pictureServer.findURL(site.getSiteImage());
                site.setUrls(urls);
            }
            site.setTypeName(siteTypeCacheServer.findSiteType(site.getType().toString()));
            if (site.getProvinceId() != null) {
                site.setProvince(areaCacheServer.findArea(site.getProvinceId()));
            }
            if (site.getCityId() != null) {
                site.setCity(areaCacheServer.findArea(site.getCityId()));
            }
            if (site.getAreaId() != null) {
                site.setArea(areaCacheServer.findArea(site.getAreaId()));
            }
        }
        return site;
    }

    public SiteItemEntity replenish(SiteItemEntity item) throws Exception {
        if (item != null) {
            item.setPublishName(userInfoCacheServer.findNickname(item.getPublishId()));
        }
        return item;
    }

    /**
     * 按类型排序搜索时，获取类型的ID数组
     * 注 选择一级类型时需要能查询出属于此类型的全部数据(二级)
     */
    private Long[] findSiteTypeIds(Long typeId) throws Exception {
        Long[] ids = null;
        SiteTypeEntity type = siteTypeDao.findSiteTypeById(typeId);
        if (type != null && type.getSiteLevel() == 1) {
            List<SiteTypeEntity> types = siteTypeDao.findSiteTypeByFatherId(typeId);
            if (!types.isEmpty()) {
                int len = types.size();
                ids = new Long[len + 1];
                ids[0] = typeId;
                for (int i = 0; i < len; i++) {
                    ids[i + 1] = types.get(i).getId();
                }
            }
        }
        return ids;
    }

    /**
     * 修改浏览量
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public int updatePV(Long id) throws Exception {
        return siteInfoDao.updatePV(id);
    }

    /**
     * 点赞
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public int updatePraise(Long id) throws Exception {
        return siteInfoDao.updatePraise(id);
    }

    /**
     * 权重
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public int updateWeight(Long id) throws Exception {
        return siteInfoDao.updateWeight(id, new Date().getTime());
    }

    /**
     * 删除场地信息
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean deleteSite(Long siteId) throws Exception {
        boolean flag = false;
        SiteInfoEntity info = findSiteInfoById(siteId);
        if (info != null) {
            //删除图片
            if (StringUtils.isNotBlank(info.getSiteImage())) {
                pictureServer.deletePicture(info.getSiteImage());
            }
            if (siteItemDao.deleteSiteItem(siteId) > 0) {
                if (siteInfoDao.deleteSiteInfo(siteId) > 0) {
                    flag = true;
                }
            }
        }
        return flag;
    }

    /**
     * 删除全部场地信息
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean deleteAllSite() throws Exception {
        boolean flag = false;
        if (siteItemDao.deleteAllSiteItem() > 0) {
            if (siteInfoDao.deleteAllSiteInfo() > 0) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 过期
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void overdue(Date time) {
        try {
            siteInfoDao.overdue(time);
        } catch (Exception ignored) {
        }
    }

    /**
     * 通过场地ID查询信息
     */
    public SiteInfoEntity findSiteInfoById(Long id) throws Exception {
        return siteInfoDao.findSiteInfoById(id);
    }

    /**
     * 通过场地ID查询信息
     */
    public SiteItemEntity findSiteItemById(Long siteId) throws Exception {
        return siteItemDao.findSiteItemById(siteId);
    }

    /**
     * 发布场地信息接口
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean releaseSite(SiteVo vo, Long userId) throws Exception {
        boolean flag = false;
        Long id = siteInfoDao.findId();
        if (insertSiteInfo(vo, id) > 0) {
            if (insertSiteItem(vo, id, userId) > 0) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 修改场地信息接口
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean updateSite(SiteVo vo) throws Exception {
        boolean flag = false;
        if (updateSiteInfo(vo) > 0) {
            if (updateSiteItem(vo) > 0) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 添加场地信息
     */
    private int insertSiteInfo(SiteVo vo, Long id) throws Exception {
        SiteInfoEntity site = new SiteInfoEntity();
        site.setId(id);
        site.setBeginTime(new Date());
        site = siteInfo(site, vo);
        site.setPv(0L);
        site.setState(1); //(STATE 0-待审核,1-正常,2-过期,3-锁定)
        return siteInfoDao.insertSiteInfo(site);
    }

    /**
     * 修改场地信息
     */
    private int updateSiteInfo(SiteVo vo) throws Exception {
        SiteInfoEntity site = siteInfoDao.findSiteInfoById(vo.getId());
        site = siteInfo(site, vo);
        return siteInfoDao.updateSiteInfo(site);
    }

    private SiteInfoEntity siteInfo(SiteInfoEntity site, SiteVo vo) {
        if (StringUtils.isNotBlank(vo.getSiteImage())) {
            site.setSiteImage(vo.getSiteImage());
        } else {
            site.setSiteImage(null);
        }
        site.setType(vo.getType());
        site.setSiteName(vo.getSiteName());
        site.setTitle(vo.getTitle());
        site.setProvinceId(vo.getProvinceId());
        site.setCityId(vo.getCityId());
        site.setAreaId(vo.getAreaId());
        if (StringUtils.isNotBlank(vo.getSiteAddress())) {
            String area = areaCacheServer.findArea(vo.getAreaId());
            Map<String, String> gps = Geocoder.getGeocoder(vo.getSiteAddress(), area);
            String lng = gps.get("lng");
            String lat = gps.get("lat");
            if (StringUtils.isNotBlank(lng) && StringUtils.isNotBlank(lat)) {
                site.setLongitude(lng);
                site.setLatitude(lat);
                Geohash e = new Geohash();
                String geohash = e.encode(Double.parseDouble(lng), Double.parseDouble(lat));
                if (StringUtils.isNotBlank(geohash)) {
                    site.setGeohash(geohash);
                }
            }
        }
        if (StringUtils.isNotBlank(vo.getPrice())) {
            site.setPrice(vo.getPrice());
        } else {
            site.setPrice(null);
        }
        if (StringUtils.isNotBlank(vo.getRent())) {
            site.setRent(vo.getRent());
        } else {
            site.setRent(null);
        }
        if (StringUtils.isNotBlank(vo.getAcreage())) {
            site.setAcreage(vo.getAcreage());
        } else {
            site.setAcreage(null);
        }
        if (StringUtils.isNotBlank(vo.getEmail())) {
            site.setEmail(vo.getEmail());
        } else {
            site.setEmail(null);
        }
        if (vo.getEndTime() != null && DateTimeUtil.getBetweenDays(site.getBeginTime(), vo.getEndTime()) > 0) {
            site.setEndTime(vo.getEndTime());
        } else {
            site.setEndTime(DateTimeUtil.afterDaysSinceDate(site.getBeginTime(), BaseConstants.VALIDITY_DAYS));
        }
        site.setWeight(new Date().getTime());
        return site;
    }

    private SiteInfoEntity siteInfo(SiteInfoEntity site, SiteVo vo, String lng, String lat) {
        if (StringUtils.isNotBlank(vo.getSiteImage())) {
            site.setSiteImage(vo.getSiteImage());
        } else {
            site.setSiteImage(null);
        }
        site.setType(vo.getType());
        site.setSiteName(vo.getSiteName());
        site.setTitle(vo.getTitle());
        site.setProvinceId(vo.getProvinceId());
        site.setCityId(vo.getCityId());
        site.setAreaId(vo.getAreaId());
        site.setLongitude(lng);
        site.setLatitude(lat);
        try {
            if (StringUtils.isNotBlank(lng) && StringUtils.isNotBlank(lat)) {
                Geohash e = new Geohash();
                String geohash = e.encode(Double.parseDouble(lng), Double.parseDouble(lat));
                if (StringUtils.isNotBlank(geohash)) {
                    site.setGeohash(geohash);
                }
            }
        } catch (NumberFormatException e) {
            logger.error(e.getMessage());
        }
        int count = 1;
        if (StringUtils.isNotBlank(vo.getPrice())&&!vo.getPrice().equals("0")) {
            count+=1;
            site.setPrice(vo.getPrice());
        } else {
            site.setPrice(null);
        }
        if (StringUtils.isNotBlank(vo.getRent())&&!vo.getRent().equals("0")) {
            count+=1;
            site.setRent(vo.getRent());
        } else {
            site.setRent(null);
        }
        if (StringUtils.isNotBlank(vo.getAcreage())) {
            site.setAcreage(vo.getAcreage());
        } else {
            site.setAcreage(null);
        }
        if (StringUtils.isNotBlank(vo.getEmail())) {
            site.setEmail(vo.getEmail());
        } else {
            site.setEmail(null);
        }
        if (vo.getEndTime() != null && DateTimeUtil.getBetweenDays(site.getBeginTime(), vo.getEndTime()) > 0) {
            site.setEndTime(vo.getEndTime());
        } else {
            site.setEndTime(DateTimeUtil.afterDaysSinceDate(site.getBeginTime(), BaseConstants.VALIDITY_DAYS));
        }
        site.setWeight(new Date().getTime()*count);
        return site;
    }

    /**
     * 明细信息
     */
    private int insertSiteItem(SiteVo vo, Long siteId, Long userId) throws Exception {
        SiteItemEntity item = new SiteItemEntity();
        item.setSiteId(siteId);
        item = siteItem(item, vo);
        item.setPublishId(userId);
        item.setLeaveNumber(0L);
        item.setCollectNumber(0L);
        return siteItemDao.insertSiteItem(item);
    }

    /**
     * 明细信息
     */
    private int updateSiteItem(SiteVo vo) throws Exception {
        SiteItemEntity item = siteItemDao.findSiteItemById(vo.getId());
        item = siteItem(item, vo);
        return siteItemDao.updateSiteItem(item);
    }

    private SiteItemEntity siteItem(SiteItemEntity item, SiteVo vo) {
        item.setLinkman(vo.getLinkman());
        item.setPhone(vo.getPhone());
        item.setDescribe(vo.getDescribe());
        if (StringUtils.isNotBlank(vo.getSiteAddress())) {
            item.setSiteAddress(vo.getSiteAddress());
        }
        if (StringUtils.isNotBlank(vo.getFitment())) {
            item.setFitment(vo.getFitment());
        } else {
            item.setFitment(null);
        }
        if (StringUtils.isNotBlank(vo.getDirection())) {
            item.setDirection(vo.getDirection());
        } else {
            item.setDirection(null);
        }
        if (StringUtils.isNotBlank(vo.getStorey())) {
            item.setStorey(vo.getStorey());
        } else {
            item.setStorey(null);
        }
        if (StringUtils.isNotBlank(vo.getProperty())) {
            item.setProperty(vo.getProperty());
        } else {
            item.setProperty(null);
        }
        if (StringUtils.isNotBlank(vo.getYears())) {
            item.setYears(vo.getYears());
        } else {
            item.setYears(null);
        }
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

    private static boolean isRun = false;


    /**
     * 从创业在线拉取场地数据 (定时)
     */
    public void loadSiteFormStartupOnline() {
        // 防止重复同步
        if (isRun) return;
        isRun = true;

        String sql = "select id," +
                "tb_picture," +
                "tb_name," +
                "tb_placetitle," +
                "tb_area," +
                "tb_officerent," +
                "tb_stationrent," +
                "tb_contact," +
                "tb_phone," +
                "tb_info," +
                "tb_address," +
                "tb_placedecorate," +
                "tb_lat," +
                "tb_lng," +
                "update_time from place"; // SQL语句
//        if (update_datetime != null) {
//            String updateTime = DateTimeUtil.getFormatTime(update_datetime);
//            sql = "select id,title,tb_instlink,update_time from app_zczx where update_time > '"
//                    + updateTime
//                    + "' order by update_time"; // SQL语句
//            logger.error("\n=============\nupdateTime : " + updateTime + "\n================");
//        }
        db = new DBHelper(sql);//创建DBHelper对象

        try {
            ret = db.pst.executeQuery();//执行语句，得到结果集

            // 清空所有场地信息
            deleteAllSite();

            while (ret.next()) {

                Long id = Long.valueOf(ret.getString(1));
                String image_urls = ret.getString(2);
                String name = ret.getString(3);
                String title = ret.getString(4);
                String area = ret.getString(5)
                        .replaceAll("市", "")
                        .replaceAll("区", "")
                        .replaceAll("县", "")
                        .replaceAll("旗", "")
                        .replaceAll("岛", "");
                String price = ret.getString(6);
                String rent = ret.getString(7);
                String linkman = ret.getString(8);
                String phone = ret.getString(9);
                String describe = ret.getString(10);
                String site_address = ret.getString(11);
                String fitment = ret.getString(12);

                double[] gd_lat_lon = RegexUtils.bdToGaoDe(ret.getDouble(13),ret.getDouble(14));

                // 经度
                String longitude = String.format("%.6f",gd_lat_lon[0]);
                // 纬度
                String latitude = String.format("%.6f",gd_lat_lon[1]);

                String datetime = ret.getString(15);

                logger.error("id : " + id + "title : " + title + "\ndate : " + datetime);
                if (title != null && datetime != null) {
                    Long[] ids = baseServer.findAreaIdByName(area);
                    SiteVo vo = new SiteVo();
                    vo.setId(id);
                    vo.setSiteImage(image_urls);
                    vo.setSiteName(name);
                    vo.setTitle(title);
                    vo.setType(type2Id(fitment));
                    vo.setProvinceId(ids[0]);
                    vo.setCityId(ids[1]);
                    vo.setAreaId(ids[2]);
                    vo.setPrice(price);
                    vo.setRent(rent);
                    vo.setAcreage("");
                    vo.setEmail("");
                    vo.setLinkman(linkman);
                    vo.setPhone(phone);
                    vo.setDescribe(RegexUtils.replaceAll(describe));
                    vo.setSiteAddress(site_address);
                    vo.setFitment(fitment);
                    vo.setDirection("");
                    vo.setStorey("");
                    vo.setProperty("");
                    vo.setYears("");
                    vo.setUrl("");
                    vo.setTbLng(longitude);
                    vo.setTbLat(latitude);

                    SiteInfoEntity site = new SiteInfoEntity();
                    site.setId(id);
                    site.setBeginTime(new Date());
                    site = siteInfo(site, vo, longitude, latitude);
                    site.setPv(0L);
                    site.setState(1); //(STATE 0-待审核,1-正常,2-过期,3-锁定)
                    if (siteInfoDao.insertSiteInfo(site) > 0) {
                        SiteItemEntity item = new SiteItemEntity();
                        item.setSiteId(id);
                        item.setPublishTime(DateTimeUtil.getFormatDate(datetime));
                        item = siteItem(item, vo);
                        item.setPublishId(40L);
                        item.setLeaveNumber(0L);
                        item.setCollectNumber(0L);
                        if (siteItemDao.insertSiteItem(item) <= 0) {
                            siteInfoDao.deleteSiteInfo(id);
                        }
                    }
                }
            }
            //显示数据
            ret.close();
            db.close();//关闭连接
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("创业在线数据访问异常啦!");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("数据储存异常啦!");
        } finally {
            db.close();//关闭连接
            isRun = false;
            db = null;
            ret = null;
        }
    }

    DBHelper db = null;
    ResultSet ret = null;

    private Long type2Id(String type) {
        if (type.indexOf("精装修") != -1) {
            return 1L;
        } else if (type.indexOf("简装") != -1) {
            return 2L;
        } else if (type.indexOf("毛坯") != -1) {
            return 3L;
        } else {
            return 0L;
        }
    }

}
