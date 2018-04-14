/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  PictureTypeVo.java
 * PACKAGE      :  com.anssy.inter.picture.vo
 * CREATE DATE  :  2016-8-2
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.inter.picture.vo;

/**
 * @author make it
 * @version SVN #V1# #2016-8-2#
 */
public class PictureTypeVo {

    /**
     * 图片ID
     */
    private String pictureId;
    /**
     * 图片类型(0-列表小图片(由主图缩小而成)/1-主图/2-其它(多图片展示))
     */
    private int pictureType;

    public String getPictureId() {
        return pictureId;
    }

    public void setPictureId(String pictureId) {
        this.pictureId = pictureId;
    }

    public int getPictureType() {
        return pictureType;
    }

    public void setPictureType(int pictureType) {
        this.pictureType = pictureType;
    }

}