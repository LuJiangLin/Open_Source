/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  PvInfoServer.java
 * PACKAGE      :  com.anssy.venturebar.app.server
 * CREATE DATE  :  2016-8-30
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.venturebar.app.server;

import com.anssy.venturebar.app.dao.PvInfoDao;
import com.anssy.venturebar.app.entity.PvInfoEntity;
import com.anssy.webcore.common.DateTimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;

/**
 * @author make it
 * @version SVN #V1# #2016-8-30#
 *          APP_PV信息
 */
@Service("pvInfoServer")
public class PvInfoServer {

    @Resource
    private PvInfoDao pvInfoDao;
    @Resource
    private PvInfoCacheServer pvInfoCacheServer;

    /**
     * PV操作
     */
    public void pvOperation() throws Exception {
        Long pvNum = pvInfoCacheServer.findPVNum();
        pvInfoCacheServer.clearPVNum();
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY) + 1;
        String appDate = DateTimeUtil.getCurDate().replaceAll("-", "");
        PvInfoEntity info = new PvInfoEntity();
        info.setAppDate(appDate);
        info.setPvHour(hour);
        info.setPvNum(pvNum);
        PvInfoEntity temp = pvInfoDao.findPvInfo(info);
        if (temp == null) {
            pvInfoDao.insertPvInfo(info);
        } else if (pvNum != null && pvNum > 0) {
            info.setId(temp.getId());
            pvInfoDao.updatePvInfo(info);
        }
    }

}