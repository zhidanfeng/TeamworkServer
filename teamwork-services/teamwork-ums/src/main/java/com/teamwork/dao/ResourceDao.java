package com.teamwork.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.teamwork.entity.ResourceDO;
import com.teamwork.entity.paging.PagingDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ResourceDao extends BaseMapper<ResourceDO> {
    List<ResourceDO> selectByPage(PagingDO pagingDO);
    int selectCount();
}
