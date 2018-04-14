/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  ActivityAction.java
 * PACKAGE      :  com.anssy.inter.activity.action
 * CREATE DATE  :  2016-8-12
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.inter.activity.action;

import com.anssy.venturebar.base.entity.ActivityTypeAllEntity;
import com.anssy.venturebar.base.entity.SiteTypeAllEntity;
import com.anssy.venturebar.base.entity.SiteTypeEntity;
import com.google.gson.Gson;
import com.anssy.venturebar.activity.entity.ActivityInfoEntity;
import com.anssy.venturebar.activity.entity.ActivityItemEntity;
import com.anssy.venturebar.activity.entity.ActivityStaffEntity;
import com.anssy.venturebar.base.entity.ActivityTypeEntity;
import com.anssy.inter.AppAction;
import com.anssy.inter.activity.server.ActivityServer;
import com.anssy.inter.activity.vo.ActivitIdVo;
import com.anssy.inter.activity.vo.ActivityVo;
import com.anssy.inter.activity.vo.SearchVo;
import com.anssy.inter.activity.vo.TypeVo;
import com.anssy.webcore.vo.MyCollectVo;
import com.anssy.webcore.vo.PublishVo;
import com.anssy.webcore.vo.ReferralsVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author make it
 * @version SVN #V1# #2016-8-12#
 *          接口_活动
 */
@Controller
@RequestMapping("/app/activity/activityAction")
public class ActivityAction extends AppAction {

    @Resource
    private ActivityServer activityServer;

    /**
     * 获取活动类型(一级/二级)
     * <p/>
     * request  {"activityLevel":"","fatherId":""}
     */
    @RequestMapping(value = "/findActivityType", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> findActivityType(HttpServletRequest request) throws Exception {
        Map<String, Object> map;
        TypeVo vo = new Gson().fromJson(getJson(request), TypeVo.class);
        if (vo != null && vo.getFatherId() != null) {
            map = writeResultRep();
            List<ActivityTypeEntity> types = activityServer.findActivityType(vo);
            map.put("ActivityType", types);
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

    /**
     * 获取活动类型(全部)
     */
    @RequestMapping(value = "/findActivityTypeAll", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> findActivityTypeAll() throws Exception {
        Map<String, Object> map = writeResultRep();

//        ActivityTypeAllEntity defallType = new ActivityTypeAllEntity();
//        defallType.setId(0L);
//        defallType.setFatherId(0L);
//        defallType.setActivityLevel(1);
//        defallType.setActivityName("全部");
//
//        ArrayList<ActivityTypeEntity> defsublist = new ArrayList<ActivityTypeEntity>();
//        ActivityTypeEntity defsubType = new ActivityTypeEntity();
//        defsubType.setId(0L);
//        defsubType.setFatherId(0L);
//        defsubType.setActivityLevel(2);
//        defsubType.setActivityName("全部类型");
//        defsublist.add(defsubType);
//        defallType.setSubType(defsublist);

//        ArrayList<ActivityTypeAllEntity> typelist = new ArrayList<ActivityTypeAllEntity>();
//        // 添加默认项
//        typelist.add(defallType);

        List<ActivityTypeEntity> types = activityServer.findActivityTypeAll();
//        for (ActivityTypeEntity type : types) {
//            if (type.getActivityLevel() == 1) {
//                // 数据转移到输出对象
//                ActivityTypeAllEntity allType = new ActivityTypeAllEntity();
//                allType.setId(type.getId());
//                allType.setActivityLevel(type.getActivityLevel());
//                allType.setActivityName(type.getActivityName());
//                allType.setFatherId(type.getFatherId());
//
//                ArrayList<ActivityTypeEntity> subtypelist = new ArrayList<ActivityTypeEntity>();
//                ActivityTypeEntity subType = new ActivityTypeEntity();
//                // 为子列表添加一项默认的选项 (全部)
//                subType.setId(type.getId());
//                subType.setFatherId(0L);
//                subType.setActivityLevel(2);
//                subType.setActivityName("全部" + type.getActivityName());
//                subtypelist.add(subType);
//                for (ActivityTypeEntity type1 : types) {
//                    if (type1.getActivityLevel() == 2 && type.getId() == type1.getFatherId()) {
//                        subtypelist.add(type1);
//                    }
//                }
//                allType.setSubType(subtypelist);
//                typelist.add(allType);
//            }
//        }
        map.put("ActivityType", types);
        return map;
    }


    /**
     * 发布活动信息接口
     * <p/>
     * request {"image":"","activityName":"","activityType":"","sponsor":"","beginTime":"","endTime":"","provinceId":"","cityId":"","areaId":"","describe":"","activitySite":"","upperNumber":"","linkman":"","phone":""}
     */
    @RequestMapping(value = "/releaseActivity", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> releaseActivity(HttpServletRequest request) throws Exception {
        Long userId = Long.parseLong(request.getHeader("userId"));
        Map<String, Object> map;
        System.out.println("====================6789454==============");
        ActivityVo vo = new Gson().fromJson(getJson(request), ActivityVo.class);
        System.out.println("====================6789455==============");
        if (vo != null && vo.getActivityType() != null && StringUtils.isNotBlank(vo.getActivityName()) &&
                vo.getBeginTime() != null && vo.getEndTime() != null &&
                vo.getProvinceId() != null && vo.getCityId() != null &&
                vo.getAreaId() != null && StringUtils.isNotBlank(vo.getActivitySite()) &&
                StringUtils.isNotBlank(vo.getLinkman()) &&
                StringUtils.isNotBlank(vo.getPhone())) {
            System.out.println("====================6789456==============");
            boolean flag = activityServer.releaseActivity(vo, userId);
            System.out.println("====================67894567==============");
            if (flag) {
                System.out.println("====================67894577==============");
                map = writeResultRep();
            } else {
                map = writeResultRep(10001);//发布活动信息失败
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

    /**
     * 修改活动信息接口
     * <p/>
     * request {"id":"","image":"","activityName":"","activityType":"","sponsor":"","beginTime":"","endTime":"","provinceId":"","cityId":"","areaId":"","describe":"","activitySite":"","upperNumber":"","linkman":"","phone":""}
     */
    @RequestMapping(value = "/updateActivity", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> updateActivity(HttpServletRequest request) throws Exception {
        Long userId = Long.parseLong(request.getHeader("userId"));
        Map<String, Object> map;
        ActivityVo vo = new Gson().fromJson(getJson(request), ActivityVo.class);
        if (vo != null && vo.getId() != null && vo.getActivityType() != null && StringUtils.isNotBlank(vo.getActivityName()) &&
                vo.getBeginTime() != null && vo.getEndTime() != null &&
                vo.getProvinceId() != null && vo.getCityId() != null &&
                vo.getAreaId() != null && StringUtils.isNotBlank(vo.getActivitySite()) &&
                StringUtils.isNotBlank(vo.getLinkman()) &&
                StringUtils.isNotBlank(vo.getPhone())) {
            ActivityInfoEntity info = activityServer.findActivityInfoById(vo.getId());
            ActivityItemEntity item = activityServer.findActivityItem(vo.getId());
            if (info != null && item != null) {
                if (item.getPublishId().equals(userId)) {
                    boolean flag = activityServer.updateActivity(vo);
                    if (flag) {
                        map = writeResultRep();
                    } else {
                        map = writeResultRep(10010);//修改活动信息失败
                    }
                } else {
                    map = writeResultRep(1004);//userId不匹配
                }
            } else {
                map = writeResultRep(10003);//活动信息不存在
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }


    /**
     * 活动信息列表接口
     * <p/>
     * request {"listType":"1","page":"","longitude":"","latitude":"","search":""}
     * request {"listType":"2","page":"","activityType":"","provinceId":"0","cityId":"0","areaId":"0","search":""}
     * request {"listType":"3","page":"","capacityType":"","provinceId":"0","cityId":"0","areaId":"0","search":""}
     */
    @RequestMapping(value = "/activityList", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> activityList(HttpServletRequest request) throws Exception {
        Map<String, Object> map;
        SearchVo vo = new Gson().fromJson(getJson(request), SearchVo.class);
        if (vo != null) {
            //搜索方式(1-按GPS进行附近搜索,2-按类型进行场地类型搜索,3-智能搜索)
            if (vo.getListType() == 1 && StringUtils.isNotBlank(vo.getLatitude())
                    && StringUtils.isNotBlank(vo.getLongitude())) {
                map = writeResultRep();
                List<ActivityInfoEntity> activitys = activityServer.findListByGPS(vo);
                map.put("activitys", activitys);
                if (!activitys.isEmpty()) {
                    map.put("count", activitys.size());
                } else {
                    map.put("count", 0);
                }
            } else if (vo.getListType() == 2 && vo.getActivityType() > 0) {
                map = writeResultRep();
                List<ActivityInfoEntity> activitys = activityServer.findListByType(vo);
                map.put("activitys", activitys);
                if (!activitys.isEmpty()) {
                    map.put("count", activitys.size());
                } else {
                    map.put("count", 0);
                }
            } else if (vo.getListType() == 3) {
                map = writeResultRep();
                List<ActivityInfoEntity> activitys = activityServer.findListByPv(vo);
                map.put("activitys", activitys);
                if (!activitys.isEmpty()) {
                    map.put("count", activitys.size());
                } else {
                    map.put("count", 0);
                }
            } else {
                map = writeResultRep(1003);//参数异常
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

    /**
     * 推荐活动信息接口
     * <p/>
     * request {"provinceId":"","cityId":"","areaId":"","count":""}
     */
    @RequestMapping(value = "/referrals", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> referrals(HttpServletRequest request)
            throws Exception {
        Map<String, Object> map;
        ReferralsVo vo = new Gson().fromJson(getJson(request), ReferralsVo.class);
        if (vo != null) {
            map = writeResultRep();
            List<ActivityInfoEntity> activitys = activityServer.referrals(vo);
            map.put("activitys", activitys);
            if (!activitys.isEmpty()) {
                map.put("count", activitys.size());
            } else {
                map.put("count", 0);
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

    /**
     * 查看活动信息接口
     * <p/>
     * request {"activityId":""} activityId 活动ID
     */
    @RequestMapping(value = "/seeActivity", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> seeActivity(HttpServletRequest request) throws Exception {
        Map<String, Object> map;
        ActivitIdVo vo = new Gson().fromJson(getJson(request), ActivitIdVo.class);
        if (vo != null && vo.getActivityId() != null) {
            activityServer.updatePV(vo.getActivityId());
            ActivityInfoEntity info = activityServer.replenish(activityServer.findActivityInfoById(vo.getActivityId()));
            ActivityItemEntity item = activityServer.replenish(activityServer.findActivityItem(vo.getActivityId()));
            if (info != null && item != null) {
                map = writeResultRep();
                map.put("info", info);
                map.put("item", item);
            } else {
                map = writeResultRep(10002);//查询活动信息失败
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

    /**
     * 活动信息点赞接口
     * <p/>
     * request {"activityId":""} activityId 活动ID
     */
    @RequestMapping(value = "/praise", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> praise(HttpServletRequest request)
            throws Exception {
        Map<String, Object> map;
        ActivitIdVo vo = new Gson().fromJson(getJson(request), ActivitIdVo.class);
        if (vo != null && vo.getActivityId() != null) {
            ActivityInfoEntity info = activityServer.findActivityInfoById(vo.getActivityId());
            if (info != null) {
                if (activityServer.updatePraise(vo.getActivityId()) > 0) {
                    map = writeResultRep();
                } else {
                    map = writeResultRep(10005);//活动信息点赞失败
                }
            } else {
                map = writeResultRep(10003);//活动信息不存在
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

    /**
     * 活动信息权重接口
     * <p/>
     * request {"activityId":""} activityId 活动ID
     */
    @RequestMapping(value = "/weight", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> weight(HttpServletRequest request)
            throws Exception {
        Long userId = Long.parseLong(request.getHeader("userId"));
        Map<String, Object> map;
        ActivitIdVo vo = new Gson().fromJson(getJson(request), ActivitIdVo.class);
        if (vo != null && vo.getActivityId() != null) {
            ActivityInfoEntity info = activityServer.findActivityInfoById(vo.getActivityId());
            ActivityItemEntity item = activityServer.findActivityItem(vo.getActivityId());
            if (info != null && item != null) {
                if (userId.equals(item.getPublishId())) {
                    if (activityServer.updateWeight(vo.getActivityId()) > 0) {
                        map = writeResultRep();
                    } else {
                        map = writeResultRep(50);//修改权重失败
                    }
                } else {
                    map = writeResultRep(1004);//userId不匹配
                }
            } else {
                map = writeResultRep(10003);//活动信息不存在
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

    /**
     * 删除活动信息
     * <p/>
     * request {"activityId":""} activityId 活动ID
     */
    @RequestMapping(value = "/deleteActivity", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> deleteActivity(HttpServletRequest request)
            throws Exception {
        Long userId = Long.parseLong(request.getHeader("userId"));
        Map<String, Object> map;
        ActivitIdVo vo = new Gson().fromJson(getJson(request), ActivitIdVo.class);
        if (vo != null && vo.getActivityId() != null) {
            ActivityItemEntity item = activityServer.findActivityItem(vo.getActivityId());
            if (item != null) {
                if (item.getPublishId().equals(userId)) {
                    if (activityServer.deleteActivity(vo.getActivityId())) {
                        map = writeResultRep();
                    } else {
                        map = writeResultRep(10004);//删除活动信息失败
                    }
                } else {
                    map = writeResultRep(1004);//userId不匹配
                }
            } else {
                map = writeResultRep(10003);//活动信息不存在
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

    /**
     * 参与活动接口
     * <p/>
     * request {"activityId":""} activityId 活动ID
     */
    @RequestMapping(value = "/joinStaffs", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> joinStaffs(HttpServletRequest request) throws Exception {
        Long userId = Long.parseLong(request.getHeader("userId"));
        Map<String, Object> map;
        ActivitIdVo vo = new Gson().fromJson(getJson(request), ActivitIdVo.class);
        if (vo != null && vo.getActivityId() != null) {
            ActivityInfoEntity info = activityServer.findActivityInfoById(vo.getActivityId());
            if (info != null) {
                int mark = activityServer.joinStaffs(vo.getActivityId(), userId);
                if (mark > 0) {
                    map = writeResultRep();
                    activityServer.inform(vo.getActivityId(), userId);
                } else if (mark == 0) {
                    map = writeResultRep(10006);//参与活动失败
                } else {
                    map = writeResultRep(10008);//您已参与此活动
                }
            } else {
                map = writeResultRep(10003);//活动信息不存在
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

    /**
     * 关注活动接口
     * <p/>
     * request {"activityId":""} activityId 活动ID
     */
    @RequestMapping(value = "/careStaffs", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> careStaffs(HttpServletRequest request) throws Exception {
        Long userId = Long.parseLong(request.getHeader("userId"));
        Map<String, Object> map;
        ActivitIdVo vo = new Gson().fromJson(getJson(request), ActivitIdVo.class);
        if (vo != null && vo.getActivityId() != null) {
            ActivityInfoEntity info = activityServer.findActivityInfoById(vo.getActivityId());
            if (info != null) {
                int mark = activityServer.careStaffs(vo.getActivityId(), userId);
                if (mark > 0) {
                    map = writeResultRep();
                } else if (mark == 0) {
                    map = writeResultRep(10007);//关注活动失败
                } else {
                    map = writeResultRep(10009);//您已关注此活动
                }
            } else {
                map = writeResultRep(10003);//活动信息不存在
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }


    /**
     * 查询参与活动人员接口
     * <p/>
     * request {"activityId":""} activityId 活动ID
     */
    @RequestMapping(value = "/findJoinStaffs", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> findJoinStaffs(HttpServletRequest request) throws Exception {
        Map<String, Object> map;
        ActivitIdVo vo = new Gson().fromJson(getJson(request), ActivitIdVo.class);
        if (vo != null && vo.getActivityId() != null) {
            ActivityInfoEntity info = activityServer.findActivityInfoById(vo.getActivityId());
            if (info != null) {
                map = writeResultRep();
                List<ActivityStaffEntity> staffs = activityServer.replenishStaff(activityServer.findJoinStaffs(vo.getActivityId()));
                map.put("staffs", staffs);
                if (!staffs.isEmpty()) {
                    map.put("count", staffs.size());
                } else {
                    map.put("count", 0);
                }
            } else {
                map = writeResultRep(10003);//活动信息不存在
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

    /**
     * 查询关注活动人员接口
     * <p/>
     * request {"activityId":""} activityId 活动ID
     */
    @RequestMapping(value = "/findCareStaffs", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> findCareStaffs(HttpServletRequest request) throws Exception {
        Map<String, Object> map;
        ActivitIdVo vo = new Gson().fromJson(getJson(request), ActivitIdVo.class);
        if (vo != null && vo.getActivityId() != null) {
            ActivityInfoEntity info = activityServer.findActivityInfoById(vo.getActivityId());
            if (info != null) {
                map = writeResultRep();
                List<ActivityStaffEntity> staffs = activityServer.replenishStaff(activityServer.findCareStaffs(vo.getActivityId()));
                map.put("staffs", staffs);
                if (!staffs.isEmpty()) {
                    map.put("count", staffs.size());
                } else {
                    map.put("count", 0);
                }
            } else {
                map = writeResultRep(10003);//活动信息不存在
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

    /**
     * 我的发布信息
     * <p/>
     * request {"page":"","search":""}
     */
    @RequestMapping(value = "/findMyPublish", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> findMyPublish(HttpServletRequest request) throws Exception {
        Long userId = Long.parseLong(request.getHeader("userId"));
        Map<String, Object> map;
        PublishVo vo = new Gson().fromJson(getJson(request), PublishVo.class);
        if (vo != null) {
            map = writeResultRep();
            List<ActivityInfoEntity> activitys = activityServer.findMyPublish(vo, userId);
            map.put("activitys", activitys);
            if (!activitys.isEmpty()) {
                map.put("count", activitys.size());
            } else {
                map.put("count", 0);
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

    /**
     * 我的收藏信息
     * <p/>
     * request {"page":"","type":"","search":""}
     */
    @RequestMapping(value = "/findCollect", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> findCollect(HttpServletRequest request) throws Exception {
        Long userId = Long.parseLong(request.getHeader("userId"));
        Map<String, Object> map;
        MyCollectVo vo = new Gson().fromJson(getJson(request), MyCollectVo.class);
        if (vo != null) {
            map = writeResultRep();
            List<ActivityInfoEntity> activitys = activityServer.findCollect(vo, userId);
            map.put("activitys", activitys);
            if (!activitys.isEmpty()) {
                map.put("count", activitys.size());
            } else {
                map.put("count", 0);
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }
}