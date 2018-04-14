package com.lt.website.utils;

import java.io.FileOutputStream;
import java.io.OutputStream;

import sun.misc.BASE64Decoder;

public class Base64ToImg {
    //base64字符串转化成图片             字符串       需要转换的图片名称
    public static boolean GenerateImage(String imgStr,String imgName)
    {   //对字节数组字符串进行Base64解码并生成图片
        if (imgStr == null) //图像数据为空
            return false;
        BASE64Decoder decoder = new BASE64Decoder();
        try
        {
            //Base64解码
            byte[] b = decoder.decodeBuffer(imgStr);
            for(int i=0;i<b.length;++i)
            {
                if(b[i]<0)
                {//调整异常数据
                    b[i]+=256;
                }
            }
            //生成jpeg图片
//            String imgFilePath = "f://403.jpg";//新生成的图片
//            String imgFilePath = "f://"+imgName;//新生成的图片
            String imgFilePath = imgName;//新生成的图片  +

            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
}
