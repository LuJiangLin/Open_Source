/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  UserInfoServer.java
 * PACKAGE      :  com.anssy.inter.base.server
 * CREATE DATE  :  2016-8-11
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.inter.base.server;

import com.anssy.venturebar.app.server.SmsInfoServer;
import com.anssy.venturebar.base.dao.DatadictDao;
import com.anssy.venturebar.base.dao.UserInfoDao;
import com.anssy.venturebar.base.dao.UserItemDao;
import com.anssy.venturebar.base.entity.UserInfoEntity;
import com.anssy.venturebar.base.entity.UserItemEntity;
import com.anssy.venturebar.base.server.DatadictCacheServer;
import com.anssy.venturebar.base.server.FieldInfoCacheServer;
import com.anssy.venturebar.base.server.UserInfoCacheServer;
import com.anssy.venturebar.base.server.VerifyCacheServer;
import com.anssy.venturebar.base.vo.DatadictVo;
import com.anssy.venturebar.log.entity.LoginLogEntity;
import com.anssy.venturebar.log.server.LoginLogServer;
import com.anssy.inter.base.vo.UserVo;
import com.anssy.inter.base.vo.VerifyVo;
import com.anssy.inter.picture.server.PictureServer;
import com.anssy.webcore.common.EmailUtils;
import com.anssy.webcore.common.SmsUtil;
import com.anssy.webcore.common.StringRandomUtil;
import com.anssy.webcore.core.encryption.BASE64;
import com.anssy.webcore.core.encryption.MD5;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author make it
 * @version SVN #V1# #2016-8-11#
 * @文件描述 基础_用户信息
 */
@Service("userInfoServer")
public class UserInfoServer {

    private static final Lock lock = new ReentrantLock();

    @Resource
    private DatadictDao datadictDao;
    @Resource
    private UserInfoDao userInfoDao;
    @Resource
    private UserItemDao userItemDao;
    @Resource
    private UserInfoCacheServer userInfoCacheServer;
    @Resource
    private LoginLogServer loginLogServer;
    @Resource
    private VerifyCacheServer verifyCacheServer;
    @Resource
    private PictureServer pictureServer;
    @Resource
    private DatadictCacheServer datadictCacheServer;
    @Resource
    private FieldInfoCacheServer fieldInfoCacheServer;
    @Resource
    private SmsInfoServer smsInfoServer;


    /**
     * 获取用户角色
     */
    public List<DatadictVo> findUserRoles() throws Exception {
        return datadictDao.findDictByCategory("USER_ROLES");
    }

    /**
     * 发送验证码
     */
    public boolean sendVerifyCode(String phone) throws Exception {
        boolean flag = false;
        SmsUtil ob = new SmsUtil();
        StringBuilder sb = new StringBuilder();
        String state = "1#1";
        Long balance = 0l;
        try {
            Long code = StringRandomUtil.RandomCode(6);
            sb.append("尊敬的用户，");
            sb.append("本次验证码为").append(code).append("。");
            sb.append("有效期10分钟！");
            sb.append("【湖北科技创业服务中心】");

            System.out.println(phone + "的手机验证码是: " + code);

            verifyCacheServer.setCode(phone, code);
            state = ob.sendSms(phone, sb.toString());
            balance = ob.findBalance();
            flag = true;
        } catch (Exception e) {
            flag = false;
        } finally {
            smsInfoServer.saveSMS(phone, sb.toString(), balance, state);
        }
        return flag;
    }

    /**
     * 验证验证码
     *
     * @return -1-失效/-2验证码错误, 1-验证成功
     */
    public int verifyCode(VerifyVo vo) throws Exception {
        int mark = -1;
        try {
            Long code = verifyCacheServer.findCode(vo.getPhone());
            if (code != null) {
                if (code.toString().equals(vo.getCode())) {
                    mark = 1;
                } else {
                    mark = -2;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            mark = -1;
        }
        return mark;
    }

    /**
     * 记录登录登出日志
     *
     * @param type 类型(1-登入,2-登出)
     */
    public void putInfoLog(Long userId, int type, String phoneTerrace,
                           String phoneVersion, String appVersion) throws Exception {
        LoginLogEntity log = new LoginLogEntity();
        log.setUserId(userId);
        if (type == 1) {
            log.setLoginTime(new Date());
        } else if (type == 2) {
            log.setLogoutTime(new Date());
        }
        if (StringUtils.isNotBlank(phoneTerrace)) {
            log.setPhoneTerrace(phoneTerrace);
        }
        if (StringUtils.isNotBlank(phoneVersion)) {
            log.setPhoneVersion(phoneVersion);
        }
        if (StringUtils.isNotBlank(appVersion)) {
            log.setAppVersion(appVersion);
        }
        loginLogServer.putInfo(log);
    }

    /**
     * 通过手机号码和密码登录
     *
     * @param phone 手机号码
     */
    public UserInfoEntity loginByPhone(String phone) throws Exception {
        return userInfoDao.findUserByPhone(phone);
    }

    /**
     * 通过Email和密码登录
     *
     * @param email Email
     */
    public UserInfoEntity loginByEmail(String email) throws Exception {
        return userInfoDao.findUserByEmail(email);
    }

    /**
     * 用户注册
     * 注册方法加上lock机制,确保手机号码和邮箱唯一
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public int register(UserVo vo) throws Exception {
        int mark = 0;
        lock.lock();
        try {
            UserInfoEntity tempUser = userInfoDao.findUserByPhone(vo.getPhone());
            if (tempUser == null) {
                if (StringUtils.isNotBlank(vo.getEmail())) {
                    tempUser = userInfoDao.findUserByEmail(vo.getEmail());
                    if (tempUser == null) {
                        mark = insertUserInfo(vo);
                    } else {
                        mark = -2;
                    }
                } else {
                    mark = insertUserInfo(vo);
                }
            } else {
                mark = -1;
            }
        } finally {
            lock.unlock();
        }
        return mark;
    }

    /**
     * 添加用户信息
     */
    private int insertUserInfo(UserVo vo) throws Exception {
        int mark;
        Long id = userInfoDao.findId();
        UserInfoEntity user = new UserInfoEntity();
        user.setId(id);
        user.setPhone(vo.getPhone());
        user.setNickname(vo.getPhone());//昵称默认使用手机号码
        if (StringUtils.isNotBlank(vo.getEmail())) {
            user.setEmail(vo.getEmail());
        }
        user.setSign(getSign(user.getPhone(), vo.getPasswd()));
        user.setPasswd(findPasswd(vo.getPasswd()));
        user.setRoles(vo.getRoles());
        user.setState(0);//状态(0-正常,1-锁定)
        user.setIsActivate(0);//是否激活(email邮件激活) (0-未激活,1-已激活)
        mark = userInfoDao.register(user);
        if (mark > 0) {
            mark = insertUserItem(id);
        }
        return mark;
    }

    /**
     * 注册时添加用户明细信息
     */
    private int insertUserItem(Long userId) throws Exception {
        UserItemEntity item = new UserItemEntity();
        item.setUserId(userId);
        item.setSex(0);//性别 (SEX)
        item.setRegisterTime(new Date());
        return userItemDao.insertUserItem(item);
    }

    /**
     * 注册时 发送激活邮件
     */
    public void sendActivateEmail(String to_email, Long userId, String sign) {
        EmailUtils emailUtils = new EmailUtils();
        emailUtils.sendActivateEmail(to_email, userId, sign);
    }

    /**
     * 通过userId查询用户信息
     */
    public UserInfoEntity findUserById(Long userId) throws Exception {
        return userInfoDao.findUserById(userId);
    }

    /**
     * 补充图片
     */
    public UserInfoEntity replenish(UserInfoEntity info) throws Exception {
        if (StringUtils.isNotBlank(info.getHeadImage())) {
            info.setImgurl(pictureServer.findTinyURL(info.getHeadImage()));
        }
        info.setRoleName(datadictCacheServer.findDict("USER_ROLES", Long.parseLong(info.getRoles() + "")));
        return info;
    }

    /**
     * 补充性别 关注领域
     */
    public UserItemEntity replenish(UserItemEntity item) throws Exception {
        item.setSexName(datadictCacheServer.findDict("SEX", Long.parseLong(item.getSex() + "")));
        item.setConcernName(fieldInfoCacheServer.findFields(item.getConcern()));
        return item;
    }

    /**
     * 修改用户信息
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public int updateUser(UserInfoEntity user) throws Exception {
        return userInfoDao.updateUser(user);
    }

    /**
     * 修改用户信息
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public int updateUserItem(UserItemEntity item) throws Exception {
        return userItemDao.updateUserItem(item);
    }


    /**
     * 删除图片
     */
    public void deleteHeadImage(String headImage) throws Exception {
        pictureServer.deletePicture(headImage);
    }

    /**
     * 用户邮箱激活
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean emailActivate(Long userId, String sign) throws Exception {
        boolean flag = false;
        UserInfoEntity user = findUserById(userId);
        if (user.getSign().equals(sign)) {
            user.setIsActivate(1);
            if (userInfoDao.updateUser(user) > 0) {
                UserItemEntity item = findUserItemByUserId(userId);
                item.setActivateTime(new Date());
                userItemDao.updateUserItem(item);
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 通过用户ID查询用户明细信息
     */
    public UserItemEntity findUserItemByUserId(Long userId) throws Exception {
        return userItemDao.findUserItemByUserId(userId);
    }

    /**
     * 用户修改密码
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean upPasswd(Long userId, String newPasswd) throws Exception {
        boolean flag = false;
        UserInfoEntity user = findUserById(userId);
        if (user != null) {
            // user.setPasswd(findPasswd(newPasswd));
            user.setPasswd(newPasswd);
            user.setSign(getSign(user.getPhone(), newPasswd));
            if (userInfoDao.updateUser(user) > 0) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 添加缓存(用户信息缓存)
     */
    public void addUserInfoCache(UserInfoEntity user) throws Exception {
        userInfoCacheServer.put(userInfoCacheServer.getCacheName(), user.getId().toString(), user);
    }

    /**
     * 通过手机号码查询用户信息
     */
    public UserInfoEntity findUserByPhone(String phone) throws Exception {
        return userInfoDao.findUserByPhone(phone);
    }

    /**
     * 密码加密
     */
    public String findPasswd(String passwd) throws Exception {
        return new MD5(BASE64.encryptBASE64(passwd.getBytes())).compute();
    }

    /**
     * 获取sign
     *
     * @param phone  手机号码
     * @param passwd 密码
     */
    private String getSign(String phone, String passwd) throws Exception {
        return new MD5(phone + "@" + BASE64.encryptBASE64(passwd.getBytes())).compute().toUpperCase();
    }

}