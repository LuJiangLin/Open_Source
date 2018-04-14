/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  SpringListener.java
 * PACKAGE      :  com.anssy.webcore.listener
 * CREATE DATE  :  2016-8-9
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.webcore.listener;

import com.anssy.venturebar.app.server.PvInfoCacheServer;
import com.anssy.venturebar.log.server.SystemLogServer;
import com.anssy.webcore.core.cache.LoadCacheService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author make it
 * @version SVN #V1# #2016-8-9#
 *          spring监听器
 */
@Service
public class SpringListener implements ApplicationListener<ContextRefreshedEvent> {

    @Resource
    private LoadCacheService loadCacheService;
    @Resource
    private SystemLogServer systemLogServer;
    @Resource
    private PvInfoCacheServer pvInfoCacheServer;

    public SpringListener() {

    }

    /**
     * 在web项目中(spring mvc)系统会存在两个容器,
     * 一个是root application context,
     * 另一个就是我们自己的 projectName-servlet context(作为root application context的子容器)
     */
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() != null) {
            pvInfoCacheServer.clearPVNum();
            loadCacheService.loadCacheAll();
            systemLogServer.putInfo("ContextRefreshed");
        }
    }

}