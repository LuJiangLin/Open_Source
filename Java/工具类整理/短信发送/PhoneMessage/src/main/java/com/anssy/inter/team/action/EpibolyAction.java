/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  EpibolyAction.java
 * PACKAGE      :  com.anssy.inter.team.action
 * CREATE DATE  :  2016-8-12
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.inter.team.action;

import com.google.gson.Gson;
import com.anssy.venturebar.team.entity.EpibolyInfoEntity;
import com.anssy.venturebar.team.entity.EpibolyItemEntity;
import com.anssy.inter.AppAction;
import com.anssy.inter.team.server.EpibolyServer;
import com.anssy.inter.team.vo.EpibolyIdVo;
import com.anssy.inter.team.vo.EpibolyVo;
import com.anssy.inter.team.vo.SearchVo;
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
 *          接口_众包
 */
@Controller
@RequestMapping("/app/team/epibolyAction")
public class EpibolyAction extends AppAction {


    @Resource
    private EpibolyServer epibolyServer;

    /**
     * 发布众包信息接口
     *
     * @param {"title":"","field":"","provinceId":"","cityId":"","areaId":"","describe":"","linkman":"","phone":""}
     */
    @RequestMapping(value = "/releaseEpiboly", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> releaseEpiboly(HttpServletRequest request) throws Exception {
        Long userId = Long.parseLong(request.getHeader("userId"));
        Map<String, Object> map;
        EpibolyVo vo = new Gson().fromJson(getJson(request), EpibolyVo.class);
        if (vo != null && StringUtils.isNotBlank(vo.getTitle()) &&
                StringUtils.isNotBlank(vo.getField()) &&
                vo.getProvinceId() != null && vo.getCityId() != null &&
                vo.getAreaId() != null &&
                StringUtils.isNotBlank(vo.getDescribe()) &&
                StringUtils.isNotBlank(vo.getLinkman()) &&
                StringUtils.isNotBlank(vo.getPhone())) {
            boolean flag = epibolyServer.releaseEpiboly(vo, userId);
            if (flag) {
                map = writeResultRep();
            } else {
                map = writeResultRep(6001);//发布众包信息失败
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

    /**
     * 修改众包信息接口
     *
     * @param {"id":"","title":"","field":"","provinceId":"","cityId":"","areaId":"","describe":"","linkman":"","phone":""}
     */
    @RequestMapping(value = "/updateEpiboly", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> updateEpiboly(HttpServletRequest request) throws Exception {
        Long userId = Long.parseLong(request.getHeader("userId"));
        Map<String, Object> map;
        EpibolyVo vo = new Gson().fromJson(getJson(request), EpibolyVo.class);
        if (vo != null && vo.getId() != null && StringUtils.isNotBlank(vo.getTitle()) &&
                StringUtils.isNotBlank(vo.getField()) &&
                vo.getProvinceId() != null && vo.getCityId() != null &&
                vo.getAreaId() != null &&
                StringUtils.isNotBlank(vo.getDescribe()) &&
                StringUtils.isNotBlank(vo.getLinkman()) &&
                StringUtils.isNotBlank(vo.getPhone())) {
            EpibolyInfoEntity info = epibolyServer.findEpibolyInfoById(vo.getId());
            EpibolyItemEntity item = epibolyServer.findEpibolyItem(vo.getId());
            if (info != null && item != null) {
                if (item.getPublishId().equals(userId)) {
                    boolean flag = epibolyServer.updateEpiboly(vo);
                    if (flag) {
                        map = writeResultRep();
                    } else {
                        map = writeResultRep(6006);//修改众包信息失败
                    }
                } else {
                    map = writeResultRep(1004);//userId不匹配
                }
            } else {
                map = writeResultRep(6003);//众包信息不存在
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }


    /**
     * 众包信息列表接口
     *
     * @param {"listType":"1","page":"","provinceId":"","cityId":"","areaId":"","search":""}
     * @param {"listType":"2","page":"","provinceId":"0","cityId":"0","areaId":"0","field":"","search":""}
     * @param {"listType":"3","page":"","capacityType":"","provinceId":"0","cityId":"0","areaId":"0","search":""}
     */
    @RequestMapping(value = "/epibolyList", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> epibolyList(HttpServletRequest request) throws Exception {
        Map<String, Object> map;
        SearchVo vo = new Gson().fromJson(getJson(request), SearchVo.class);
        if (vo != null) {
            //搜索方式(1-附近搜索,2-行业搜索,3-智能搜索)
            if (vo.getListType() == 1 && vo.getProvinceId() != null && vo.getProvinceId() > 0) {
                map = writeResultRep();
                List<EpibolyInfoEntity> epibolys = epibolyServer.findEpibolyByNearby(vo);
                map.put("epibolys", epibolys);
                if (!epibolys.isEmpty()) {
                    map.put("count", epibolys.size());
                } else {
                    map.put("count", 0);
                }
            } else if (vo.getListType() == 2 && vo.getField() != null) {
                map = writeResultRep();
                List<EpibolyInfoEntity> epibolys = epibolyServer.findEpibolyByField(vo);
                map.put("epibolys", epibolys);
                if (!epibolys.isEmpty()) {
                    map.put("count", epibolys.size());
                } else {
                    map.put("count", 0);
                }
            } else if (vo.getListType() == 3) {
                map = writeResultRep();
                List<EpibolyInfoEntity> epibolys = epibolyServer.findEpibolyByPv(vo);
                map.put("epibolys", epibolys);
                if (!epibolys.isEmpty()) {
                    map.put("count", epibolys.size());
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
     * 推荐众包信息接口
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
            List<EpibolyInfoEntity> epibolys = epibolyServer.referrals(vo);
            map.put("epibolys", epibolys);
            if (!epibolys.isEmpty()) {
                map.put("count", epibolys.size());
            } else {
                map.put("count", 0);
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

    /**
     * 查看众包信息接口
     *
     * @param {"epibolyId":""} epibolyId 众包ID
     */
    @RequestMapping(value = "/seeEpiboly", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> seeEpiboly(HttpServletRequest request) throws Exception {
        Map<String, Object> map;
        EpibolyIdVo vo = new Gson().fromJson(getJson(request), EpibolyIdVo.class);
        if (vo != null && vo.getEpibolyId() != null) {
            epibolyServer.updatePV(vo.getEpibolyId());
            EpibolyInfoEntity info = epibolyServer.replenish(epibolyServer.findEpibolyInfoById(vo.getEpibolyId()));
            EpibolyItemEntity item = epibolyServer.replenish(epibolyServer.findEpibolyItem(vo.getEpibolyId()));
            if (info != null && item != null) {
                map = writeResultRep();
                map.put("info", info);
                map.put("item", item);
            } else {
                map = writeResultRep(6002);//查询众包信息失败
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

    /**
     * 众包信息点赞接口
     *
     * @param {"epibolyId":""} epibolyId 众包ID
     */
    @RequestMapping(value = "/praise", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> praise(HttpServletRequest request) throws Exception {
        Map<String, Object> map;
        EpibolyIdVo vo = new Gson().fromJson(getJson(request), EpibolyIdVo.class);
        if (vo != null && vo.getEpibolyId() != null) {
            EpibolyInfoEntity info = epibolyServer.findEpibolyInfoById(vo.getEpibolyId());
            if (info != null) {
                if (epibolyServer.updatePraise(vo.getEpibolyId()) > 0) {
                    map = writeResultRep();
                } else {
                    map = writeResultRep(6005);//众包信息点赞失败
                }
            } else {
                map = writeResultRep(6003);//众包信息不存在
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

    /**
     * 众包信息权重接口
     *
     * @param {"epibolyId":""} epibolyId 众包ID
     */
    @RequestMapping(value = "/weight", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> weight(HttpServletRequest request) throws Exception {
        Long userId = Long.parseLong(request.getHeader("userId"));
        Map<String, Object> map;
        EpibolyIdVo vo = new Gson().fromJson(getJson(request), EpibolyIdVo.class);
        if (vo != null && vo.getEpibolyId() != null) {
            EpibolyInfoEntity info = epibolyServer.findEpibolyInfoById(vo.getEpibolyId());
            EpibolyItemEntity item = epibolyServer.findEpibolyItem(vo.getEpibolyId());
            if (info != null && item != null) {
                if (item.getPublishId().equals(userId)) {
                    if (epibolyServer.updateWeight(vo.getEpibolyId()) > 0) {
                        map = writeResultRep();
                    } else {
                        map = writeResultRep(50);//修改权重失败
                    }
                } else {
                    map = writeResultRep(1004);//userId不匹配
                }
            } else {
                map = writeResultRep(6003);//众包信息不存在
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

    /**
     * 删除众包信息接口
     *
     * @param {"epibolyId":""} epibolyId 众包ID
     */
    @RequestMapping(value = "/deleteEpiboly", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> deleteEpiboly(HttpServletRequest request) throws Exception {
        Long userId = Long.parseLong(request.getHeader("userId"));
        Map<String, Object> map;
        EpibolyIdVo vo = new Gson().fromJson(getJson(request), EpibolyIdVo.class);
        if (vo != null && vo.getEpibolyId() != null) {
            EpibolyItemEntity item = epibolyServer.findEpibolyItem(vo.getEpibolyId());
            if (item != null) {
                if (item.getPublishId().equals(userId)) {
                    if (epibolyServer.deleteEpiboly(vo.getEpibolyId())) {
                        map = writeResultRep();
                    } else {
                        map = writeResultRep(6004);//删除众包信息失败
                    }
                } else {
                    map = writeResultRep(1004);//userId不匹配
                }
            } else {
                map = writeResultRep(6003);//众包信息不存在
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
            List<EpibolyInfoEntity> epibolys = epibolyServer.findMyPublish(vo, userId);
            map.put("epibolys", epibolys);
            if (!epibolys.isEmpty()) {
                map.put("count", epibolys.size());
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
            List<EpibolyInfoEntity> epibolys = epibolyServer.findCollect(vo, userId);
            map.put("epibolys", epibolys);
            if (!epibolys.isEmpty()) {
                map.put("count", epibolys.size());
            } else {
                map.put("count", 0);
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }


}