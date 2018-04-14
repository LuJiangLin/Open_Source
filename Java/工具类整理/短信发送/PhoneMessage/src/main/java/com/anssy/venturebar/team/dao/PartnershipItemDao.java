/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  PartnershipItemDao.java
 * PACKAGE      :  com.anssy.venturebar.team.dao
 * CREATE DATE  :  2016-8-10
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.venturebar.team.dao;

import com.anssy.venturebar.team.entity.PartnershipItemEntity;
import org.springframework.stereotype.Repository;

/**
 * @author make it
 * @version SVN #V1# #2016-8-10#
 *          团队_合伙明细
 */
@Repository("partnershipItemDao")
public interface PartnershipItemDao {

    /**
     * 添加合伙明细
     */
    int insertPartnershipItem(PartnershipItemEntity item);

    /**
     * 修改合伙明细
     */
    int updatePartnershipItem(PartnershipItemEntity item);

    /**
     * 通过合伙ID查询合伙明细信息
     */
    PartnershipItemEntity findPartnershipItem(Long partnershipId);

    /**
     * 删除合伙明细
     */
    int deletePartnership(Long partnershipId);

    /**
     * 修改留言量
     */
    int updateLeaveNumber(Long partnershipId);

    /**
     * 修改收藏量
     */
    int updateCollectNumber(Long partnershipId, Long number);

}