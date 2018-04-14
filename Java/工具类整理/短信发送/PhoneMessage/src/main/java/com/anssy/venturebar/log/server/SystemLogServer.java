/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  SystemLogServer.java
 * PACKAGE      :  com.anssy.venturebar.log.server
 * CREATE DATE  :  2016-8-25
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.venturebar.log.server;

import com.anssy.venturebar.log.dao.SystemLogDao;
import com.anssy.venturebar.log.entity.SystemLogEntity;
import org.apache.commons.lang.StringUtils;
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
 *          日志_系统日志
 */
@Service("systemLogServer")
public class SystemLogServer {

    private Queue<SystemLogEntity> queue = new LinkedList<SystemLogEntity>();

    @Resource
    private SystemLogDao systemLogDao;

    /**
     * java 队列
     */
    public synchronized void putInfo(String logDesc) {
        try {
            if (StringUtils.isNotBlank(logDesc)) {
                SystemLogEntity log = new SystemLogEntity();
                log.setLogTime(new Date());
                log.setLogDesc(logDesc);
                queue.offer(log);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private synchronized SystemLogEntity pollInfo() {
        return queue.poll();
    }

    /**
     * 添加日志  Quartz
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void addLog() {
        SystemLogEntity log = pollInfo();
        while (log != null) {
            try {
                systemLogDao.insertSystemLog(log);
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
    public void deleteSystemLog(Date delDate) throws Exception {
        systemLogDao.deleteSystemLog(delDate);
    }

}