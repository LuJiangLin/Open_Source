/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  ActivityServer.java
 * PACKAGE      :  com.anssy.inter.activity.server
 * CREATE DATE  :  2016-8-27
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.inter.activity.server;

import com.anssy.inter.activity.vo.ActivityVo;
import com.anssy.inter.activity.vo.SearchVo;
import com.anssy.inter.activity.vo.TypeVo;
import com.anssy.inter.base.server.BaseServer;
import com.anssy.inter.picture.server.PictureServer;
import com.anssy.venturebar.activity.dao.ActivityInfoDao;
import com.anssy.venturebar.activity.dao.ActivityItemDao;
import com.anssy.venturebar.activity.dao.ActivityStaffDao;
import com.anssy.venturebar.activity.entity.ActivityInfoEntity;
import com.anssy.venturebar.activity.entity.ActivityItemEntity;
import com.anssy.venturebar.activity.entity.ActivityStaffEntity;
import com.anssy.venturebar.activity.vo.ActivityTypeVo;
import com.anssy.venturebar.activity.vo.PvVo;
import com.anssy.venturebar.app.server.SmsInfoServer;
import com.anssy.venturebar.base.dao.ActivityTypeDao;
import com.anssy.venturebar.base.dao.UserInfoDao;
import com.anssy.venturebar.base.entity.ActivityTypeEntity;
import com.anssy.venturebar.base.entity.UserInfoEntity;
import com.anssy.venturebar.base.server.ActivityTypeCacheServer;
import com.anssy.venturebar.base.server.AreaCacheServer;
import com.anssy.venturebar.base.server.UserInfoCacheServer;
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
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author make it
 * @version SVN #V1# #2016-8-27#
 *          活动
 */
@Service("activityServer")
public class ActivityServer {

    private static Logger logger = Logger.getLogger(ActivityServer.class);

    private static final Lock lock = new ReentrantLock();

    @Resource
    private ActivityInfoDao activityInfoDao;
    @Resource
    private ActivityItemDao activityItemDao;
    @Resource
    private ActivityStaffDao activityStaffDao;
    @Resource
    private AreaCacheServer areaCacheServer;
    @Resource
    private ActivityTypeCacheServer activityTypeCacheServer;
    @Resource
    private PictureServer pictureServer;
    @Resource
    private ActivityTypeDao activityTypeDao;
    @Resource
    private UserInfoCacheServer userInfoCacheServer;
    @Resource
    private UserInfoDao userInfoDao;
    @Resource
    private SmsInfoServer smsInfoServer;
    @Resource
    private BaseServer baseServer;

    /**
     * 获取活动类型
     */
    public List<ActivityTypeEntity> findActivityType(TypeVo vo) throws Exception {
        return activityTypeDao.findActivityType(vo);
    }

    /**
     * 获取活动类型
     */
    public List<ActivityTypeEntity> findActivityTypeAll() throws Exception {
        return activityTypeDao.findActivityTypeAll();
    }

    /**
     * 按GPS进行附近搜索,
     */
    public List<ActivityInfoEntity> findListByGPS(SearchVo vo) throws Exception {
        GPSVo gpsVo = new GPSVo();
        if (StringUtils.isNotBlank(vo.getSearch())) {
            gpsVo.setSearch(vo.getSearch());
        }
        Geohash e = new Geohash();
        String geohash = e.encode(Double.parseDouble(vo.getLongitude()), Double.parseDouble(vo.getLatitude()));

        int len = 5;
        if (geohash != null && geohash.length() > len) {
            gpsVo.setGeohash(geohash.substring(0, len));
        }

        List<ActivityInfoEntity> temps = activityInfoDao.findListByGPS(gpsVo);

        //为空,且len>=2
        while (temps.isEmpty() && len >= 2) {
            len--;
            if (geohash != null && geohash.length() > len) {
                gpsVo.setGeohash(geohash.substring(0, len));
                temps = activityInfoDao.findListByGPS(gpsVo);
            }
        }

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
    private List<ActivityInfoEntity> distanceSort(List<ActivityInfoEntity> temps, double longitude, double latitude, int page) throws Exception {
        int being = (page - 1) * BaseConstants.PAGE_SIZE + 1;
        int end = (page - 1) * BaseConstants.PAGE_SIZE + BaseConstants.PAGE_SIZE;
        int len = temps.size();
        if (len >= being) {
            for (ActivityInfoEntity temp : temps) {
                temp.setDistance(latitude, longitude);
            }
            Collections.sort(temps, new Comparator<ActivityInfoEntity>() {
                public int compare(ActivityInfoEntity arg0, ActivityInfoEntity arg1) {
                    return arg0.getSort().compareTo(arg1.getSort());
                }
            });
        }
        List<ActivityInfoEntity> infos = new ArrayList<ActivityInfoEntity>();
        if (len >= being) {
            being--;//下标从0开始
            for (int i = being; (i < len && i < end); i++) {
                infos.add(temps.get(i));
            }
        }
        return replenish(infos);
    }

    /**
     * 按活动类型搜索
     */
    public List<ActivityInfoEntity> findListByType(SearchVo vo) throws Exception {
        ActivityTypeVo aVo = new ActivityTypeVo();
        aVo.setBeing((vo.getPage() - 1) * BaseConstants.PAGE_SIZE + 1);
        aVo.setEnd((vo.getPage() - 1) * BaseConstants.PAGE_SIZE + BaseConstants.PAGE_SIZE);
        if (StringUtils.isNotBlank(vo.getSearch())) {
            aVo.setSearch(vo.getSearch());
        }
        if (vo.getProvinceId() > 0) {
            aVo.setProvinceId(vo.getProvinceId());
            if (vo.getCityId() > 0) {
                aVo.setCityId(vo.getCityId());
                if (vo.getAreaId() > 0) {
                    aVo.setAreaId(vo.getAreaId());
                }
            }
        }
        aVo.setActivityType(vo.getActivityType());
        return replenish(activityInfoDao.findListByType(aVo));
    }

    /**
     * 智能搜索
     */
    public List<ActivityInfoEntity> findListByPv(SearchVo vo) throws Exception {
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
        return replenish(activityInfoDao.findListByPv(pVo));
    }

    /**
     * 推荐活动
     */
    public List<ActivityInfoEntity> referrals(ReferralsVo vo) throws Exception {
        if (vo.getProvinceId() == 0) {
            vo.setProvinceId(null);
        }
        if (vo.getCityId() == 0) {
            vo.setCityId(null);
        }
        if (vo.getAreaId() == 0) {
            vo.setAreaId(null);
        }
        return replenish(activityInfoDao.referrals(vo));
    }

    /**
     * 我发布的信息
     */
    public List<ActivityInfoEntity> findMyPublish(PublishVo vo, Long userId) throws Exception {
        MyVo mv = new MyVo();
        mv.setBeing((vo.getPage() - 1) * BaseConstants.PAGE_SIZE + 1);
        mv.setEnd((vo.getPage() - 1) * BaseConstants.PAGE_SIZE + BaseConstants.PAGE_SIZE);
        if (StringUtils.isNotBlank(vo.getSearch())) {
            mv.setSearch(vo.getSearch());
        }
        mv.setPublishId(userId);
        return replenish(activityInfoDao.findMyPublish(mv));
    }

    /**
     * 我的收藏
     */
    public List<ActivityInfoEntity> findCollect(MyCollectVo vo, Long userId) throws Exception {
        CollectVo cVo = new CollectVo();
        cVo.setBeing((vo.getPage() - 1) * BaseConstants.PAGE_SIZE + 1);
        cVo.setEnd((vo.getPage() - 1) * BaseConstants.PAGE_SIZE + BaseConstants.PAGE_SIZE);
        if (StringUtils.isNotBlank(vo.getSearch())) {
            cVo.setSearch(vo.getSearch());
        }
        cVo.setCollectId(userId);
        cVo.setType(vo.getType());
        return replenish(activityInfoDao.findCollect(cVo));
    }

    /**
     * 补充省市区县信息
     */
    private List<ActivityInfoEntity> replenish(List<ActivityInfoEntity> infos) throws Exception {
        for (ActivityInfoEntity info : infos) {
            if (StringUtils.isNotBlank(info.getImage())) {
                info.setImage(pictureServer.findTinyURL(info.getImage()));
            }
            info.setTypeName(activityTypeCacheServer.findActivityType(info.getActivityType().toString()));
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
    public ActivityInfoEntity replenish(ActivityInfoEntity info) throws Exception {
        if (info != null && info.getId() > 0) {
            if (StringUtils.isNotBlank(info.getImage())) {
                info.setUrls(pictureServer.findURL(info.getImage()));
            }
            info.setTypeName(activityTypeCacheServer.findActivityType(info.getActivityType().toString()));
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

    public ActivityItemEntity replenish(ActivityItemEntity item) throws Exception {
        if (item != null) {
            item.setPublishName(userInfoCacheServer.findNickname(item.getPublishId()));
        }
        return item;
    }

    public List<ActivityStaffEntity> replenishStaff(List<ActivityStaffEntity> staffs) throws Exception {
        for (ActivityStaffEntity staff : staffs) {
            if (staff.getJionId() > 0) {
                UserInfoEntity user = userInfoCacheServer.findUserInfo(staff.getJionId());
                if (user != null) {
                    staff.setJionName(user.getNickname());
                }
            }
        }
        return staffs;
    }

    /**
     * 查询参与活动人员
     */
    public List<ActivityStaffEntity> findJoinStaffs(Long activityId) throws Exception {
        return activityStaffDao.findJoinStaffs(activityId);
    }

    /**
     * 查询关注活动人员
     */
    public List<ActivityStaffEntity> findCareStaffs(Long activityId) throws Exception {
        return activityStaffDao.findCareStaffs(activityId);
    }

    /**
     * 参与活动
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public int joinStaffs(Long activityId, Long userId) throws Exception {
        int mark = 0;
        int joinType = 0;
        if (addStaffs(activityId, userId, joinType) > 0) {
            if (activityItemDao.updateJoinNumber(activityId) > 0) {
                mark = 1;
            }
        } else {
            mark = -1;
        }
        return mark;
    }

    /**
     * 参与活动-通知
     * 发送邮件/短信
     */
    public void inform(Long activityId, Long userId) {
        try {
            UserInfoEntity user = userInfoDao.findUserById(userId);
            ActivityInfoEntity info = activityInfoDao.findActivityInfoById(activityId);
            ActivityItemEntity item = activityItemDao.findActivityItem(activityId);
            //邮件
            if (user != null && info != null && item != null
                    && StringUtils.isNotBlank(user.getEmail())) {
                sendEmail(user.getEmail(), user.getNickname(),
                        info.getActivityName(), item.getJoinNumber(), item.getUpperNumber());
            }
            //短信
            if (user != null && info != null && item != null) {
                sendSMS(user.getPhone(), user.getNickname(),
                        info.getActivityName(), item.getJoinNumber(), item.getUpperNumber());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //发送email
    private void sendEmail(String to_email, String nickname, String activityName, int joinNumber, String upperNumber) {
        //EmailUtils emailUtils=new EmailUtils();
        //emailUtils.sendActivityEmail(to_email,nickname,activityName,joinNumber,upperNumber);
    }

    //发送短信
    private void sendSMS(String phone, String nickname, String activityName, int joinNumber, String upperNumber) {
        SmsUtil ob = new SmsUtil();
        StringBuilder sb = new StringBuilder();
        String state;
        Long balance;
        try {
            sb.append("尊敬的").append(nickname).append("女士/先生:");
            sb.append("已成功报名").append(activityName).append("活动,");
            sb.append("您是第").append(joinNumber).append("/").append(upperNumber).append("位报名者,");
            sb.append("请等待工作人员和您联系(注册电话/邮箱),感谢您的大力支持.【创业服务中心】");
            state = ob.sendSms(phone, sb.toString());
            balance = ob.findBalance();
            smsInfoServer.saveSMS(phone, sb.toString(), balance, state);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 关注活动
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public int careStaffs(Long activityId, Long userId) throws Exception {
        int mark = 0;
        int joinType = 1;
        if (addStaffs(activityId, userId, joinType) > 0) {
            if (activityItemDao.updateCareNumber(activityId) > 0) {
                mark = 1;
            }
        } else {
            mark = -1;
        }
        return mark;
    }

    /**
     * 添加活动人员/关注活动
     */
    private int addStaffs(Long activityId, Long userId, int joinType) throws Exception {
        int count = 0;
        lock.lock();
        try {
            ActivityStaffEntity temp = new ActivityStaffEntity();
            temp.setActivityId(activityId);
            temp.setJionId(userId);
            temp.setJoinType(joinType);
            //不能重复参加/关注活动
            List<ActivityStaffEntity> staffs = activityStaffDao.findStaff(temp);
            if (staffs.isEmpty()) {
                ActivityStaffEntity staff = new ActivityStaffEntity();
                staff.setActivityId(activityId);
                staff.setJoinType(joinType);//参与类型(0-参加活动,1-关注活动)
                staff.setJionId(userId);
                staff.setJionTime(new Date());
                count = activityStaffDao.insertActivityStaff(staff);
            }
        } finally {
            lock.unlock();
        }
        return count;
    }

    /**
     * 删除活动
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean deleteActivity(Long activityId) throws Exception {
        boolean flag = false;
        ActivityInfoEntity info = findActivityInfoById(activityId);
        if (info != null) {
            if (StringUtils.isNotBlank(info.getImage())) {
                pictureServer.deletePicture(info.getImage());
            }
            if (activityInfoDao.deleteActivityInfo(activityId) > 0) {
                if (activityItemDao.deleteActivityItem(activityId) > 0) {
                    if (activityStaffDao.deleteCareStaffs(activityId) >= 0) {
                        flag = true;
                    }
                }
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
            activityInfoDao.overdue(time);
        } catch (Exception ignored) {
        }
    }

    /**
     * 修改浏览量
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public int updatePV(Long id) throws Exception {
        return activityInfoDao.updatePV(id);
    }

    /**
     * 点赞
     */
    public int updatePraise(Long id) throws Exception {
        return activityInfoDao.updatePraise(id);
    }

    /**
     * 权重
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public int updateWeight(Long id) throws Exception {
        return activityInfoDao.updateWeight(id, new Date().getTime());
    }

    /**
     * 通过活动ID查询活动信息
     */
    public ActivityInfoEntity findActivityInfoById(Long id) throws Exception {
        return activityInfoDao.findActivityInfoById(id);
    }

    /**
     * 通过活动ID查询活动明细
     */
    public ActivityItemEntity findActivityItem(Long activityId) throws Exception {
        return activityItemDao.findActivityItem(activityId);
    }

    /**
     * 发布活动信息
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean releaseActivity(ActivityVo vo, Long userId) throws Exception {
        boolean flag = false;
        Long id = activityInfoDao.findId();
        if (insertActivityInfo(vo, id) > 0) {
            if (insertActivityItem(vo, id, userId) > 0) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 修改活动信息
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean updateActivity(ActivityVo vo) throws Exception {
        boolean flag = false;
        if (updateActivityInfo(vo) > 0) {
            if (updateActivityItem(vo) > 0) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 添加活动信息
     */
    private int insertActivityInfo(ActivityVo vo, Long id) throws Exception {
        ActivityInfoEntity info = new ActivityInfoEntity();
        info.setId(id);
        info = activityInfo(info, vo);
        info.setPv(0L);
        info.setPraise(0L);
        info.setState(1);//状态 (STATE 0-待审核,1-正常,2-过期,3-锁定)
        return activityInfoDao.insertActivityInfo(info);
    }

    /**
     * 添加活动信息
     */
    private int updateActivityInfo(ActivityVo vo) throws Exception {
        ActivityInfoEntity info = activityInfoDao.findActivityInfoById(vo.getId());
        info = activityInfo(info, vo);
        return activityInfoDao.updateActivityInfo(info);
    }

    private ActivityInfoEntity activityInfo(ActivityInfoEntity info, ActivityVo vo) {
        if (StringUtils.isNotBlank(vo.getImage())) {
            info.setImage(vo.getImage());
        } else {
            info.setImage(null);
        }
        info.setActivityName(vo.getActivityName());
        if (StringUtils.isNotBlank(vo.getSponsor())) {
            info.setSponsor(vo.getSponsor());
        } else {
            info.setSponsor(null);
        }
        info.setActivityType(vo.getActivityType());
        if (DateTimeUtil.getBetweenDays(vo.getBeginTime(), vo.getEndTime()) >= 0) {
            info.setBeginTime(vo.getBeginTime());
            info.setEndTime(vo.getEndTime());
        } else {
            info.setBeginTime(vo.getEndTime());
            info.setEndTime(vo.getBeginTime());
        }
        info.setProvinceId(vo.getProvinceId());
        info.setCityId(vo.getCityId());
        info.setAreaId(vo.getAreaId());
        if (StringUtils.isNotBlank(vo.getActivitySite())) {
            String area = areaCacheServer.findArea(vo.getAreaId());
            Map<String, String> gps = Geocoder.getGeocoder(vo.getActivitySite(), area);
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

    private ActivityInfoEntity activityInfo(ActivityInfoEntity info, ActivityVo vo, String lng, String lat) {
        if (StringUtils.isNotBlank(vo.getImage())) {
            info.setImage(vo.getImage());
        } else {
            info.setImage(null);
        }
        info.setActivityName(vo.getActivityName());
        if (StringUtils.isNotBlank(vo.getSponsor())) {
            info.setSponsor(vo.getSponsor());
        } else {
            info.setSponsor(null);
        }
        info.setActivityType(vo.getActivityType());
        if (DateTimeUtil.getBetweenDays(vo.getBeginTime(), vo.getEndTime()) >= 0) {
            info.setBeginTime(vo.getBeginTime());
            info.setEndTime(vo.getEndTime());
        } else {
            info.setBeginTime(vo.getEndTime());
            info.setEndTime(vo.getBeginTime());
        }
        info.setProvinceId(vo.getProvinceId());
        info.setCityId(vo.getCityId());
        info.setAreaId(vo.getAreaId());
        info.setLongitude(lng);
        info.setLatitude(lat);
        Geohash e = new Geohash();
        String geohash = e.encode(Double.parseDouble(lng), Double.parseDouble(lat));
        if (StringUtils.isNotBlank(geohash)) {
            info.setGeohash(geohash);
        }
        info.setWeight(new Date().getTime());
        return info;
    }

    /**
     * 添加活动明细
     */
    private int insertActivityItem(ActivityVo vo, Long id, Long userId) throws Exception {
        ActivityItemEntity item = new ActivityItemEntity();
        item.setActivityId(id);
        item = activityItem(item, vo);
        item.setJoinNumber(0);
        item.setCareNumber(0);
        item.setActivityState(0);
        item.setPublishId(userId);
        item.setCollectNumber(0L);
        item.setLeaveNumber(0L);
        return activityItemDao.insertActivityItem(item);
    }

    /**
     * 修改活动明细
     */
    private int updateActivityItem(ActivityVo vo) throws Exception {
        ActivityItemEntity item = activityItemDao.findActivityItem(vo.getId());
        item = activityItem(item, vo);
        return activityItemDao.updateActivityItem(item);
    }

    private ActivityItemEntity activityItem(ActivityItemEntity item, ActivityVo vo) throws Exception {
        if (StringUtils.isNotBlank(vo.getDescribe())) {
            item.setDescribe(vo.getDescribe());
        } else {
            item.setDescribe(null);
        }
        item.setActivitySite(vo.getActivitySite());
        if (StringUtils.isNotBlank(vo.getUpperNumber())) {
            item.setUpperNumber(vo.getUpperNumber());
        } else {
            item.setUpperNumber(null);
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
     * 从创业在线拉取活动数据 (定时)
     */
    public void loadActivityFormStartupOnline() {

        // 防止重复同步
        if (isRun)return;
        isRun = true;

        String sql = "select id," +
                "tb_picture," +
                "tb_attachmentone," +
                "tb_attachmenttwo," +
                "tb_name," +
                "tb_type," +
                "tb_area," +
                "tb_sponsor," +
                "tb_starttime," +
                "tb_endtime," +
                "tb_actorjobnum," +
                "tb_info," +
                "tb_contact," +
                "tb_phone," +
                "tb_email," +
                "tb_lat," +
                "tb_lng," +
                "tb_address," +
                "update_time from activity"; // SQL语句

        db = new DBHelper(sql); // 创建DBHelper对象

        try {
            ret = db.pst.executeQuery(); // 执行语句，得到结果集

            // 删除所有投资项目
            activityInfoDao.deleteAllByComefrom();
            activityItemDao.deleteAllByNotidComefrom();

            while (ret.next()) {
                // Long id = Long.valueOf(ret.getString(1));
                String image_url = ret.getString(2);
                String attachmentone = ret.getString(3);
                String attachmenttwo = ret.getString(4);
                String name = ret.getString(5);
                String type = ret.getString(6);
                String area = ret.getString(7)
                        .replaceAll("市", "")
                        .replaceAll("区", "")
                        .replaceAll("县", "")
                        .replaceAll("旗", "")
                        .replaceAll("岛", "");
                String sponsor = ret.getString(8);
                String starttime = ret.getString(9);
                String endtime = ret.getString(10);
                String actorjobnum = ret.getString(11);
                String describe = ret.getString(12);
                String contact = ret.getString(13);
                String phone = ret.getString(14);
                String email = ret.getString(15);

                double[] gd_lat_lon = RegexUtils.bdToGaoDe(ret.getDouble(16),ret.getDouble(17));

                // 经度
                String longitude = String.format("%.6f",gd_lat_lon[0]);
                // 纬度
                String latitude = String.format("%.6f",gd_lat_lon[1]);

                String address = ret.getString(18);
                String datetime = ret.getString(19);

                Long id = activityInfoDao.findId();
                logger.error("id : " + id + "name : " + name + "\ndate : " + datetime);
                if (name != null && datetime != null) {
                    Long[] ids = baseServer.findAreaIdByName(area);
                    ActivityVo vo = new ActivityVo();
                    vo.setId(id);
                    StringBuffer imageSb = new StringBuffer(image_url);
                    if (StringUtils.isNotBlank(attachmentone)) {
                        imageSb.append(";").append(attachmentone);
                    }
                    if (StringUtils.isNotBlank(attachmenttwo)) {
                        imageSb.append(";").append(attachmenttwo);
                    }
                    vo.setImage(imageSb.toString());
                    vo.setActivityName(name);
                    vo.setActivityType(type2ID(type));
                    vo.setProvinceId(ids[0]);
                    vo.setCityId(ids[1]);
                    vo.setAreaId(ids[2]);
                    vo.setBeginTime(DateTimeUtil.getFormatDate(starttime));
                    vo.setEndTime(DateTimeUtil.getFormatDate(endtime));
                    vo.setSponsor(sponsor);
                    vo.setLinkman(contact);
                    vo.setPhone(phone);
                    vo.setDescribe(RegexUtils.replaceAll(describe));
                    vo.setUpperNumber(actorjobnum);
                    vo.setActivitySite(address);

                    ActivityInfoEntity info = new ActivityInfoEntity();
                    info.setId(id);
                    info = activityInfo(info, vo, longitude, latitude);
                    info.setPv(0L);
                    info.setPraise(0L);
                    info.setState(1);//状态 (STATE 0-待审核,1-正常,2-过期,3-锁定)
                    info.setComefrom(1L);

                    if (activityInfoDao.insertActivityInfo(info) > 0) {
                        ActivityItemEntity item = new ActivityItemEntity();
                        item.setActivityId(id);
                        item.setPublishTime(DateTimeUtil.getFormatDate(datetime));
                        item = activityItem(item, vo);
                        item.setPublishId(40L);
                        item.setLeaveNumber(0l);
                        item.setCollectNumber(0l);
                        if (activityItemDao.insertActivityItem(item) <= 0) {
                            activityInfoDao.deleteActivityInfo(id);
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


    private Long type2ID(String field) {

        if (field.indexOf("大赛") != -1) {
            return 2L;
        } else if (field.indexOf("宣讲") != -1) {
            return 1L;
        } else if (field.indexOf("沙龙") != -1) {
            return 3L;
        } else if (field.indexOf("论坛") != -1) {
            return 4L;
        }
        return 5L;
    }
}
