package com.teamwork.service;

import com.teamwork.vo.Result;
import com.teamwork.vo.TaskRemindRuleVO;

import java.util.List;

public interface TaskRemindConfigService extends BaseService<TaskRemindRuleVO> {
    Result selectListByTaskId(String taskId);
    Result updateBatch(List<TaskRemindRuleVO> list);
}
