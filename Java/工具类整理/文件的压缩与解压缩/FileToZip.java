package com.anssy.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
 
/**
 *   �ļ�ѹ�����ѹ�� .rar
 *
 * @author Mr.Ѧ
 *
 */
public final class FileToZip {
 
    FileToZip() {
 
    }
 
    /**
     * �������sourceFilePathĿ¼�µ�Դ�ļ�,�����fileName���Ƶ�ZIP�ļ�,����ŵ�zipFilePath��
     *
     * @param sourceFilePath
     *            ��ѹ�����ļ�·��
     * @param zipFilePath
     *            ѹ������·��
     * @param fileName
     *            ѹ�����ļ�������
     * @return flag
     */
    public static boolean fileToZip(String sourceFilePath, String zipFilePath,
                                    String fileName) {
        boolean flag = false;
        File sourceFile = new File(sourceFilePath);
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        ZipOutputStream zos = null;
 
        if (sourceFile.exists() == false) {
            System.out.println("��ѹ�����ļ�Ŀ¼��" + sourceFilePath+ " ������.");
        } else {
            try {
                File zipFile = new File(zipFilePath + "/" + fileName + ".rar");
                //                File zipFile = new File(zipFilePath + "/" + fileName + ".zip");
                if (zipFile.exists()) {
                    System.out.println(zipFilePath + " Ŀ¼�´�������Ϊ��"+ fileName + ".rar" + " ����ļ�. ");
//                  ��Ҫ���ǵĵĻ�����ͼ�һ��ɾ���ļ������ͺ���
                } else {
                    File[] sourceFiles = sourceFile.listFiles();
                    if (null == sourceFiles || sourceFiles.length < 1) {
                        System.out.println("��ѹ�����ļ�Ŀ¼��" + sourceFilePath+ " ���治�����ļ�,����ѹ��.");
//                        ��������ÿ��Բ������ļ���ֱ��ѹ���ļ��Ļ��Ϳ���ֱ��ȥ���ж���� ȥ�����ļ��е��ж�   ���ļ����滻Ϊ׼ȷ���ļ�·������
                    } else {
                        fos = new FileOutputStream(zipFile);
                        zos = new ZipOutputStream(new BufferedOutputStream(fos));
                        byte[] bufs = new byte[1024 * 10];
                        for (int i = 0; i < sourceFiles.length; i++) {
                            // ����ZIPʵ��,����ӽ�ѹ����
                            ZipEntry zipEntry = new ZipEntry(sourceFiles[i]
                                                             .getName());
                            zos.putNextEntry(zipEntry);
                            // ��ȡ��ѹ�����ļ���д��ѹ������
                            fis = new FileInputStream(sourceFiles[i]);
                            bis = new BufferedInputStream(fis, 1024 * 10);
                            int read = 0;
                            while ((read = bis.read(bufs, 0, 1024 * 10)) != -1) {
                                zos.write(bufs, 0, read);
                            }
                        }
                        flag = true;
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } finally {
                // �ر���
                try {
                    if (null != bis)
                        bis.close();
                    if (null != zos)
                        zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        }
 
        return flag;
    }
 
    public static void unZip(String sourceFilePath, String unzipFilePath) {
        File sourceFile = new File(sourceFilePath);
        ZipFile zipFile = null;
        ZipEntry zipEntry = null;
        ZipInputStream zis = null;
        FileOutputStream fos = null;
        FileInputStream fis = null;
 
        if (sourceFile.exists() == false) {
            System.out.println("����ѹ���ļ�Ŀ¼��" + sourceFilePath+ "������.");
        } else {
            try {
                System.out.println("��ʼ��ѹ��" + sourceFilePath);
                zipFile = new ZipFile(sourceFile);
                zis = new ZipInputStream(new FileInputStream(sourceFile));
                while ((zipEntry = zis.getNextEntry()) != null) {
                    String fileName = zipEntry.getName();
                    File temp = new File(unzipFilePath + "\\" + fileName);
                    System.out.println(fileName + "     ��ѹ��" + unzipFilePath);
                    if (!temp.getParentFile().exists()) {
                        temp.getParentFile().mkdirs();
                    }
                    fos = new FileOutputStream(temp);
                    InputStream is = zipFile.getInputStream(zipEntry);
                    int len = 0;
                    while ((len = is.read()) != -1) {
                        fos.write(len);
                    }
                    is.close();
                }
 
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ZipException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                // �ر���
                try {
                    if (null != fos)
                        fos.close();
                    if (null != fis)
                        fis.close();
                    if (null != zis)
                        zis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        }
 
    }
 
    /**
     * ���ļ������ZIPѹ���ļ�,main��������
     *
     * @param args
     */
    public static void main(String[] args) {
    	new FileToZip().FileToZip();
//    	new FileToZip().ZipToFile();
    }
    
//    �ļ��д��ѹ��
    public void FileToZip()
    {
    	String sourceFilePath = "F:/a";//Ŀ���ļ���
        String zipFilePath = "F:/a";//�浵ѹ������ļ���
        String fileName = "ѹ������";//ѹ������ʼ����ƣ�ϵͳ�Զ���.rar��
        boolean flag = FileToZip.fileToZip(sourceFilePath, zipFilePath,
        fileName);
        if(flag) {
        	System.out.println("�ļ�����ɹ�.");
        } else {
        	System.out.println("�ļ����ʧ��.");
        }
    }
    
//    ѹ���ļ���ѹ
    public void ZipToFile()
    {
    	String sourceFilePath = "F:/a/ѹ������.RAR";//Ŀ���ļ���
    	FileToZip.unZip(sourceFilePath, "F:/a");
    }
    
}