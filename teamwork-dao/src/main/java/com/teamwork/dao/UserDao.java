package com.teamwork.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.teamwork.entity.T_USER;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao extends BaseMapper<T_USER> {
}
