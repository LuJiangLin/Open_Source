/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  ExceptionHandler.java
 * PACKAGE      :  com.anssy.inter.handler
 * CREATE DATE  :  2016-8-12
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.inter.handler;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author make it
 * @version SVN #V1# #2016-8-12#
 *          spring mvc 异常统一处理
 */
public class ExceptionHandler implements HandlerExceptionResolver {

    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        System.out.println("================\n================\n================\n"+ex.getMessage()+"\n================\n================\n================");
        return new ModelAndView("redirect:/app/authority/authorityAction/exception.action");
    }

}