/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  RegisterAction.java
 * PACKAGE      :  com.anssy.inter.base.action
 * CREATE DATE  :  2016-8-10
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.inter.base.action;

import com.google.gson.Gson;
import com.anssy.venturebar.base.entity.UserInfoEntity;
import com.anssy.venturebar.base.vo.DatadictVo;
import com.anssy.inter.AppAction;
import com.anssy.inter.base.server.UserInfoServer;
import com.anssy.inter.base.vo.UserVo;
import com.anssy.inter.base.vo.VerifyVo;
import com.anssy.webcore.common.RegexUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author make it
 * @version SVN #V1# #2016-8-10#
 *          接口_注册
 */
@Controller
@RequestMapping("/app/base/registerAction")
public class RegisterAction extends AppAction {

    @Resource
    private UserInfoServer userInfoServer;

    /**
     * 发送验证码接口
     *
     * @param {"phone":""}
     */
    @RequestMapping(value = "/sendVerifyCode", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> sendVerifyCode(HttpServletRequest request) throws Exception {
        Map<String, Object> map;
        VerifyVo vo = new Gson().fromJson(getJson(request), VerifyVo.class);
        if (vo != null && StringUtils.isNotBlank(vo.getPhone())) {
            if (RegexUtils.matcher(RegexUtils.REGEX_MOBILE, vo.getPhone())) {
                boolean flag = userInfoServer.sendVerifyCode(vo.getPhone());
                if (flag) {
                    map = writeResultRep();
                } else {
                    map = writeResultRep(2013);//发送验证码失败
                }
            } else {
                map = writeResultRep(1005);//手机号码格式异常
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

    /**
     * 验证验证码接口
     * <p>
     * request {"phone":"","code":""}
     */
    @RequestMapping(value = "/verifyCode", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> verifyCode(HttpServletRequest request) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        VerifyVo vo = new Gson().fromJson(getJson(request), VerifyVo.class);
        if (vo != null && StringUtils.isNotBlank(vo.getPhone()) && StringUtils.isNotBlank(vo.getCode())) {
            if (RegexUtils.matcher(RegexUtils.REGEX_MOBILE, vo.getPhone())) {
                int mark = userInfoServer.verifyCode(vo);
                if (mark == 1) {
                    map = writeResultRep();
                } else if (mark == -1) {
                    map = writeResultRep(2014);//验证码失效
                } else if (mark == -2) {
                    map = writeResultRep(2015);//验证码错误
                }
            } else {
                map = writeResultRep(1005);//手机号码格式异常
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

    /**
     * 获取用户角色接口
     */
    @RequestMapping(value = "/findUserRoles", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> findUserRoles() throws Exception {
        Map<String, Object> map = writeResultRep();
        List<DatadictVo> roles = userInfoServer.findUserRoles();
        map.put("roles", roles);
        return map;
    }

    /**
     * 注册接口
     *
     * @param {"phone":"","email":"","passwd":"","roles":""}
     */
    @RequestMapping(value = "/register", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> register(HttpServletRequest request) throws Exception {
        Map<String, Object> map;
        UserVo vo = new Gson().fromJson(getJson(request), UserVo.class);
        if (vo != null && StringUtils.isNotBlank(vo.getPhone()) && StringUtils.isNotBlank(vo.getPasswd())) {
            if (RegexUtils.matcher(RegexUtils.REGEX_MOBILE, vo.getPhone())) {
                int count = userInfoServer.register(vo);
                if (count > 0) {
                    map = writeResultRep();
                    UserInfoEntity user = userInfoServer.findUserByPhone(vo.getPhone());
                    if (user != null) {
                        userInfoServer.addUserInfoCache(user);
                        map.put("userId", user.getId());
                        map.put("sign", user.getSign());
                        map.put("roles", user.getRoles());
                        if (StringUtils.isNotBlank(vo.getEmail())) {
                            userInfoServer.sendActivateEmail(user.getEmail(), user.getId(), user.getSign());
                        }
                    }
                } else if (count == -1) {
                    map = writeResultRep(2002);//此号码已被注册
                } else if (count == -2) {
                    map = writeResultRep(2012);//此邮箱已被注册
                } else {
                    map = writeResultRep(2003);//注册失败
                }
            } else {
                map = writeResultRep(1005);//手机号码格式异常
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

    /**
     * 判断手机号码是否已经注册
     *
     * @param {"phone":""}
     */
    @RequestMapping(value = "/phoneExist", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> phoneExist(HttpServletRequest request) throws Exception {
        Map<String, Object> map;
        UserVo vo = new Gson().fromJson(getJson(request), UserVo.class);
        if (vo != null && StringUtils.isNotBlank(vo.getPhone())) {
            if (RegexUtils.matcher(RegexUtils.REGEX_MOBILE, vo.getPhone())) {
                UserInfoEntity user = userInfoServer.findUserByPhone(vo.getPhone());
                if (user != null && user.getId() > 0) {
                    map = writeResultRep();
                } else {
                    map = writeResultRep(2010);//没有此用户信息
                }
            } else {
                map = writeResultRep(1005);//手机号码格式异常
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

    /**
     * 重置密码
     *
     * @param {"phone":"","passwd":""}
     */
    @RequestMapping(value = "/resetPasswd", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> resetPasswd(HttpServletRequest request) throws Exception {
        Map<String, Object> map;
        UserVo vo = new Gson().fromJson(getJson(request), UserVo.class);
        if (vo != null && StringUtils.isNotBlank(vo.getPhone()) && StringUtils.isNotBlank(vo.getPasswd())) {
            if (RegexUtils.matcher(RegexUtils.REGEX_MOBILE, vo.getPhone())) {
                UserInfoEntity user = userInfoServer.findUserByPhone(vo.getPhone());
                if (user != null && user.getId() > 0) {
                    if (userInfoServer.upPasswd(user.getId(), vo.getPasswd())) {
                        map = writeResultRep();
                        user = userInfoServer.findUserById(user.getId());
                        userInfoServer.addUserInfoCache(user);
                        map.put("userId", user.getId());
                        map.put("sign", user.getSign());
                    } else {
                        map = writeResultRep(2011);//修改密码失败
                    }
                } else {
                    map = writeResultRep(2010);//没有此用户信息
                }
            } else {
                map = writeResultRep(1005);//手机号码格式异常
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

    /**
     * 用户邮箱激活接口
     */
    @RequestMapping(value = "/emailActivate", produces = {"application/json;charset=UTF-8"})
    public ModelAndView emailActivate(@RequestParam("userId") Long userId, @RequestParam("sign") String sign) throws Exception {
        String viewname = "activate_fail";
        String email = "";
        if (userId != null && StringUtils.isNotBlank(sign)) {
            if (userInfoServer.emailActivate(userId, sign)) {
                viewname = "activate_success";
            }
            UserInfoEntity user = userInfoServer.findUserById(userId);
            if (user != null && user.getId() > 0) {
                email = user.getEmail();
            }
        }
        return new ModelAndView(viewname, "email", email);
    }

}