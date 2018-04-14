package com.anssy.webcore.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by makeit on 16/8/24.
 */
public class DBHelper {
    public static final String url = "jdbc:mysql://114.55.57.125:3306/app_cyzx";
    public static final String name = "com.mysql.jdbc.Driver";
    public static final String user = "app_reader";
    public static final String password = "123456";

    public Connection conn = null;
    public PreparedStatement pst = null;

    public DBHelper(String sql) {
        try {
            Class.forName(name);//指定连接类型
            conn = DriverManager.getConnection(url, user, password);//获取连接
            pst = conn.prepareStatement(sql);//准备执行语句


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            this.conn.close();
            this.pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static DBHelper db1 = null;
    static ResultSet ret = null;

    public static void main(String[] args) {
        // String sql = "select id,title,tb_instlink,update_time from app_zczx order by id"; // SQL语句

        String sql = "select title,tb_instlink,update_time from app_hyzx" +
                           " where update_time > '2016-10-12 09:12:33' order by update_time";//SQL语句; // SQL语句
        // SQL语句
      // String sql = "select title,tb_instlink,update_time from app_zczx where update_time " +
      //           "<= '2016-08-08 09:12:33' order by update_time";//SQL语句
        db1 = new DBHelper(sql); // 创建DBHelper对象

        try {
            ret = db1.pst.executeQuery();//执行语句，得到结果集
            while (ret.next()) {
                String title = ret.getString(1);
                String url = ret.getString(2);
                String date = ret.getString(3);
                System.out.println("title : " + title + "\nurl : " + url + "\ndate : " + date);
            }//显示数据
            ret.close();
            db1.close();//关闭连接
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
