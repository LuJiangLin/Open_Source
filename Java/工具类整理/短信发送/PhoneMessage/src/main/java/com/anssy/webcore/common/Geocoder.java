/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  Geocoder.java
 * PACKAGE      :  com.anssy.webcore.common
 * CREATE DATE  :  2016-8-17
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.webcore.common;

import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author make it
 * @version SVN #V1# #2016-8-17#
 * @文件描述 百度/腾讯API地址转GPS坐标
 */
public class Geocoder {

    /**
     * 百度API的key
     */
    //public static final String BAIDU_KEY = "Nl8WgGlfl9ebK6P8oOGeaUjU";
    /**
     * 腾讯API的key
     */
    public static final String QQ_KEY = "GQ5BZ-V5X3J-JZUFE-KHDQ7-LFGAO-T3FJN";

    /**
     * 返回输入地址的经纬度坐标 key lng(经度),lat(纬度)
     */
    public static Map<String, String> getGeocoder(String address, String area) {
        BufferedReader in = null;
        Map<String, String> map = new HashMap<String, String>();
        try {
            //todo check -add by elvizlai
//            if (!judgeAddress(address)) {
//                address = area + address;
//            }
            // 将地址转换成utf-8的16进制
            address = URLEncoder.encode(address, "UTF-8");
            //URL tirc = new URL("http://api.map.baidu.com/geocoder?address="+ address + "&output=json&key=" + BAIDU_KEY);
            URL tirc = new URL("http://apis.map.qq.com/ws/geocoder/v1/?key=" + QQ_KEY + "&output=json&address=" + address);
            in = new BufferedReader(new InputStreamReader(tirc.openStream(), "UTF-8"));
            String res;
            StringBuilder sb = new StringBuilder("");
            while ((res = in.readLine()) != null) {
                sb.append(res.trim());
            }
            String str = sb.toString();
            if (StringUtils.isNotEmpty(str)) {
                if (str.lastIndexOf("location") > 0) {
                    str = str.substring(str.lastIndexOf("location"));
                    str = str.substring(0, str.indexOf("}") + 1);
                    str = str.substring(str.indexOf("{"), str.length());
                    Gson gson = new Gson();
                    Gps gps = gson.fromJson(str, Gps.class);
                    if (gps != null) {
                        map.put("lng", gps.getLng());
                        map.put("lat", gps.getLat());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                assert in != null;
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    /**
     * 判断地址是否存在(市/区/县/旗/岛)
     */
    private static boolean judgeAddress(String address) {
        String[] areas = new String[]{"市", "区", "县", "旗", "岛"};
        for (String area : areas) {
            if (address.contains(area)) {
                return true;
            }
        }
        return false;
    }

    class Gps {

        private String lng;
        private String lat;

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

    }

}