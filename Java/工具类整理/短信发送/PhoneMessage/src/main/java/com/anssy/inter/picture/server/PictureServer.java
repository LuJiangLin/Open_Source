/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  PictureServer.java
 * PACKAGE      :  com.anssy.inter.picture.server
 * CREATE DATE  :  2016-8-19
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.inter.picture.server;

import com.anssy.venturebar.picture.dao.PictureInfoDao;
import com.anssy.venturebar.picture.entity.PictureInfoEntity;
import com.anssy.inter.picture.vo.PictureVo;
import com.anssy.webcore.common.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.cglib.util.StringSwitcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author make it
 * @version SVN #V1# #2016-8-19#
 *          图片
 */
@Service("pictureServer")
public class PictureServer {

    @Resource
    private PictureInfoDao pictureInfoDao;

    /**
     * 上传图片
     */
    public String[] uploadPicture(PictureVo vo) throws Exception {
        // (0-列表小图片(由主图缩小而成)/1-主图/2-其它(多图片展示))
        String dir = BaseConstants.findPictureDir();
        String path = savePicture(dir, vo.getEncrypt(), vo.getSuffix());
        String pictureId;
        String mark = null;
        String[] res = new String[2];
        if (StringUtils.isNotBlank(vo.getPictureId())) {
            pictureId = vo.getPictureId();
        } else {
            pictureId = findPictureId();
        }
        if (vo.getPictureType() > 0) {
            mark = findPictureMark();
            // 保存大图片
            insertPicture(pictureId, mark, path, vo.getPictureType());
            if (vo.getPictureType() == 1) {
                // 缩小图片
                path = saveTinyPicture(dir, path, vo.getSuffix());
                // 保存小图片
                insertPicture(pictureId, mark, path, 0);
            }
        }
        res[0] = pictureId;
        res[1] = mark;
        return res;
    }

    /**
     * 上传图片
     */
    public String[] upload(int pictureType, String pictureId, MultipartFile file) throws Exception {
        // (0-列表小图片(由主图缩小而成)/1-主图/2-其它(多图片展示))
        String dir = BaseConstants.findPictureDir();
        String name = file.getOriginalFilename();
        String suffix = "";
        String mark = null;
        String[] res = new String[2];
        if (StringUtils.isNotBlank(name)) {
            suffix = name.substring(name.lastIndexOf("."), name.length());
        }
        String path = savePicture(dir, file, suffix);
        if (StringUtils.isBlank(pictureId)) {
            pictureId = findPictureId();
        }
        if (pictureType > 0) {
            mark = findPictureMark();
            // 保存大图片
            insertPicture(pictureId, mark, path, pictureType);
            if (pictureType == 1) {
                // 缩小图片
                path = saveTinyPicture(dir, path, suffix);
                // 保存小图片
                insertPicture(pictureId, mark, path, 0);
            }
        }
        res[0] = pictureId;
        res[1] = mark;
        return res;
    }

    /**
     * 拼凑图片路径，图片不存在使用默认图片
     */
    public File findImageFile(String suffixPath) throws Exception {
        File file;
        if (StringUtils.isNotBlank(suffixPath)) {
            file = new File(BaseConstants.findPictureDir(), suffixPath);
            if (!file.exists() || !file.isFile()) {//默认图片
                file = new File(BaseConstants.findPictureDir(), "default.png");
            }
        } else {
            //默认图片
            file = new File(BaseConstants.findPictureDir(), "default.png");
        }
        return file;
    }

    /**
     * 获取列表图片URL
     */
    public String findTinyURL(String pictureId) throws Exception {
        pictureId = pictureId.replaceAll("\\\\", "/");
        if (pictureId.indexOf("download/image_url/") != -1) {

            StringBuffer sb = new StringBuffer();
            String[] urls = pictureId.split(";");

            return "http://www.startuponline.cn/" + urls[0];
        }

        PictureInfoEntity picture = pictureInfoDao.findTinyPicture(pictureId);
        if (picture != null) {
            return BaseConstants.PICTURE_URL + picture.getPicturePath();
        }
        return "";
    }

    public PictureInfoEntity findTinyPicture(String pictureId) throws Exception {
        return pictureInfoDao.findTinyPicture(pictureId);
    }

    /**
     * 获取图片URL
     */
    public List<String> findURL(String pictureId) throws Exception {
        List<String> urllist = new ArrayList<String>();

        pictureId = pictureId.replaceAll("\\\\", "/");
        if (pictureId.indexOf("download/image_url/") != -1) {
            String[] urls = pictureId.split(";");
            for (String url : urls) {
                if (StringUtils.isNotBlank(url)) {
                    urllist.add("http://www.startuponline.cn/" + url);
                }
            }

            return urllist;
        }
        List<PictureInfoEntity> pictures = pictureInfoDao.findbigPicture(pictureId);
        for (PictureInfoEntity picture : pictures) {
            urllist.add(BaseConstants.PICTURE_URL + picture.getPicturePath());
        }
        return urllist;
    }

    public List<PictureInfoEntity> findURL(String pictureId, int pictureType) throws Exception {
        List<PictureInfoEntity> ps = pictureInfoDao.findPictureAll(pictureId);
        List<PictureInfoEntity> temp = new ArrayList<PictureInfoEntity>();
        for (PictureInfoEntity p : ps) {
            if (pictureType == p.getPictureType()) {
                temp.add(p);
            }
        }
        return temp;
    }

    public List<String> findURL(List<PictureInfoEntity> pictures) throws Exception {
        List<String> urls = new ArrayList<String>();
        for (PictureInfoEntity picture : pictures) {
            urls.add(BaseConstants.PICTURE_URL + picture.getPicturePath());
        }
        return urls;
    }

    public List<PictureInfoEntity> findbigPicture(String pictureId) throws Exception {
        return pictureInfoDao.findbigPicture(pictureId);
    }

    /**
     * 删除图片
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean deletePicture(String pictureId) throws Exception {
        boolean flag = false;
        List<PictureInfoEntity> pictures = pictureInfoDao.findPictureAll(pictureId);
        if (!pictures.isEmpty()) {
            String dir = BaseConstants.findPictureDir();
            // 删除图片
            for (PictureInfoEntity picture : pictures) {
                FileUtil.deleteFile(dir + picture.getPicturePath());
            }
            // 删除数据
            if (pictureInfoDao.deletePicture(pictureId) > 0) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 删除图片
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean deletePictureByMark(String pictureMark) throws Exception {
        boolean flag = false;
        List<PictureInfoEntity> pictures = pictureInfoDao.findPictureByMark(pictureMark);
        if (!pictures.isEmpty()) {
            String dir = BaseConstants.findPictureDir();
            // 删除图片
            for (PictureInfoEntity picture : pictures) {
                FileUtil.deleteFile(dir + picture.getPicturePath());
            }
            // 删除数据
            if (pictureInfoDao.deletePictureByMark(pictureMark) > 0) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 添加图片信息
     */
    @Transactional(propagation = Propagation.REQUIRED)
    private boolean insertPicture(String pictureId, String mark, String picturePath, int pictureType) throws Exception {
        boolean flag = false;
        PictureInfoEntity picture = new PictureInfoEntity();
        picture.setPictureId(pictureId);
        picture.setPictureMark(mark);
        picture.setPicturePath(picturePath);
        picture.setPictureType(pictureType);
        picture.setUploadTime(new Date());
        if (pictureInfoDao.insertPicture(picture) > 0) {
            flag = true;
        }
        return flag;
    }

    /**
     * 保存图片
     */
    private String savePicture(String dir, String encrypt, String suffix) throws Exception {
        String prefix = DateTimeUtil.getCurDate().replaceAll("-", "/");
        File dst = new File(dir + prefix);
        if (!dst.exists()) {
            dst.mkdirs();
        }
        StringBuilder path = new StringBuilder();
        path.append(prefix).append(File.separator);
        path.append(UUID.randomUUID().toString());
        path.append("-");
        path.append(StringRandomUtil.RandomString(5));
        path.append(suffix);
        ImageBase64Util.GenerateImage(encrypt, dir + path.toString());
        return path.toString();
    }

    private String savePicture(String dir, MultipartFile file, String suffix) throws Exception {
        String prefix = DateTimeUtil.getCurDate().replaceAll("-", "/");
        File dst = new File(dir + prefix);
        if (!dst.exists()) {
            dst.mkdirs();
        }
        StringBuilder path = new StringBuilder();
        // path.append(prefix).append(File.separator);
        // 不能用上面一句  "\"在移动设备上回被认为是转译符
        path.append(prefix).append("/");
        path.append(UUID.randomUUID().toString());
        path.append("-");
        path.append(StringRandomUtil.RandomString(5));
        path.append(suffix);
        File imageFile = new File(dir, path.toString());
        if (!imageFile.exists()) {
            imageFile.mkdirs();
        }
        file.transferTo(imageFile);
        return path.toString();
    }

    /**
     * 保存小图片
     */
    private String saveTinyPicture(String dir, String path, String suffix) throws Exception {
        String prefix = DateTimeUtil.getCurDate().replaceAll("-", "/");
        StringBuilder tempPath = new StringBuilder();
        tempPath.append(prefix).append(File.separator);
        tempPath.append(System.currentTimeMillis());
        tempPath.append("-");
        tempPath.append(StringRandomUtil.RandomString(20));
        tempPath.append(suffix);
        ImageUtils.scale(dir + path, dir + tempPath.toString(), 3, false);
        return tempPath.toString();
    }

    /**
     * 产生图片ID
     */
    private String findPictureId() {
        return UUID.randomUUID().toString() + "-" + StringRandomUtil.RandomString(10);
    }

    /**
     * 产生图片标志
     */
    private String findPictureMark() {
        return findPictureId();
    }

}