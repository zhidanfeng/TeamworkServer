package com.teamwork.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.teamwork.entity.TaskRemindRuleDO;
import com.teamwork.vo.TaskRemindRuleVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TaskRemindRuleDao extends BaseMapper<TaskRemindRuleDO> {
    List<TaskRemindRuleVO> selectListByTaskId(String taskId);
}
