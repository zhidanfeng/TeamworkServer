package com.teamwork.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.teamwork.dao.TaskDao;
import com.teamwork.dao.TaskGroupDao;
import com.teamwork.dao.TaskGroupTemplateDao;
import com.teamwork.entity.T_TASK;
import com.teamwork.entity.T_TASK_GROUP;
import com.teamwork.service.TaskGroupService;
import com.teamwork.utils.BeanUtil;
import com.teamwork.utils.DateUtil;
import com.teamwork.utils.RedisUtil;
import com.teamwork.utils.SnowflakeIdWorker;
import com.teamwork.vo.Result;
import com.teamwork.vo.TaskGroupVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class TaskGroupServiceImpl implements TaskGroupService {

    @Autowired
    private TaskGroupDao taskGroupDao;
    @Autowired
    private TaskGroupTemplateDao taskGroupTemplateDao;
    @Autowired
    private TaskDao taskDao;
    @Autowired
    private RedisUtil redisUtil;
    @Resource
    private SnowflakeIdWorker idWorker;

    @Override
    public Result getAll(String projectId) {
        List<TaskGroupVO> list = new ArrayList<>();
        QueryWrapper<T_TASK_GROUP> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("project_id", projectId);
        queryWrapper.orderByAsc("seq");
        List<T_TASK_GROUP> entityList = this.taskGroupDao.selectList(queryWrapper);
        for (T_TASK_GROUP entity : entityList) {
            TaskGroupVO vo = BeanUtil.copy(entity, TaskGroupVO.class);
            list.add(vo);
        }
        return list == null ? Result.failure() : Result.success(list);
    }

    @Override
    public Result add(TaskGroupVO vo) {
        Integer seq = this.taskGroupDao.getSeq(vo.getProjectId());
        if(seq == null) {
            seq = 0;
        }
        T_TASK_GROUP entity = new T_TASK_GROUP();
        vo.setId(idWorker.nextId());
        BeanUtils.copyProperties(vo, entity);
        entity.setCreateTime(DateUtil.getCurrentDate());
        entity.setUpdateTime(entity.getCreateTime());
        entity.setSeq(seq + 1);
        int rows = this.taskGroupDao.insert(entity);
        return rows > 0 ? Result.success(entity.getId()) : Result.failure();
    }

    @Override
    public boolean update(TaskGroupVO vo) {
        T_TASK_GROUP entity = this.taskGroupDao.selectById(vo.getId());
        int rows = -1;
        if(entity == null)
            System.out.println("record not found");
        else {
            entity.setGroupName(vo.getGroupName());
            entity.setUpdateTime(DateUtil.getCurrentDate());
            rows = this.taskGroupDao.updateById(entity);
        }
        return rows == 1;
    }

    @Override
    @Transactional
    public boolean delete(String groupId) {
        this.taskGroupDao.deleteById(groupId);
        UpdateWrapper<T_TASK> deleteWrapper = new UpdateWrapper<>();
        deleteWrapper.eq("group_id", groupId);
        this.taskDao.delete(deleteWrapper);
        return true;
    }
}
