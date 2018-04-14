/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  RegexUtils.java
 * PACKAGE      :  com.anssy.webcore.common
 * CREATE DATE  :  2016-8-11
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.webcore.common;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author make it
 * @version SVN #V1# #2016-8-11#
 * @文件描述 正则封装工具类
 */
public class RegexUtils {

    /**
     * 手机号码
     */
    public static final String REGEX_MOBILE = "^[1][3,4,5,7,8][0-9]{9}$";
    /**
     * 电话号码(区号)
     */
    public static final String REGEX_PHONE_AREACODE = "^[0][1-9]{2,3}-[0-9]{5,10}$";
    /**
     * 电话号码
     */
    public static final String REGEX_PHONE = "^[1-9]{1}[0-9]{5,8}$";
    /**
     * Email
     */
    public static final String REGEX_EMAIL = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";

    /**
     * 匹配方法
     */
    public static boolean matcher(String regex, String str) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 判断字符串是否是数字
     */
    public static boolean isNumber(String num) {
        boolean flag = false;
        if (StringUtils.isNotBlank(num)) {
            String regex = "^[0-9]*$";
            flag = num.matches(regex);
        }
        return flag;
    }

    /**
     * 判断是否是Long
     */
    public static boolean isLong(String num) {
        boolean flag = false;
        if (StringUtils.isNotBlank(num)) {
            String regex = "^\\+?[1-9][0-9]*$";
            flag = num.matches(regex);
        }
        return flag;
    }

    /**
     * 判断是否是double
     */
    public static boolean isDouble(String dou) {
        boolean flag = false;
        if (StringUtils.isNotBlank(dou)) {
            String regex = "^[-+]?/d[dD]$";
            flag = dou.matches(regex);
        }
        return flag;
    }

    /**
     * 字符串(A-Z、a-z、数字0-9)
     */
    public static boolean isNotSymbol(String par) {
        boolean flag = false;
        if (StringUtils.isNotBlank(par)) {
            String regex = "^[A-Za-z0-9]+$";
            flag = par.matches(regex);
        }
        return flag;
    }

    /**
     * 切割字符串成Long数组
     */
    public static Long[] convertIdsStringToLongArray(String idsString) {
        List<Long> value = new ArrayList<Long>();
        if (idsString != null && !StringUtils.isBlank(idsString)) {
            String[] list = idsString.split(",");
            int i = 0;
            for (String str : list) {
                if (!StringUtils.isBlank(str)) {
                    value.add(Long.valueOf(str));
                    i++;
                }
            }
            Long[] ids = new Long[i];
            for (int j = 0; j < ids.length; j++) {
                ids[j] = value.get(j);
            }
            return ids;
        }
        return new Long[0];
    }

    /**
     * 判断Long是否存在于Long数组中
     */
    public static boolean LongContainLongArray(Long id, Long[] ids) {
        if (id != null && ids != null && ids.length > 0) {
            for (Long tid : ids) {
                if (tid != null) {
                    if (tid.equals(id)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    /**
     * 富文本转换成普通的文本
     */
    public static String replaceAll(String string) {
        if (string == null) return "-----我是占位符-----";
        string = string.replaceAll(regex, "");
        string = string.replaceAll("&quot;", "\"");
        string = string.replaceAll("&nbsp;", "  ");
        if (string.length() >= 1280) return string.substring(1280);
        if (string == null||string.equals("")) return "-----我是占位符-----";
        return string;
    }

    static String regex = "<[^>]*>";



    /**
     * baidu2Gaode(百度经纬度转换成高德经纬度)
     *
     * @param bd_lon  纬度
     * @param bd_lat 经度
     * @return
     */
    public static double[] bdToGaoDe(double bd_lat, double bd_lon) {
        double[] gd_lat_lon = new double[2];
        if (bd_lon>0||bd_lat>0) {
            double PI = 3.14159265358979324 * 3000.0 / 180.0;
            double x = bd_lon - 0.0065, y = bd_lat - 0.006;
            double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * PI);
            double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * PI);
            gd_lat_lon[0] = z * Math.cos(theta);
            gd_lat_lon[1] = z * Math.sin(theta);
        }else{
            gd_lat_lon[0] = bd_lat;
            gd_lat_lon[1] = bd_lon;
        }
        return gd_lat_lon;
    }

}