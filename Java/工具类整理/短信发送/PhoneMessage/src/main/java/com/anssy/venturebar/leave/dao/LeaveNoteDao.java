/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  LeaveNoteDao.java
 * PACKAGE      :  com.anssy.venturebar.leave.dao
 * CREATE DATE  :  2016-8-10
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.venturebar.leave.dao;

import com.anssy.venturebar.leave.entity.LeaveNoteEntity;
import com.anssy.inter.leave.vo.NoteListVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author make it
 * @version SVN #V1# #2016-8-10#
 *          留言_留言本
 */
@Repository("leaveNoteDao")
public interface LeaveNoteDao {

    /**
     * 添加留言
     */
    int insertLeaveNote(LeaveNoteEntity note);

    /**
     * 查看留言
     */
    List<LeaveNoteEntity> findNote(NoteListVo vo);

    /**
     * 我的留言
     */
    List<LeaveNoteEntity> myLeave(Long leaveId);

    /**
     * 给我的留言
     */
    List<LeaveNoteEntity> giveLeave(Long publishId);

    /**
     * 我的未读留言(给我的留言未读数量)
     */
    long giveLeaveNum(Long publishId);

    /**
     * 通过ID查询留言信息
     */
    LeaveNoteEntity findLeaveById(Long id);

    /**
     * 已阅留言
     */
    int updateRead(Long id);

}