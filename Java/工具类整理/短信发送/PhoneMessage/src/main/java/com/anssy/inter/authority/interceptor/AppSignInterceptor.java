/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  AppSignInterceptor.java
 * PACKAGE      :  com.anssy.inter.authority.interceptor
 * CREATE DATE  :  2016-8-10
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.inter.authority.interceptor;

import com.anssy.inter.authority.PvMonitoring;
import com.anssy.inter.authority.SignVerify;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author make it
 * @version SVN #V1# #2016-8-10#
 *          App接口sign拦截器
 */
public class AppSignInterceptor implements HandlerInterceptor {

    private List<String> excludeUrls;
    /**
     * 是否启用app拦截器
     */
    //todo check elvizlai
    private boolean disable = false;

    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3) throws Exception {
    }

    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3) throws Exception {
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
        PvMonitoring.getInstance();
        if (disable) {
            return true;
        }
        String requestUri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String url = requestUri.substring(contextPath.length());
        //拦截的请求
        if (!regexListStr(excludeUrls, url)) {
            return true;
        }
        // 判断sign
        String userId = request.getHeader("userId");
        String sign = request.getHeader("sign");

        // HandlerMethod hm = (HandlerMethod) arg2;

        System.out.println("========================\nuserId : "+userId+"\n sign : "+sign +"\n========================");

        if (StringUtils.isNotBlank(sign) && StringUtils.isNotBlank(userId)) {
            //判断sign是否正确
            if (SignVerify.getInstance(userId, sign)) {
               // System.out.println("----" + hm.getMethod().getName() + ":token校验成功----" + sign);
                return true;
            }
//            response.setStatus(401);
//            response.setHeader("WWW-Authenticate","Basic realm=\"cyb\"");
            // System.out.println("----" + hm.getMethod().getName() + ":token校验失败----" + sign);
            // response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            request.getRequestDispatcher("/app/authority/authorityAction/signFault.action").forward(request, response);
        } else {
//            response.setStatus(401);
//            response.setHeader("WWW-Authenticate","Basic realm=\"cyb\"");
            // System.out.println("----" + hm.getMethod().getName() + ":token校验失败----" + sign);
            // response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            request.getRequestDispatcher("/app/authority/authorityAction/noSign.action").forward(request, response);
        }
        // System.out.println("----" + hm.getMethod().getName() + ":token校验失败----" + sign);
        // request.getRequestDispatcher("/error/401").forward(request, response);
        return false;
    }

    /**
     * 判断是否进行拦截
     */
    private boolean regexListStr(List<String> urls, String url) {
        for (String url1 : urls) {
            if (url.startsWith(url1)) {
                return true;
            }
        }
        return false;
    }

    public List<String> getExcludeUrls() {
        return excludeUrls;
    }

    public void setExcludeUrls(List<String> excludeUrls) {
        this.excludeUrls = excludeUrls;
    }

}