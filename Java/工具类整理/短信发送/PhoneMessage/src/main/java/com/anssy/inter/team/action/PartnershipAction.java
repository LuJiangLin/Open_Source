/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  PartnershipAction.java
 * PACKAGE      :  com.anssy.inter.team.action
 * CREATE DATE  :  2016-8-12
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.inter.team.action;

import com.google.gson.Gson;
import com.anssy.venturebar.base.vo.DatadictVo;
import com.anssy.venturebar.team.entity.PartnershipInfoEntity;
import com.anssy.venturebar.team.entity.PartnershipItemEntity;
import com.anssy.inter.AppAction;
import com.anssy.inter.team.server.PartnershipServer;
import com.anssy.inter.team.vo.PartnershipIdVo;
import com.anssy.inter.team.vo.PartnershipVo;
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
 *          接口_合伙
 */
@Controller
@RequestMapping("/app/team/partnershipAction")
public class PartnershipAction extends AppAction {

    @Resource
    private PartnershipServer partnershipServer;

    /**
     * 获取信息(合伙人要求/项目阶段/初始资金)
     */
    @RequestMapping(value = "/findDicInfo", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> findDicInfo() throws Exception {
        Map<String, Object> map = writeResultRep();
        List<DatadictVo> askFors = partnershipServer.findAskFor();
        List<DatadictVo> stages = partnershipServer.findStage();
        List<DatadictVo> funds = partnershipServer.findFund();
        map.put("askFors", askFors);
        map.put("stages", stages);
        map.put("funds", funds);
        return map;
    }


    /**
     * 发布合伙信息接口
     *
     * @param {"image":"","title":"","field":"","post":"","askFor":"","sketch":"","provinceId":"","cityId":"","areaId":"","describe":"","linkman":"","phone":"","direction":"","teamCase":"","site":"","lightspot":"","prospect":"","stage":"","fund":"","appURL":"","email":""}
     */
    @RequestMapping(value = "/releasePartnership", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> releasePartnership(HttpServletRequest request) throws Exception {
        Long userId = Long.parseLong(request.getHeader("userId"));
        Map<String, Object> map;
        PartnershipVo vo = new Gson().fromJson(getJson(request), PartnershipVo.class);
        if (vo != null && StringUtils.isNotBlank(vo.getTitle()) &&
                StringUtils.isNotBlank(vo.getField()) &&
                vo.getProvinceId() != null && vo.getCityId() != null &&
                vo.getAreaId() != null &&
                StringUtils.isNotBlank(vo.getDescribe()) &&
                StringUtils.isNotBlank(vo.getLinkman()) &&
                StringUtils.isNotBlank(vo.getPhone())) {
            boolean flag = partnershipServer.releasePartnership(vo, userId);
            if (flag) {
                map = writeResultRep();
            } else {
                map = writeResultRep(7001);//发布合伙信息失败
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

    /**
     * 修改合伙信息接口
     *
     * @param {"id":"","image":"","title":"","field":"","post":"","askFor":"","sketch":"","provinceId":"","cityId":"","areaId":"","describe":"","linkman":"","phone":"","direction":"","teamCase":"","site":"","lightspot":"","prospect":"","stage":"","fund":"","appURL":"","email":""}
     */
    @RequestMapping(value = "/updatePartnership", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> updatePartnership(HttpServletRequest request) throws Exception {
        Long userId = Long.parseLong(request.getHeader("userId"));
        Map<String, Object> map;
        PartnershipVo vo = new Gson().fromJson(getJson(request), PartnershipVo.class);
        if (vo != null && vo.getId() != null && StringUtils.isNotBlank(vo.getTitle()) &&
                StringUtils.isNotBlank(vo.getField()) &&
                vo.getProvinceId() != null && vo.getCityId() != null &&
                vo.getAreaId() != null &&
                StringUtils.isNotBlank(vo.getDescribe()) &&
                StringUtils.isNotBlank(vo.getLinkman()) &&
                StringUtils.isNotBlank(vo.getPhone())) {
            PartnershipInfoEntity info = partnershipServer.findPartnershipInfoById(vo.getId());
            PartnershipItemEntity item = partnershipServer.findPartnershipItem(vo.getId());
            if (info != null && item != null) {
                if (item.getPublishId().equals(userId)) {
                    boolean flag = partnershipServer.updatePartnership(vo);
                    if (flag) {
                        map = writeResultRep();
                    } else {
                        map = writeResultRep(7006);//修改合伙信息失败
                    }
                } else {
                    map = writeResultRep(1004);//userId不匹配
                }
            } else {
                map = writeResultRep(7003);//合伙信息不存在
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }


    /**
     * 合伙信息列表接口
     *
     * @param {"listType":"1","page":"","longitude":"","latitude":"","search":""}
     * @param {"listType":"2","page":"","provinceId":"0","cityId":"0","areaId":"0","field":"","search":""}
     * @param {"listType":"3","page":"","capacityType":"","provinceId":"0","cityId":"0","areaId":"0","search":""}
     */
    @RequestMapping(value = "/partnershipList", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> partnershipList(HttpServletRequest request) throws Exception {
        Map<String, Object> map;
        SearchVo vo = new Gson().fromJson(getJson(request), SearchVo.class);
        if (vo != null) {
            //搜索方式(1-附近搜索,2-行业搜索,3-智能搜索)
            if (vo.getListType() == 1 && StringUtils.isNotBlank(vo.getLatitude())
                    && StringUtils.isNotBlank(vo.getLongitude())) {
                map = writeResultRep();
                List<PartnershipInfoEntity> partnerships = partnershipServer.findPartnershipByGPS(vo);
                map.put("partnerships", partnerships);
                if (!partnerships.isEmpty()) {
                    map.put("count", partnerships.size());
                } else {
                    map.put("count", 0);
                }
            } else if (vo.getListType() == 2 && vo.getField() != null) {
                map = writeResultRep();
                List<PartnershipInfoEntity> partnerships = partnershipServer.findPartnershipByField(vo);
                map.put("partnerships", partnerships);
                if (!partnerships.isEmpty()) {
                    map.put("count", partnerships.size());
                } else {
                    map.put("count", 0);
                }
            } else if (vo.getListType() == 3) {
                map = writeResultRep();
                List<PartnershipInfoEntity> partnerships = partnershipServer.findPartnershipByPv(vo);
                map.put("partnerships", partnerships);
                if (!partnerships.isEmpty()) {
                    map.put("count", partnerships.size());
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
     * 推荐合伙信息接口
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
            List<PartnershipInfoEntity> partnerships = partnershipServer.referrals(vo);
            map.put("partnerships", partnerships);
            if (!partnerships.isEmpty()) {
                map.put("count", partnerships.size());
            } else {
                map.put("count", 0);
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

    /**
     * 查看合伙信息接口
     *
     * @param {"partnershipId":""} partnershipId 合伙ID
     */
    @RequestMapping(value = "/seePartnership", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> seePartnership(HttpServletRequest request) throws Exception {
        Map<String, Object> map;
        PartnershipIdVo vo = new Gson().fromJson(getJson(request), PartnershipIdVo.class);
        if (vo != null && vo.getPartnershipId() != null) {
            partnershipServer.updatePV(vo.getPartnershipId());
            PartnershipInfoEntity info = partnershipServer.replenish(partnershipServer.findPartnershipInfoById(vo.getPartnershipId()));
            PartnershipItemEntity item = partnershipServer.replenish(partnershipServer.findPartnershipItem(vo.getPartnershipId()));
            if (info != null && item != null) {
                map = writeResultRep();
                map.put("info", info);
                map.put("item", item);
            } else {
                map = writeResultRep(7002);//查询合伙信息失败
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

    /**
     * 合伙信息点赞接口
     *
     * @param {"partnershipId":""} partnershipId 合伙ID
     */
    @RequestMapping(value = "/praise", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> praise(HttpServletRequest request) throws Exception {
        Map<String, Object> map;
        PartnershipIdVo vo = new Gson().fromJson(getJson(request), PartnershipIdVo.class);
        if (vo != null && vo.getPartnershipId() != null) {
            PartnershipInfoEntity info = partnershipServer.findPartnershipInfoById(vo.getPartnershipId());
            if (info != null) {
                if (partnershipServer.updatePraise(vo.getPartnershipId()) > 0) {
                    map = writeResultRep();
                } else {
                    map = writeResultRep(7005);//合伙信息点赞失败
                }
            } else {
                map = writeResultRep(7003);//合伙信息不存在
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

    /**
     * 合伙信息权重接口
     *
     * @param {"partnershipId":""} partnershipId 合伙ID
     */
    @RequestMapping(value = "/weight", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> weight(HttpServletRequest request)
            throws Exception {
        Long userId = Long.parseLong(request.getHeader("userId"));
        Map<String, Object> map;
        PartnershipIdVo vo = new Gson().fromJson(getJson(request), PartnershipIdVo.class);
        if (vo != null && vo.getPartnershipId() != null) {
            PartnershipInfoEntity info = partnershipServer.findPartnershipInfoById(vo.getPartnershipId());
            PartnershipItemEntity item = partnershipServer.findPartnershipItem(vo.getPartnershipId());
            if (info != null && item != null) {
                if (item.getPublishId().equals(userId)) {
                    if (partnershipServer.updateWeight(vo.getPartnershipId()) > 0) {
                        map = writeResultRep();
                    } else {
                        map = writeResultRep(50);//修改权重失败
                    }
                } else {
                    map = writeResultRep(1004);//userId不匹配
                }
            } else {
                map = writeResultRep(7003);//合伙信息不存在
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

    /**
     * 删除众包信息接口
     *
     * @param {"partnershipId":""} partnershipId 合伙ID
     */
    @RequestMapping(value = "/deletePartnership", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> deletePartnership(HttpServletRequest request) throws Exception {
        Long userId = Long.parseLong(request.getHeader("userId"));
        Map<String, Object> map;
        PartnershipIdVo vo = new Gson().fromJson(getJson(request), PartnershipIdVo.class);
        if (vo != null && vo.getPartnershipId() != null) {
            PartnershipItemEntity item = partnershipServer.findPartnershipItem(vo.getPartnershipId());
            if (item != null) {
                if (item.getPublishId().equals(userId)) {
                    if (partnershipServer.deletePartnershipInfo(vo.getPartnershipId())) {
                        map = writeResultRep();
                    } else {
                        map = writeResultRep(7004);//删除合伙信息失败
                    }
                } else {
                    map = writeResultRep(1004);//userId不匹配
                }
            } else {
                map = writeResultRep(7003);//合伙信息不存在
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
            List<PartnershipInfoEntity> partnerships = partnershipServer.findMyPublish(vo, userId);
            map.put("partnerships", partnerships);
            if (!partnerships.isEmpty()) {
                map.put("count", partnerships.size());
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
            List<PartnershipInfoEntity> partnerships = partnershipServer.findCollect(vo, userId);
            map.put("partnerships", partnerships);
            if (!partnerships.isEmpty()) {
                map.put("count", partnerships.size());
            } else {
                map.put("count", 0);
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

}