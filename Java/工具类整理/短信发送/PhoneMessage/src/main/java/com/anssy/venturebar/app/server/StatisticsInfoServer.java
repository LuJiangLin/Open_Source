/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  StatisticsInfoServer.java
 * PACKAGE      :  com.anssy.venturebar.app.server
 * CREATE DATE  :  2016-8-31
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.venturebar.app.server;

import com.anssy.venturebar.app.dao.PvInfoDao;
import com.anssy.venturebar.app.dao.StatisticsInfoDao;
import com.anssy.venturebar.app.entity.StatisticsInfoEntity;
import com.anssy.venturebar.base.dao.UserItemDao;
import com.anssy.venturebar.log.dao.LoginLogDao;
import com.anssy.webcore.common.DateTimeUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author make it
 * @version SVN #V1# #2016-8-31#
 *          APP_统计信息
 */
@Service("statisticsInfoServer")
public class StatisticsInfoServer {

    @Resource
    private StatisticsInfoDao statisticsInfoDao;
    @Resource
    private PvInfoDao pvInfoDao;
    @Resource
    private LoginLogDao loginLogDao;
    @Resource
    private UserItemDao userItemDao;

    /**
     * 统计信息操作
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void statisticsOperation() throws Exception {
        Date today = DateTimeUtil.preDay(new Date());
        String appDate = DateTimeUtil.getFormatDate(today).replaceAll("-", "");
        Date begin = DateTimeUtil.beginOfDay(today);
        Date end = DateTimeUtil.endOfDay(today);
        StatisticsInfoEntity info = statisticsInfoDao.findStatistics(appDate);
        if (info == null) {
            info = new StatisticsInfoEntity();
            info.setAppDate(appDate);
            Long loginNum = loginLogDao.findLoginNum(begin, end);
            if (loginNum == null) {
                loginNum = 0L;
            }
            info.setLoginNumber(loginNum);
            Long reNum = userItemDao.findRegisterNum(begin, end);
            if (reNum == null) {
                reNum = 0L;
            }
            info.setRegisterNumber(reNum);
            Long actNum = userItemDao.findActivateNum(begin, end);
            if (actNum == null) {
                actNum = 0L;
            }
            info.setActivateNumber(actNum);
            Long pv = pvInfoDao.findPvByAppDate(appDate);
            if (pv == null) {
                pv = 0L;
            }
            info.setPv(pv);
            statisticsInfoDao.insertStatistics(info);
        }
    }

}