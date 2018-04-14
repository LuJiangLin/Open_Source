/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  SiteAction.java
 * PACKAGE      :  com.anssy.inter.site.action
 * CREATE DATE  :  2016-8-12
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.inter.site.action;

import com.anssy.inter.AppAction;
import com.anssy.inter.site.server.SiteServer;
import com.anssy.inter.site.vo.SearchVo;
import com.anssy.inter.site.vo.SiteIdVo;
import com.anssy.inter.site.vo.SiteVo;
import com.anssy.inter.site.vo.TypeVo;
import com.anssy.venturebar.base.entity.SiteTypeAllEntity;
import com.anssy.venturebar.base.entity.SiteTypeEntity;
import com.anssy.venturebar.site.entity.SiteInfoEntity;
import com.anssy.venturebar.site.entity.SiteItemEntity;
import com.anssy.webcore.vo.MyCollectVo;
import com.anssy.webcore.vo.PublishVo;
import com.anssy.webcore.vo.ReferralsVo;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author make it
 * @version SVN #V1# #2016-8-12#
 *          接口_场地
 */
@Controller
@RequestMapping("/app/site/siteAction")
public class SiteAction extends AppAction {

    @Resource
    private SiteServer siteServer;

    /**
     * 获取场地类型(一级/二级)
     *
     * @param {"siteLevel":"","fatherId":""}
     */
    @RequestMapping(value = "/findSiteType", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> findSiteType(HttpServletRequest request) throws Exception {
        Map<String, Object> map;
        TypeVo vo = new Gson().fromJson(getJson(request), TypeVo.class);
        if (vo != null && vo.getFatherId() != null) {
            map = writeResultRep();
            List<SiteTypeEntity> types = siteServer.findSiteType(vo);
            map.put("SiteType", types);
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

    /**
     * 获取场地类型(全部)
     */
    @RequestMapping(value = "/findSiteTypeAll", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> findSiteTypeAll() throws Exception {
        Map<String, Object> map = writeResultRep();


        SiteTypeAllEntity defallType = new SiteTypeAllEntity();
        defallType.setId(0L);
        defallType.setFatherId(0L);
        defallType.setSiteLevel(1);
        defallType.setSiteName("全部");

        ArrayList<SiteTypeEntity> defsublist = new ArrayList<SiteTypeEntity>();
        SiteTypeEntity defsubType = new SiteTypeEntity();
        defsubType.setId(0L);
        defsubType.setFatherId(0L);
        defsubType.setSiteLevel(2);
        defsubType.setSiteName("全部类型");
        defsublist.add(defsubType);
        defallType.setSubType(defsublist);

        ArrayList<SiteTypeAllEntity> typelist = new ArrayList<SiteTypeAllEntity>();
        // 添加默认项
        typelist.add(defallType);

        List<SiteTypeEntity> types = siteServer.findSiteTypeAll();
        for (SiteTypeEntity type : types) {
            if (type.getSiteLevel() == 1) {
                // 数据转移到输出对象
                SiteTypeAllEntity allType = new SiteTypeAllEntity();
                allType.setId(type.getId());
                allType.setSiteLevel(type.getSiteLevel());
                allType.setSiteName(type.getSiteName());
                allType.setFatherId(type.getFatherId());

                ArrayList<SiteTypeEntity> subtypelist = new ArrayList<SiteTypeEntity>();
                SiteTypeEntity subType = new SiteTypeEntity();
                // 为子列表添加一项默认的选项 (全部)
                subType.setId(type.getId());
                subType.setFatherId(0L);
                subType.setSiteLevel(2);
                subType.setSiteName("全部" + type.getSiteName());
                subtypelist.add(subType);
                for (SiteTypeEntity type1 : types) {
                    if (type1.getSiteLevel() == 2 && type.getId() == type1.getFatherId()) {
                        subtypelist.add(type1);
                    }
                }
                allType.setSubType(subtypelist);
                typelist.add(allType);
            }
        }

        map.put("SiteType", typelist);
        return map;
    }

    /**
     * 发布场地信息接口
     *
     * @param {"siteImage":"","type":"","siteName":"","title":"","provinceId":"","cityId":"","areaId":"","price":"","rent":"","acreage":"","email":"","linkman":"","phone":"","describe":"","siteAddress":"","fitment":"","direction":"","storey":"","property":"","years":"","url":"","endTime":""}
     */
    @RequestMapping(value = "/releaseSite", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> releaseSite(HttpServletRequest request) throws Exception {
        Long userId = Long.parseLong(request.getHeader("userId"));
        Map<String, Object> map;
        SiteVo vo = new Gson().fromJson(getJson(request), SiteVo.class);
        if (vo != null && StringUtils.isNotBlank(vo.getSiteName()) &&
                StringUtils.isNotBlank(vo.getTitle()) &&
                vo.getProvinceId() != null && vo.getCityId() != null &&
                vo.getAreaId() != null &&
                StringUtils.isNotBlank(vo.getSiteAddress()) &&
                StringUtils.isNotBlank(vo.getLinkman()) &&
                StringUtils.isNotBlank(vo.getPhone()) &&
                StringUtils.isNotBlank(vo.getDescribe())) {
            boolean flag = siteServer.releaseSite(vo, userId);
            if (flag) {
                map = writeResultRep();
            } else {
                map = writeResultRep(3001);//发布场地信息失败
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

    /**
     * 修改场地信息接口
     *
     * @param {"id":"","siteImage":"","type":"","siteName":"","title":"","provinceId":"","cityId":"","areaId":"","price":"","rent":"","acreage":"","email":"","linkman":"","phone":"","describe":"","siteAddress":"","fitment":"","direction":"","storey":"","property":"","years":"","url":"","endTime":""}
     */
    @RequestMapping(value = "/updateSite", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> updateSite(HttpServletRequest request) throws Exception {
        Long userId = Long.parseLong(request.getHeader("userId"));
        Map<String, Object> map;
        SiteVo vo = new Gson().fromJson(getJson(request), SiteVo.class);
        if (vo != null && vo.getId() != null && StringUtils.isNotBlank(vo.getSiteName()) &&
                StringUtils.isNotBlank(vo.getTitle()) &&
                vo.getProvinceId() != null && vo.getCityId() != null &&
                vo.getAreaId() != null &&
                StringUtils.isNotBlank(vo.getSiteAddress()) &&
                StringUtils.isNotBlank(vo.getLinkman()) &&
                StringUtils.isNotBlank(vo.getPhone()) &&
                StringUtils.isNotBlank(vo.getDescribe())) {
            SiteInfoEntity info = siteServer.findSiteInfoById(vo.getId());
            SiteItemEntity item = siteServer.findSiteItemById(vo.getId());
            if (info != null && item != null) {
                if (item.getPublishId().equals(userId)) {
                    boolean flag = siteServer.updateSite(vo);
                    if (flag) {
                        map = writeResultRep();
                    } else {
                        map = writeResultRep(3006);//修改场地信息失败
                    }
                } else {
                    map = writeResultRep(1004);//userId不匹配
                }
            } else {
                map = writeResultRep(3003);//场地信息不存在
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }


    /**
     * 场地信息列表接口
     *
     * @param {"listType":"1","page":"","longitude":"","latitude":"","search":""}
     * @param {"listType":"2","page":"","type":"","provinceId":"0","cityId":"0","areaId":"0","search":""}
     * @param {"listType":"3","page":"","capacityType":"","provinceId":"0","cityId":"0","areaId":"0","search":""}
     */
    @RequestMapping(value = "/siteList", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> siteList(HttpServletRequest request) throws Exception {
        Map<String, Object> map;
        SearchVo vo = new Gson().fromJson(getJson(request), SearchVo.class);
        if (vo != null) {
            //搜索方式(1-按GPS进行附近搜索,2-按类型进行场地类型搜索,3-智能搜索)
            if (vo.getListType() == 1 && StringUtils.isNotBlank(vo.getLatitude())
                    && StringUtils.isNotBlank(vo.getLongitude())) {
                map = writeResultRep();
                List<SiteInfoEntity> sites = siteServer.findListByGPS(vo);
                map.put("sites", sites);
                if (!sites.isEmpty()) {
                    map.put("count", sites.size());
                } else {
                    map.put("count", 0);
                }
            } else if (vo.getListType() == 2 && vo.getType() != null) {
                map = writeResultRep();
                List<SiteInfoEntity> sites = siteServer.findListByType(vo);
                map.put("sites", sites);
                if (!sites.isEmpty()) {
                    map.put("count", sites.size());
                } else {
                    map.put("count", 0);
                }
            } else if (vo.getListType() == 3) {
                map = writeResultRep();
                List<SiteInfoEntity> sites = siteServer.findListByPv(vo);
                map.put("sites", sites);
                if (!sites.isEmpty()) {
                    map.put("count", sites.size());
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
     * 推荐场地信息接口
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
            List<SiteInfoEntity> sites = siteServer.referrals(vo);
            map.put("sites", sites);
            if (!sites.isEmpty()) {
                map.put("count", sites.size());
            } else {
                map.put("count", 0);
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

    /**
     * 查看场地信息接口
     *
     * @param {"siteId":""}
     */
    @RequestMapping(value = "/seeSiteInfo", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> seeSiteInfo(HttpServletRequest request) throws Exception {
        Map<String, Object> map;
        SiteIdVo vo = new Gson().fromJson(getJson(request), SiteIdVo.class);
        if (vo != null && vo.getSiteId() != null) {
            siteServer.updatePV(vo.getSiteId());
            SiteInfoEntity info = siteServer.replenish(siteServer.findSiteInfoById(vo.getSiteId()));
            SiteItemEntity item = siteServer.replenish(siteServer.findSiteItemById(vo.getSiteId()));
            if (info != null && item != null) {
                map = writeResultRep();
                map.put("info", info);
                map.put("item", item);
            } else {
                map = writeResultRep(3002);//查询场地信息失败
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

    /**
     * 场地信息点赞接口
     *
     * @param {"siteId":""}
     */
    @RequestMapping(value = "/praise", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> praise(HttpServletRequest request) throws Exception {
        Map<String, Object> map;
        SiteIdVo vo = new Gson().fromJson(getJson(request), SiteIdVo.class);
        if (vo != null && vo.getSiteId() != null) {
            SiteInfoEntity info = siteServer.findSiteInfoById(vo.getSiteId());
            if (info != null) {
                if (siteServer.updatePraise(vo.getSiteId()) > 0) {
                    map = writeResultRep();
                } else {
                    map = writeResultRep(3005);//场地信息点赞失败
                }
            } else {
                map = writeResultRep(3003);//场地信息不存在
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

    /**
     * 场地信息权重接口
     *
     * @param {"siteId":""}
     */
    @RequestMapping(value = "/weight", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> weight(HttpServletRequest request) throws Exception {
        Long userId = Long.parseLong(request.getHeader("userId"));
        Map<String, Object> map;
        SiteIdVo vo = new Gson().fromJson(getJson(request), SiteIdVo.class);
        if (vo != null && vo.getSiteId() != null) {
            SiteInfoEntity info = siteServer.findSiteInfoById(vo.getSiteId());
            SiteItemEntity item = siteServer.findSiteItemById(vo.getSiteId());
            if (info != null && item != null) {
                if (userId.equals(item.getPublishId())) {
                    if (siteServer.updateWeight(vo.getSiteId()) > 0) {
                        map = writeResultRep();
                    } else {
                        map = writeResultRep(50);//修改权重失败
                    }
                } else {
                    map = writeResultRep(1004);//userId不匹配
                }
            } else {
                map = writeResultRep(3003);//场地信息不存在
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }


    /**
     * 删除场地信息接口
     *
     * @param {"siteId":""}
     */
    @RequestMapping(value = "/deleteSite", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> deleteSite(HttpServletRequest request) throws Exception {
        Long userId = Long.parseLong(request.getHeader("userId"));
        Map<String, Object> map;
        SiteIdVo vo = new Gson().fromJson(getJson(request), SiteIdVo.class);
        if (vo != null && vo.getSiteId() != null) {
            SiteItemEntity item = siteServer.findSiteItemById(vo.getSiteId());
            if (item != null) {
                if (item.getPublishId().equals(userId)) {
                    if (siteServer.deleteSite(vo.getSiteId())) {
                        map = writeResultRep();
                    } else {
                        map = writeResultRep(3004);//删除场地信息失败
                    }
                } else {
                    map = writeResultRep(1004);//userId不匹配
                }
            } else {
                map = writeResultRep(3003);//场地信息不存在
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
            List<SiteInfoEntity> sites = siteServer.findMyPublish(vo, userId);
            map.put("sites", sites);
            if (!sites.isEmpty()) {
                map.put("count", sites.size());
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
            List<SiteInfoEntity> sites = siteServer.findCollect(vo, userId);
            map.put("sites", sites);
            if (!sites.isEmpty()) {
                map.put("count", sites.size());
            } else {
                map.put("count", 0);
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

}