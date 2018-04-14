/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  PolicyAction.java
 * PACKAGE      :  com.anssy.inter.service.action
 * CREATE DATE  :  2016-8-3
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.inter.service.action;

import com.anssy.inter.service.server.PolicyElucidationServer;
import com.anssy.venturebar.service.vo.PEVo;
import com.google.gson.Gson;
import com.anssy.venturebar.service.entity.PolicyElucidationEntity;
import com.anssy.inter.AppAction;
import com.anssy.inter.service.vo.PolicyIdVo;
import com.anssy.inter.service.vo.PolicyVo;
import com.anssy.inter.service.vo.SearchVo;
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
 * @version SVN #V1# #2016-8-3#
 *          服务_政策解读
 */
@Controller
@RequestMapping("/app/service/policyAction")
public class PolicyAction extends AppAction {

    @Resource
    private PolicyElucidationServer policyElucidationServer;

    /**
     * 发布政策信息接口
     *
     * @param {"image":"","title":"","details":"","source":"","url":""}
     */
    @RequestMapping(value = "/releasePolicy", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> releasePolicy(HttpServletRequest request) throws Exception {
        Long userId = Long.parseLong(request.getHeader("userId"));
        Map<String, Object> map;
        PolicyVo vo = new Gson().fromJson(getJson(request), PolicyVo.class);
        if (vo != null && StringUtils.isNotBlank(vo.getTitle())
                && StringUtils.isNotBlank(vo.getDetails())) {
            if (policyElucidationServer.releasePolicy(vo, userId)) {
                map = writeResultRep();
            } else {
                map = writeResultRep(11001);//发布政策信息失败
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

    /**
     * 修改政策信息接口
     *
     * @param {"id":"","image":"","title":"","details":"","source":"","url":""}
     */
    @RequestMapping(value = "/updatePolicy", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> updatePolicy(HttpServletRequest request) throws Exception {
        Long userId = Long.parseLong(request.getHeader("userId"));
        Map<String, Object> map;
        PolicyVo vo = new Gson().fromJson(getJson(request), PolicyVo.class);
        if (vo != null && vo.getId() != null && StringUtils.isNotBlank(vo.getTitle()) && StringUtils.isNotBlank(vo.getDetails())) {
            PolicyElucidationEntity info = policyElucidationServer.findPolicyElucidationById(vo.getId());
            if (info != null) {
                if (info.getPublishId().equals(userId)) {
                    if (policyElucidationServer.updatePolicy(vo)) {
                        map = writeResultRep();
                    } else {
                        map = writeResultRep(11006);//修改政策信息失败
                    }
                } else {
                    map = writeResultRep(1004);//userId不匹配
                }
            } else {
                map = writeResultRep(11003);//政策信息不存在
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

    /**
     * 政策信息列表接口
     *
     * @param {"page":"","search":"","capacityType":""}
     */
    @RequestMapping(value = "/policyList", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> policyList(HttpServletRequest request) throws Exception {
        Map<String, Object> map;
        SearchVo vo = new Gson().fromJson(getJson(request), SearchVo.class);
        if (vo != null) {
            map = writeResultRep();
            List<PEVo> policys = policyElucidationServer.findList(vo);
            map.put("policys", policys);
            if (!policys.isEmpty()) {
                map.put("count", policys.size());
            } else {
                map.put("count", 0);
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

    /**
     * 推荐政策信息接口
     *
     * @param {"count":""}
     */
    @RequestMapping(value = "/referrals", produces = {" "})
    @ResponseBody
    public Map<String, Object> referrals(HttpServletRequest request) throws Exception {
        Map<String, Object> map;
        ReferralsVo vo = new Gson().fromJson(getJson(request), ReferralsVo.class);
        if (vo != null) {
            map = writeResultRep();
            List<PEVo> policys = policyElucidationServer.referrals(vo);
            map.put("policys", policys);
            if (!policys.isEmpty()) {
                map.put("count", policys.size());
            } else {
                map.put("count", 0);
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

    /**
     * 查看政策信息接口
     *
     * @param {"policyId":""} policyId 政策ID
     */
    @RequestMapping(value = "/seePolicy", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> seePolicy(HttpServletRequest request) throws Exception {
        Map<String, Object> map;
        PolicyIdVo vo = new Gson().fromJson(getJson(request), PolicyIdVo.class);
        if (vo != null && vo.getPolicyId() != null) {
            policyElucidationServer.updatePV(vo.getPolicyId());
            PolicyElucidationEntity info = policyElucidationServer.replenish(policyElucidationServer.findPolicyElucidationById(vo.getPolicyId()));
            if (info != null) {
                map = writeResultRep();
                map.put("info", info);
            } else {
                map = writeResultRep(11002);//查询政策信息失败
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

    /**
     * 政策信息点赞接口
     *
     * @param {"policyId":""} policyId 政策ID
     */
    @RequestMapping(value = "/praise", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> praise(HttpServletRequest request) throws Exception {
        Map<String, Object> map;
        PolicyIdVo vo = new Gson().fromJson(getJson(request), PolicyIdVo.class);
        if (vo != null && vo.getPolicyId() != null) {
            PolicyElucidationEntity info = policyElucidationServer.findPolicyElucidationById(vo.getPolicyId());
            if (info != null) {
                if (policyElucidationServer.updatePraise(vo.getPolicyId()) > 0) {
                    map = writeResultRep();
                } else {
                    map = writeResultRep(11005);//政策信息点赞失败
                }
            } else {
                map = writeResultRep(11003);//政策信息不存在
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

    /**
     * 政策信息权重接口
     *
     * @param {"policyId":""} policyId 政策ID
     */
    @RequestMapping(value = "/weight", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> weight(HttpServletRequest request) throws Exception {
        Long userId = Long.parseLong(request.getHeader("userId"));
        Map<String, Object> map;
        PolicyIdVo vo = new Gson().fromJson(getJson(request), PolicyIdVo.class);
        if (vo != null && vo.getPolicyId() != null) {
            PolicyElucidationEntity info = policyElucidationServer.findPolicyElucidationById(vo.getPolicyId());
            if (info != null) {
                if (info.getPublishId().equals(userId)) {
                    if (policyElucidationServer.updateWeight(vo.getPolicyId()) > 0) {
                        map = writeResultRep();
                    } else {
                        map = writeResultRep(50);//修改权重失败
                    }
                } else {
                    map = writeResultRep(1004);//userId不匹配
                }
            } else {
                map = writeResultRep(11003);//政策信息不存在
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

    /**
     * 删除政策信息接口
     *
     * @param {"policyId":""} policyId 政策ID
     */
    @RequestMapping(value = "/deletePolicy", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> deletePolicy(HttpServletRequest request) throws Exception {
        Long userId = Long.parseLong(request.getHeader("userId"));
        Map<String, Object> map;
        PolicyIdVo vo = new Gson().fromJson(getJson(request), PolicyIdVo.class);
        if (vo != null && vo.getPolicyId() != null) {
            PolicyElucidationEntity info = policyElucidationServer.findPolicyElucidationById(vo.getPolicyId());
            if (info != null) {
                if (info.getPublishId().equals(userId)) {
                    if (policyElucidationServer.deletePolicy(vo.getPolicyId()) > 0) {
                        map = writeResultRep();
                    } else {
                        map = writeResultRep(11004);//删除政策信息失败
                    }
                } else {
                    map = writeResultRep(1004);//userId不匹配
                }
            } else {
                map = writeResultRep(11003);//政策信息不存在
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
            List<PEVo> policys = policyElucidationServer.findMyPublish(vo, userId);
            map.put("policys", policys);
            if (!policys.isEmpty()) {
                map.put("count", policys.size());
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
            List<PEVo> policys = policyElucidationServer.findCollect(vo, userId);
            map.put("policys", policys);
            if (!policys.isEmpty()) {
                map.put("count", policys.size());
            } else {
                map.put("count", 0);
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

}