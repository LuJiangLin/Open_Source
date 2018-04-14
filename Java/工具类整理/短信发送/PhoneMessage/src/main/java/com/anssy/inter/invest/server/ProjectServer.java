/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  ProjectServer.java
 * PACKAGE      :  com.anssy.inter.invest.server
 * CREATE DATE  :  2016-8-21
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.inter.invest.server;

import com.anssy.inter.site.vo.SiteVo;
import com.anssy.venturebar.base.dao.DatadictDao;
import com.anssy.venturebar.base.server.AreaCacheServer;
import com.anssy.venturebar.base.server.DatadictCacheServer;
import com.anssy.venturebar.base.server.FieldInfoCacheServer;
import com.anssy.venturebar.base.server.UserInfoCacheServer;
import com.anssy.venturebar.base.vo.DatadictVo;
import com.anssy.venturebar.invest.dao.ProjectInfoDao;
import com.anssy.venturebar.invest.dao.ProjectItemDao;
import com.anssy.venturebar.invest.entity.ProjectInfoEntity;
import com.anssy.venturebar.invest.entity.ProjectItemEntity;
import com.anssy.venturebar.invest.vo.FieldVo;
import com.anssy.venturebar.invest.vo.PvVo;
import com.anssy.inter.base.server.BaseServer;
import com.anssy.inter.invest.vo.ProjectVo;
import com.anssy.inter.invest.vo.SearchVo;
import com.anssy.inter.picture.server.PictureServer;
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
 * @version SVN #V1# #2016-8-21#
 *          投资_项目
 */
@Service("projectServer")
public class ProjectServer {

    private static Logger logger = Logger.getLogger(ProjectServer.class);

    @Resource
    private DatadictDao datadictDao;
    @Resource
    private ProjectInfoDao projectInfoDao;
    @Resource
    private ProjectItemDao projectItemDao;
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
     * 获取融资需求
     */
    public List<DatadictVo> findIsFinancing() throws Exception {
        return datadictDao.findDictByCategory("IS_FINANCING");
    }

    /**
     * 获取融资状态
     */
    public List<DatadictVo> findFinancingState() throws Exception {
        return datadictDao.findDictByCategory("FINANCING_STATE");
    }

    /**
     * 获取出让股份比例
     */
    public List<DatadictVo> findSellShare() throws Exception {
        return datadictDao.findDictByCategory("SHARE");
    }


    /**
     * 修改浏览量
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public int updatePV(Long id) throws Exception {
        return projectInfoDao.updatePV(id);
    }

    /**
     * 点赞
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public int updatePraise(Long id) throws Exception {
        return projectInfoDao.updatePraise(id);
    }

    /**
     * 权重
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public int updateWeight(Long id) throws Exception {
        return projectInfoDao.updateWeight(id, new Date().getTime());
    }

    /**
     * 删除投资项目信息
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean deleteProject(Long projectId) throws Exception {
        boolean flag = false;
        ProjectInfoEntity info = findProjectInfoById(projectId);
        if (info != null) {
            if (StringUtils.isNotBlank(info.getImage())) {
                pictureServer.deletePicture(info.getImage());
            }
            if (projectInfoDao.deleteProjectInfo(projectId) > 0) {
                if (projectItemDao.deleteProjectItem(projectId) > 0) {
                    flag = true;
                }
            }
        }
        return flag;
    }

    /**
     * 删除全部投资项目信息
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean deleteAllProject() throws Exception {
        boolean flag = false;
        if (projectInfoDao.deleteAllProjectInfo() > 0) {
            if (projectItemDao.deleteAllProjectItem() > 0) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 通过主键ID查询项目信息
     */
    public ProjectInfoEntity findProjectInfoById(Long id) throws Exception {
        return projectInfoDao.findProjectInfoById(id);
    }

    /**
     * 通过项目ID查询项目明细
     */
    public ProjectItemEntity findProjectItem(Long projectId) throws Exception {
        return projectItemDao.findProjectItem(projectId);
    }

    /**
     * 按GPS进行附近搜索,
     */
    public List<ProjectInfoEntity> findListByGPS(SearchVo vo) throws Exception {
        GPSVo gpsVo = new GPSVo();
        if (StringUtils.isNotBlank(vo.getSearch())) {
            gpsVo.setSearch(vo.getSearch());
        }
        Geohash e = new Geohash();
        String geohash = e.encode(Double.parseDouble(vo.getLongitude()), Double.parseDouble(vo.getLatitude()));
        List<ProjectInfoEntity> temps = null;
        int len = 5;
        do {
            //前5位编码相同表示10平方千米的范围，前4位编码相同表示60平方千米的范围
            if (geohash != null && geohash.length() > len) {
                gpsVo.setGeohash(geohash.substring(0, len));
                len--;
            }
            temps = projectInfoDao.findListByGPS(gpsVo);
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
    private List<ProjectInfoEntity> distanceSort(List<ProjectInfoEntity> temps, double longitude, double latitude, int page) throws Exception {
        int being = (page - 1) * BaseConstants.PAGE_SIZE + 1;
        int end = (page - 1) * BaseConstants.PAGE_SIZE + BaseConstants.PAGE_SIZE;
        int len = temps.size();
        if (len >= being) {
            for (int i = 0; i < len; i++) {
                temps.get(i).setDistance(latitude, longitude);
            }
            Collections.sort(temps, new Comparator<ProjectInfoEntity>() {
                public int compare(ProjectInfoEntity arg0, ProjectInfoEntity arg1) {
                    return arg0.getSort().compareTo(arg1.getSort());
                }
            });
        }
        List<ProjectInfoEntity> infos = new ArrayList<ProjectInfoEntity>();
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
    public List<ProjectInfoEntity> findProjectByField(SearchVo vo) throws Exception {
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
        return replenish(projectInfoDao.findListByField(fieldVo));
    }

    /**
     * 智能搜索
     */
    public List<ProjectInfoEntity> findProjectByPv(SearchVo vo) throws Exception {
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
        return replenish(projectInfoDao.findListByPv(pVo));
    }

    /**
     * 推荐项目
     */
    public List<ProjectInfoEntity> referrals(ReferralsVo vo) throws Exception {
        if (vo.getProvinceId() == 0) {
            vo.setProvinceId(null);
        }
        if (vo.getCityId() == 0) {
            vo.setCityId(null);
        }
        if (vo.getAreaId() == 0) {
            vo.setAreaId(null);
        }
        return replenish(projectInfoDao.referrals(vo));
    }

    /**
     * 我发布的信息
     */
    public List<ProjectInfoEntity> findMyPublish(PublishVo vo, Long userId) throws Exception {
        MyVo mv = new MyVo();
        mv.setBeing((vo.getPage() - 1) * BaseConstants.PAGE_SIZE + 1);
        mv.setEnd((vo.getPage() - 1) * BaseConstants.PAGE_SIZE + BaseConstants.PAGE_SIZE);
        if (StringUtils.isNotBlank(vo.getSearch())) {
            mv.setSearch(vo.getSearch());
        }
        mv.setPublishId(userId);
        return replenish(projectInfoDao.findMyPublish(mv));
    }

    /**
     * 我的收藏
     */
    public List<ProjectInfoEntity> findCollect(MyCollectVo vo, Long userId) throws Exception {
        CollectVo cVo = new CollectVo();
        cVo.setBeing((vo.getPage() - 1) * BaseConstants.PAGE_SIZE + 1);
        cVo.setEnd((vo.getPage() - 1) * BaseConstants.PAGE_SIZE + BaseConstants.PAGE_SIZE);
        if (StringUtils.isNotBlank(vo.getSearch())) {
            cVo.setSearch(vo.getSearch());
        }
        cVo.setCollectId(userId);
        cVo.setType(vo.getType());
        return replenish(projectInfoDao.findCollect(cVo));
    }

    /**
     * 补充省市区县信息
     */
    private List<ProjectInfoEntity> replenish(List<ProjectInfoEntity> infos) throws Exception {
        for (ProjectInfoEntity info : infos) {
            if (StringUtils.isNotBlank(info.getImage())) {
                info.setImage(pictureServer.findTinyURL(info.getImage()));
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
    public ProjectInfoEntity replenish(ProjectInfoEntity info) throws Exception {
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
        }
        return info;
    }

    public ProjectItemEntity replenish(ProjectItemEntity item) throws Exception {
        if (item != null && item.getId() > 0) {
            item.setPublishName(userInfoCacheServer.findNickname(item.getPublishId()));
            item.setIsFinancingName(datadictCacheServer.findDict("IS_FINANCING", Long.parseLong(item.getIsFinancing() + "")));
            item.setFinancingStateName(datadictCacheServer.findDict("FINANCING_STATE", Long.parseLong(item.getFinancingState() + "")));
            item.setSellShareName(datadictCacheServer.findDict("SHARE", Long.parseLong(item.getSellShare() + "")));
        }
        return item;
    }

    /**
     * 发布投资项目信息
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean releaseProject(ProjectVo vo, Long userId) throws Exception {
        boolean flag = false;
        Long id = projectInfoDao.findId();
        if (insertProjectInfo(vo, id) > 0) {
            if (insertProjectItem(vo, userId, id) > 0) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 修改投资项目信息
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean updateProject(ProjectVo vo) throws Exception {
        boolean flag = false;
        if (updateProjectInfo(vo) > 0) {
            if (updateProjectItem(vo) > 0) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 添加项目信息
     */
    private int insertProjectInfo(ProjectVo vo, Long id) throws Exception {
        ProjectInfoEntity info = new ProjectInfoEntity();
        info.setId(id);
        info = projectInfo(info, vo);
        info.setPv(0L);
        info.setPraise(0L);
        info.setState(1);//状态 (STATE 0-待审核,1-正常,2-过期,3-锁定)
        return projectInfoDao.insertProjectInfo(info);
    }

    /**
     * 修改项目信息
     */
    private int updateProjectInfo(ProjectVo vo) throws Exception {
        ProjectInfoEntity info = projectInfoDao.findProjectInfoById(vo.getId());
        info = projectInfo(info, vo);
        return projectInfoDao.updateProjectInfo(info);
    }

    private ProjectInfoEntity projectInfo(ProjectInfoEntity info, ProjectVo vo) {
        if (StringUtils.isNotBlank(vo.getImage())) {
            info.setImage(vo.getImage());
        } else {
            info.setImage(null);
        }
        info.setTitle(vo.getTitle());
        if (StringUtils.isNotBlank(vo.getSketch())) {
            info.setSketch(vo.getSketch());
        } else {
            info.setSketch(null);
        }
        vo.setField(vo.getField());
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

    private ProjectInfoEntity projectInfo(ProjectInfoEntity info, ProjectVo vo, String lng, String lat) {
        if (StringUtils.isNotBlank(vo.getImage())) {
            info.setImage(vo.getImage());
        } else {
            info.setImage(null);
        }
        info.setTitle(vo.getTitle());
        if (StringUtils.isNotBlank(vo.getSketch())) {
            info.setSketch(vo.getSketch());
        } else {
            info.setSketch(null);
        }
        vo.setField(vo.getField());
        info.setField(vo.getField());
        info.setProvinceId(vo.getProvinceId());
        info.setCityId(vo.getCityId());
        info.setAreaId(vo.getAreaId());
        info.setLongitude(lng);
        info.setLatitude(lat);
        try {
            if (StringUtils.isNotBlank(lng) && StringUtils.isNotBlank(lat)) {
                Geohash e = new Geohash();
                String geohash = e.encode(Double.parseDouble(lng), Double.parseDouble(lat));
                if (StringUtils.isNotBlank(geohash)) {
                    info.setGeohash(geohash);
                }
            }
        } catch (NumberFormatException e) {
            logger.error(e.getMessage());
        }
        info.setWeight(new Date().getTime());
        return info;
    }

    /**
     * 添加项目明细
     */
    private int insertProjectItem(ProjectVo vo, Long userId, Long id) throws Exception {
        ProjectItemEntity item = new ProjectItemEntity();
        item.setProjectId(id);
        item = projectItem(item, vo);
        item.setPublishId(userId);
        item.setLeaveNumber(0l);
        item.setCollectNumber(0l);
        return projectItemDao.insertProjectItem(item);
    }

    /**
     * 修改项目明细
     */
    private int updateProjectItem(ProjectVo vo) throws Exception {
        ProjectItemEntity item = projectItemDao.findProjectItem(vo.getId());
        item = projectItem(item, vo);
        return projectItemDao.updateProjectItem(item);
    }

    private ProjectItemEntity projectItem(ProjectItemEntity item, ProjectVo vo) {
        if (StringUtils.isNotBlank(vo.getDescribe())) {
            item.setDescribe(vo.getDescribe());
        } else {
            item.setDescribe(null);
        }
        if (StringUtils.isNotBlank(vo.getAdvantage())) {
            item.setAdvantage(vo.getAdvantage());
        } else {
            item.setAdvantage(null);
        }
        item.setIsFinancing(vo.getIsFinancing());
        item.setFinancingState(vo.getFinancingState());
        if (StringUtils.isNotBlank(vo.getFinancingSum())) {
            item.setFinancingSum(vo.getFinancingSum());
        }
        item.setSellShare(vo.getSellShare());
        if (StringUtils.isNotBlank(vo.getFounder())) {
            item.setFounder(vo.getFounder());
        } else {
            item.setFounder(null);
        }
        item.setCompanyName(vo.getCompanyName());
        item.setCompanySite(vo.getCompanySite());
        if (StringUtils.isNotBlank(vo.getEmail())) {
            item.setEmail(vo.getEmail());
        } else {
            item.setEmail(null);
        }
        if (StringUtils.isNotBlank(vo.getUrl())) {
            item.setUrl(vo.getUrl());
        } else {
            item.setUrl(null);
        }
        if (StringUtils.isNotBlank(vo.getAppUrl())) {
            item.setAppUrl(vo.getAppUrl());
        } else {
            item.setAppUrl(null);
        }
        item.setLinkman(vo.getLinkman());
        item.setPhone(vo.getPhone());
        if (item.getPublishTime() == null) {
            item.setPublishTime(new Date());
        }
        return item;
    }

    private static boolean isRun = false;

    /**
     * 从创业在线拉取项目数据 (定时)
     */
    public void loadProjectFormStartupOnline() {

        // 防止重复同步
        if (isRun) return;
        isRun = true;

        String sql = "select id," +
                "tb_picture," +
                "tb_attachmentone," +
                "tb_attachmenttwo," +
                "tb_name," +
                "title," +
                "tb_probusiness," +
                "tb_area," +
                "tb_info," +
                "tb_procompete," +
                "tb_prostate," +
                "tb_profinance," +
                "tb_prostock," +
                "tb_proteam," +
                "tb_contact," +
                "tb_phone," +
                "tb_lat," +
                "tb_lng," +
                "tb_address," +
                "update_time from project"; // SQL语句

        db = new DBHelper(sql);//创建DBHelper对象

        try {
            ret = db.pst.executeQuery();//执行语句，得到结果集

            // 删除所有投资项目
            deleteAllProject();

            while (ret.next()) {
                Long id = Long.valueOf(ret.getString(1));
                String image_url = ret.getString(2);
                String attachmentone = ret.getString(3);
                String attachmenttwo = ret.getString(4);
                String name = ret.getString(5);
                String title = ret.getString(6);
                String field = ret.getString(7);
                String area = ret.getString(8)
                        .replaceAll("市", "")
                        .replaceAll("区", "")
                        .replaceAll("县", "")
                        .replaceAll("旗", "")
                        .replaceAll("岛", "");
                String describe = ret.getString(9);
                String advantage = ret.getString(10);
                int financingState = financingState(ret.getString(11));
                String financingSum = ret.getString(12);
                int sellShare = sellShare(ret.getString(13));
                String founder = ret.getString(14);
                String linkman = ret.getString(15);
                String phone = ret.getString(16);

                double[] gd_lat_lon = RegexUtils.bdToGaoDe(ret.getDouble(17), ret.getDouble(18));

                // 经度
                String longitude = String.format("%.6f", gd_lat_lon[0]);
                // 纬度
                String latitude = String.format("%.6f", gd_lat_lon[1]);


                String address = ret.getString(19);
                String datetime = ret.getString(20);

                logger.error("id : " + id + "title : " + title);
                if (title != null) {
                    Long[] ids = baseServer.findAreaIdByName(area);
                    ProjectVo vo = new ProjectVo();
                    vo.setId(id);

                    StringBuffer imageSb = new StringBuffer(image_url);
                    if (StringUtils.isNotBlank(attachmentone)) {
                        imageSb.append(";").append(attachmentone);
                    }
                    if (StringUtils.isNotBlank(attachmenttwo)) {
                        imageSb.append(";").append(attachmenttwo.replace(",", ";"));
                    }
                    vo.setImage(imageSb.toString());
                    vo.setTitle(title);
                    vo.setSketch(name);
                    vo.setField(findFields(field));
                    vo.setProvinceId(ids[0]);
                    vo.setCityId(ids[1]);
                    vo.setAreaId(ids[2]);
                    vo.setDescribe(RegexUtils.replaceAll(describe));
                    vo.setAdvantage(RegexUtils.replaceAll(advantage));
                    vo.setIsFinancing(0);
                    vo.setFinancingState(financingState);
                    vo.setFinancingSum(financingSum);
                    vo.setSellShare(sellShare);
                    vo.setFounder(RegexUtils.replaceAll(founder));
                    vo.setCompanyName("");
                    vo.setCompanySite(address);
                    vo.setEmail("");
                    vo.setUrl("");
                    vo.setAppUrl("");
                    vo.setLinkman(linkman);
                    vo.setPhone(phone);
                    vo.setTbLng(longitude);
                    vo.setTbLat(latitude);
                    // releaseProject(vo, 40L);

                    ProjectInfoEntity info = new ProjectInfoEntity();
                    info.setId(id);
                    info = projectInfo(info, vo, longitude, latitude);
                    info.setPv(0L);
                    info.setPraise(0L);
                    info.setState(1);//状态 (STATE 0-待审核,1-正常,2-过期,3-锁定)
                    if (projectInfoDao.insertProjectInfo(info) > 0) {
                        ProjectItemEntity item = new ProjectItemEntity();
                        item.setProjectId(id);
                        item.setPublishTime(DateTimeUtil.getFormatDate(datetime));
                        item = projectItem(item, vo);
                        item.setPublishId(40L);
                        item.setLeaveNumber(0l);
                        item.setCollectNumber(0l);
                        if (projectItemDao.insertProjectItem(item) <= 0) {
                            projectInfoDao.deleteProjectInfo(id);
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

    private int financingState(String state) {
        if (state == null)
            return 40;
        if (state.equals("天使轮"))
            return 37;
        else if (state.equals("VC轮"))
            return 38;
        else if (state.equals("PE轮"))
            return 39;
        return 40;
    }

    private int sellShare(String share) {
        if (share == null)
            return 49;
        if (share.equals("0%—10%"))
            return 49;
        else if (share.equals("10%—30%"))
            return 50;
        else if (share.equals("30%—50%"))
            return 51;
        else if (share.equals("50%以上"))
            return 51;
        return 49;
    }

    /**
     * 通过field获取对应的行业信息
     */
    public String findFields(String fieldStr) {
        StringBuilder fields = new StringBuilder(",");
        if (fieldStr != null && !StringUtils.isBlank(fieldStr)) {
            String[] strings = fieldStr.split(",");
            for (String field : strings) {
                if (field != null && !field.equals("null")
                        && !field.equals("") && StringUtils.isNotBlank(field)) {
                    fields.append(findField2ID(field)).append(",");
                }
            }
        }
        return fields.toString();
    }

    private int findField2ID(String field) {

        if (field.indexOf("移动互联网") != -1) {
            return 2;
        } else if (field.indexOf("互联网") != -1) {
            return 1;
        } else if (field.indexOf("电子商务") != -1) {
            return 3;
        } else if (field.indexOf("O2O") != -1) {
            return 4;
        } else if (field.indexOf("文体娱乐") != -1) {
            return 5;
        } else if (field.indexOf("医疗健康") != -1) {
            return 6;
        } else if (field.indexOf("能源环保") != -1) {
            return 7;
        } else if (field.indexOf("智能硬件") != -1) {
            return 8;
        } else if (field.indexOf("软件服务") != -1) {
            return 9;
        } else if (field.indexOf("电子产品") != -1) {
            return 10;
        } else if (field.indexOf("生物") != -1) {
            return 11;
        } else if (field.indexOf("消费") != -1) {
            return 12;
        } else if (field.indexOf("金融") != -1) {
            return 13;
        } else if (field.indexOf("旅游") != -1) {
            return 14;
        } else if (field.indexOf("教育") != -1) {
            return 15;
        } else if (field.indexOf("游戏") != -1) {
            return 16;
        } else if (field.indexOf("汽车") != -1) {
            return 17;
        } else if (field.indexOf("房地产") != -1) {
            return 18;
        } else if (field.indexOf("服务业") != -1) {
            return 19;
        } else if (field.indexOf("综合类") != -1) {
            return 20;
        } else if (field.indexOf("农业") != -1) {
            return 21;
        }

        return 22;
    }

}