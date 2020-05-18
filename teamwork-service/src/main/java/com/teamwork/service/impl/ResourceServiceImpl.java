package com.teamwork.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.teamwork.dao.ResourceDao;
import com.teamwork.entity.ResourceDO;
import com.teamwork.service.ResourceService;
import com.teamwork.utils.SnowflakeIdWorker;
import com.teamwork.vo.ResourceVO;
import com.teamwork.vo.Result;
import com.teamwork.vo.paging.PageRequestVO;
import com.teamwork.vo.paging.PageResponseVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ResourceServiceImpl implements ResourceService {

    @Resource
    private ResourceDao resourceDao;
    @Resource
    private SnowflakeIdWorker idWorker;

    @Override
    public Result selectAll() {
        return null;
    }

    @Override
    public Result insert(ResourceVO item) {
        ResourceDO entity = BeanUtil.copyProperties(item, ResourceDO.class);
        long key = idWorker.nextId();
        entity.setResourceId(key);
        //TODO 看是否能利用aop将createtime与updateTime的赋值统一处理
        entity.setCreateTime(DateUtil.date());
        entity.setUpdateTime(entity.getCreateTime());
        int rows = this.resourceDao.insert(entity);
        return rows > 0 ? Result.success(key) : Result.failure();
    }

    @Override
    public Result update(Long key, ResourceVO item) {
        return null;
    }

    @Override
    public Result deleteByKey(Long key) {
        return null;
    }

    @Override
    public Result selectByPage(PageRequestVO pageVO) {
        PageHelper.startPage(pageVO.getCurrentPage(), pageVO.getPageSize());

        List<ResourceDO> resourceList = this.resourceDao.selectByPage();

        List<ResourceVO> resultList = new ArrayList<>();
        resourceList.stream().forEach(item -> {
            resultList.add(BeanUtil.copyProperties(item, ResourceVO.class));
        });
        PageInfo<ResourceVO> pageInfo = new PageInfo<ResourceVO>(resultList, pageVO.getPageSize());

        PageResponseVO pageResponseVO = new PageResponseVO();
        pageResponseVO.setCurrentPage(pageInfo.getPageNum());
        pageResponseVO.setPageSize(pageInfo.getSize());
        pageResponseVO.setTotalPage(pageInfo.getPages());
        pageResponseVO.setTotalSize(pageInfo.getTotal());
        pageResponseVO.setRecords(pageInfo.getList());

        return Result.success(pageResponseVO);
    }
}
