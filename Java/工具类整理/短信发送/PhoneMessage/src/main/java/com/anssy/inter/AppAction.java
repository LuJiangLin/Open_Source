/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  AppAction.java
 * PACKAGE      :  com.anssy.inter
 * CREATE DATE  :  2016-8-11
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.inter;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author make it
 * @version SVN #V1# #2016-8-11#
 *          App Action封装类
 */
public class AppAction {

    /**
     * 通过HttpServletRequest对象获取body中的JSON参数字符串
     */
    public String getJson(HttpServletRequest request) throws Exception {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
        String s;
        while ((s = br.readLine()) != null) {
            sb.append(s);
        }
        System.out.println("=======getJson==========\n"+sb.toString()+"\n=======getJson==========");
        return sb.toString();
    }

    /**
     * 其它返回码
     */
    protected Map<String, Object> writeResultRep(int code) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", code);
        map.put("msg", CodeEnum.getName(code));
        return map;
    }

    /**
     * 操作成功返回
     */
    protected Map<String, Object> writeResultRep() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", 0);
        map.put("msg", CodeEnum.getName(0));
        return map;
    }

}