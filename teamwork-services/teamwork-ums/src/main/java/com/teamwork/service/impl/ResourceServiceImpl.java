package com.teamwork.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.teamwork.dao.ResourceDao;
import com.teamwork.entity.ResourceDO;
import com.teamwork.entity.paging.PagingDO;
import com.teamwork.service.ResourceService;
import com.teamwork.utils.SnowflakeIdWorker;
import com.teamwork.vo.ResourceVO;
import com.teamwork.vo.Result;
import com.teamwork.vo.paging.PageRequestVO;
import com.teamwork.vo.paging.PageResponseVO;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
        PagingDO pagingDO = new PagingDO();
        pagingDO.setOffset((Math.abs(pageVO.getOffsetPage()) - 1) * pageVO.getPageSize());
        pagingDO.setRows(pageVO.getPageSize());
        pagingDO.setMaxRowId(pageVO.getMaxRowId());
        pagingDO.setMinRowId(pageVO.getMinRowId());

        List<ResourceDO> recordList = this.resourceDao.selectByPage(pagingDO);
        int totalRows = this.resourceDao.selectCount();

        PageResponseVO pageResponseVO = new PageResponseVO();
        pageResponseVO.setCurrentPage(pageVO.getCurrentPage());
        pageResponseVO.setPageSize(pageVO.getPageSize());
        pageResponseVO.setTotalPage(getTotalPage(totalRows, pageVO.getPageSize()));
        pageResponseVO.setTotalSize(totalRows);
        pageResponseVO.setRecords(recordList.stream().sorted(Comparator.comparing(ResourceDO::getResourceId)).collect(Collectors.toList()));

        return Result.success(pageResponseVO);
    }

    private int getTotalPage(int totalRows, int pageSize) {
        int quotient = totalRows / pageSize;
        int remainder = totalRows % pageSize == 0 ? 0 : 1;
        return quotient + remainder;
    }

    private Result getByPageHelper(PageRequestVO pageVO) {
        //startPage必须先于实际的dao前执行
        //PageInfo<ResourceDO> pageInfo = PageHelper.startPage(pageVO.getCurrentPage(), pageVO.getPageSize()).doSelectPageInfo(() -> this.resourceDao.selectByPage());
        PageHelper.startPage(pageVO.getCurrentPage(), pageVO.getPageSize());

        List<ResourceDO> resourceList = this.resourceDao.selectByPage(null);

        List<ResourceVO> resultList = new ArrayList<>();
        resourceList.stream().forEach(item -> {
            resultList.add(BeanUtil.copyProperties(item, ResourceVO.class));
        });
        PageInfo<ResourceVO> pageInfo = new PageInfo<ResourceVO>(resultList);

        PageResponseVO pageResponseVO = new PageResponseVO();
        pageResponseVO.setCurrentPage(pageInfo.getPageNum());
        pageResponseVO.setPageSize(pageInfo.getSize());
        pageResponseVO.setTotalPage(pageInfo.getPages());
        pageResponseVO.setTotalSize(pageInfo.getTotal());
        pageResponseVO.setRecords(pageInfo.getList());

        return Result.success(pageResponseVO);
    }
}
