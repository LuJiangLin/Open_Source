/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  AuthorityAction.java
 * PACKAGE      :  com.anssy.inter.authority.action
 * CREATE DATE  :  2016-8-10
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.inter.authority.action;

import com.anssy.venturebar.log.server.SystemLogServer;
import com.anssy.inter.AppAction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author make it
 * @version SVN #V1# #2016-8-10#
 *          接口_权限
 */
@Controller
@RequestMapping("/app/authority/authorityAction")
public class AuthorityAction extends AppAction {

    @Resource
    private SystemLogServer systemLogServer;

    /**
     * 无sign(请求head中无sign参数)
     * 登录拦截器重定向到此方法
     */
    @RequestMapping(value = "/noSign", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> noSign() throws Exception {
        systemLogServer.putInfo("noSign");
        return writeResultRep(1001);
    }

    /**
     * sign错误(sign参数错误)
     * 登录拦截器重定向到此方法
     */
    @RequestMapping(value = "/signFault", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> signFault() throws Exception {
        systemLogServer.putInfo("signFault");
        return writeResultRep(1002);
    }

    /**
     * 系统处理遇到异常
     * 异常处理重定向到此方法
     */
    @RequestMapping(value = "/exception", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> exception() throws Exception {
        systemLogServer.putInfo("exception");
        return writeResultRep(500);
    }

    /**
     * 系统处理遇到异常404
     * 异常处理重定向到此方法
     */
    @RequestMapping(value = "/notFound", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> notFound() throws Exception {
        systemLogServer.putInfo("notFound");
        return writeResultRep(404);
    }

}