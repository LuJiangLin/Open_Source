/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  UserInfoEntity.java
 * PACKAGE      :  com.anssy.venturebar.base.entity
 * CREATE DATE  :  2016-8-9
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.venturebar.base.entity;

import com.anssy.webcore.core.entity.IdEntity;

import java.util.List;

/**
 * @author make it
 * @version SVN #V1# #2016-8-9#
 *          基础_用户信息
 */
public class UserInfoEntity extends IdEntity {

    private static final long serialVersionUID = -7925271678080588852L;

    /**
     * 头像
     */
    private String headImage;
    private String imgurl;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 手机号码
     */
    private String phone;
    /**
     * Email
     */
    private String email;
    /**
     * 密码(MD5加密密文)
     */
    private String passwd;
    /**
     * 角色 USER_ROLES
     */
    private int roles;
    private String roleName;
    /**
     * 状态(0-正常,1-锁定)
     */
    private int state;
    /**
     * 是否激活(email邮件激活) (0-未激活,1-已激活)
     */
    private int isActivate;
    /**
     * 签名(app)
     */
    private String sign;

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public int getRoles() {
        return roles;
    }

    public void setRoles(int roles) {
        this.roles = roles;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getIsActivate() {
        return isActivate;
    }

    public void setIsActivate(int isActivate) {
        this.isActivate = isActivate;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

}