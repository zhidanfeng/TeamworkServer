package com.teamwork.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.teamwork.entity.T_TASK_REQUIREMENT;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RequirementDao extends BaseMapper<T_TASK_REQUIREMENT> {
}
