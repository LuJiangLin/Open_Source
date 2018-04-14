/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  LoginAction.java
 * PACKAGE      :  com.anssy.inter.base.action
 * CREATE DATE  :  2016-8-10
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.inter.base.action;

import com.google.gson.Gson;
import com.anssy.venturebar.base.entity.UserInfoEntity;
import com.anssy.inter.AppAction;
import com.anssy.inter.base.server.UserInfoServer;
import com.anssy.inter.base.vo.LoginVo;
import com.anssy.webcore.common.RegexUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author make it
 * @version SVN #V1# #2016-8-10#
 *          接口_登录
 */
@Controller
@RequestMapping("/app/base/loginAction")
public class LoginAction extends AppAction {

    @Resource
    private UserInfoServer userInfoServer;

    /**
     * 登录接口
     * <p/>
     * request {"username":"","passwd":""} username(phone/email)用户名/passwd 密码
     */
    @RequestMapping(value = "/login", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> login(HttpServletRequest request) throws Exception {
        Map<String, Object> map;
        LoginVo vo = new Gson().fromJson(getJson(request), LoginVo.class);
        //// TODO: 15/12/24  check why using this?
        String phoneTerrace = null, phoneVersion = null, appVersion = null;
        if (vo != null && StringUtils.isNotBlank(vo.getUsername()) && StringUtils.isNotBlank(vo.getPasswd())) {
            UserInfoEntity user;
            if (RegexUtils.matcher(RegexUtils.REGEX_MOBILE, vo.getUsername())) {
                user = userInfoServer.loginByPhone(vo.getUsername());
            } else {
                user = userInfoServer.loginByEmail(vo.getUsername());
            }
            if (user != null) {
                // if (!user.getPasswd().equals(userInfoServer.findPasswd(vo.getPasswd()))) {
                if (!user.getPasswd().equals(vo.getPasswd())) {
                    map = writeResultRep(2006);//密码错误
                } else if (!RegexUtils.matcher(RegexUtils.REGEX_MOBILE, vo.getUsername())) {
                    map = writeResultRep(2005);//用户邮箱未激活
                } else if (user.getState() == 1) {
                    map = writeResultRep(2004);//用户被锁定
                } else {
                    map = writeResultRep();
                    map.put("userId", user.getId());
                    map.put("sign", user.getSign());
                    map.put("roles", user.getRoles());
                    userInfoServer.putInfoLog(user.getId(), 1, phoneTerrace, phoneVersion, appVersion);
                }
            } else {
                map = writeResultRep(2003);//此用户不存在
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

    /**
     * 登出接口
     */
    @RequestMapping(value = "/logout", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> logout(HttpServletRequest request) throws Exception {
        Long userId = Long.parseLong(request.getHeader("userId"));
        userInfoServer.putInfoLog(userId, 2, "", "", "");
        return writeResultRep();
    }

}