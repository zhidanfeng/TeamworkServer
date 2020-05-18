package com.teamwork.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.teamwork.dao.PriorityDao;
import com.teamwork.entity.PriorityDO;
import com.teamwork.service.PriorityService;
import com.teamwork.utils.SnowflakeIdWorker;
import com.teamwork.vo.PriorityVO;
import com.teamwork.vo.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PriorityServiceImpl implements PriorityService {

    @Resource
    private PriorityDao priorityDao;
    @Resource
    private SnowflakeIdWorker idWorker;

    @Override
    public Result selectAll() {
        List<PriorityDO> list = this.priorityDao.selectList(null);
        return Result.success(list);
    }

    @Override
    public Result insert(PriorityVO item) {
        PriorityDO entity = BeanUtil.copyProperties(item, PriorityDO.class);
        Long key = idWorker.nextId();
        entity.setPriorityId(key);
        entity.setGmtCreated(DateUtil.date());
        entity.setGmtModified(entity.getGmtCreated());
        int rows = this.priorityDao.insert(entity);
        return rows > 0 ? Result.success(key) : Result.failure();
    }

    @Override
    public Result update(Long key, PriorityVO item) {
        PriorityDO entity = BeanUtil.copyProperties(item, PriorityDO.class);
        entity.setGmtModified(DateUtil.date());
        int rows = this.priorityDao.updateById(entity);
        return rows > 0 ? Result.success(key) : Result.failure();
    }

    @Override
    public Result deleteByKey(Long key) {
        int rows = this.priorityDao.deleteById(key);
        return rows > 0 ? Result.success(key) : Result.failure();
    }
}
