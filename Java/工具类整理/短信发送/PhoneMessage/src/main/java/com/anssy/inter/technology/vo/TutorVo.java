/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  TutorVo.java
 * PACKAGE      :  com.anssy.inter.technology.vo
 * CREATE DATE  :  2016-8-25
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.inter.technology.vo;

/**
 * @author make it
 * @version SVN #V1# #2016-8-25#
 *          导师辅助类
 */
public class TutorVo {

    private Long id;
    /**
     * 头像
     */
    private String headImage;
    /**
     * 姓名
     */
    private String name;
    /**
     * 性别
     */
    private int sex;
    /**
     * 职称
     */
    private String title;
    /**
     * 职务
     */
    private String post;
    /**
     * 个人标签
     */
    private String label;
    /**
     * 省ID
     */
    private Long provinceId;
    /**
     * 市ID
     */
    private Long cityId;
    /**
     * 区县ID
     */
    private Long areaId;
    /**
     * 所在领域
     */
    private String field;
    /**
     * 所属机构
     */
    private String organization;
    /**
     * 年龄
     */
    private int age;
    /**
     * 背景信息
     */
    private String bgInfo;
    /**
     * 介绍网址
     */
    private String url;
    /**
     * 联系人
     */
    private String linkman;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * Email
     */
    private String email;
    /**
     * 机构地址
     */
    private String organizationSite;
    /**
     * 服务意愿
     */
    private String will;
    /**
     * 可合作项目
     */
    private String cooperation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getBgInfo() {
        return bgInfo;
    }

    public void setBgInfo(String bgInfo) {
        this.bgInfo = bgInfo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWill() {
        return will;
    }

    public void setWill(String will) {
        this.will = will;
    }

    public String getCooperation() {
        return cooperation;
    }

    public void setCooperation(String cooperation) {
        this.cooperation = cooperation;
    }

    public String getOrganizationSite() {
        return organizationSite;
    }

    public void setOrganizationSite(String organizationSite) {
        this.organizationSite = organizationSite;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

}