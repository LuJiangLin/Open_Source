package com.anssy.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author Mr.薛
 * java判断日期与星期
 */
public class GetWeek {
    public static void main(String[] args) {
    	SimpleDateFormat sFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    	System.out.println("今天是："+sFormat.format(new Date())+"     "+new GetWeek().getWeekOfDate(new Date()));
    	
    }
    public static String getWeekOfDate(Date dt) {
//    	七天数组
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
//        日历
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
//        时间 -1 
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
        w = 0;

        return weekDays[w];
        }
}