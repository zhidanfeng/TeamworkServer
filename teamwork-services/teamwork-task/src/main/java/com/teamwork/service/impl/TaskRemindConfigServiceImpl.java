package com.teamwork.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.teamwork.dao.TaskDao;
import com.teamwork.dao.TaskRemindMemberDao;
import com.teamwork.dao.TaskRemindRuleDao;
import com.teamwork.entity.T_TASK;
import com.teamwork.entity.TaskRemindMemberDO;
import com.teamwork.entity.TaskRemindRuleDO;
import com.teamwork.service.TaskRemindConfigService;
import com.teamwork.utils.SnowflakeIdWorker;
import com.teamwork.vo.Result;
import com.teamwork.vo.TaskRemindMemberVO;
import com.teamwork.vo.TaskRemindRuleVO;
import com.teamwork.vo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TaskRemindConfigServiceImpl implements TaskRemindConfigService {

    @Resource
    private TaskDao taskDao;
    @Resource
    private TaskRemindRuleDao taskRemindRuleDao;
    @Resource
    private TaskRemindMemberDao taskRemindMemberDao;
    @Resource
    private SnowflakeIdWorker idWorker;

    @Override
    public Result selectAll() {
        List<TaskRemindRuleDO> list = this.taskRemindRuleDao.selectList(null);
        return Result.success(list);
    }

    @Override
    public Result selectListByTaskId(String taskId) {
        List<TaskRemindRuleVO> list = new ArrayList<>();
        list = taskRemindRuleDao.selectListByTaskId(taskId);
        return Result.success(list);
    }

    @Override
    public Result insert(TaskRemindRuleVO item) {
        TaskRemindRuleDO entity = BeanUtil.copyProperties(item, TaskRemindRuleDO.class);
        Long key = idWorker.nextId();
        entity.setRuleId(key);
        entity.setGmtCreated(DateUtil.date());
        entity.setGmtModified(entity.getGmtCreated());
        int rows = this.taskRemindRuleDao.insert(entity);
        return rows > 0 ? Result.success(key) : Result.failure();
    }

    @Override
    public Result update(Long key, TaskRemindRuleVO item) {
        TaskRemindRuleDO entity = BeanUtil.copyProperties(item, TaskRemindRuleDO.class);
        entity.setGmtModified(DateUtil.date());
        int rows = this.taskRemindRuleDao.updateById(entity);
        return rows > 0 ? Result.success(key) : Result.failure();
    }

    @Transactional
    @Override
    public Result updateBatch(List<TaskRemindRuleVO> ruleList) {
        for (TaskRemindRuleVO ruleVO : ruleList) {
            // 保存/更新任务提醒规则
            TaskRemindRuleDO ruleDO = this.taskRemindRuleDao.selectById(ruleVO.getRuleId());
            if (ruleDO == null) {
                ruleDO = BeanUtil.copyProperties(ruleVO, TaskRemindRuleDO.class);
                ruleDO.setRuleId(idWorker.nextId());
                ruleDO.setGmtCreated(DateUtil.date());
                ruleDO.setGmtModified(ruleDO.getGmtCreated());
                ruleDO.setRemindTimestamp(getRemindTimestamp(ruleVO));
                this.taskRemindRuleDao.insert(ruleDO);
            } else {
                ruleDO = BeanUtil.copyProperties(ruleVO, TaskRemindRuleDO.class);
                ruleDO.setGmtModified(DateUtil.date());
                ruleDO.setRemindTimestamp(getRemindTimestamp(ruleVO));
                this.taskRemindRuleDao.updateById(ruleDO);
            }

            if (ruleVO.getMemberList() == null)
                continue;

            // 保存/更新任务提醒对象信息
            for (TaskRemindMemberVO memberVO : ruleVO.getMemberList()) {
                TaskRemindMemberDO memberDO = this.taskRemindMemberDao.selectById(memberVO.getId());
                if (memberDO == null) {
                    memberDO = BeanUtil.copyProperties(memberVO, TaskRemindMemberDO.class);
                    memberDO.setGmtCreated(DateUtil.date());
                    memberDO.setGmtModified(ruleDO.getGmtCreated());
                    this.taskRemindMemberDao.insert(memberDO);
                } else {
                    memberDO = BeanUtil.copyProperties(memberVO, TaskRemindMemberDO.class);
                    memberDO.setGmtModified(DateUtil.date());
                    this.taskRemindMemberDao.updateById(memberDO);
                }
            }
        }
        return Result.success();
    }

    @Transactional
    @Override
    public Result deleteByKey(Long key) {
        int rows = this.taskRemindRuleDao.deleteById(key);
        if(key == 723216701545648128L)
            throw new RuntimeException("");
        this.taskRemindMemberDao.delete(new QueryWrapper<TaskRemindMemberDO>().eq("remind_rule_id", key));
        return rows > 0 ? Result.success(key) : Result.failure();
    }

    private Long getRemindTimestamp(TaskRemindRuleVO rule) {
        T_TASK task = this.taskDao.selectById(rule.getTaskId());
        Long remindTimestamp = null;
        if (task != null) {
            switch (rule.getRemindFieldType()) {
                case 0:
                    remindTimestamp = task.getCreateTime().getTime();
                    break;
                case 1:
                    remindTimestamp = getOffsetDate(task.getCreateTime(), -rule.getRemindTimeLength(), rule.getRemindTimeUnit()).getTime();
                    break;
                case 2:
                    remindTimestamp = getOffsetDate(task.getCreateTime(), rule.getRemindTimeLength(), rule.getRemindTimeUnit()).getTime();
                    break;
                case 3:
                    remindTimestamp = task.getEndTime().getTime();
                    break;
                case 4:
                    remindTimestamp = getOffsetDate(task.getEndTime(), -rule.getRemindTimeLength(), rule.getRemindTimeUnit()).getTime();
                    break;
                case 5:
                    remindTimestamp = getOffsetDate(task.getEndTime(), rule.getRemindTimeLength(), rule.getRemindTimeUnit()).getTime();
                    break;
                case 6:
                    remindTimestamp = rule.getRemindTimestamp();
                    break;
            }
        }
        return remindTimestamp;
    }

    private Date getOffsetDate(Date date, int offset, int timeUnit) {
        Date newDate = date;
        switch (timeUnit) {
            case 0:
                DateUtil.offsetMinute(date, offset);
                break;
            case 1:
                DateUtil.offsetHour(date, offset);
                break;
            case 2:
                DateUtil.offsetDay(date, offset);
                break;
        }
        return newDate;
    }
}
