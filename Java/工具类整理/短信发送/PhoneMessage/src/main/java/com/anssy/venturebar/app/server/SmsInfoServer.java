/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  SmsInfoServer.java
 * PACKAGE      :  com.anssy.venturebar.app.server
 * CREATE DATE  :  2016-8-28
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.venturebar.app.server;

import com.anssy.venturebar.app.dao.SmsInfoDao;
import com.anssy.venturebar.app.entity.SmsInfoEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author make it
 * @version SVN #V1# #2016-8-28#
 *          APP_短信信息
 */
@Service("smsInfoServer")
public class SmsInfoServer {

    @Resource
    private SmsInfoDao smsInfoDao;

    /**
     * 保存短信信息
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveSMS(String mobile, String content, Long balance, String state) {
        try {
            SmsInfoEntity info = new SmsInfoEntity();
            info.setSendTime(new Date());
            info.setMobile(mobile);
            info.setContent(content);
            info.setBalance(balance);
            info.setState(state);
            smsInfoDao.insertSMSInfo(info);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}