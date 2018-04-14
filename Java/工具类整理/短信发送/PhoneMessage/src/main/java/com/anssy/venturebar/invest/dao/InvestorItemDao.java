/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  InvestorItemDao.java
 * PACKAGE      :  com.anssy.venturebar.invest.dao
 * CREATE DATE  :  2016-8-10
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.venturebar.invest.dao;

import com.anssy.venturebar.invest.entity.InvestorItemEntity;
import org.springframework.stereotype.Repository;

/**
 * @author make it
 * @version SVN #V1# #2016-8-10#
 *          投资_投资人明细
 */
@Repository("investorItemDao")
public interface InvestorItemDao {

    /**
     * 添加投资人明细
     */
    int insertInvestorItem(InvestorItemEntity item);

    /**
     * 修改投资人明细
     */
    int updateInvestorItem(InvestorItemEntity item);

    /**
     * 通过投资人ID查询投资人明细
     */
    InvestorItemEntity findInvestorItem(Long investorId);

    /**
     * 删除投资人明细
     */
    int deleteInvestorItem(Long investorId);

    /**
     * 修改留言量
     */
    int updateLeaveNumber(Long investorId);

    /**
     * 修改收藏量
     */
    int updateCollectNumber(Long investorId, Long number);

}