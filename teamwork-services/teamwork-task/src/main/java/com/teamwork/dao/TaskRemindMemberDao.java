package com.teamwork.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.teamwork.entity.TaskRemindMemberDO;
import com.teamwork.entity.TaskRemindRuleDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TaskRemindMemberDao extends BaseMapper<TaskRemindMemberDO> {

}
