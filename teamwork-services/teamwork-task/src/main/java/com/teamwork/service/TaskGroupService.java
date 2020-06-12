package com.teamwork.service;

import com.teamwork.vo.Result;
import com.teamwork.vo.TaskGroupVO;

public interface TaskGroupService {
    /**
     * 获取所有标签
     */
    Result getAll(String projectId);
    /**
     * 新增任务分组
     */
    Result add(TaskGroupVO vo);
    /**
     * 更新任务分组
     */
    boolean update(TaskGroupVO vo);
    boolean delete(String groupId);
}
