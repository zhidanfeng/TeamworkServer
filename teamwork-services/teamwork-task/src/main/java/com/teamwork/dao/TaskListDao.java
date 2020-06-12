package com.teamwork.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.teamwork.entity.T_TASK_LIST;
import com.teamwork.vo.TaskListVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TaskListDao extends BaseMapper<T_TASK_LIST> {
    Integer getSeq(String projectId);
    /**
     * 获取任务列表
     */
    List<TaskListVO> getTaskListList(String groupId);
}
