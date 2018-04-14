package com.anssy.arit;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.security.MessageDigest;

/**
 * Created by Mr.薛 on 2017-11-15.
 * 生成加密数据的算法 生成32位字符串
 */
public class SF {
//加密桩字符串   位数：16/32
    private String getStr(String plainText){
        try {
            //生成实现指定摘要算法的 MessageDigest 对象。
            MessageDigest md = MessageDigest.getInstance("MD5");
            //使用指定的字节数组更新摘要。
            md.update(plainText.getBytes());
            //通过执行诸如填充之类的最终操作完成哈希计算。
            byte b[] = md.digest();
            //生成具体的md5密码到buf数组
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
//            System.out.println("32位: " + buf.toString());// 32位的加密
            plainText = buf.toString();
//            System.out.println("16位: " + buf.toString().substring(8, 24));// 16位的加密，其实就是32位加密后的截取
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return plainText;
    }

//      小写全转大写
    private String ToDx(String str)
    {
        return str.toUpperCase();
    }

    private String encryptBASE64(byte[] key) throws Exception {
        return (new BASE64Encoder()).encodeBuffer(key);
    }

//    删除末尾字符 将= 改为 W
    private String deleteLast(String str){
        return  str.replace("=","W");
    }

    public static void main(String[] args) {
//       new SF().encryption("asadsda545");
        System.out.println(new SF().encryption("784asdaWERSDF"));
    }

    private String encryption(String str)
    {
        try {
        str = ToDx(deleteLast(encryptBASE64(ToDx(getStr(str)).getBytes())));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }





}
