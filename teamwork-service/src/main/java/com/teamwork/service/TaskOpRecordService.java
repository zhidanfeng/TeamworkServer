package com.teamwork.service;

import com.teamwork.vo.TaskAttachmentVO;
import com.teamwork.vo.TaskOpRecordVO;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface TaskOpRecordService {
    /**
     * 获取任务的操作记录
     */
    List<TaskOpRecordVO> getListByTaskId(long taskId);
    /**
     * 新增一条操作记录
     */
    boolean addRecord(TaskOpRecordVO record);
    /**
     * 添加附件
     */
    boolean addAttachment(TaskAttachmentVO attachment);
    /**
     * 添加附件
     */
    TaskAttachmentVO addAttachment(HttpServletRequest request) throws UnsupportedEncodingException;
    /**
     * 获取附件添加记录（当用户添加附件后但未正式发表，关闭任务窗口重新打开任务查看窗口时需要显示之前的附件添加记录，方便发表动态）
     */
    List<TaskAttachmentVO> getTempAttachmentList(long taskId);
}
