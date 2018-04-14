/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  CollectAction.java
 * PACKAGE      :  com.anssy.inter.collect.action
 * CREATE DATE  :  2016-8-3
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.inter.collect.action;

import com.google.gson.Gson;
import com.anssy.venturebar.base.vo.DatadictVo;
import com.anssy.venturebar.collect.entity.CollectInfoEntity;
import com.anssy.inter.AppAction;
import com.anssy.inter.collect.server.CollectServer;
import com.anssy.inter.collect.vo.CollectVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author make it
 * @version SVN #V1# #2016-8-3#
 *          收藏_收藏信息
 */
@Controller
@RequestMapping("/app/coolect/collectAction")
public class CollectAction extends AppAction {

    @Resource
    private CollectServer collectServer;

    /**
     * 获取收藏类型接口
     */
    @RequestMapping(value = "/findCollectType", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> findCollectType() throws Exception {
        Map<String, Object> map = writeResultRep();
        List<DatadictVo> types = collectServer.findCollectType();
        map.put("types", types);
        return map;
    }

    /**
     * 收藏/取消收藏信息接口
     *
     * @param {"type":"","fid":""}
     */
    @RequestMapping(value = "/releaseCollect", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> releaseCollect(HttpServletRequest request) throws Exception {
        Long userId = Long.parseLong(request.getHeader("userId"));
        Map<String, Object> map;
        CollectVo vo = new Gson().fromJson(getJson(request), CollectVo.class);
        if (vo != null && vo.getFid() != null) {
            CollectInfoEntity collect = collectServer.findCollectByType(vo, userId);
            if (collect == null) {//收藏
                boolean flag = collectServer.collect(vo, userId);
                if (flag) {
                    map = writeResultRep();
                } else {
                    map = writeResultRep(30);//收藏失败
                }
            } else {//取消收藏
                boolean flag = collectServer.cancelCollect(vo, userId);
                if (flag) {
                    map = writeResultRep();
                } else {
                    map = writeResultRep(31);//取消收藏失败
                }
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

    /**
     * 查看是否收藏此信息接口
     *
     * @param {"type":"","fid":""}
     */
    @RequestMapping(value = "/findWhetherCollect", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> findWhetherCollect(HttpServletRequest request) throws Exception {
        Long userId = Long.parseLong(request.getHeader("userId"));
        Map<String, Object> map = new HashMap<String, Object>();
        CollectVo vo = new Gson().fromJson(getJson(request), CollectVo.class);
        if (vo != null && vo.getFid() != null) {
            CollectInfoEntity collect = collectServer.findCollectByType(vo, userId);
            if (collect != null && collect.getId() > 0) {
                map = writeResultRep(32);//已收藏
            } else {
                map = writeResultRep(33);//未收藏
            }
        }
        return map;
    }

    /**
     * 查询我的收藏信息接口(某模块)
     *
     * @param {"type":""}
     */
    @RequestMapping(value = "/findCollect", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> findCollect(HttpServletRequest request) throws Exception {
        Long userId = Long.parseLong(request.getHeader("userId"));
        Map<String, Object> map = new HashMap<String, Object>();
        CollectVo vo = new Gson().fromJson(getJson(request), CollectVo.class);
        if (vo != null) {
            map = writeResultRep();
            List<CollectInfoEntity> collects = collectServer.findCollect(vo, userId);
            map.put("collects", collects);
            if (!collects.isEmpty()) {
                map.put("count", collects.size());
            } else {
                map.put("count", 0);
            }
        }
        return map;
    }
}