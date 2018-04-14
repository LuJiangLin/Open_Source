package com.anssy.utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
 
import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;
 
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
 
/**
 * @author Mr.薛
 * 图片压缩
 */
public class ImageUtil {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(ImageUtil.class);
//    				         原图路径            目标图片路径
    public void imageScale(String srcFilePath, String targetFilePath,int width, int height) {
//    	     根据路径将对象拿出来封装到File中
        this.imageScale(new File(srcFilePath), new File(targetFilePath), width,height);
    }
 
    public void imageScale(File srcFile, File targetFile, int width, int height) {
        try {
//        	图片转化为流
            Image image = javax.imageio.ImageIO.read(srcFile);
            image = image.getScaledInstance(width, height,
                    Image.SCALE_AREA_AVERAGING);
            // Make a BufferedImage from the Image.
            BufferedImage mBufferedImage = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
//            开始绘制图片   拿到画笔
            Graphics2D g2 = mBufferedImage.createGraphics();            
            g2.drawImage(image, 0, 0, width, height, Color.white, null);
            g2.dispose();
 
            float[] kernelData2 = { -0.125f, -0.125f, -0.125f, -0.125f, 2,-0.125f, -0.125f, -0.125f, -0.125f };
            Kernel kernel = new Kernel(3, 3, kernelData2);
            ConvolveOp cOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
            mBufferedImage = cOp.filter(mBufferedImage, null);
// 			检测文件存在否？存在则开始转   不存在就退出执行
            File targetDir = targetFile.getParentFile();
            if (!targetDir.exists())
                targetDir.mkdirs();
// 			做为流输出
            FileOutputStream out = new FileOutputStream(targetFile);
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            encoder.encode(mBufferedImage);
            out.close();
        } catch (Exception e) {
//        	打印日志
            logger.error(
                    "imageScale(String, String, int, int) - 图片压缩出错 - srcFilePath="
                            + srcFile.getPath() + ", targeFilePath="
                            + targetFile.getPath() + ", width=" + width
                            + ", height=" + height, e);
        }
    }
 
// 		可省略   提供给绘制使用的方法   
    public void imageScale(FileItem fileItem, String targetFilePath, int width,
            int height) {
        try {
            InputStream input = fileItem.getInputStream();
            Image image = javax.imageio.ImageIO.read(input);
 
            image = image.getScaledInstance(width, height,
                    Image.SCALE_AREA_AVERAGING);
            BufferedImage mBufferedImage = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = mBufferedImage.createGraphics();
 
            g2.drawImage(image, 0, 0, width, height, Color.white, null);
            g2.dispose();
 
            float[] kernelData2 = { -0.125f, -0.125f, -0.125f, -0.125f, 2,
                    -0.125f, -0.125f, -0.125f, -0.125f };
            Kernel kernel = new Kernel(3, 3, kernelData2);
            ConvolveOp cOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
            mBufferedImage = cOp.filter(mBufferedImage, null);
 
            File target = new File(targetFilePath);
            File targetDir = target.getParentFile();
            if (!targetDir.exists())
                targetDir.mkdirs();
 
            FileOutputStream out = new FileOutputStream(targetFilePath);
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            encoder.encode(mBufferedImage);
            out.close();
            input.close();
        } catch (Exception e) {
            logger.error(
                    "imageScale(String, String, int, int) - 图片压缩出错 - fileItem="
                            + fileItem.getName() + ", targetFilePath="
                            + targetFilePath + ", width=" + width + ", height="
                            + height, e);
        }
    }
   
     
    public static void main(String[] args) {
//        原图路径               压缩后路径     大小（尺寸）
        new ImageUtil().imageScale("F:/111518eplldaz6ds6cj4dc.jpg", "F:/t.jpg", 270, 220);
    }
}