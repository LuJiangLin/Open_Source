/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  PvMonitoring.java
 * PACKAGE      :  com.anssy.inter.authority
 * CREATE DATE  :  2016-8-30
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.inter.authority;

import com.anssy.venturebar.app.server.PvInfoCacheServer;
import com.anssy.webcore.core.context.ApplicationUtil;

/**
 * @author make it
 * @version SVN #V1# #2016-8-30#
 *          PV监控
 */
public class PvMonitoring {

    private static PvMonitoring pv = new PvMonitoring();

    private PvInfoCacheServer pvInfoCacheServer;

    private PvMonitoring() {
        pvInfoCacheServer = (PvInfoCacheServer) ApplicationUtil.getBean("pvInfoCacheServer");
    }

    public static boolean getInstance() {
        return pv.monitoring();
    }

    private boolean monitoring() {
        boolean flag;
        try {
            pvInfoCacheServer.addPVNum();
            flag = true;
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

}