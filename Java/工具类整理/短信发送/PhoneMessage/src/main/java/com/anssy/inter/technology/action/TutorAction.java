/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  TutorAction.java
 * PACKAGE      :  com.anssy.inter.technology.action
 * CREATE DATE  :  2016-8-12
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.inter.technology.action;

import com.google.gson.Gson;
import com.anssy.venturebar.technology.entity.TutorInfoEntity;
import com.anssy.venturebar.technology.entity.TutorItemEntity;
import com.anssy.inter.AppAction;
import com.anssy.inter.technology.server.TutorServer;
import com.anssy.inter.technology.vo.SearchVo;
import com.anssy.inter.technology.vo.TutorIdVo;
import com.anssy.inter.technology.vo.TutorVo;
import com.anssy.webcore.vo.MyCollectVo;
import com.anssy.webcore.vo.PublishVo;
import com.anssy.webcore.vo.ReferralsVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author make it
 * @version SVN #V1# #2016-8-12#
 *          接口_导师
 */
@Controller
@RequestMapping("/app/technology/tutorAction")
public class TutorAction extends AppAction {

    @Resource
    private TutorServer tutorServer;

    /**
     * 发布导师信息接口
     *
     * @param {"headImage":"","name":"","sex":"","title":"","post":"","label":"","provinceId":"","cityId":"","areaId":"","field":"","organization":"","age":"","bgInfo":"","url":"","linkman":"","phone":"","email":"","organizationSite":"","will":"","cooperation":""}
     */
    @RequestMapping(value = "/releaseTutor", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> releaseTutor(HttpServletRequest request) throws Exception {
        Long userId = Long.parseLong(request.getHeader("userId"));
        Map<String, Object> map;
        TutorVo vo = new Gson().fromJson(getJson(request), TutorVo.class);
        if (vo != null && StringUtils.isNotBlank(vo.getName()) &&
                vo.getProvinceId() != null && vo.getCityId() != null &&
                vo.getAreaId() != null &&
                StringUtils.isNotBlank(vo.getField()) &&
                StringUtils.isNotBlank(vo.getLinkman()) &&
                StringUtils.isNotBlank(vo.getPhone())) {
            boolean flag = tutorServer.releaseTutor(vo, userId);
            if (flag) {
                map = writeResultRep();
            } else {
                map = writeResultRep(8001);//发布导师信息失败
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

    /**
     * 修改导师信息接口
     *
     * @param {"id":"","headImage":"","name":"","sex":"","title":"","post":"","label":"","provinceId":"","cityId":"","areaId":"","field":"","organization":"","age":"","bgInfo":"","url":"","linkman":"","phone":"","email":"","organizationSite":"","will":"","cooperation":""}
     */
    @RequestMapping(value = "/updateTutor", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> updateTutor(HttpServletRequest request) throws Exception {
        Long userId = Long.parseLong(request.getHeader("userId"));
        Map<String, Object> map;
        TutorVo vo = new Gson().fromJson(getJson(request), TutorVo.class);
        if (vo != null && vo.getId() != null && StringUtils.isNotBlank(vo.getName()) &&
                vo.getProvinceId() != null && vo.getCityId() != null &&
                vo.getAreaId() != null &&
                StringUtils.isNotBlank(vo.getField()) &&
                StringUtils.isNotBlank(vo.getLinkman()) &&
                StringUtils.isNotBlank(vo.getPhone())) {
            TutorInfoEntity info = tutorServer.findTutorInfoById(vo.getId());
            TutorItemEntity item = tutorServer.findTutorItem(vo.getId());
            if (info != null && item != null) {
                if (item.getPublishId().equals(userId)) {
                    boolean flag = tutorServer.updateTutor(vo);
                    if (flag) {
                        map = writeResultRep();
                    } else {
                        map = writeResultRep(8006);//修改导师信息失败
                    }
                } else {
                    map = writeResultRep(1004);//userId不匹配
                }
            } else {
                map = writeResultRep(8003);//导师信息不存在
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }


    /**
     * 导师信息列表接口
     *
     * @param {"listType":"1","page":"","longitude":"","latitude":"","search":""}
     * @param {"listType":"2","page":"","provinceId":"0","cityId":"0","areaId":"0","field":"","search":""}
     * @param {"listType":"3","page":"","capacityType":"","provinceId":"0","cityId":"0","areaId":"0","search":""}
     */
    @RequestMapping(value = "/tutorList", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> tutorList(HttpServletRequest request) throws Exception {
        Map<String, Object> map;
        SearchVo vo = new Gson().fromJson(getJson(request), SearchVo.class);
        if (vo != null) {
            //搜索方式(1-附近搜索,2-行业搜索,3-智能搜索)
            if (vo.getListType() == 1 && StringUtils.isNotBlank(vo.getLatitude()) && StringUtils.isNotBlank(vo.getLongitude())) {
                map = writeResultRep();
                List<TutorInfoEntity> tutor = tutorServer.findTutorByGPS(vo);
                map.put("tutor", tutor);
                if (!tutor.isEmpty()) {
                    map.put("count", tutor.size());
                } else {
                    map.put("count", 0);
                }
            } else if (vo.getListType() == 2 && vo.getField() != null) {
                map = writeResultRep();
                List<TutorInfoEntity> tutor = tutorServer.findTutorByField(vo);
                map.put("tutor", tutor);
                if (!tutor.isEmpty()) {
                    map.put("count", tutor.size());
                } else {
                    map.put("count", 0);
                }
            } else if (vo.getListType() == 3) {
                map = writeResultRep();
                List<TutorInfoEntity> tutor = tutorServer.findTutorByPv(vo);
                map.put("tutor", tutor);
                if (!tutor.isEmpty()) {
                    map.put("count", tutor.size());
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
     * 推荐导师信息接口
     *
     * @param {"provinceId":"","cityId":"","areaId":"","count":""}
     */
    @RequestMapping(value = "/referrals", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> referrals(HttpServletRequest request) throws Exception {
        Map<String, Object> map;
        ReferralsVo vo = new Gson().fromJson(getJson(request), ReferralsVo.class);
        if (vo != null) {
            map = writeResultRep();
            List<TutorInfoEntity> tutor = tutorServer.referrals(vo);
            map.put("tutor", tutor);
            if (!tutor.isEmpty()) {
                map.put("count", tutor.size());
            } else {
                map.put("count", 0);
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

    /**
     * 查看导师信息接口
     *
     * @param {"tutorId":""} tutorId 导师ID
     */
    @RequestMapping(value = "/seeTutor", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> seeTutor(HttpServletRequest request) throws Exception {
        Map<String, Object> map;
        TutorIdVo vo = new Gson().fromJson(getJson(request), TutorIdVo.class);
        if (vo != null && vo.getTutorId() != null) {
            tutorServer.updatePV(vo.getTutorId());
            TutorInfoEntity info = tutorServer.replenish(tutorServer.findTutorInfoById(vo.getTutorId()));
            TutorItemEntity item = tutorServer.replenish(tutorServer.findTutorItem(vo.getTutorId()));
            if (info != null && item != null) {
                map = writeResultRep();
                map.put("info", info);
                map.put("item", item);
            } else {
                map = writeResultRep(8002);//查询导师信息失败
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

    /**
     * 导师信息点赞接口
     *
     * @param {"tutorId":""} tutorId 导师ID
     */
    @RequestMapping(value = "/praise", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> praise(HttpServletRequest request) throws Exception {
        Map<String, Object> map;
        TutorIdVo vo = new Gson().fromJson(getJson(request), TutorIdVo.class);
        if (vo != null && vo.getTutorId() != null) {
            TutorInfoEntity info = tutorServer.findTutorInfoById(vo.getTutorId());
            if (info != null) {
                if (tutorServer.updatePraise(vo.getTutorId()) > 0) {
                    map = writeResultRep();
                } else {
                    map = writeResultRep(8005);//导师信息点赞失败
                }
            } else {
                map = writeResultRep(8003);//导师信息不存在
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

    /**
     * 导师信息权重接口
     *
     * @param {"tutorId":""} tutorId 导师ID
     */
    @RequestMapping(value = "/weight", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> weight(HttpServletRequest request) throws Exception {
        Long userId = Long.parseLong(request.getHeader("userId"));
        Map<String, Object> map;
        TutorIdVo vo = new Gson().fromJson(getJson(request), TutorIdVo.class);
        if (vo != null && vo.getTutorId() != null) {
            TutorInfoEntity info = tutorServer.findTutorInfoById(vo.getTutorId());
            TutorItemEntity item = tutorServer.findTutorItem(vo.getTutorId());
            if (info != null && item != null) {
                if (item.getPublishId().equals(userId)) {
                    if (tutorServer.updateWeight(vo.getTutorId()) > 0) {
                        map = writeResultRep();
                    } else {
                        map = writeResultRep(50);//修改权重失败
                    }
                } else {
                    map = writeResultRep(1004);//userId不匹配
                }
            } else {
                map = writeResultRep(8003);//导师信息不存在
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

    /**
     * 删除导师信息接口
     *
     * @param {"tutorId":""} tutorId 导师ID
     */
    @RequestMapping(value = "/deleteTutor", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> deleteTutor(HttpServletRequest request) throws Exception {
        Long userId = Long.parseLong(request.getHeader("userId"));
        Map<String, Object> map;
        TutorIdVo vo = new Gson().fromJson(getJson(request), TutorIdVo.class);
        if (vo != null && vo.getTutorId() != null) {
            TutorItemEntity item = tutorServer.findTutorItem(vo.getTutorId());
            if (item != null) {
                if (item.getPublishId().equals(userId)) {
                    if (tutorServer.deleteTutor(vo.getTutorId())) {
                        map = writeResultRep();
                    } else {
                        map = writeResultRep(8004);//删除导师信息失败
                    }
                } else {
                    map = writeResultRep(1004);//userId不匹配
                }
            } else {
                map = writeResultRep(8003);//导师信息不存在
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

    /**
     * 我的发布信息
     *
     * @param {"page":"","search":""}
     */
    @RequestMapping(value = "/findMyPublish", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> findMyPublish(HttpServletRequest request) throws Exception {
        Long userId = Long.parseLong(request.getHeader("userId"));
        Map<String, Object> map;
        PublishVo vo = new Gson().fromJson(getJson(request), PublishVo.class);
        if (vo != null) {
            map = writeResultRep();
            List<TutorInfoEntity> tutor = tutorServer.findMyPublish(vo, userId);
            map.put("tutor", tutor);
            if (!tutor.isEmpty()) {
                map.put("count", tutor.size());
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
     *
     * @param {"page":"","type":"","search":""}
     */
    @RequestMapping(value = "/findCollect", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> findCollect(HttpServletRequest request) throws Exception {
        Long userId = Long.parseLong(request.getHeader("userId"));
        Map<String, Object> map;
        MyCollectVo vo = new Gson().fromJson(getJson(request), MyCollectVo.class);
        if (vo != null) {
            map = writeResultRep();
            List<TutorInfoEntity> tutor = tutorServer.findCollect(vo, userId);
            map.put("tutor", tutor);
            if (!tutor.isEmpty()) {
                map.put("count", tutor.size());
            } else {
                map.put("count", 0);
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

}