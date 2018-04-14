/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  PolicyVo.java
 * PACKAGE      :  com.anssy.inter.service.vo
 * CREATE DATE  :  2016-8-3
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.inter.base.vo;

/**
 * @author make it
 * @version SVN #V1# #2016-8-3#
 *          政策解读辅助类
 */
public class NewsVo {
    /**
     * 第几页(初始化时值为1,加载时每次加1)
     */
    private int page;
    /**
     * 搜索词组
     */
    private String search;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}