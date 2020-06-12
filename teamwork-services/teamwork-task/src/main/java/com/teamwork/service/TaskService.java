package com.teamwork.service;

import com.teamwork.vo.PersonalPlanVO;
import com.teamwork.vo.RequirementVO;
import com.teamwork.vo.Result;
import com.teamwork.vo.condition.TaskFilterCondition;

public interface TaskService {
    /**
     * 新增需求任务
     */
    Result addRequirement(RequirementVO vo);
    /**
     * 修改需求任务
     */
    boolean updateRequirement(RequirementVO vo);
    /**
     * 获取需求任务详情
     */
    RequirementVO getRequirement(long taskId);
    /**
     *通过过滤条件获取任务列表
     */
    Result getAllByCondition(TaskFilterCondition condition);
    /**
     * 新增个人安排任务
     */
    Result addPersonalPlan(PersonalPlanVO vo);
    /**
     * 获取个人安排任务详情
     */
    PersonalPlanVO getPersonalPlan(long taskId);
    /**
     * 更新个人安排任务详情
     */
    boolean updatePersonalPlan(PersonalPlanVO vo);
    /**
     * 删除任务
     */
    boolean delete(long taskId);
}
