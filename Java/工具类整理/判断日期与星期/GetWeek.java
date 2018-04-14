package com.anssy.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author Mr.Ѧ
 * java�ж�����������
 */
public class GetWeek {
    public static void main(String[] args) {
    	SimpleDateFormat sFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    	System.out.println("�����ǣ�"+sFormat.format(new Date())+"     "+new GetWeek().getWeekOfDate(new Date()));
    	
    }
    public static String getWeekOfDate(Date dt) {
//    	��������
        String[] weekDays = {"������", "����һ", "���ڶ�", "������", "������", "������", "������"};
//        ����
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
//        ʱ�� -1 
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
        w = 0;

        return weekDays[w];
        }
}