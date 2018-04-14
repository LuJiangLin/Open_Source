/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  PartnershipVo.java
 * PACKAGE      :  com.anssy.inter.team.vo
 * CREATE DATE  :  2016-8-23
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.inter.team.vo;

/**
 * @author make it
 * @version SVN #V1# #2016-8-23#
 *          团队合伙辅助类
 */
public class PartnershipVo {

    private Long id;
    /**
     * 图片ID
     */
    private String image;
    /**
     * 标题
     */
    private String title;
    /**
     * 行业(基础_行业信息)
     */
    private String field;
    /**
     * 职位
     */
    private String post;
    /**
     * 合伙人要求
     */
    private Long askFor;
    /**
     * 一句描述
     */
    private String sketch;
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
     * 描述
     */
    private String describe;
    /**
     * 联系人
     */
    private String linkman;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 项目方向
     */
    private String direction;
    /**
     * 团队情况
     */
    private String teamCase;
    /**
     * 创业地址
     */
    private String site;
    /**
     * 项目亮点
     */
    private String lightspot;
    /**
     * 项目前景
     */
    private String prospect;
    /**
     * 项目阶段
     */
    private Long stage;
    /**
     * 初始资金
     */
    private String fund;
    /**
     * 产品链接
     */
    private String appURL;
    /**
     * 邮箱
     */
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Long getAskFor() {
        return askFor;
    }

    public void setAskFor(Long askFor) {
        this.askFor = askFor;
    }

    public String getSketch() {
        return sketch;
    }

    public void setSketch(String sketch) {
        this.sketch = sketch;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
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

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
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

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getTeamCase() {
        return teamCase;
    }

    public void setTeamCase(String teamCase) {
        this.teamCase = teamCase;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getLightspot() {
        return lightspot;
    }

    public void setLightspot(String lightspot) {
        this.lightspot = lightspot;
    }

    public String getProspect() {
        return prospect;
    }

    public void setProspect(String prospect) {
        this.prospect = prospect;
    }

    public Long getStage() {
        return stage;
    }

    public void setStage(Long stage) {
        this.stage = stage;
    }

    public String getFund() {
        return fund;
    }

    public void setFund(String fund) {
        this.fund = fund;
    }

    public String getAppURL() {
        return appURL;
    }

    public void setAppURL(String appURL) {
        this.appURL = appURL;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}