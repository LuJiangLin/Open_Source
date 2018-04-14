/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  QuartzServer.java
 * PACKAGE      :  com.anssy.webcore.quartz
 * CREATE DATE  :  2016-8-12
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.webcore.quartz;

import com.anssy.inter.base.server.NewsIndustryServer;
import com.anssy.inter.base.server.NewsPolicyServer;
import com.anssy.inter.invest.server.ProjectServer;
import com.anssy.inter.service.server.PolicyElucidationServer;
import com.anssy.venturebar.app.server.PvInfoServer;
import com.anssy.venturebar.app.server.StatisticsInfoServer;
import com.anssy.venturebar.log.server.LoginLogServer;
import com.anssy.venturebar.log.server.SystemLogServer;
import com.anssy.inter.activity.server.ActivityServer;
import com.anssy.inter.site.server.SiteServer;
import com.anssy.webcore.common.BaseConstants;
import com.anssy.webcore.common.DateTimeUtil;
import com.anssy.webcore.core.cache.LoadCacheService;
import com.sun.webkit.PolicyClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author make it
 * @version SVN #V1# #2016-8-12#
 *          定时器
 */
@Service("quartzServer")
public class QuartzServer {

    @Resource
    private LoadCacheService loadCacheService;
    @Resource
    private LoginLogServer loginLogServer;
    @Resource
    private SystemLogServer systemLogServer;
    @Resource
    private PvInfoServer pvInfoServer;
    @Resource
    private StatisticsInfoServer statisticsInfoServer;
    @Resource
    private SiteServer siteServer;
    @Resource
    private ProjectServer projectServer;
    @Resource
    private ActivityServer activityServer;
    @Resource
    private NewsPolicyServer policyServer;
    @Resource
    private NewsIndustryServer industryServer;

    /**
     * 定时刷新缓存数据
     */
    public void refreshCache() throws Exception {
        loadCacheService.loadCacheAll();
    }

    /**
     * 高频率
     */
    public void highFrequency() throws Exception {
        pvInfoServer.pvOperation();
        loginLogServer.addLog();
        systemLogServer.addLog();

    }

    /**
     * 每天执行一次
     */
    public void everyDay() throws Exception {
        Date delDate = DateTimeUtil.afterDaysSinceDate(new Date(), -(BaseConstants.LOG_DAYS));
        try {
            statisticsInfoServer.statisticsOperation();
            loginLogServer.deleteLoginLog(delDate);
            systemLogServer.deleteSystemLog(delDate);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            systemLogServer.putInfo("statisticsOperation");
            systemLogServer.putInfo("clearLog");
            overdue();
        }
    }

    /**
     * 每半小时执行一次
     */
    public void everyHour() throws Exception {

        System.out.println("=================》数据同步开始！");

        System.out.println("=================》定时从创业在线拉取政策数据！");
        // 定时从创业在线拉取政策数据
        policyServer.loadPolcyFormStartupOnline();
        System.out.println("=================》定时从创业在线拉取行业新闻数据！");
        // 定时从创业在线拉取行业新闻数据
        industryServer.loadIndustryFormStartupOnline();
        System.out.println("=================》从创业在线获取场地数据！");
        // 从创业在线获取场地数据
        siteServer.loadSiteFormStartupOnline();
        System.out.println("=================》从创业在线获取项目数据！");
        // 从创业在线获取项目数据
        projectServer.loadProjectFormStartupOnline();
        System.out.println("=================》从创业在线获取活动数据！");
        // 从创业在线获取活动数据
        activityServer.loadActivityFormStartupOnline();
        System.out.println("=================》数据同步完成！");

    }

    /**
     * 过期
     */
    private void overdue() {
        try {
            siteServer.overdue(new Date());
            activityServer.overdue(new Date());
        } finally {
            systemLogServer.putInfo("overdue");
        }
    }

}