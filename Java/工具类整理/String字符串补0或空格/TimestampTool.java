package com.time.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * @author Mr.薛
 * 内容：时间的工具类
 */
public class TimestampTool{

//	  public static void main(String[] args) {
//	    System.out.println("返回的原始时间类型："+TimestampTool.getTimeMills());
//	    System.out.println("将毫秒（上面的时间）转成时间："+TimestampTool.getTimeMillisToDate(getTimeMills()));
//	    System.out.println("当前时间："+crunttime());
//	    System.out.println("获取当前时间的字符串："+getCurrentDate());
//	  }
//获取毫秒时间
	  public static long getTimeMills()
	  {
		return System.currentTimeMillis();
	  }
	  
  /**
   * 将毫秒转成时间
   */
  public static String getTimeMillisToDate(long l) {
    Timestamp d = new Timestamp(l);
    return d.toString().substring(0, 19);
  }

  /**
   * 当前时间
   * 
   * @return Timestamp
   */
  public static Timestamp crunttime() {
    return new Timestamp(System.currentTimeMillis());
  }

  /**
   * 获取当前时间的字符串
   * 
   * @return String ex:2006-07-07
   */
  public static String getCurrentDate() {
    Timestamp d = crunttime();
    return d.toString().substring(0, 10);
  }

  /**
   * 获取当前时间的字符串
   * 
   * @return String ex:2006-07-07 22:10:10
   */
  public static String getCurrentDateTime() {
    Timestamp d = crunttime();
    return d.toString().substring(0, 19);
  }

  public static String getWeekDay() {
    Calendar date = Calendar.getInstance();
    date.setTime(crunttime());
    return new SimpleDateFormat("EEEE").format(date.getTime());
  }

  /**
   * 获取指定时间的字符串,只到日期
   * 
   * @param t
   *            Timestamp
   * @return String ex:2006-07-07
   */
  public static String getStrDate(Timestamp t) {
    return t.toString().substring(0, 10);
  }

  /**
   * 获取指定时间的字符串
   * 
   * @param t
   *            Timestamp
   * @return String ex:2006-07-07 22:10:10
   */
  public static String getStrDateTime(Timestamp t) {
    return t.toString().substring(0, 19);
  }

  /**
   * 获得当前日期的前段日期
   * 
   * @param days
   * @return String
   */
  public static String getStrIntervalDate(String days) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.DATE, -Integer.parseInt(days));
    String strBeforeDays = sdf.format(cal.getTime());
    return strBeforeDays;
  }

  /**
   * 格式化时间
   * 
   * @param dt
   *            String -> yyyy-MM-dd hh:mm:ss
   * @return java.util.Date.Date -> yyyy-MM-dd hh:mm:ss
   */
  public static Date parseDateTime(String dt) {
    Date jDt = new Date();
    try {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      if (dt.length() > 10) {
        jDt = sdf.parse(dt);
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return jDt;
  }

  /**
   * 格式化时间yyyy-MM-dd HH:mm:ss
   * 
   * @param date
   *            java.util.Date
   * @return String -> yyyy-MM-dd HH:mm:ss
   */
  public static String parseDateTime(Date date) {
    String s = null;
    if (date != null) {
      try {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        s = f.format(date);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return s;
  }

  /**
   * 格式化日期
   * 
   * @param dt
   *            String -> yyyy-MM-dd
   * @return java.util.Date.Date -> yyyy-MM-dd
   */
  public static Date parseDate(String dt) {
    Date jDt = new Date();
    try {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      if (dt.length() >= 8) {
        jDt = sdf.parse(dt);
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return jDt;
  }

  /**
   * 格式化时间yyyy-MM-dd
   * 
   * @param date
   *            java.util.Date
   * @return String -> yyyy-MM-dd
   */
  public static String parseDate(Date date) {
    String s = null;
    try {
      if (date != null) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        s = f.format(date);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return s;
  }

  /**
   * 
   * @param dt
   * @return String
   */
  public static String getLongDateFromShortDate(String dt) {
    String strDT = dt;
    try {
      if (strDT != null && strDT.length() <= 10) {
        strDT = dt.trim() + " 00:00:00";
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return strDT;
  }

  /**
   * 
   * @param dt
   * @return String
   */
  public static String getShortDateToHHMM(String dt) {
    String jDt = dt;
    try {
      if (jDt != null && jDt.length() <= 10) {
        jDt = jDt + " 00:00";
      }
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
      jDt = sdf.parse(jDt).toLocaleString();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return jDt;
  }

  /**
   * 
   * @param dateStr
   * @return String
   */
  public static String formatDateToHHMM(String dateStr) {
    String resultDate = null;
    try {
      if (dateStr.length() > 10) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:ss");
        Date date = sdf.parse(dateStr);
        resultDate = sdf.format(date);
      } else
        resultDate = dateStr;
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return resultDate;
  }

  /**
   * 返回日期 格式:2006-07-05
   * 
   * @param str
   * @return Timestamp
   */
  public static Timestamp date(String str) {
    Timestamp tp = null;
    if (str.length() <= 10) {
      String[] string = str.trim().split("-");
      int one = Integer.parseInt(string[0]) - 1900;
      int two = Integer.parseInt(string[1]) - 1;
      int three = Integer.parseInt(string[2]);
      tp = new Timestamp(one, two, three, 0, 0, 0, 0);
    }
    return tp;
  }

  // 获取指定日期之后的日期字符串 如 2007-04-15 后一天 就是 2007-04-16
  public static String getNextDay(String strDate, int day) {
    if (strDate != null && !strDate.equals("")) {
      Calendar cal1 = Calendar.getInstance();
      String[] string = strDate.trim().split("-");
      int one = Integer.parseInt(string[0]) - 1900;
      int two = Integer.parseInt(string[1]) - 1;
      int three = Integer.parseInt(string[2]);
      cal1.setTime(new Date(one, two, three));
      cal1.add(Calendar.DAY_OF_MONTH, day);
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
      return formatter.format(cal1.getTime());
    } else {
      return null;
    }
  }

  // 获取指定日期之后的日期字符串 如 2007-02-28 后一年 就是 2008-02-29 （含闰年）
  public static String getNextYear(String strDate, int year) {
    Calendar cal1 = Calendar.getInstance();
    String[] string = strDate.trim().split("-");
    int one = Integer.parseInt(string[0]) - 1900;
    int two = Integer.parseInt(string[1]) - 1;
    int three = Integer.parseInt(string[2]);
    cal1.setTime(new Date(one, two, three));
    cal1.add(Calendar.YEAR, year);
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    return formatter.format(cal1.getTime());
  }

  /**
   * 返回时间和日期 格式:2006-07-05 22:10:10
   * 
   * @param str
   * @return Timestamp
   */
  public static Timestamp datetime(String str) {
    Timestamp tp = null;
    if (str != null && str.length() > 10) {
      String[] string = str.trim().split(" ");
      String[] date = string[0].split("-");
      String[] time = string[1].split(":");
      int date1 = Integer.parseInt(date[0]) - 1900;
      int date2 = Integer.parseInt(date[1]) - 1;
      int date3 = Integer.parseInt(date[2]);
      int time1 = Integer.parseInt(time[0]);
      int time2 = Integer.parseInt(time[1]);
      int time3 = Integer.parseInt(time[2]);
      tp = new Timestamp(date1, date2, date3, time1, time2, time3, 0);
    }
    return tp;
  }

  /**
   * 返回日期和时间(没有秒) 格式:2006-07-05 22:10
   * 
   * @param str
   * @return Timestamp
   */
  public static Timestamp datetimeHm(String str) {
    Timestamp tp = null;
    if (str.length() > 10) {
      String[] string = str.trim().split(" ");
      String[] date = string[0].split("-");
      String[] time = string[1].split(":");
      int date1 = Integer.parseInt(date[0]) - 1900;
      int date2 = Integer.parseInt(date[1]) - 1;
      int date3 = Integer.parseInt(date[2]);
      int time1 = Integer.parseInt(time[0]);
      int time2 = Integer.parseInt(time[1]);
      tp = new Timestamp(date1, date2, date3, time1, time2, 0, 0);
    }
    return tp;
  }

  /**
   * 获得当前系统日期与本周一相差的天数
   * 
   * @return int
   */
  private static int getMondayPlus() {
    Calendar calendar = Calendar.getInstance();
    // 获得今天是一周的第几天，正常顺序是星期日是第一天，星期一是第二天......
    int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK); // 星期日是第一天
    return (dayOfWeek == 1) ? -6 : 2 - dayOfWeek;
  }

  /**
   * 获得距当前时间所在某星期的周一的日期 例： 0-本周周一日期 -1-上周周一日期 1-下周周一日期
   * 
   * @param week
   *            int
   * @return java.util.Date
   */
  public static Date getMondayOfWeek(int week) {
    int mondayPlus = getMondayPlus(); // 相距周一的天数差
    GregorianCalendar current = new GregorianCalendar();
    current.add(GregorianCalendar.DATE, mondayPlus + 7 * week);
    return current.getTime();
  }

  /**
   * 获得某日前后的某一天
   * 
   * @param date
   *            java.util.Date
   * @param day
   *            int
   * @return java.util.Date
   */
  public static Date getDay(Date date, int day) {
    GregorianCalendar c = new GregorianCalendar();
    c.setTime(date);
    c.add(GregorianCalendar.DATE, day);
    return c.getTime();
  }

  /**
   * 获得距当前周的前后某一周的日期
   * 
   * @param week
   *            int
   * @return String[]
   */
  public static String[] getDaysOfWeek(int week) {
    String[] days = new String[7];
    Date monday = getMondayOfWeek(week); // 获得距本周前或后的某周周一
    Timestamp t = new Timestamp(monday.getTime());
    days[0] = getStrDate(t);
    for (int i = 1; i < 7; i++) {
      t = new Timestamp(getDay(monday, i).getTime());
      days[i] = getStrDate(t);
    }
    return days;
  }

  /***
   * MCC的UTC时间转换，MCC的UTC不是到毫秒的
   * 
   * @param utc
   * @return java.util.Date
   */
  public static Date mccUTC2Date(long utc) {
    Date d = new Date();
    d.setTime(utc * 1000); // 转成毫秒
    return d;
  }

  // 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
  public static Date strToDateLong(String strDate) {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    ParsePosition pos = new ParsePosition(0);
    Date strtodate = (Date) formatter.parse(strDate, pos);
    if (strtodate == null) {
      formatter = new SimpleDateFormat("yyyy-MM-dd");
      strtodate = (Date) formatter.parse(strDate, pos);
    }
    return strtodate;
  }

  // 将 yyyy-MM-dd HH:mm 格式字符串转换为时间
  public static Date strToDateTime(String strDate) {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    ParsePosition pos = new ParsePosition(0);
    Date strtodate = (Date) formatter.parse(strDate, pos);
    if (strtodate == null) {
      formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      strtodate = (Date) formatter.parse(strDate, pos);
    }
    return strtodate;
  }

  // 根据输入的字符串返回日期字符串 2006-07-07 22:10 2006-07-07
  public static String getStrDate(String str) {
    if (str.length() > 10) {
      String[] string = str.trim().split(" ");
      return string[0];
    } else {
      return getCurrentDate();
    }
  }

  // 获取当前时间的字符串 2006-07-07 22:10:10 2006-07-07_221010
  public static String getStrDateTime() {
    Timestamp d = crunttime();
    return d.toString().substring(0, 19).replace(":", "").replace(" ", "_");
  }

  // 根据日期字符串，返回今天，昨天或日期
  public static String getDayOrDate(String str) {
    if (str != null && !str.equals("")) {
      if (getNextDay(str, 0).equals(getCurrentDate())) {
        str = "今天";
      } else if (getNextDay(str, 1).equals(getCurrentDate())) {
        str = "昨天";
      }
    }
    return str;
  }

  // 返回当前日期所在星期，2对应星期一
  public static int getMonOfWeek() {
    Calendar cal1 = Calendar.getInstance();
    cal1.setTime(new Date());
    return cal1.get(Calendar.DAY_OF_WEEK);
  }

  /**
   * 获取当前日期之前的日期字符串 如 2007-04-15 前5月 就是 2006-11-15
   */
  public static String getPreviousMonth(int month) {
    Calendar cal1 = Calendar.getInstance();
    cal1.setTime(new Date());
    cal1.add(Calendar.MONTH, -month);
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    return formatter.format(cal1.getTime());

  }

  public static String getStrYear(int year) {
    Calendar cal1 = Calendar.getInstance();
    cal1.setTime(new Date());
    cal1.add(Calendar.YEAR, -year);
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
    return formatter.format(cal1.getTime()) + "年份";
  }

  /**
   * 比较两个日期前后 可以大于或等于
   * 
   * @param starDate
   * @param endDate
   * @return
   */
  public static boolean compareTwoDays(String starDate, String endDate) {
    Calendar cal_start = Calendar.getInstance();
    Calendar cal_end = Calendar.getInstance();
    cal_start.setTime(parseDate(starDate));
    cal_end.setTime(parseDate(endDate));
    return cal_end.after(cal_start);
  }

  public static int getDaysBetween(java.util.Calendar d1,
      java.util.Calendar d2) {
    if (d1.after(d2)) { // swap dates so that d1 is start and d2 is end
      java.util.Calendar swap = d1;
      d1 = d2;
      d2 = swap;
    }
    int days = d2.get(java.util.Calendar.DAY_OF_YEAR)
        - d1.get(java.util.Calendar.DAY_OF_YEAR);
    int y2 = d2.get(java.util.Calendar.YEAR);
    if (d1.get(java.util.Calendar.YEAR) != y2) {
      d1 = (java.util.Calendar) d1.clone();
      do {
        days += d1.getActualMaximum(java.util.Calendar.DAY_OF_YEAR);
        d1.add(java.util.Calendar.YEAR, 1);
      } while (d1.get(java.util.Calendar.YEAR) != y2);
    }
    return days;
  }

  // 得到两个日期之间的年
  public static int dateDiffYear(String starDate, String endDate) {
    int result = 0;
    Calendar d1 = Calendar.getInstance();
    Calendar d2 = Calendar.getInstance();
    d1.setTime(parseDate(starDate));
    d2.setTime(parseDate(endDate));

    // 日期大小翻转
    if (d1.after(d2)) { // swap dates so that d1 is start and d2 is end
      java.util.Calendar swap = d1;
      d1 = d2;
      d2 = swap;
    }
    int yy = d2.get(Calendar.YEAR) - d1.get(Calendar.YEAR);
    int mm = d2.get(Calendar.MONTH) - d1.get(Calendar.MONTH);
    if (mm < 0) {
      result = yy - 1;
    }
    if (mm > 0) {
      result = yy;
    }
    if (mm == 0) {
      if ((d2.getTimeInMillis() - d1.getTimeInMillis()) >= 0) {
        result = yy;
      } else {
        result = yy - 1;
      }
    }
    return result;
  }

  // 获取年龄
  public static int getAgeByBirth(String starDate) {
    return dateDiffYear(starDate, getCurrentDate());
  }
  
  
  
  
  
  /**
   * 默认的日期格式化样式
   */
  public static final String DAY_PATTERN = "yyyy-MM-dd";
  /**
   * 默认的时间格式化样式
   */
  public static final String TIME_PATTERN = "HH:mm:ss";

  /**
   * 将Date格式化成符合默认格式的字符串
   * 
   * @param date
   * @return 返回样例:2012-03-29 14:32:23
   */
  public static String format(Date date) {
      SimpleDateFormat formatTool = new SimpleDateFormat();
      formatTool.applyPattern(DAY_PATTERN + " " + TIME_PATTERN);
      return formatTool.format(date);
  }

  /**
   * 将Date格式化成符合默认日期格式的字符串
   * 
   * @param date
   * @return 返回样例:2012-03-29
   */
  public static String formatDate(Date date) {
      SimpleDateFormat formatTool = new SimpleDateFormat();
      formatTool.applyPattern(DAY_PATTERN);
      return formatTool.format(date);
  }

  /**
   * 将Date格式化成符合默认时间格式的字符串
   * 
   * @param date
   * @return 返回样例:14:32:23
   */
  public static String formatTime(Date date) {
      SimpleDateFormat formatTool = new SimpleDateFormat();
      formatTool.applyPattern(TIME_PATTERN);
      return formatTool.format(date);
  }

  /**
   * 按照pattern格式格式化Date
   * 
   * @param date
   * @param pattern
   *            样例: yyyy/MM/dd
   * @return 返回样例:2012/03/29
   */
  public static String format(Date date, String pattern) {
      SimpleDateFormat formatTool = new SimpleDateFormat();
      formatTool.applyPattern(pattern);
      return formatTool.format(date);
  }

  /**
   * 将符合默认格式的字符串转换成Date
   * 
   * @param dateValue
   *            样例:2012-03-29 14:32:23
   * @return
   * @throws ParseException
   */
  public static Date parse(String dateValue) throws ParseException {
      SimpleDateFormat formatTool = new SimpleDateFormat();
      formatTool.applyPattern(DAY_PATTERN + " " + TIME_PATTERN);
      return formatTool.parse(dateValue);
  }

  /**
   * 将符合默认格式的日期字符串转换成Date
   * 
   * @param dateValue
   *            样例:2012-03-29
   * @return
   * @throws ParseException
   */
  public static Date parseDate1(String dateValue) throws ParseException {
      SimpleDateFormat formatTool = new SimpleDateFormat();
      formatTool.applyPattern(DAY_PATTERN);
      return formatTool.parse(dateValue);
  }

  /**
   * 将符合pattern格式的dateValue转换成Date
   * 
   * @param dateValue
   *            样例:2012/03/29
   * @param pattern
   *            样例:yyyy/MM/dd
   * @return
   * @throws ParseException
   */
  public static Date parse(String dateValue, String pattern)
          throws ParseException {
      SimpleDateFormat formatTool = new SimpleDateFormat();
      formatTool.applyPattern(pattern);
      return formatTool.parse(dateValue);
  }

  /**
   * 返回这一天的最早的时候
   * 
   * @param date
   * @return
   */
  public static Date getEarliest(Date date) {
      return parseTime(date, 0, 0, 0, 0);
  }

  /**
   * 返回这一天的最晚时候
   * 
   * @param date
   * @return
   */
  public static Date getLastest(Date date) {
      return parseTime(date, 23, 59, 59, 999);
  }

  /**
   * 得到指定月的天数
   * 
   * @param year
   * @param month
   * @return
   */
  public static int getMonthLastDay(int year, int month) {
      Calendar a = Calendar.getInstance();
      a.set(Calendar.YEAR, year);
      a.set(Calendar.MONTH, month - 1);
      a.set(Calendar.DATE, 1);// 把日期设置为当月第一天
      a.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
      int maxDate = a.get(Calendar.DATE);
      return maxDate;
  }

  /**
   * 返回指定时间加上num天后的时间
   * 
   * @param date
   * @param num
   * @return
   */
  public static Date add(Date date, int num) {
      Calendar a = Calendar.getInstance();
      a.setTime(date);
      a.add(Calendar.DAY_OF_MONTH, num);
      return a.getTime();
  }

  /**
   * 返回false：date=null，date是1970年；其它都返回true
   * 
   * @param date
   * @return
   */
  public static boolean isNotEmpty(Date date) {
      if (date != null) {
          Calendar a = Calendar.getInstance();
          a.setTime(date);
          return a.get(Calendar.YEAR) != 1970;
      }
      return Boolean.FALSE;
  }

  /**
   * 获得date的小时数0-23
   * 
   * @param date
   * @return
   */
  public static int getHours(Date date) {
      Calendar a = Calendar.getInstance();
      a.setTime(date);
      return a.get(Calendar.HOUR_OF_DAY);
  }

  /**
   * 获得date是一周的第几天，注意：周日 返回 1，周六 返回 7
   * 
   * @param date
   * @return
   */
  public static int getDay(Date date) {
      Calendar a = Calendar.getInstance();
      a.setTime(date);
      return a.get(Calendar.DAY_OF_WEEK);
  }

  /**
   * 设定date的时间细节
   * 
   * @param date
   *            要设定时间细节的date
   * @param hourOfDay
   *            0-23
   * @param minute
   *            0-59
   * @param second
   *            0-59
   * @param milliSecond
   *            0-999
   * @return
   */
  public static Date parseTime(Date date, int hourOfDay, int minute,
          int second, int milliSecond) {
      Calendar cal = Calendar.getInstance();
      cal.setTime(date);
      setCalendarTime(cal, hourOfDay, minute, second, milliSecond);
      return cal.getTime();
  }

  /**
   * 设定date的时间细节
   * 
   * @param date
   *            要设定时间细节的date
   * @param timeDetail
   *            以:号分隔的24小时制的时间，例:16:23:42:267 或 16(等同于16:00:00:000)
   * @return
   */
  public static Date parseTime(Date date, String timeDetail) {

      List<String> strList = new ArrayList<String>();
      strList.addAll(Arrays.asList(timeDetail.split(":")));

      while (strList.size() < 4) {
          strList.add("0");
      }
      return parseTime(date, Integer.parseInt(strList.get(0)),
              Integer.parseInt(strList.get(1)),
              Integer.parseInt(strList.get(2)),
              Integer.parseInt(strList.get(3)));
  }

  /**
   * 指定时间 是否在 当前时间 之后，注：和日期无关
   * 
   * @param time
   *            指定的时间， 传入样例:16:23:42:267 或 16(等同于16:00:00:000)
   * @return
   */
  public static boolean isAfterTime(String time) {
      Date date = parseTime(new Date(), time);
      return date.after(new Date());
  }

  /**
   * 指定时间 是否在 当前时间 之前，注：和日期无关
   * 
   * @param time
   *            指定的时间， 传入样例:16:23:42:267 或 16(等同于16:00:00:000)
   * @return
   */
  public static boolean isBeforeTime(String time) {
      Date date = parseTime(new Date(), time);
      return date.before(new Date());
  }

  private static void setCalendarTime(Calendar cal, int hourOfDay,
          int minute, int second, int milliSecond) {
//	  该方法需要类继承org.apache.commons.lang.time.DateUtils
//      cal.set(HOUR_OF_DAY, hourOfDay);
//      cal.set(MINUTE, minute);
//      cal.set(SECOND, second);
//      cal.set(MILLISECOND, milliSecond);
  }
  
  
  
  
  
  
  
  
  
  
}