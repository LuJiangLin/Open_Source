/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  PictureVo.java
 * PACKAGE      :  com.anssy.inter.picture.vo
 * CREATE DATE  :  2016-8-19
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.inter.picture.vo;

/**
 * @author make it
 * @version SVN #V1# #2016-8-19#
 *          图片辅助类
 */
public class PictureVo {

    /**
     * 图片ID
     */
    private String pictureId;
    /**
     * 图片类型(0-列表小图片(由主图缩小而成)/1-主图/2-其它(多图片展示))
     */
    private int pictureType;
    /**
     * 图片加密字符(base64加密)
     */
    private String encrypt;
    /**
     * 图片后缀名(.jpg/.png)等等
     */
    private String suffix;

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

    public String getEncrypt() {
        return encrypt;
    }

    public void setEncrypt(String encrypt) {
        this.encrypt = encrypt;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

}