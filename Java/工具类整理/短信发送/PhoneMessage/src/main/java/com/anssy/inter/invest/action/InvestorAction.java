/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  InvestorAction.java
 * PACKAGE      :  com.anssy.inter.invest.action
 * CREATE DATE  :  2016-8-12
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.inter.invest.action;

import com.google.gson.Gson;
import com.anssy.venturebar.base.vo.DatadictVo;
import com.anssy.venturebar.invest.entity.InvestorInfoEntity;
import com.anssy.venturebar.invest.entity.InvestorItemEntity;
import com.anssy.inter.AppAction;
import com.anssy.inter.invest.server.InvestorServer;
import com.anssy.inter.invest.vo.InvestorIdVo;
import com.anssy.inter.invest.vo.InvestorVo;
import com.anssy.inter.invest.vo.SearchVo;
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
 *          接口_投资人
 */
@Controller
@RequestMapping("/app/invest/investorAction")
public class InvestorAction extends AppAction {

    @Resource
    private InvestorServer investorServer;

    /**
     * 获取信息(投资主体/投资方式/投资类型 )
     */
    @RequestMapping(value = "/findDicInfo", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> findDicInfo() throws Exception {
        Map<String, Object> map = writeResultRep();
        List<DatadictVo> bodys = investorServer.findInvestorBody();
        List<DatadictVo> investWays = investorServer.findInvestWay();
        List<DatadictVo> ways = investorServer.findWay();
        List<DatadictVo> investTypes = investorServer.findInvestType();
        map.put("bodys", bodys);
        map.put("investWays", investWays);
        map.put("ways", ways);
        map.put("investTypes", investTypes);
        return map;
    }

    /**
     * 发布投资人信息接口
     *
     * @param {"image":"","name":"","body":"","field":"","provinceId":"","cityId":"","areaId":"","callingCard":"","companyName":"","investIntro":"","post":"","email":"","companySite":"","successfulCase":"","investWay":"","way":"","investType":"","investSum":"","deadline":"","focus":"","remark":"","other":"","linkman":"","phone":"","url":""}
     */
    @RequestMapping(value = "/releaseInvestor", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> releaseInvestor(HttpServletRequest request) throws Exception {
        Long userId = Long.parseLong(request.getHeader("userId"));
        Map<String, Object> map;
        InvestorVo vo = new Gson().fromJson(getJson(request), InvestorVo.class);
        if (vo != null && StringUtils.isNotBlank(vo.getName()) &&
                vo.getBody() != null && StringUtils.isNotBlank(vo.getField()) &&
                vo.getProvinceId() != null && vo.getCityId() != null
                && vo.getAreaId() != null && StringUtils.isNotBlank(vo.getWay())
                && StringUtils.isNotBlank(vo.getCompanyName())
                && StringUtils.isNotBlank(vo.getCompanySite())
                && StringUtils.isNotBlank(vo.getPost())
                && StringUtils.isNotBlank(vo.getLinkman()) && StringUtils.isNotBlank(vo.getPhone())) {
            boolean flag = investorServer.releaseInvestor(vo, userId);
            if (flag) {
                map = writeResultRep();
            } else {
                map = writeResultRep(4001);//发布投资人信息失败
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

    /**
     * 修改投资人信息接口
     *
     * @param {"id":"","image":"","name":"","body":"","field":"","provinceId":"","cityId":"","areaId":"","callingCard":"","companyName":"","investIntro":"","post":"","email":"","companySite":"","successfulCase":"","investWay":"","way":"","investType":"","investSum":"","deadline":"","focus":"","remark":"","other":"","linkman":"","phone":"","url":""}
     */
    @RequestMapping(value = "/updateInvestor", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> updateInvestor(HttpServletRequest request) throws Exception {
        Long userId = Long.parseLong(request.getHeader("userId"));
        Map<String, Object> map;
        InvestorVo vo = new Gson().fromJson(getJson(request), InvestorVo.class);
        if (vo != null && vo.getId() != null && StringUtils.isNotBlank(vo.getName()) &&
                vo.getBody() != null && StringUtils.isNotBlank(vo.getField()) &&
                vo.getProvinceId() != null && vo.getCityId() != null
                && vo.getAreaId() != null && StringUtils.isNotBlank(vo.getWay())
                && StringUtils.isNotBlank(vo.getCompanyName())
                && StringUtils.isNotBlank(vo.getCompanySite())
                && StringUtils.isNotBlank(vo.getPost())
                && StringUtils.isNotBlank(vo.getLinkman()) && StringUtils.isNotBlank(vo.getPhone())) {
            InvestorInfoEntity info = investorServer.findInvestorInfoById(vo.getId());
            InvestorItemEntity item = investorServer.findInvestorItem(vo.getId());
            if (info != null && item != null) {
                if (item.getPublishId().equals(userId)) {
                    boolean flag = investorServer.updateInvestor(vo);
                    if (flag) {
                        map = writeResultRep();
                    } else {
                        map = writeResultRep(4006);//修改投资人信息失败
                    }
                } else {
                    map = writeResultRep(1004);//userId不匹配
                }
            } else {
                map = writeResultRep(4003);//投资人信息不存在
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }


    /**
     * 投资人信息列表接口
     *
     * @param {"listType":"1","page":"","longitude":"","latitude":"","search":""}
     * @param {"listType":"2","page":"","provinceId":"0","cityId":"0","areaId":"0","field":"","search":""}
     * @param {"listType":"3","page":"","capacityType":"","provinceId":"0","cityId":"0","areaId":"0","search":""}
     */
    @RequestMapping(value = "/investorList", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> investorList(HttpServletRequest request) throws Exception {
        Map<String, Object> map;
        SearchVo vo = new Gson().fromJson(getJson(request), SearchVo.class);
        if (vo != null) {
            //搜索方式(1-附近搜索,2-行业搜索,3-智能搜索)
            if (vo.getListType() == 1 && StringUtils.isNotBlank(vo.getLatitude()) &&
                    StringUtils.isNotBlank(vo.getLongitude())) {
                map = writeResultRep();
                List<InvestorInfoEntity> infos = investorServer.findListByGPS(vo);
                map.put("investors", infos);
                if (!infos.isEmpty()) {
                    map.put("count", infos.size());
                } else {
                    map.put("count", 0);
                }
            } else if (vo.getListType() == 2 && vo.getField() != null) {
                map = writeResultRep();
                List<InvestorInfoEntity> infos = investorServer.findInvestorByField(vo);
                map.put("investors", infos);
                if (!infos.isEmpty()) {
                    map.put("count", infos.size());
                } else {
                    map.put("count", 0);
                }
            } else if (vo.getListType() == 3) {
                map = writeResultRep();
                List<InvestorInfoEntity> infos = investorServer.findInvestorByPv(vo);
                map.put("investors", infos);
                if (!infos.isEmpty()) {
                    map.put("count", infos.size());
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
     * 推荐投资人信息接口
     *
     * @param {"provinceId":"","cityId":"","areaId":"","count":""}
     */
    @RequestMapping(value = "/referrals", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> referrals(HttpServletRequest request)
            throws Exception {
        Map<String, Object> map;
        ReferralsVo vo = new Gson().fromJson(getJson(request), ReferralsVo.class);
        if (vo != null) {
            map = writeResultRep();
            List<InvestorInfoEntity> infos = investorServer.referrals(vo);
            map.put("investors", infos);
            if (!infos.isEmpty()) {
                map.put("count", infos.size());
            } else {
                map.put("count", 0);
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }


    /**
     * 查看投资人信息接口
     *
     * @param {"investorId":""} investorId 投资人ID
     */
    @RequestMapping(value = "/seeInvestor", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> seeInvestor(HttpServletRequest request) throws Exception {
        Map<String, Object> map;
        InvestorIdVo vo = new Gson().fromJson(getJson(request), InvestorIdVo.class);
        if (vo != null && vo.getInvestorId() != null) {
            investorServer.updatePV(vo.getInvestorId());
            InvestorInfoEntity info = investorServer.replenish(investorServer.findInvestorInfoById(vo.getInvestorId()));
            InvestorItemEntity item = investorServer.replenish(investorServer.findInvestorItem(vo.getInvestorId()));
            if (info != null && item != null) {
                map = writeResultRep();
                map.put("info", info);
                map.put("item", item);
            } else {
                map = writeResultRep(4002);//查询投资人信息失败
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

    /**
     * 投资人信息点赞接口
     *
     * @param {"investorId":""} investorId 投资人ID
     */
    @RequestMapping(value = "/praise", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> praise(HttpServletRequest request)
            throws Exception {
        Map<String, Object> map;
        InvestorIdVo vo = new Gson().fromJson(getJson(request), InvestorIdVo.class);
        if (vo != null && vo.getInvestorId() != null) {
            InvestorInfoEntity info = investorServer.findInvestorInfoById(vo.getInvestorId());
            if (info != null) {
                if (investorServer.updatePraise(vo.getInvestorId()) > 0) {
                    map = writeResultRep();
                } else {
                    map = writeResultRep(4005);//投资人信息点赞失败
                }
            } else {
                map = writeResultRep(4003);//投资人信息不存在
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

    /**
     * 投资人信息权重接口
     *
     * @param {"investorId":""} investorId 投资人ID
     */
    @RequestMapping(value = "/weight", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> weight(HttpServletRequest request)
            throws Exception {
        Long userId = Long.parseLong(request.getHeader("userId"));
        Map<String, Object> map;
        InvestorIdVo vo = new Gson().fromJson(getJson(request), InvestorIdVo.class);
        if (vo != null && vo.getInvestorId() != null) {
            InvestorInfoEntity info = investorServer.findInvestorInfoById(vo.getInvestorId());
            InvestorItemEntity item = investorServer.findInvestorItem(vo.getInvestorId());
            if (info != null && item != null) {
                if (item.getPublishId().equals(userId)) {
                    if (investorServer.updateWeight(vo.getInvestorId()) > 0) {
                        map = writeResultRep();
                    } else {
                        map = writeResultRep(50);//修改权重失败
                    }
                } else {
                    map = writeResultRep(1004);//userId不匹配
                }
            } else {
                map = writeResultRep(4003);//投资人信息不存在
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

    /**
     * 删除投资人信息
     *
     * @param {"investorId":""} investorId 投资人ID
     */
    @RequestMapping(value = "/deleteInvestor", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> deleteInvestor(HttpServletRequest request)
            throws Exception {
        Long userId = Long.parseLong(request.getHeader("userId"));
        Map<String, Object> map;
        InvestorIdVo vo = new Gson().fromJson(getJson(request), InvestorIdVo.class);
        if (vo != null && vo.getInvestorId() != null) {
            InvestorItemEntity item = investorServer.findInvestorItem(vo.getInvestorId());
            if (item != null) {
                if (item.getPublishId().equals(userId)) {
                    if (investorServer.deleteInvestor(vo.getInvestorId())) {
                        map = writeResultRep();
                    } else {
                        map = writeResultRep(4004);//删除投资人信息失败
                    }
                } else {
                    map = writeResultRep(1004);//userId不匹配
                }
            } else {
                map = writeResultRep(4003);//投资人信息不存在
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
            List<InvestorInfoEntity> infos = investorServer.findMyPublish(vo, userId);
            map.put("investors", infos);
            if (!infos.isEmpty()) {
                map.put("count", infos.size());
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
            List<InvestorInfoEntity> infos = investorServer.findCollect(vo, userId);
            map.put("investors", infos);
            if (!infos.isEmpty()) {
                map.put("count", infos.size());
            } else {
                map.put("count", 0);
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

}