/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  SlideInfoEntity.java
 * PACKAGE      :  com.anssy.venturebar.base.entity
 * CREATE DATE  :  2016-8-11
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.venturebar.base.entity;

import com.anssy.webcore.core.entity.IdEntity;

/**
 * @author make it
 * @version SVN #V1# #2016-8-11#
 *          基础_幻灯片信息
 */
public class SlideInfoEntity extends IdEntity {

    private static final long serialVersionUID = -8347117102774382078L;

    /**
     * 图片路径
     */
    private String imageUrl;
    /**
     * 广告路径
     */
    private String ad_Url;
    /**
     * 标题
     */
    private String title;
    /**
     * 标志(0-有效/1-无效)
     */
    private int mark;
    /**
     * 类型(0-幻灯片/1-广告)
     */
    private int type;
    /**
     * 点击次数
     */
    private long clickNum;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAd_Url() {
        return ad_Url;
    }

    public void setAd_Url(String ad_Url) {
        this.ad_Url = ad_Url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getClickNum() {
        return clickNum;
    }

    public void setClickNum(long clickNum) {
        this.clickNum = clickNum;
    }

}