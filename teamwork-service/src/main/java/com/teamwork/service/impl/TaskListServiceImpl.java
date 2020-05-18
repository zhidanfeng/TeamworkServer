package com.teamwork.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.teamwork.dao.TaskDao;
import com.teamwork.dao.TaskGroupTemplateDao;
import com.teamwork.dao.TaskListDao;
import com.teamwork.entity.T_TASK;
import com.teamwork.entity.T_TASK_LIST;
import com.teamwork.service.TaskListService;
import com.teamwork.utils.DateUtil;
import com.teamwork.utils.RedisUtil;
import com.teamwork.utils.SnowflakeIdWorker;
import com.teamwork.vo.Result;
import com.teamwork.vo.TaskListVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TaskListServiceImpl implements TaskListService {

    @Autowired
    private TaskListDao taskListDao;
    @Autowired
    private TaskGroupTemplateDao taskGroupTemplateDao;
    @Autowired
    private TaskDao taskDao;
    @Autowired
    private RedisUtil redisUtil;
    @Resource
    private SnowflakeIdWorker idWorker;

//    @Override
//    public List<TaskListVO> getAll(String groupId) {
//        List<TaskListVO> list = new ArrayList<>();
//        QueryWrapper<T_TASK_LIST> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("group_id", groupId);
//        queryWrapper.orderByAsc("seq");
//        List<T_TASK_LIST> entityList = this.taskListDao.selectList(queryWrapper);
//        for (T_TASK_LIST entity : entityList) {
//            TaskListVO vo = new TaskListVO();
//            BeanUtils.copyProperties(entity, vo);
//
//            QueryWrapper<T_TASK> taskQueryWrapper = new QueryWrapper<>();
//            //TODO 分组功能最好在代码里面进行，不要用SQL过滤
//            taskQueryWrapper.eq("list_id", entity.getId());
//            taskQueryWrapper.eq("parent_taskid", "0");
//            taskQueryWrapper.orderByAsc("create_time");
//
//            List<T_TASK> taskList = taskDao.selectList(taskQueryWrapper);
//            List<TaskVO> taskVoList = new ArrayList<>();
//            for (T_TASK task : taskList) {
//                TaskVO taskVo = new TaskVO();
//                BeanUtils.copyProperties(task, taskVo);
//                taskVoList.add(taskVo);
//            }
//            vo.setTaskList(taskVoList);
//            list.add(vo);
//        }
//        return list;
//    }

    @Override
    public List<TaskListVO> getAll(String groupId) {
        List<TaskListVO> list = this.taskListDao.getTaskListList(groupId);
        return list;
    }

    @Override
    public Result add(TaskListVO vo) {
        Integer seq = this.taskListDao.getSeq(vo.getGroupId());
        if(seq == null) {
            seq = 0;
        }
        T_TASK_LIST entity = new T_TASK_LIST();
        vo.setId(idWorker.nextId());
        BeanUtils.copyProperties(vo, entity);
        entity.setCreateTime(DateUtil.getCurrentDate());
        entity.setUpdateTime(entity.getCreateTime());
        entity.setSeq(seq + 1);
        int rows = this.taskListDao.insert(entity);
        return rows > 0 ? Result.success(entity.getId()) : Result.failure();
    }

    @Override
    public boolean update(TaskListVO vo) {
        T_TASK_LIST entity = this.taskListDao.selectById(vo.getId());
        int rows = -1;
        if(entity == null)
            System.out.println("record not found");
        else {
            entity.setListName(vo.getListName());
            entity.setUpdateTime(DateUtil.getCurrentDate());
            rows = this.taskListDao.updateById(entity);
        }
        return rows == 1;
    }

    @Override
    @Transactional
    public boolean delete(String groupId) {
        this.taskListDao.deleteById(groupId);
        UpdateWrapper<T_TASK> deleteWrapper = new UpdateWrapper<>();
        deleteWrapper.eq("group_id", groupId);
        this.taskDao.delete(deleteWrapper);
        return true;
    }
}
