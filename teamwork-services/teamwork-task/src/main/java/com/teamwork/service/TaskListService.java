package com.teamwork.service;

import com.teamwork.vo.Result;
import com.teamwork.vo.TaskListVO;

import java.util.List;

public interface TaskListService {
    /**
     * 获取所有列表
     */
    List<TaskListVO> getAll(String groupId);
    /**
     * 新增任务列表
     */
    Result add(TaskListVO vo);
    /**
     * 更新任务分组
     */
    boolean update(TaskListVO vo);
    boolean delete(String groupId);
}
