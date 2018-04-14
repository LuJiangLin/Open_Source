/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  ServiceAction.java
 * PACKAGE      :  com.anssy.inter.service.action
 * CREATE DATE  :  2016-8-12
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.inter.service.action;

import com.google.gson.Gson;
import com.anssy.venturebar.base.entity.ServiceCategoryEntity;
import com.anssy.venturebar.service.entity.ServiceInfoEntity;
import com.anssy.venturebar.service.entity.ServiceItemEntity;
import com.anssy.inter.AppAction;
import com.anssy.inter.service.server.ServiceServer;
import com.anssy.inter.service.vo.SearchVo;
import com.anssy.inter.service.vo.ServiceCategoryVo;
import com.anssy.inter.service.vo.ServiceIdVo;
import com.anssy.inter.service.vo.ServiceVo;
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
 *          接口_服务
 */
@Controller
@RequestMapping("/app/service/serviceAction")
public class ServiceAction extends AppAction {

    @Resource
    private ServiceServer serviceServer;

    /**
     * 获取服务类别接口(一级/二级)
     *
     * @param {"serviceLevel":"","fatherId":""}
     */
    @RequestMapping(value = "/findServiceCategory", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> findServiceCategory(HttpServletRequest request) throws Exception {
        Map<String, Object> map;
        ServiceCategoryVo vo = new Gson().fromJson(getJson(request), ServiceCategoryVo.class);
        if (vo != null && vo.getFatherId() != null) {
            map = writeResultRep();
            List<ServiceCategoryEntity> categorys = serviceServer.findServiceCategory(vo);
            map.put("ServiceCategory", categorys);
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

    /**
     * 获取服务类别接口
     */
    @RequestMapping(value = "/findServiceCategoryAll", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> findServiceCategoryAll() throws Exception {
        Map<String, Object> map = writeResultRep();
        List<ServiceCategoryEntity> categorys = serviceServer.findServiceCategoryAll();
        map.put("ServiceCategory", categorys);
        return map;
    }

    /**
     * 发布服务信息接口
     *
     * @param {"logo":"","serviceType":"","field":"","organization":"","provinceId":"","cityId":"","areaId":"","describe":"","other":"","serviceSite":"","serviceTime":"","url":"","linkman":"","phone":"","email":""}
     */
    @RequestMapping(value = "/releaseService", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> releaseService(HttpServletRequest request) throws Exception {
        Long userId = Long.parseLong(request.getHeader("userId"));
        Map<String, Object> map;
        ServiceVo vo = new Gson().fromJson(getJson(request), ServiceVo.class);
        if (vo != null && StringUtils.isNotBlank(vo.getOrganization()) &&
                vo.getProvinceId() != null && vo.getCityId() != null &&
                vo.getAreaId() != null && StringUtils.isNotBlank(vo.getField()) &&
                StringUtils.isNotBlank(vo.getServiceSite()) &&
                StringUtils.isNotBlank(vo.getLinkman()) &&
                StringUtils.isNotBlank(vo.getPhone())) {
            boolean flag = serviceServer.releaseService(vo, userId);
            if (flag) {
                map = writeResultRep();
            } else {
                map = writeResultRep(9001);//发布服务信息失败
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

    /**
     * 修改服务信息接口
     *
     * @param {"id":"","logo":"","serviceType":"","field":"","organization":"","provinceId":"","cityId":"","areaId":"","describe":"","other":"","serviceSite":"","serviceTime":"","url":"","linkman":"","phone":"","email":""}
     */
    @RequestMapping(value = "/updateService", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> updateService(HttpServletRequest request) throws Exception {
        Long userId = Long.parseLong(request.getHeader("userId"));
        Map<String, Object> map;
        ServiceVo vo = new Gson().fromJson(getJson(request), ServiceVo.class);
        if (vo != null && vo.getId() != null && StringUtils.isNotBlank(vo.getOrganization()) &&
                vo.getProvinceId() != null && vo.getCityId() != null &&
                vo.getAreaId() != null && StringUtils.isNotBlank(vo.getField()) &&
                StringUtils.isNotBlank(vo.getServiceSite()) &&
                StringUtils.isNotBlank(vo.getLinkman()) &&
                StringUtils.isNotBlank(vo.getPhone())) {
            ServiceInfoEntity info = serviceServer.findServiceInfoById(vo.getId());
            ServiceItemEntity item = serviceServer.findServiceItem(vo.getId());
            if (info != null && item != null) {
                if (item.getPublishId().equals(userId)) {
                    boolean flag = serviceServer.updateService(vo);
                    if (flag) {
                        map = writeResultRep();
                    } else {
                        map = writeResultRep(9006);//修改服务信息失败
                    }
                } else {
                    map = writeResultRep(1004);//userId不匹配
                }
            } else {
                map = writeResultRep(9003);//服务信息不存在
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }


    /**
     * 服务信息列表接口
     *
     * @param {"listType":"1","page":"","serviceType":"","longitude":"","latitude":"","search":""}
     * @param {"listType":"2","page":"","serviceType":"","field":"","provinceId":"0","cityId":"0","areaId":"0","search":""}
     * @param {"listType":"3","page":"","serviceType":"","capacityType":"","provinceId":"0","cityId":"0","areaId":"0","search":""}
     */
    @RequestMapping(value = "/serviceList", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> serviceList(HttpServletRequest request) throws Exception {
        Map<String, Object> map;
        SearchVo vo = new Gson().fromJson(getJson(request), SearchVo.class);
        if (vo != null && vo.getServiceType() != null) {
            //搜索方式(1-按GPS进行附近搜索,2-按类型进行场地类型搜索,3-智能搜索)
            if (vo.getListType() == 1 && StringUtils.isNotBlank(vo.getLatitude())
                    && StringUtils.isNotBlank(vo.getLongitude())) {
                map = writeResultRep();
                List<ServiceInfoEntity> services = serviceServer.findListByGPS(vo);
                map.put("services", services);
                if (!services.isEmpty()) {
                    map.put("count", services.size());
                } else {
                    map.put("count", 0);
                }
            } else if (vo.getListType() == 2 && vo.getField() != null) {
                map = writeResultRep();
                List<ServiceInfoEntity> services = serviceServer.findListByType(vo);
                map.put("services", services);
                if (!services.isEmpty()) {
                    map.put("count", services.size());
                } else {
                    map.put("count", 0);
                }
            } else if (vo.getListType() == 3) {
                map = writeResultRep();
                List<ServiceInfoEntity> services = serviceServer.findListByPv(vo);
                map.put("services", services);
                if (!services.isEmpty()) {
                    map.put("count", services.size());
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
     * 推荐服务信息接口
     *
     * @param {"serviceType":"","provinceId":"","cityId":"","areaId":"","count":""}
     */
    @RequestMapping(value = "/referrals", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> referrals(HttpServletRequest request) throws Exception {
        Map<String, Object> map;
        ReferralsVo vo = new Gson().fromJson(getJson(request), ReferralsVo.class);
        if (vo != null && vo.getServiceType() != null) {
            map = writeResultRep();
            List<ServiceInfoEntity> services = serviceServer.referrals(vo);
            map.put("services", services);
            if (!services.isEmpty()) {
                map.put("count", services.size());
            } else {
                map.put("count", 0);
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

    /**
     * 查看服务信息接口
     *
     * @param {"serviceId":""} serviceId 服务ID
     */
    @RequestMapping(value = "/seeService", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> seeService(HttpServletRequest request) throws Exception {
        Map<String, Object> map;
        ServiceIdVo vo = new Gson().fromJson(getJson(request), ServiceIdVo.class);
        if (vo != null && vo.getServiceId() != null) {
            serviceServer.updatePV(vo.getServiceId());
            ServiceInfoEntity info = serviceServer.replenish(serviceServer.findServiceInfoById(vo.getServiceId()));
            ServiceItemEntity item = serviceServer.replenish(serviceServer.findServiceItem(vo.getServiceId()));
            if (info != null && item != null) {
                map = writeResultRep();
                map.put("info", info);
                map.put("item", item);
            } else {
                map = writeResultRep(9002);//查询服务信息失败
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

    /**
     * 服务信息点赞接口
     *
     * @param {"serviceId":""} serviceId 服务ID
     */
    @RequestMapping(value = "/praise", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> praise(HttpServletRequest request) throws Exception {
        Map<String, Object> map;
        ServiceIdVo vo = new Gson().fromJson(getJson(request), ServiceIdVo.class);
        if (vo != null && vo.getServiceId() != null) {
            ServiceInfoEntity info = serviceServer.findServiceInfoById(vo.getServiceId());
            if (info != null) {
                if (serviceServer.updatePraise(vo.getServiceId()) > 0) {
                    map = writeResultRep();
                } else {
                    map = writeResultRep(9005);//服务信息点赞失败
                }
            } else {
                map = writeResultRep(9003);//服务信息不存在
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

    /**
     * 服务信息权重接口
     *
     * @param {"serviceId":""} serviceId 服务ID
     */
    @RequestMapping(value = "/weight", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> weight(HttpServletRequest request) throws Exception {
        Long userId = Long.parseLong(request.getHeader("userId"));
        Map<String, Object> map;
        ServiceIdVo vo = new Gson().fromJson(getJson(request), ServiceIdVo.class);
        if (vo != null && vo.getServiceId() != null) {
            ServiceInfoEntity info = serviceServer.findServiceInfoById(vo.getServiceId());
            ServiceItemEntity item = serviceServer.findServiceItem(vo.getServiceId());
            if (info != null && item != null) {
                if (item.getPublishId() == userId) {
                    if (serviceServer.updateWeight(vo.getServiceId()) > 0) {
                        map = writeResultRep();
                    } else {
                        map = writeResultRep(50);//修改权重失败
                    }
                } else {
                    map = writeResultRep(1004);//userId不匹配
                }
            } else {
                map = writeResultRep(9003);//服务信息不存在
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

    /**
     * 删除服务信息接口
     *
     * @param {"serviceId":""} serviceId 服务ID
     */
    @RequestMapping(value = "/deleteService", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> deleteService(HttpServletRequest request) throws Exception {
        Long userId = Long.parseLong(request.getHeader("userId"));
        Map<String, Object> map;
        ServiceIdVo vo = new Gson().fromJson(getJson(request), ServiceIdVo.class);
        if (vo != null && vo.getServiceId() != null) {
            ServiceItemEntity item = serviceServer.findServiceItem(vo.getServiceId());
            if (item != null) {
                if (item.getPublishId().equals(userId)) {
                    if (serviceServer.deleteService(vo.getServiceId())) {
                        map = writeResultRep();
                    } else {
                        map = writeResultRep(9004);//删除服务信息失败
                    }
                } else {
                    map = writeResultRep(1004);//userId不匹配
                }
            } else {
                map = writeResultRep(9003);//服务信息不存在
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

    /**
     * 我的发布信息
     *
     * @param {"page":"","serviceType":"","search":""}
     */
    @RequestMapping(value = "/findMyPublish", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> findMyPublish(HttpServletRequest request) throws Exception {
        Long userId = Long.parseLong(request.getHeader("userId"));
        Map<String, Object> map;
        PublishVo vo = new Gson().fromJson(getJson(request), PublishVo.class);
        if (vo != null && vo.getServiceType() != null) {
            map = writeResultRep();
            List<ServiceInfoEntity> services = serviceServer.findMyPublish(vo, userId);
            map.put("services", services);
            if (!services.isEmpty()) {
                map.put("count", services.size());
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
     * @param {"page":"","type":"","search":"","serviceType":""}
     */
    @RequestMapping(value = "/findCollect", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> findCollect(HttpServletRequest request) throws Exception {
        Long userId = Long.parseLong(request.getHeader("userId"));
        Map<String, Object> map;
        MyCollectVo vo = new Gson().fromJson(getJson(request), MyCollectVo.class);
        if (vo != null && vo.getServiceType() != null) {
            map = writeResultRep();
            List<ServiceInfoEntity> services = serviceServer.findCollect(vo, userId);
            map.put("services", services);
            if (!services.isEmpty()) {
                map.put("count", services.size());
            } else {
                map.put("count", 0);
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

}