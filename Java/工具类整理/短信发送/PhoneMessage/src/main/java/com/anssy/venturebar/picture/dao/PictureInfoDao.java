/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  PictureInfoDao.java
 * PACKAGE      :  com.anssy.venturebar.picture.dao
 * CREATE DATE  :  2016-8-19
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.venturebar.picture.dao;

import com.anssy.venturebar.picture.entity.PictureInfoEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author make it
 * @version SVN #V1# #2016-8-19#
 *          图片_图片信息
 */
@Repository("pictureInfoDao")
public interface PictureInfoDao {

    /**
     * 添加图片
     */
    int insertPicture(PictureInfoEntity picture);

    /**
     * 通过图片ID查询列表小图片(单个)
     */
    PictureInfoEntity findTinyPicture(String pictureId);

    /**
     * 通过图片ID查询大图片(多个)
     */
    List<PictureInfoEntity> findbigPicture(String pictureId);

    /**
     * 通过图片ID查询图片
     */
    List<PictureInfoEntity> findPictureAll(String pictureId);

    /**
     * 通过图片标志查询图片
     */
    List<PictureInfoEntity> findPictureByMark(String pictureMark);

    /**
     * 通过图片ID删除图片
     */
    int deletePicture(String pictureId);

    /**
     * 通过ID删除图片
     */
    int deletePictureByMark(String pictureMark);

}