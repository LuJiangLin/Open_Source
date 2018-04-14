/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  MyCollectVo.java
 * PACKAGE      :  com.anssy.webcore.vo
 * CREATE DATE  :  2016-8-8
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.webcore.vo;

/**
 * @author make it
 * @version SVN #V1# #2016-8-8#
 */
public class MyCollectVo {

    /**
     * 第几页(初始化时值为1,加载时每次加1)
     */
    private int page;
    /**
     * 类型
     */
    private int type;
    /**
     * 搜索词组
     */
    private String search;
    /**
     * 服务类型(服务模块)
     */
    private Long serviceType;

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Long getServiceType() {
        return serviceType;
    }

    public void setServiceType(Long serviceType) {
        this.serviceType = serviceType;
    }

}