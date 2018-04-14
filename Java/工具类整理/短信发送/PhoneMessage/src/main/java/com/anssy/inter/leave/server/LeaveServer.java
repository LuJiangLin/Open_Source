/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  LeaveServer.java
 * PACKAGE      :  com.anssy.inter.leave.server
 * CREATE DATE  :  2016-8-20
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.inter.leave.server;

import com.anssy.venturebar.activity.dao.ActivityItemDao;
import com.anssy.venturebar.base.dao.DatadictDao;
import com.anssy.venturebar.base.server.UserInfoCacheServer;
import com.anssy.venturebar.base.vo.DatadictVo;
import com.anssy.venturebar.invest.dao.InvestorItemDao;
import com.anssy.venturebar.invest.dao.ProjectItemDao;
import com.anssy.venturebar.leave.dao.LeaveNoteDao;
import com.anssy.venturebar.leave.entity.LeaveNoteEntity;
import com.anssy.venturebar.service.dao.PolicyElucidationDao;
import com.anssy.venturebar.service.dao.ServiceItemDao;
import com.anssy.venturebar.site.dao.SiteItemDao;
import com.anssy.venturebar.team.dao.EpibolyItemDao;
import com.anssy.venturebar.team.dao.PartnershipItemDao;
import com.anssy.venturebar.technology.dao.TutorItemDao;
import com.anssy.inter.leave.vo.NoteListVo;
import com.anssy.inter.leave.vo.NoteVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author make it
 * @version SVN #V1# #2016-8-20#
 *          留言本
 */
@Service("leaveServer")
public class LeaveServer {

    @Resource
    private DatadictDao datadictDao;
    @Resource
    private LeaveNoteDao leaveNoteDao;
    @Resource
    private SiteItemDao siteItemDao;
    @Resource
    private InvestorItemDao investorItemDao;
    @Resource
    private ProjectItemDao projectItemDao;
    @Resource
    private EpibolyItemDao epibolyItemDao;
    @Resource
    private PartnershipItemDao partnershipItemDao;
    @Resource
    private TutorItemDao tutorItemDao;
    @Resource
    private ServiceItemDao serviceItemDao;
    @Resource
    private ActivityItemDao activityItemDao;
    @Resource
    private PolicyElucidationDao policyElucidationDao;
    @Resource
    private UserInfoCacheServer userInfoCacheServer;

    /**
     * 获取类型
     */
    public List<DatadictVo> findLeaveType() throws Exception {
        return datadictDao.findDictByCategory("LEAVE_TYPE");
    }

    /**
     * 发布(回复)留言
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean releaseLeave(NoteVo vo, Long userId) throws Exception {
        boolean flag = false;
        LeaveNoteEntity note = new LeaveNoteEntity();
        note.setType(vo.getType());
        note.setFid(vo.getFid());
        note.setPublishId(vo.getPublishId());
        note.setLeaveId(userId);
        note.setLeaveTime(new Date());
        note.setLeaveDetails(vo.getLeaveDetails());
        if (vo.getFatherId() != null) {
            note.setFatherId(vo.getFatherId());
        } else {
            note.setFatherId(0L);
        }
        if (leaveNoteDao.insertLeaveNote(note) > 0) {
            flag = true;
            updateLeaveNumber(note.getType(), note.getFid());
        }
        return flag;
    }

    /**
     * 修改留言量
     */
    private void updateLeaveNumber(int type, Long fid) {
        if (type == 1) {//1-场地
            siteItemDao.updateLeaveNumber(fid);
        } else if (type == 2) {//2-投资人
            investorItemDao.updateLeaveNumber(fid);
        } else if (type == 3) {//3-投资项目
            projectItemDao.updateLeaveNumber(fid);
        } else if (type == 4) {//4-团队合伙
            partnershipItemDao.updateLeaveNumber(fid);
        } else if (type == 5) {//5-团队众包
            epibolyItemDao.updateLeaveNumber(fid);
        } else if (type == 6) {//6-技术导师
            tutorItemDao.updateLeaveNumber(fid);
        } else if (type == 7) {//7 服务
            serviceItemDao.updateLeaveNumber(fid);
        } else if (type == 8) {//8 活动信息
            activityItemDao.updateLeaveNumber(fid);
        } else if (type == 9) {//9 政策解读
            policyElucidationDao.updateLeaveNumber(fid);
        }
    }

    public List<LeaveNoteEntity> replenish(List<LeaveNoteEntity> leaves) throws Exception {
        if (!leaves.isEmpty()) {
            for (LeaveNoteEntity leave : leaves) {
                leave.setPublishName(userInfoCacheServer.findNickname(leave.getPublishId()));
            }
        }
        return leaves;
    }

    public LeaveNoteEntity replenish(LeaveNoteEntity leave) throws Exception {
        if (leave != null) {
            leave.setPublishName(userInfoCacheServer.findNickname(leave.getPublishId()));
        }
        return leave;
    }

    /**
     * 查看留言
     */
    public List<LeaveNoteEntity> findNote(NoteListVo vo) throws Exception {
        return leaveNoteDao.findNote(vo);
    }

    /**
     * 我的留言
     */
    public List<LeaveNoteEntity> myLeave(Long leaveId) throws Exception {
        return leaveNoteDao.myLeave(leaveId);
    }

    /**
     * 给我的留言
     */
    public List<LeaveNoteEntity> giveLeave(Long publishId) throws Exception {
        return leaveNoteDao.giveLeave(publishId);
    }

    /**
     * 我的未读留言(给我的留言未读数量)
     */
    public long giveLeaveNum(Long publishId) throws Exception {
        return leaveNoteDao.giveLeaveNum(publishId);
    }

    /**
     * 通过ID查询留言信息
     */
    public LeaveNoteEntity findLeaveById(Long id) throws Exception {
        return leaveNoteDao.findLeaveById(id);
    }

    /**
     * 已阅留言
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public int updateRead(Long id) throws Exception {
        return leaveNoteDao.updateRead(id);
    }

}