package com.anssy.utils;
import java.io.*;

/**
 * Created by Administrator on 2018/1/24.
 */
public class DB {

//      public static void main(String[] args) throws IOException {
//          DB db = new DB();
//          String fileName = String.valueOf(System.currentTimeMillis()) + "financeSystem.sql";//替换文件名称
//          String savePath = "D:\\";
//          db.backup(savePath,fileName);
//      }

//    数据备份
    public void backup(String savePath,String fileName) {
        try {
            Runtime rt = Runtime.getRuntime();

            // 调用 调用mysql的安装目录的命令
            Process child = rt
                    .exec("C:\\Program Files (x86)\\MySQL\\MySQL Server 5.0\\bin\\mysqldump -h localhost -uroot -ptest dp-lte-boot");
//                    .exec("C:\\Program Files (x86)\\MySQL\\MySQL Server 5.0\\bin\\mysqldump -h code.anssy.com -uanssydatabase -panssy!@#wb7jgD9m%bwj! financeSystem");
            // 设置导出编码为utf-8。这里必须是utf-8
            // 把进程执行中的控制台输出信息写入.sql文件，即生成了备份文件。注：如果不对控制台信息进行读出，则会导致进程堵塞无法运行
            InputStream in = child.getInputStream();// 控制台的输出信息作为输入流

            InputStreamReader xx = new InputStreamReader(in, "utf-8");
            // 设置输出流编码为utf-8。这里必须是utf-8，否则从流中读入的是乱码
            String inStr;
            StringBuffer sb = new StringBuffer("");
            String outStr;
            // 组合控制台输出信息字符串
            BufferedReader br = new BufferedReader(xx);
            while ((inStr = br.readLine()) != null) {
                sb.append(inStr + "\r\n");
            }
            outStr = sb.toString();
            // 要用来做导入用的sql目标文件：
            FileOutputStream fout = new FileOutputStream(savePath+fileName);
            OutputStreamWriter writer = new OutputStreamWriter(fout, "utf-8");
            writer.write(outStr);
            writer.flush();
            in.close();
            xx.close();
            br.close();
            writer.close();
            fout.close();
            System.out.println("");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


//数据还原
public void restore(String databaseName) {
    try {
        Runtime runtime = Runtime.getRuntime();
        Process process = runtime
//                .exec("e:\\MySQL\\bin\\mysql.exe -hlocalhost -uroot -p123 --default-character-set=utf8 "
                .exec("C:\\Program Files (x86)\\MySQL\\MySQL Server 5.0\\bin\\mysql.exe -hlocalhost -uroot -ptest --default-character-set=utf8" + databaseName);
        OutputStream outputStream = process.getOutputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream("C:\\test.sql"), "utf-8"));
        String str = null;
        StringBuffer sb = new StringBuffer();
        while ((str = br.readLine()) != null) {
            sb.append(str + "\r\n");
        }
        str = sb.toString();
        // System.out.println(str);
        OutputStreamWriter writer = new OutputStreamWriter(outputStream,
                "utf-8");
        writer.write(str);
        writer.flush();
        outputStream.close();
        br.close();
        writer.close();
    } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }
}


}
