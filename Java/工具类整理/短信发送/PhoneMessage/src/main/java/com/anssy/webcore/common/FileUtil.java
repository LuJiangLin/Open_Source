/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  FileUtil.java
 * PACKAGE      :  com.anssy.webcore.common
 * CREATE DATE  :  2016-8-19
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.webcore.common;

import java.io.*;

/**
 * @author make it
 * @version SVN #V1# #2016-8-19#
 */
public class FileUtil {

    private static final int BUFFER_SIZE = 16 * 1024;

    /**
     * 删除文件
     */
    public static boolean deleteFile(String path) throws Exception {
        File imageFile = new File(path);
        return imageFile.exists() && imageFile.isFile() && imageFile.delete();
    }

    /**
     * 复制文件
     */
    public static void copy(File src, File dst) throws Exception {
        try {
            InputStream in = null;
            OutputStream out = null;
            try {
                in = new BufferedInputStream(new FileInputStream(src), BUFFER_SIZE);
                out = new BufferedOutputStream(new FileOutputStream(dst), BUFFER_SIZE);
                byte[] buffer = new byte[BUFFER_SIZE];
                while (in.read(buffer) > 0) {
                    out.write(buffer);
                }
            } finally {
                if (null != in) {
                    in.close();
                }
                if (null != out) {
                    out.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 文件路径复制
     */
    public static void copyFolder(File srcFolder, File destFolder)
            throws Exception {
        File[] files = srcFolder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    String pathname = destFolder + File.separator + file.getName();
                    File dest = new File(pathname);
                    File destPar = dest.getParentFile();
                    destPar.mkdirs();
                    if (!dest.exists()) {
                        dest.createNewFile();
                    }
                    copy(file, dest);
                } else if (file.isDirectory()) {
                    copyFolder(file, new File(destFolder, file.getName()));
                }
            }
        }
    }

    /**
     * 得到文件后缀名
     */
    public static String getExtention(String fileName) throws Exception {
        int pos = fileName.lastIndexOf(".");
        return fileName.substring(pos);
    }

}