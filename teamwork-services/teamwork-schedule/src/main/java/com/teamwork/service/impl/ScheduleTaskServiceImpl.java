package com.teamwork.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.teamwork.JobUtil;
import com.teamwork.dao.ScheduleTaskDao;
import com.teamwork.entity.ScheduleTaskDO;
import com.teamwork.service.ScheduleTaskService;
import com.teamwork.utils.SnowflakeIdWorker;
import com.teamwork.vo.Result;
import com.teamwork.vo.ScheduleTaskVO;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleTaskServiceImpl implements ScheduleTaskService {

    @Autowired
    private ScheduleTaskDao scheduleTaskDao;
    @Autowired
    private SnowflakeIdWorker idWorker;
    @Autowired
    private JobUtil jobUtil;

    @Override
    public Result selectAll() {
        QueryWrapper<ScheduleTaskDO> wrapper = new QueryWrapper<>();
        wrapper.eq("enabled", 1);
        List<ScheduleTaskDO> list = this.scheduleTaskDao.selectList(wrapper);

        for (ScheduleTaskDO scheduleTaskDO : list) {
            ScheduleTaskVO vo = BeanUtil.copyProperties(scheduleTaskDO, ScheduleTaskVO.class);
            addJob(vo);
        }

        return Result.success();
    }

    @Override
    public Result insert(ScheduleTaskVO item) {
        ScheduleTaskDO entity = BeanUtil.copyProperties(item, ScheduleTaskDO.class);
        entity.setId(idWorker.nextId());
        entity.setGmtCreated(DateUtil.date());
        entity.setGmtModified(entity.getGmtCreated());
        this.scheduleTaskDao.insert(entity);

        addJob(item);

        return Result.success();
    }

    @Override
    public Result update(Long key, ScheduleTaskVO item) {
        ScheduleTaskDO entity = BeanUtil.copyProperties(item, ScheduleTaskDO.class);
        entity.setGmtModified(DateUtil.date());
        this.scheduleTaskDao.updateById(entity);
        return Result.success();
    }

    @Override
    public Result deleteByKey(Long key) {
        this.scheduleTaskDao.deleteById(key);
        return Result.success();
    }

    private void addJob(ScheduleTaskVO vo) {
        try {
            this.jobUtil.addJob(vo);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
