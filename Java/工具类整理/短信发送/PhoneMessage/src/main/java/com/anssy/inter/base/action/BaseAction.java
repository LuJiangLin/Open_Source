/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  BaseAction.java
 * PACKAGE      :  com.anssy.inter.base.action
 * CREATE DATE  :  2016-8-12
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.inter.base.action;

import com.google.gson.Gson;
import com.anssy.venturebar.base.entity.*;
import com.anssy.venturebar.base.vo.DatadictVo;
import com.anssy.inter.AppAction;
import com.anssy.inter.base.server.BaseServer;
import com.anssy.inter.base.server.UserInfoServer;
import com.anssy.inter.base.vo.*;
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
 *          接口_基础
 */
@Controller
@RequestMapping("/app/base/baseAction")
public class BaseAction extends AppAction {

    @Resource
    private BaseServer baseServer;
    @Resource
    private UserInfoServer userInfoServer;

    /**
     * 查询所有的省信息接口
     */
    @RequestMapping(value = "/findProvince", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> findProvince() throws Exception {
        List<ProvinceEntity> provinces = baseServer.findAllProvince();
        Map<String, Object> map = writeResultRep();
        map.put("provinces", provinces);
        return map;
    }

    /**
     * 通过省ID查询市信息接口
     * <p/>
     * request {"provinceId":""} provinceId 省ID
     */
    @RequestMapping(value = "/findCity", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> findCity(HttpServletRequest request) throws Exception {
        Map<String, Object> map;
        ProvinceVo vo = new Gson().fromJson(getJson(request), ProvinceVo.class);
        if (vo != null && vo.getProvinceId() != null) {
            map = writeResultRep();
            List<CityEntity> citys = baseServer.findCityByProvinceId(vo.getProvinceId());
            map.put("citys", citys);
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

    /**
     * 通过name反查省市县ID接口
     */
    @RequestMapping(value = "/findAreaIdByName", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> findAreaIdByName(HttpServletRequest request) throws Exception {
        Map<String, Object> map;
        NameVo vo = new Gson().fromJson(getJson(request), NameVo.class);
        if (vo != null && StringUtils.isNotBlank(vo.getName())) {
            map = writeResultRep();
            Long[] areaIds = baseServer.findAreaIdByName(vo.getName().trim());
            map.put("provinceId", areaIds[0]);
            map.put("cityId", areaIds[1]);
            map.put("areaId", areaIds[2]);
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

    /**
     * 通过市ID查询区县信息接口
     * <p/>
     * request {cityId:""} cityId 市ID
     */
    @RequestMapping(value = "/findArea", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> findArea(HttpServletRequest request) throws Exception {
        Map<String, Object> map;
        CityVo vo = new Gson().fromJson(getJson(request), CityVo.class);
        if (vo != null && vo.getCityId() != null) {
            List<AreaEntity> areas = baseServer.findAreaByCityId(vo.getCityId());
            map = writeResultRep();
            map.put("areas", areas);
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

    /**
     * 获取行业信息接口
     * <p/>
     * request {"type":"","fieldLevel":"","fatherId":""}
     * type 类型(1-投资人,2-导师)
     * fieldLevel 行业级别(1-一级行业,2-二级行业)
     * fatherId 父ID
     */
    @RequestMapping(value = "/findField", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> findField(HttpServletRequest request) throws Exception {
        Map<String, Object> map;
        FieldVo vo = new Gson().fromJson(getJson(request), FieldVo.class);
        if (vo != null && vo.getFatherId() != null) {
            map = writeResultRep();
            List<FieldInfoEntity> fields = baseServer.findField(vo);
            map.put("fields", fields);
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

    /**
     * 获取行业信息接口
     * <p/>
     * request {"type":""} type 类型(1-投资人,2-导师)
     */
    @RequestMapping(value = "/findFieldAll", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> findFieldAll(HttpServletRequest request) throws Exception {
        Map<String, Object> map;
        FieldVo vo = new Gson().fromJson(getJson(request), FieldVo.class);
        if (vo != null) {
            map = writeResultRep();
            List<FieldInfoEntity> fields = baseServer.findFieldAll(vo.getType());
            map.put("fields", fields);
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

    /**
     * 获取性别
     */
    @RequestMapping(value = "/findSex", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> findSex() throws Exception {
        Map<String, Object> map = writeResultRep();
        List<DatadictVo> dics = baseServer.findSex();
        map.put("sexs", dics);
        return map;
    }

    /**
     * 通过userId获取用户信息接口
     * <p/>
     * request {"userId":""}
     */
    @RequestMapping(value = "/findUserInfo", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> findUserInfo(HttpServletRequest request) throws Exception {
        Map<String, Object> map;
        UserIdVo vo = new Gson().fromJson(getJson(request), UserIdVo.class);
        if (vo != null && vo.getUserId() > 0) {
            map = writeResultRep();
            UserInfoEntity user = userInfoServer.replenish(userInfoServer.findUserById(vo.getUserId()));
            user.setPasswd("");
            user.setSign("");
            map.put("user", user);
            map.put("item", userInfoServer.replenish(userInfoServer.findUserItemByUserId(vo.getUserId())));

            // detail
            // simple
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

    /**
     * 获取首页轮播幻灯片接口
     * <p/>
     * request {"type":""}
     */
    @RequestMapping(value = "/findSlideList", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> findSlideList(HttpServletRequest request) throws Exception {
        Map<String, Object> map = writeResultRep();
        TypeVo vo = new Gson().fromJson(getJson(request), TypeVo.class);
        System.out.println("==========type=============\n type : "+vo.getType()+"\n==========type===============");
        if (vo != null) {
            System.out.println("==========type 1=============\n type : "+vo.getType()+"\n==========type 1===============");
            List<SlideInfoEntity> slides = baseServer.findSlideList(vo.getType());
            map.put("slides", slides);
            if (!slides.isEmpty()) {
                map.put("count", slides.size());
            } else {
                map.put("count", 0);
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

    /**
     * 点击查看轮播幻灯片的广告链接增加点击次数接口
     * <p/>
     * request {"slideId":""}
     */
    @RequestMapping(value = "/updateClickNum", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> updateClickNum(HttpServletRequest request) throws Exception {
        Map<String, Object> map;
        SlideIdVo vo = new Gson().fromJson(getJson(request), SlideIdVo.class);
        if (vo != null && vo.getSlideId() > 0) {
            map = writeResultRep();
            baseServer.updateClickNum(vo.getSlideId());
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

}