/**
 * 湖北安式软件有限公司
 * Hubei Anssy Software Co., Ltd.
 * FILENAME     :  LeaveAction.java
 * PACKAGE      :  com.anssy.inter.leave.action
 * CREATE DATE  :  2016-8-12
 * AUTHOR       :  make it
 * MODIFIED BY  :
 * DESCRIPTION  :
 */
package com.anssy.inter.leave.action;

import com.google.gson.Gson;
import com.anssy.venturebar.base.vo.DatadictVo;
import com.anssy.venturebar.leave.entity.LeaveNoteEntity;
import com.anssy.inter.AppAction;
import com.anssy.inter.leave.server.LeaveServer;
import com.anssy.inter.leave.vo.LeaveIdVo;
import com.anssy.inter.leave.vo.NoteListVo;
import com.anssy.inter.leave.vo.NoteVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author make it
 * @version SVN #V1# #2016-8-12#
 *          接口_留言
 */
@Controller
@RequestMapping("/app/leave/leaveAction")
public class LeaveAction extends AppAction {

    @Resource
    private LeaveServer leaveServer;

    /**
     * 获取留言类型接口
     */
    @RequestMapping(value = "/findLeaveType", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> findLeaveType() throws Exception {
        Map<String, Object> map = writeResultRep();
        List<DatadictVo> types = leaveServer.findLeaveType();
        map.put("types", types);
        return map;
    }

    /**
     * 发布(回复)留言信息接口
     *
     * @param {"type":"","fid":"","publishId":"","leaveDetails":""}               发布留言
     * @param {"type":"","fid":"","publishId":"","leaveDetails":"","fatherId":""} 回复留言
     * fatherId 要回复的留言的id
     * type:   (1-场地,2-资金,3-项目,4-团队,5-众包,6-导师)来自findLeaveType
     * publishId 发布人或留言人的ID
     */
    @RequestMapping(value = "/releaseLeave", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> releaseLeave(HttpServletRequest request) throws Exception {
        Long userId = Long.parseLong(request.getHeader("userId"));
        Map<String, Object> map;
        NoteVo vo = new Gson().fromJson(getJson(request), NoteVo.class);
        if (vo != null && vo.getFid() != null && vo.getPublishId() != null &&
                StringUtils.isNotBlank(vo.getLeaveDetails())) {
            boolean flag = leaveServer.releaseLeave(vo, userId);
            if (flag) {
                map = writeResultRep();
            } else {
                map = writeResultRep(20);//发布留言失败
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

    /**
     * 留言信息列表接口
     *
     * @param {"type":"","fid":""}
     */
    @RequestMapping(value = "/leaveList", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> leaveList(HttpServletRequest request) throws Exception {
        Map<String, Object> map;
        NoteListVo vo = new Gson().fromJson(getJson(request), NoteListVo.class);
        if (vo != null && vo.getFid() != null) {
            List<LeaveNoteEntity> notes = leaveServer.replenish(leaveServer.findNote(vo));
            map = writeResultRep();
            map.put("notes", notes);
            if (!notes.isEmpty()) {
                map.put("count", notes.size());
            } else {
                map.put("count", 0);
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

    /**
     * 查询我给别人的留言信息接口
     */
    @RequestMapping(value = "/myLeave", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> myLeave(HttpServletRequest request) throws Exception {
        Long userId = Long.parseLong(request.getHeader("userId"));
        Map<String, Object> map;
        List<LeaveNoteEntity> notes = leaveServer.replenish(leaveServer.myLeave(userId));
        map = writeResultRep();
        map.put("notes", notes);
        if (!notes.isEmpty()) {
            map.put("count", notes.size());
        } else {
            map.put("count", 0);
        }
        return map;
    }

    /**
     * 查询给我的留言信息接口
     */
    @RequestMapping(value = "/giveLeave", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> giveLeave(HttpServletRequest request) throws Exception {
        Long userId = Long.parseLong(request.getHeader("userId"));
        Map<String, Object> map;
        List<LeaveNoteEntity> notes = leaveServer.replenish(leaveServer.giveLeave(userId));
        map = writeResultRep();
        map.put("notes", notes);
        if (!notes.isEmpty()) {
            map.put("count", notes.size());
        } else {
            map.put("count", 0);
        }
        return map;
    }

    /**
     * 查询我的未读留言数量接口
     */
    @RequestMapping(value = "/giveLeaveNum", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> giveLeaveNum(HttpServletRequest request) throws Exception {
        Long userId = Long.parseLong(request.getHeader("userId"));
        Map<String, Object> map;
        long num = leaveServer.giveLeaveNum(userId);
        map = writeResultRep();
        map.put("num", num);
        return map;
    }

    /**
     * 已阅留言接口
     *
     * @param {"leaveId":""}
     */
    @RequestMapping(value = "/updateRead", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> updateRead(HttpServletRequest request) throws Exception {
        Long userId = Long.parseLong(request.getHeader("userId"));
        Map<String, Object> map;
        LeaveIdVo vo = new Gson().fromJson(getJson(request), LeaveIdVo.class);
        if (vo != null) {
            LeaveNoteEntity leave = leaveServer.findLeaveById(vo.getLeaveId());
            if (leave != null) {
                if (leave.getPublishId().longValue() == userId.longValue()) {
                    int count = leaveServer.updateRead(vo.getLeaveId());
                    if (count > 0) {
                        map = writeResultRep(42);//已阅留言成功
                    } else {
                        map = writeResultRep(43);//已阅留言失败
                    }
                } else {
                    map = writeResultRep(41);//留言查看人不匹配
                }
            } else {
                map = writeResultRep(40);//留言信息不存在
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }

    /**
     * 查询留言接口
     *
     * @param {"leaveId":""}
     */
    @RequestMapping(value = "/findLeave", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String, Object> findLeave(HttpServletRequest request) throws Exception {
        Map<String, Object> map;
        LeaveIdVo vo = new Gson().fromJson(getJson(request), LeaveIdVo.class);
        if (vo != null) {
            LeaveNoteEntity leave = leaveServer.replenish(leaveServer.findLeaveById(vo.getLeaveId()));
            if (leave != null) {
                map = writeResultRep();
                map.put("leave", leave);
            } else {
                map = writeResultRep(40);//留言信息不存在
            }
        } else {
            map = writeResultRep(1003);//参数异常
        }
        return map;
    }


}