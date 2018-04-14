/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  BaseServer.java
 * PACKAGE      :  com.anssy.inter.base.server
 * CREATE DATE  :  2016-8-12
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.inter.base.server;

import com.anssy.venturebar.base.dao.*;
import com.anssy.venturebar.base.entity.*;
import com.anssy.venturebar.base.vo.DatadictVo;
import com.anssy.inter.base.vo.FieldVo;
import com.anssy.webcore.common.BaseConstants;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author make it
 * @version SVN #V1# #2016-8-12#
 *          基础
 */
@Service("baseServer")
public class BaseServer {

    @Resource
    private ProvinceDao provinceDao;
    @Resource
    private CityDao cityDao;
    @Resource
    private AreaDao areaDao;
    @Resource
    private FieldInfoDao fieldInfoDao;
    @Resource
    private DatadictDao datadictDao;
    @Resource
    private SlideInfoDao slideInfoDao;

    /**
     * 查询所有的省信息
     */
    public List<ProvinceEntity> findAllProvince() throws Exception {
        return provinceDao.findProvince();
    }

    /**
     * 通过省ID查询市信息
     *
     * @param provinceId 省ID
     */
    public List<CityEntity> findCityByProvinceId(Long provinceId) throws Exception {
        return cityDao.findCityByProvinceId(provinceId);
    }

    /**
     * 通过市ID查询区县信息
     *
     * @param cityId 市ID
     */
    public List<AreaEntity> findAreaByCityId(Long cityId) throws Exception {
        return areaDao.findAreaByCityId(cityId);
    }

    /**
     * 通过那么反查省市县ID
     *
     * @param name name
     */
    public Long[] findAreaIdByName(String name) throws Exception {
        Long[] areaIds = {0L, 0L, 0L};
        try {
            if (name != null && StringUtils.isNotBlank(name)) {
                Long areaId = areaDao.findAreaIdByName(name);
                Long cityId = cityDao.findCityIdByName(name);
                Long provinceId = provinceDao.findProvinceIdByName(name);
                if (areaId != null) {
                    areaIds[2] = areaId;
                    cityId = areaDao.findCityId(areaId);
                    areaIds[1] = areaDao.findCityId(areaId);
                    areaIds[0] = cityDao.findProvinceId(cityId);
                } else if (cityId != null) {
                    areaIds[1] = cityId;
                    areaIds[0] = cityDao.findProvinceId(cityId);
                } else if (provinceId != null) {
                    areaIds[0] = provinceId;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return areaIds;
    }

    /**
     * 查询某一级的行业
     */
    public List<FieldInfoEntity> findField(FieldVo vo) throws Exception {
        return fieldInfoDao.findField(vo);
    }

    /**
     * 查询类型全部行业
     */
    public List<FieldInfoEntity> findFieldAll(int type) throws Exception {
        return fieldInfoDao.findFieldByType(type);
    }

    /**
     * 获取性别信息
     */
    public List<DatadictVo> findSex() throws Exception {
        return datadictDao.findDictByCategory("SEX");
    }

    /**
     * 查询首页轮播幻灯片信息
     */
    public List<SlideInfoEntity> findSlideList(int type) throws Exception {
        System.out.println("==========type 2=============\n type : " + type + "\n==========type 2===============");
        List<SlideInfoEntity> slides = slideInfoDao.findSlideList(type);
        System.out.println("==========type 3=============\n type : " + type + "\n==========type 3===============");

        if (!slides.isEmpty()) {
            for (SlideInfoEntity slide : slides) {
                slide.setImageUrl(BaseConstants.PICTURE_URL + slide.getImageUrl());
            }
        }
        return slides;
    }

    /**
     * 修改点击次数
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateClickNum(Long id) throws Exception {
        slideInfoDao.updateClickNum(id);
    }

    /**
     * 按行业信息搜索时，获取类型的ID数组
     * 注 选择一级类型时需要能查询出属于此行业信息的全部数据(二级)
     */
    public Long[] findFieldIds(Long fieldId) throws Exception {
        Long[] fields;
        FieldInfoEntity info = fieldInfoDao.findFieldById(fieldId);
        if (info != null && info.getFieldLevel() == 1) {
            List<FieldInfoEntity> infos = fieldInfoDao.findFieldByFatherId(fieldId);
            if (!infos.isEmpty()) {
                int len = infos.size();
                fields = new Long[len + 1];
                fields[0] = fieldId;
                for (int i = 0; i < len; i++) {
                    fields[i + 1] = infos.get(i).getId();
                }
            } else {
                fields = new Long[1];
                fields[0] = fieldId;
            }
        } else {
            fields = new Long[1];
            fields[0] = fieldId;
        }
        return fields;
    }

}