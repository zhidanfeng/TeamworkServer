package com.teamwork.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.teamwork.entity.T_TASK_GROUP;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TaskGroupDao extends BaseMapper<T_TASK_GROUP> {
    Integer getSeq(String projectId);
}
