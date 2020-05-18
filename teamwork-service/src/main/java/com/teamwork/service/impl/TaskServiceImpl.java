package com.teamwork.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.teamwork.dao.PersonalPlanDao;
import com.teamwork.dao.RequirementDao;
import com.teamwork.dao.TaskDao;
import com.teamwork.entity.T_TASK;
import com.teamwork.entity.T_TASK_PERSONALPLAN;
import com.teamwork.entity.T_TASK_REQUIREMENT;
import com.teamwork.service.CommonService;
import com.teamwork.service.MQProducer;
import com.teamwork.service.TaskService;
import com.teamwork.utils.*;
import com.teamwork.vo.PersonalPlanVO;
import com.teamwork.vo.RequirementVO;
import com.teamwork.vo.Result;
import com.teamwork.vo.TaskVO;
import com.teamwork.vo.condition.TaskFilterCondition;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskDao taskDao;
    @Autowired
    private RequirementDao requirementDao;
    @Autowired
    private PersonalPlanDao personalPlanDao;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private CommonService commonService;
    @Autowired
    private MQProducer mqProducer;
    @Autowired
    private AmqpTemplate amqpTemplate;
//    @Autowired
//    private TaskRepository taskRepository;
    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    @Override
    @Transactional
    public Result addRequirement(RequirementVO vo) {
        long taskId = this.InsertTaskRecord(vo);

        T_TASK_REQUIREMENT entity = new T_TASK_REQUIREMENT();
        BeanUtils.copyProperties(vo, entity);
        entity.setTaskId(taskId);
        this.requirementDao.insert(entity);

        return Result.success(taskId);
    }

    @Override
    @Transactional
    public boolean updateRequirement(RequirementVO vo) {
        T_TASK taskEntity = new T_TASK();
        UpdateWrapper<T_TASK> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("task_name", vo.getTaskName());
        updateWrapper.set("list_id", vo.getListId());
        updateWrapper.set("update_time", DateUtil.getCurrentDate());
        updateWrapper.eq("id", vo.getTaskId());
        taskDao.update(taskEntity, updateWrapper);

        T_TASK_REQUIREMENT entity = new T_TASK_REQUIREMENT();
        BeanUtils.copyProperties(vo, entity);
        entity.setTaskId(vo.getTaskId());
        entity.setStartTime(DateUtil.convertTimestampToDate(vo.getStartTime()));
        entity.setEndTime(DateUtil.convertTimestampToDate(vo.getEndTime()));
        entity.setPlanOnlineTime(DateUtil.convertTimestampToDate(vo.getPlanOnlineTime()));
        int rows = this.requirementDao.updateById(entity);

        return rows > 0;
    }

    @Override
    public RequirementVO getRequirement(long taskId) {

        RequirementVO vo = new RequirementVO();
        T_TASK task = this.taskDao.selectById(taskId);
        BeanUtils.copyProperties(task, vo);
        T_TASK_REQUIREMENT entity = this.requirementDao.selectById(taskId);
        BeanUtils.copyProperties(entity, vo);
        vo.setStartTime(DateUtil.convertDateToTimestamp(entity.getStartTime()));
        vo.setEndTime(DateUtil.convertDateToTimestamp(entity.getEndTime()));
        vo.setPlanOnlineTime(DateUtil.convertDateToTimestamp(entity.getPlanOnlineTime()));

        if(redisUtil.isConnected()) {
            Map<Object, Object> map = this.redisUtil.hget("task::" + taskId);
            if(map == null || map.size() == 0) { //没有查询到任务缓存
                Map<Object, Object> fields = new HashMap<>();
                fields.put("taskid", vo.getTaskId());
                fields.put("taskname", vo.getTaskName());
                this.redisUtil.hset("task::" + taskId, fields);
            }
        }

        return vo;
    }

    @Override
    public Result getAllByCondition(TaskFilterCondition condition) {
        condition.longToDate();
        List<TaskVO> list = this.taskDao.getAllByCondition(condition);
        return list == null ? Result.failure() : Result.success(list);
    }

    @Override
    public Result addPersonalPlan(PersonalPlanVO vo) {

        //1、viewid为空则表示有可能是第三方直接发送的无效请求，需抛弃
        //2、viewid不为空，则判断redis是否有该值，如果没有，则表示是第一次请求，缓存该viewid，并设置过期时间为2分钟
        //2.1、如果redis中有这个viewid，则表示已经有过一次请求了，则不再请求
        if(StringUtil.isNullOrEmpty(vo.getViewId()) || this.commonService.existViewId(vo.getViewId())) {
            System.out.println("相同请求，抛弃");
            return Result.failure();
        }
        System.out.println("第一次处理");

        long taskId = this.InsertTaskRecord(vo);

        T_TASK_PERSONALPLAN entity = new T_TASK_PERSONALPLAN();
        BeanUtils.copyProperties(vo, entity);
        entity.setTaskId(taskId);
        this.personalPlanDao.insert(entity);

        //往es插入任务
//        TaskInfo taskInfo = new TaskInfo();
//        taskInfo.setId(taskId);
//        taskInfo.setTaskName(vo.getTaskName());
//        this.taskRepository.save(taskInfo);

        this.commonService.deleteViewId(vo.getViewId());

        return Result.success(taskId);
    }

    @Override
    public PersonalPlanVO getPersonalPlan(long taskId) {
        PersonalPlanVO vo = new PersonalPlanVO();
        T_TASK task = this.taskDao.selectById(taskId);
        BeanUtils.copyProperties(task, vo);
        T_TASK_PERSONALPLAN entity = this.personalPlanDao.selectById(taskId);
        BeanUtils.copyProperties(entity, vo);
        vo.setEndTime(DateUtil.convertDateToTimestamp(entity.getEndTime()));

        List<T_TASK> subTaskList = this.taskDao.selectList(new QueryWrapper<T_TASK>().eq("parent_taskid", task.getTaskId()));
        final List<TaskVO> subList = new ArrayList<>();
        subTaskList.forEach((t) -> subList.add(BeanUtil.copy(t, TaskVO.class)));
        vo.setSubTaskList(subList);

        return vo;
    }

    @Override
    public boolean updatePersonalPlan(PersonalPlanVO vo) {
        this.UpdateTaskRecord(vo);

        T_TASK_PERSONALPLAN entity = new T_TASK_PERSONALPLAN();
        BeanUtils.copyProperties(vo, entity);
        entity.setTaskId(vo.getTaskId());
        entity.setEndTime(DateUtil.convertTimestampToDate(vo.getEndTime()));
        int rows = this.personalPlanDao.updateById(entity);

        //this.mqProducer.sendDataToQueue("test_queue_key", vo);
        this.amqpTemplate.convertAndSend("task_sync_exchange", vo.getProjectId(), vo);

        return rows > 0;
    }

    /**
     *
     */
    private long InsertTaskRecord(TaskVO vo) {
        T_TASK taskEntity = new T_TASK();
        taskEntity.setTaskId(this.snowflakeIdWorker.nextId());
        taskEntity.setTaskName(vo.getTaskName());
        taskEntity.setListId(vo.getListId());
        taskEntity.setProjectId(vo.getProjectId());
        taskEntity.setParentTaskId(vo.getParentTaskId());
        taskEntity.setCreateTime(DateUtil.getCurrentDate());
        taskEntity.setUpdateTime(taskEntity.getCreateTime());
        taskDao.insert(taskEntity);

        return taskEntity.getTaskId();
    }

    private void UpdateTaskRecord(TaskVO vo) {
        T_TASK taskEntity = new T_TASK();

        UpdateWrapper<T_TASK> updateWrapper = new UpdateWrapper<>();
        BeanUtils.copyProperties(vo, taskEntity);
        updateWrapper.set("update_time", DateUtil.getCurrentDate());
        updateWrapper.eq("id", vo.getTaskId());

        taskDao.update(taskEntity, updateWrapper);
    }

    @Override
    public boolean delete(long taskId) {
        return true;
    }
}
