/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  UserInfoAction.java
 * PACKAGE      :  com.anssy.inter.base.action
 * CREATE DATE  :  2016-8-11
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.inter.base.action;

import com.google.gson.Gson;
import com.anssy.venturebar.base.entity.UserInfoEntity;
import com.anssy.venturebar.base.entity.UserItemEntity;
import com.anssy.inter.AppAction;
import com.anssy.inter.base.server.UserInfoServer;
import com.anssy.inter.base.vo.*;
import com.anssy.webcore.common.RegexUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author make it
 * @version SVN #V1# #2016-8-11#
 *          接口_用户信息
 */
@Controller
@RequestMapping("/app/base/userInfoAction")
public class UserInfoAction extends AppAction {

    @Resource
    private UserInfoServer userInfoServer;

    /**
     * 查看用户详细信息接口
     */
    @RequestMapping(value = "/findUserInfo", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> findUserInfo(HttpServletRequest request) throws Exception {
        Long userId = Long.parseLong(request.getHeader("userId"));
        Map<String, Object> map = writeResultRep();
        UserInfoEntity user = userInfoServer.replenish(userInfoServer.findUserById(userId));
        user.setPasswd(null);
        map.put("user", user);
        map.put("item", userInfoServer.replenish(userInfoServer.findUserItemByUserId(userId)));
        return map;
    }


    /**
     * 修改密码接口
     *
     * @param {"oldPasswd":"","newPasswd":""}
     */
    @RequestMapping(value = "/upPasswd", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> upPasswd(HttpServletRequest request) throws Exception {
        Long userId = Long.parseLong(request.getHeader("userId"));
        Map<String, Object> map;
        PasswdVo vo = new Gson().fromJson(getJson(request), PasswdVo.class);
        if (vo != null && StringUtils.isNotBlank(vo.getOldPasswd()) && StringUtils.isNotBlank(vo.getNewPasswd())) {
            UserInfoEntity user = userInfoServer.findUserById(userId);
            if (user != null && user.getId() > 0) {
                if (user.getPasswd().equals(userInfoServer.findPasswd(vo.getOldPasswd()))) {
                    if (userInfoServer.upPasswd(userId, vo.getNewPasswd())) {
                        map = writeResultRep();
                        user = userInfoServer.findUserById(userId);
                        userInfoServer.addUserInfoCache(user);
                        map.put("userId", user.getId());
                        map.put("sign", user.getSign());
                    } else {
                        map = writeResultRep(2011);//修改密码失败
                    }
                } else {
                    map = writeResultRep(2006);//密码错误
                }
            } else {
                map = writeResultRep(2010);//没有此用户信息
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

    /**
     * 完善用户信息接口-头像(图片ID)
     * {"headImage":""}
     */
    @RequestMapping(value = "/upHead", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> upHead(HttpServletRequest request) throws Exception {
        Long userId = Long.parseLong(request.getHeader("userId"));
        Map<String, Object> map;
        HeadVo vo = new Gson().fromJson(getJson(request), HeadVo.class);
        if (vo != null && userId != null) {
            UserInfoEntity user = userInfoServer.findUserById(userId);
            if (user != null) {
                map = writeResultRep();
                if (StringUtils.isNotBlank(user.getHeadImage())) {
                    userInfoServer.deleteHeadImage(user.getHeadImage());
                }
                if (StringUtils.isNotBlank(vo.getHeadImage())) {
                    user.setHeadImage(vo.getHeadImage());
                } else {
                    user.setHeadImage("");
                }
                userInfoServer.updateUser(user);
            } else {
                map = writeResultRep(2010);//没有此用户信息
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

    /**
     * 完善用户信息接口-Email(重新激活)
     *
     * @param {"email":""}
     */
    @RequestMapping(value = "/upEmail", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> upEmail(HttpServletRequest request) throws Exception {
        Long userId = Long.parseLong(request.getHeader("userId"));
        Map<String, Object> map;
        UserVo vo = new Gson().fromJson(getJson(request), UserVo.class);
        if (vo != null && StringUtils.isNotBlank(vo.getEmail())) {
            if (RegexUtils.matcher(RegexUtils.REGEX_EMAIL, vo.getEmail())) {
                UserInfoEntity user = userInfoServer.findUserById(userId);
                if (user != null) {
                    map = writeResultRep();
                    if (StringUtils.isBlank(user.getEmail()) || !vo.getEmail().equals(user.getEmail())) {
                        user.setEmail(vo.getEmail());
                        userInfoServer.sendActivateEmail(user.getEmail(), user.getId(), user.getSign());
                        userInfoServer.updateUser(user);
                    }
                } else {
                    map = writeResultRep(2010);//没有此用户信息
                }
            } else {
                map = writeResultRep(1006);//邮箱格式异常
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

    /**
     * 完善用户信息接口-昵称
     *
     * @param {"nickname":""}
     */
    @RequestMapping(value = "/upNickname", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> upNickname(HttpServletRequest request) throws Exception {
        Long userId = Long.parseLong(request.getHeader("userId"));
        Map<String, Object> map;
        NicknameVo vo = new Gson().fromJson(getJson(request), NicknameVo.class);
        if (vo != null && StringUtils.isNotBlank(vo.getNickname())) {
            UserInfoEntity user = userInfoServer.findUserById(userId);
            if (user != null) {
                map = writeResultRep();
                user.setNickname(vo.getNickname());
                userInfoServer.updateUser(user);
            } else {
                map = writeResultRep(2010);//没有此用户信息
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

    /**
     * 完善用户信息接口-角色
     *
     * @param {"roles":""}
     */
    @RequestMapping(value = "/upRole", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> upRole(HttpServletRequest request) throws Exception {
        Long userId = Long.parseLong(request.getHeader("userId"));
        Map<String, Object> map;
        RoleVo vo = new Gson().fromJson(getJson(request), RoleVo.class);
        if (vo != null) {
            UserInfoEntity user = userInfoServer.findUserById(userId);
            if (user != null) {
                map = writeResultRep();
                user.setRoles(vo.getRoles());
                userInfoServer.updateUser(user);
            } else {
                map = writeResultRep(2010);//没有此用户信息
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

    /**
     * 完善用户信息接口-姓名
     *
     * @param {"userName":""}
     */
    @RequestMapping(value = "/upUserName", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> upUserName(HttpServletRequest request) throws Exception {
        Long userId = Long.parseLong(request.getHeader("userId"));
        Map<String, Object> map;
        UserNameVo vo = new Gson().fromJson(getJson(request), UserNameVo.class);
        if (vo != null) {
            UserInfoEntity user = userInfoServer.findUserById(userId);
            UserItemEntity item = userInfoServer.findUserItemByUserId(userId);
            if (user != null && item != null) {
                map = writeResultRep();
                item.setUserName(vo.getUserName());
                userInfoServer.updateUserItem(item);
            } else {
                map = writeResultRep(2010);//没有此用户信息
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

    /**
     * 完善用户信息接口-性别
     *
     * @param {"sex":""}
     */
    @RequestMapping(value = "/upSex", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> upSex(HttpServletRequest request) throws Exception {
        Long userId = Long.parseLong(request.getHeader("userId"));
        Map<String, Object> map;
        SexVo vo = new Gson().fromJson(getJson(request), SexVo.class);
        if (vo != null) {
            UserInfoEntity user = userInfoServer.findUserById(userId);
            if (user != null) {
                map = writeResultRep();
                UserItemEntity item = userInfoServer.findUserItemByUserId(userId);
                item.setSex(vo.getSex());
                userInfoServer.updateUserItem(item);
            } else {
                map = writeResultRep(2010);//没有此用户信息
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

    /**
     * 完善用户信息接口-公司
     *
     * @param {"company":""}
     */
    @RequestMapping(value = "/upCompany", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> upCompany(HttpServletRequest request) throws Exception {
        Long userId = Long.parseLong(request.getHeader("userId"));
        Map<String, Object> map;
        CompanyVo vo = new Gson().fromJson(getJson(request), CompanyVo.class);
        if (vo != null) {
            UserInfoEntity user = userInfoServer.findUserById(userId);
            UserItemEntity item = userInfoServer.findUserItemByUserId(userId);
            if (user != null && item != null) {
                map = writeResultRep();
                item.setCompany(vo.getCompany());
                userInfoServer.updateUserItem(item);
            } else {
                map = writeResultRep(2010);//没有此用户信息
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

    /**
     * 完善用户信息接口-职位
     *
     * @param {"post":""}
     */
    @RequestMapping(value = "/upPost", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> upPost(HttpServletRequest request) throws Exception {
        Long userId = Long.parseLong(request.getHeader("userId"));
        Map<String, Object> map;
        PostVo vo = new Gson().fromJson(getJson(request), PostVo.class);
        if (vo != null) {
            UserInfoEntity user = userInfoServer.findUserById(userId);
            UserItemEntity item = userInfoServer.findUserItemByUserId(userId);
            if (user != null && item != null) {
                map = writeResultRep();
                item.setPost(vo.getPost());
                userInfoServer.updateUserItem(item);
            } else {
                map = writeResultRep(2010);//没有此用户信息
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

    /**
     * 完善用户信息接口-关注领域
     *
     * @param {"concern":""}
     */
    @RequestMapping(value = "/upConcern", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> upConcern(HttpServletRequest request) throws Exception {
        Long userId = Long.parseLong(request.getHeader("userId"));
        Map<String, Object> map;
        ConcernVo vo = new Gson().fromJson(getJson(request), ConcernVo.class);
        if (vo != null) {
            UserInfoEntity user = userInfoServer.findUserById(userId);
            UserItemEntity item = userInfoServer.findUserItemByUserId(userId);
            if (user != null && item != null) {
                map = writeResultRep();
                item.setConcern(vo.getConcern());
                userInfoServer.updateUserItem(item);
            } else {
                map = writeResultRep(2010);//没有此用户信息
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

    /**
     * 重新发送激活邮件接口
     */
    @RequestMapping(value = "/sendActivateEmail", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> sendActivateEmail(HttpServletRequest request) throws Exception {
        Long userId = Long.parseLong(request.getHeader("userId"));
        Map<String, Object> map;
        UserInfoEntity user = userInfoServer.findUserById(userId);
        if (user != null) {
            if (StringUtils.isNotBlank(user.getEmail())) {
                userInfoServer.sendActivateEmail(user.getEmail(), user.getId(), user.getSign());
                map = writeResultRep();
            } else {
                map = writeResultRep(2009);//没有邮箱地址
            }
        } else {
            map = writeResultRep(2010);//没有此用户信息
        }
        return map;
    }

}