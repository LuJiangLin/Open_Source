/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  QuartzListener.java
 * PACKAGE      :  com.anssy.webcore.listener
 * CREATE DATE  :  2016-8-26
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.webcore.listener;

import com.anssy.webcore.core.context.ApplicationUtil;
import org.quartz.Scheduler;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author make it
 * @version SVN #V1# #2016-8-26#
 *          spring Quartz 监听器
 *          quartz定时任务(root application context)和运行项目的容器(projectName-servlet context),是两个容器。
 *          因此有可能当项目还没有加载好数据源(数据库连接)时，就已经在执行定时任务栏。
 *          运行项目的容器已经关闭(数据源断开)，而定时器任务还在执行。
 */
public class QuartzListener implements ServletContextListener {

    /**
     * tomcat开启时-定时器job延后
     */
    public void contextInitialized(ServletContextEvent sce) {
        try {
            Scheduler scheduler = (Scheduler) ApplicationUtil.getBean("quartzFactory");
            scheduler.pauseAll();
            Thread.sleep(10000);
            scheduler.resumeAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * tomcat关闭时停止定时器job
     */
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            Scheduler scheduler = (Scheduler) ApplicationUtil.getBean("quartzFactory");
            scheduler.shutdown(true);
            Thread.sleep(10000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}