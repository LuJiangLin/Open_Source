/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  PictureInfoEntity.java
 * PACKAGE      :  com.anssy.venturebar.picture.entity
 * CREATE DATE  :  2016-8-19
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.venturebar.picture.entity;

import com.anssy.webcore.core.entity.IdEntity;

import java.util.Date;

/**
 * @author make it
 * @version SVN #V1# #2016-8-19#
 *          图片_图片信息
 */
public class PictureInfoEntity extends IdEntity {

    private static final long serialVersionUID = 6668235549337036226L;

    /**
     * 图片ID
     */
    private String pictureId;
    /**
     * 图片标志
     */
    private String pictureMark;
    /**
     * 图片类型(0-列表小图片(由主图缩小而成)/1-主图/2-其它(多图片展示))
     */
    private int pictureType;
    /**
     * 图片路径
     */
    private String picturePath;
    /**
     * 上传时间
     */
    private Date uploadTime;

    public String getPictureId() {
        return pictureId;
    }

    public void setPictureId(String pictureId) {
        this.pictureId = pictureId;
    }

    public String getPictureMark() {
        return pictureMark;
    }

    public void setPictureMark(String pictureMark) {
        this.pictureMark = pictureMark;
    }

    public int getPictureType() {
        return pictureType;
    }

    public void setPictureType(int pictureType) {
        this.pictureType = pictureType;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

}