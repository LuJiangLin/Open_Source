/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  ProjectAction.java
 * PACKAGE      :  com.anssy.inter.invest.action
 * CREATE DATE  :  2016-8-12
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.inter.invest.action;

import com.google.gson.Gson;
import com.anssy.venturebar.base.vo.DatadictVo;
import com.anssy.venturebar.invest.entity.ProjectInfoEntity;
import com.anssy.venturebar.invest.entity.ProjectItemEntity;
import com.anssy.inter.AppAction;
import com.anssy.inter.invest.server.ProjectServer;
import com.anssy.inter.invest.vo.ProjectIdVo;
import com.anssy.inter.invest.vo.ProjectVo;
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
 *          接口_投资项目
 */
@Controller
@RequestMapping("/app/invest/projectAction")
public class ProjectAction extends AppAction {

    @Resource
    private ProjectServer projectServer;

    /**
     * 获取信息(融资需求/融资状态/出让股份比例)
     */
    @RequestMapping(value = "/findDicInfo", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> findDicInfo() throws Exception {
        Map<String, Object> map = writeResultRep();
        List<DatadictVo> isFinancings = projectServer.findIsFinancing();
        List<DatadictVo> financingStates = projectServer.findFinancingState();
        List<DatadictVo> sellShares = projectServer.findSellShare();
        map.put("isFinancings", isFinancings);
        map.put("financingStates", financingStates);
        map.put("sellShares", sellShares);
        return map;
    }

    /**
     * 发布投资项目信息接口
     *
     * @param {"image":"","title":"","sketch":"","field":"","provinceId":"","cityId":"","areaId":"","describe":"","advantage":"","isFinancing":"","financingState":"","financingSum":"","sellShare":"","founder":"","companyName":"","companySite":"","email":"","url":"","appUrl":"","linkman":"","phone":""}
     */
    @RequestMapping(value = "/releaseProject", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> releaseProject(HttpServletRequest request) throws Exception {
        Long userId = Long.parseLong(request.getHeader("userId"));
        Map<String, Object> map;
        ProjectVo vo = new Gson().fromJson(getJson(request), ProjectVo.class);
        if (vo != null && StringUtils.isNotBlank(vo.getTitle())
                && StringUtils.isNotBlank(vo.getField())
                && vo.getProvinceId() != null && vo.getCityId() != null
                && vo.getAreaId() != null &&
                StringUtils.isNotBlank(vo.getFinancingSum())
                && StringUtils.isNotBlank(vo.getCompanyName())
                && StringUtils.isNotBlank(vo.getCompanySite())
                && StringUtils.isNotBlank(vo.getLinkman())
                && StringUtils.isNotBlank(vo.getPhone())) {
            boolean flag = projectServer.releaseProject(vo, userId);
            if (flag) {
                map = writeResultRep();
            } else {
                map = writeResultRep(5001);//发布投资项目信息失败
            }
        } else {
            map = writeResultRep(1003);// 参数异常
        }
        return map;
    }

    /**
     * 修改投资项目信息接口
     *
     * @param {"id":"","image":"","title":"","sketch":"","field":"","provinceId":"","cityId":"","areaId":"","describe":"","advantage":"","isFinancing":"","financingState":"","financingSum":"","sellShare":"","founder":"","companyName":"","companySite":"","email":"","url":"","appUrl":"","linkman":"","phone":""}
     */
    @RequestMapping(value = "/updateProject", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> updateProject(HttpServletRequest request) throws Exception {
        Long userId = Long.parseLong(request.getHeader("userId"));
        Map<String, Object> map;
        ProjectVo vo = new Gson().fromJson(getJson(request), ProjectVo.class);
        if (vo != null && vo.getId() != null && StringUtils.isNotBlank(vo.getTitle())
                && StringUtils.isNotBlank(vo.getField())
                && vo.getProvinceId() != null && vo.getCityId() != null
                && vo.getAreaId() != null &&
                StringUtils.isNotBlank(vo.getFinancingSum())
                && StringUtils.isNotBlank(vo.getCompanyName())
                && StringUtils.isNotBlank(vo.getCompanySite())
                && StringUtils.isNotBlank(vo.getLinkman())
                && StringUtils.isNotBlank(vo.getPhone())) {
            ProjectInfoEntity info = projectServer.findProjectInfoById(vo.getId());
            ProjectItemEntity item = projectServer.findProjectItem(vo.getId());
            if (info != null && item != null) {
                if (item.getPublishId().equals(userId)) {
                    boolean flag = projectServer.updateProject(vo);
                    if (flag) {
                        map = writeResultRep();
                    } else {
                        map = writeResultRep(5006);//修改投资项目信息失败
                    }
                } else {
                    map = writeResultRep(1004);//userId不匹配
                }
            } else {
                map = writeResultRep(5003);//投资项目信息不存在
            }
        } else {
            map = writeResultRep(1003);// 参数异常
        }
        return map;
    }

    /**
     * 项目信息列表接口
     *
     * @param {"listType":"1","page":"","longitude":"","latitude":"","search":""}
     * @param {"listType":"2","page":"","provinceId":"0","cityId":"0","areaId":"0","field":"","search":""}
     * @param {"listType":"3","page":"","capacityType":"","provinceId":"0","cityId":"0","areaId":"0","search":""}
     */
    @RequestMapping(value = "/projectList", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> projectList(HttpServletRequest request) throws Exception {
        Map<String, Object> map;
        SearchVo vo = new Gson().fromJson(getJson(request), SearchVo.class);
        if (vo != null) {
            //搜索方式(1-附近搜索,2-行业搜索,3-智能搜索)
            if (vo.getListType() == 1 && StringUtils.isNotBlank(vo.getLatitude()) &&
                    StringUtils.isNotBlank(vo.getLongitude())) {
                map = writeResultRep();
                List<ProjectInfoEntity> projects = projectServer.findListByGPS(vo);
                map.put("projects", projects);
                if (!projects.isEmpty()) {
                    map.put("count", projects.size());
                } else {
                    map.put("count", 0);
                }
            } else if (vo.getListType() == 2 && vo.getField() != null) {
                map = writeResultRep();
                List<ProjectInfoEntity> projects = projectServer.findProjectByField(vo);
                map.put("projects", projects);
                if (!projects.isEmpty()) {
                    map.put("count", projects.size());
                } else {
                    map.put("count", 0);
                }
            } else if (vo.getListType() == 3) {
                map = writeResultRep();
                List<ProjectInfoEntity> projects = projectServer.findProjectByPv(vo);
                map.put("projects", projects);
                if (!projects.isEmpty()) {
                    map.put("count", projects.size());
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
     * 推荐项目信息接口
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
            List<ProjectInfoEntity> projects = projectServer.referrals(vo);
            map.put("projects", projects);
            if (!projects.isEmpty()) {
                map.put("count", projects.size());
            } else {
                map.put("count", 0);
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }


    /**
     * 查看项目信息接口
     *
     * @param {"projectId":""} projectId 项目ID
     */
    @RequestMapping(value = "/seeProject", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> seeProject(HttpServletRequest request) throws Exception {
        Map<String, Object> map;
        ProjectIdVo vo = new Gson().fromJson(getJson(request), ProjectIdVo.class);
        if (vo != null && vo.getProjectId() != null) {
            projectServer.updatePV(vo.getProjectId());
            ProjectInfoEntity info = projectServer.replenish(projectServer.findProjectInfoById(vo.getProjectId()));
            ProjectItemEntity item = projectServer.replenish(projectServer.findProjectItem(vo.getProjectId()));
            if (info != null && item != null) {
                map = writeResultRep();
                map.put("info", info);
                map.put("item", item);
            } else {
                map = writeResultRep(5002);//查询投资项目信息失败
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

    /**
     * 投资项目信息点赞接口
     *
     * @param {"projectId":""} projectId 项目ID
     */
    @RequestMapping(value = "/praise", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> praise(HttpServletRequest request) throws Exception {
        Map<String, Object> map;
        ProjectIdVo vo = new Gson().fromJson(getJson(request), ProjectIdVo.class);
        if (vo != null && vo.getProjectId() != null) {
            ProjectInfoEntity info = projectServer.findProjectInfoById(vo.getProjectId());
            if (info != null) {
                if (projectServer.updatePraise(vo.getProjectId()) > 0) {
                    map = writeResultRep();
                } else {
                    map = writeResultRep(5005);//投资项目信息点赞失败
                }
            } else {
                map = writeResultRep(5003);//投资项目信息不存在
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

    /**
     * 投资项目信息权重接口
     *
     * @param {"projectId":""} projectId 项目ID
     */
    @RequestMapping(value = "/weight", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> weight(HttpServletRequest request) throws Exception {
        Long userId = Long.parseLong(request.getHeader("userId"));
        Map<String, Object> map;
        ProjectIdVo vo = new Gson().fromJson(getJson(request), ProjectIdVo.class);
        if (vo != null && vo.getProjectId() != null) {
            ProjectInfoEntity info = projectServer.findProjectInfoById(vo.getProjectId());
            ProjectItemEntity item = projectServer.findProjectItem(vo.getProjectId());
            if (info != null && item != null) {
                if (item.getPublishId().equals(userId)) {
                    if (projectServer.updateWeight(vo.getProjectId()) > 0) {
                        map = writeResultRep();
                    } else {
                        map = writeResultRep(50);//修改权重失败
                    }
                } else {
                    map = writeResultRep(1004);//userId不匹配
                }
            } else {
                map = writeResultRep(5003);//投资项目信息不存在
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

    /**
     * 删除投资项目信息
     *
     * @param {"projectId":""} projectId 项目ID
     */
    @RequestMapping(value = "/deleteProject", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> deleteProject(HttpServletRequest request) throws Exception {
        Long userId = Long.parseLong(request.getHeader("userId"));
        Map<String, Object> map;
        ProjectIdVo vo = new Gson().fromJson(getJson(request), ProjectIdVo.class);
        if (vo != null && vo.getProjectId() != null) {
            ProjectItemEntity item = projectServer.findProjectItem(vo.getProjectId());
            if (item != null) {
                if (item.getPublishId().equals(userId)) {
                    if (projectServer.deleteProject(vo.getProjectId())) {
                        map = writeResultRep();
                    } else {
                        map = writeResultRep(5004);//删除投资项目信息失败
                    }
                } else {
                    map = writeResultRep(1004);//userId不匹配
                }
            } else {
                map = writeResultRep(5003);//投资项目信息不存在
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
            List<ProjectInfoEntity> projects = projectServer.findMyPublish(vo, userId);
            map.put("projects", projects);
            if (!projects.isEmpty()) {
                map.put("count", projects.size());
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
            List<ProjectInfoEntity> projects = projectServer.findCollect(vo, userId);
            map.put("projects", projects);
            if (!projects.isEmpty()) {
                map.put("count", projects.size());
            } else {
                map.put("count", 0);
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

}