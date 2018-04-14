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
package com.anssy.inter.base.action;

import com.anssy.inter.AppAction;
import com.anssy.inter.activity.vo.SearchVo;
import com.anssy.inter.base.server.NewsIndustryServer;
import com.anssy.inter.base.server.NewsPolicyServer;
import com.anssy.inter.base.vo.NewsPageVo;
import com.anssy.inter.base.vo.NewsVo;
import com.anssy.venturebar.base.entity.NewsEntity;
import com.google.gson.Gson;
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
@RequestMapping("/app/base/newsIndustryAction")
public class NewsIndustryAction extends AppAction {

    @Resource
    private NewsIndustryServer industryServer;

    /**
     * 发布政策信息接口
     *
     * @param {"image":"","title":"","details":"","source":"","url":""}
     */
    @RequestMapping(value = "/releaseNewsIndustry", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> releaseNewsIndustry(HttpServletRequest request) throws Exception {
        Map<String, Object> map;
        NewsEntity entity = new Gson().fromJson(getJson(request), NewsEntity.class);
        if (entity != null && StringUtils.isNotBlank(entity.getTitle())) {
            if (industryServer.releaseNews(entity)) {
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
     * 政策信息列表接口
     *
     * @param {"page":""}
     */
    @RequestMapping(value = "/newsIndustryList", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> newsIndustryList(HttpServletRequest request) throws Exception {
        NewsVo vo = new Gson().fromJson(getJson(request), NewsVo.class);
        // 兼容老版本的接口
        if (vo.getPage() == 0) {
            vo.setPage(1);
        }
        Map<String, Object> map = writeResultRep();
        List<NewsEntity> news = industryServer.findList(vo);
        map.put("news", news);
        if (!news.isEmpty()) {
            map.put("count", news.size());
        } else {
            map.put("count", 0);
        }
        return map;
    }
}