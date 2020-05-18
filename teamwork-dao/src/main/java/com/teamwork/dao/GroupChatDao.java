package com.teamwork.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.teamwork.entity.T_GROUPCHAT_MSG;
import com.teamwork.vo.condition.GroupChatMsgFilterCondition;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GroupChatDao extends BaseMapper<T_GROUPCHAT_MSG> {
    List<T_GROUPCHAT_MSG> getHistoryMessage(String projectId);
    List<T_GROUPCHAT_MSG> getHistoryMessage2(GroupChatMsgFilterCondition condition);
}
