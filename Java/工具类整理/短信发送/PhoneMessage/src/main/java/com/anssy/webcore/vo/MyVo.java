/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  MyVo.java
 * PACKAGE      :  com.anssy.webcore.vo
 * CREATE DATE  :  2016-8-28
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.webcore.vo;

/**
 * @author make it
 * @version SVN #V1# #2016-8-28#
 */
public class MyVo {

    /**
     * 搜索词组
     */
    private String search;
    /**
     * 发布人(user_id)
     */
    private Long publishId;
    /**
     * 服务类型(服务模块)
     */
    private String serviceType;

    private int being;
    private int end;

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public Long getPublishId() {
        return publishId;
    }

    public void setPublishId(Long publishId) {
        this.publishId = publishId;
    }

    public int getBeing() {
        return being;
    }

    public void setBeing(int being) {
        this.being = being;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

}