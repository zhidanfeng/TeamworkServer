package com.teamwork.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.teamwork.entity.T_TASK;
import com.teamwork.vo.TaskVO;
import com.teamwork.vo.condition.TaskFilterCondition;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TaskDao extends BaseMapper<T_TASK> {
    List<TaskVO> getAllByCondition(TaskFilterCondition condition);
}
