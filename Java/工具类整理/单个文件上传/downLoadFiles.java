package com.lt.website.utils;

import com.lt.core.constants.ResultStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mr.薛 on 2017/9/1 0001.
 */
public class downLoadFiles {
//文件下载   需要提供   文件所在的绝对路径（RealPath = 服务器路径拼接）  下载之后显示的名字（dname）   返回map  前台ajax通过读取map中的内容显示出来
    public Object downLoad(String RealPath,String dname, HttpServletRequest request, HttpServletResponse response)throws Exception
    {
//          读取文件  设置路径    注意这里需要抛出异常
        InputStream in = new FileInputStream(RealPath);
//        OutputStream out = response.getOutputStream();
        File f= new File("d:" +dname) ;
        OutputStream out = null ; // 准备好一个输出的对象
        out = new FileOutputStream(f)  ; // 通过对象多态性，进行实例化
        byte[] temp=new byte[1024*1024*10];//限制下载速度
        int b1;
        while((b1=in.read(temp))!= -1)
        {
            out.write(temp,0,b1);
        }
        in.close();
        out.flush();
        out.close();
        Map map = new HashMap();
        map.put("status", ResultStatus.RESULT_STATUS_FAILURE);
        map.put("errorMsg", "作业已经帮您下载到D盘啦！");
        return map;
    }


//前台下载的方法    后台根据ID查询获取绝对路径 调用下载方法
/*
function down(id)
{
    $.ajax({
            url: '${base}/admin/datum/downLoad',
            datatype: 'json',
            type: 'post',// ‘get’
            data:{id:id},
            success: function (data) {
            $.messager.show({
                    title: '提示',
                    msg: data.errorMsg
            });
        }
    });
}
*/


}
