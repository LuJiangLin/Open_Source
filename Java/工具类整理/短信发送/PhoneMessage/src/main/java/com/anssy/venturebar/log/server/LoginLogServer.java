/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  LoginLogServer.java
 * PACKAGE      :  com.anssy.venturebar.log.server
 * CREATE DATE  :  2016-8-25
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.venturebar.log.server;

import com.anssy.venturebar.log.dao.LoginLogDao;
import com.anssy.venturebar.log.entity.LoginLogEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author make it
 * @version SVN #V1# #2016-8-25#
 *          日志_登录日志
 */
@Service("loginLogServer")
public class LoginLogServer {

    private Queue<LoginLogEntity> queue = new LinkedList<LoginLogEntity>();

    @Resource
    private LoginLogDao loginLogDao;

    /**
     * java 队列
     */
    public synchronized void putInfo(LoginLogEntity log) {
        try {
            queue.offer(log);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private synchronized LoginLogEntity pollInfo() {
        return queue.poll();
    }

    /**
     * 添加日志  Quartz
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void addLog() {
        LoginLogEntity log = pollInfo();
        while (log != null) {
            try {
                if (log.getLoginTime() != null) {
                    loginLogDao.insertLoginLog(log);
                } else {
                    Date logoutTime = log.getLogoutTime();
                    log = loginLogDao.findLoginLogByUserId(log.getUserId());
                    if (log != null) {
                        log.setLogoutTime(logoutTime);
                        loginLogDao.updateLoginLog(log);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                log = pollInfo();
            }
        }
    }

    /**
     * 清理日志
     */
    public void deleteLoginLog(Date delDate) throws Exception {
        loginLogDao.deleteLoginLog(delDate);
    }

}