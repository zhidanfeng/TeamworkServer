package com.teamwork.service;

import com.teamwork.vo.ChatMessageVo;
import com.teamwork.vo.condition.GroupChatMsgFilterCondition;

import java.util.List;

public interface GroupChatService {
    /**
     * 收到客户端的群聊消息
     */
    List<ChatMessageVo> getHistoryMessage(String projectId) throws Exception;
    List<ChatMessageVo> getHistoryMessage(GroupChatMsgFilterCondition condition);
    void receivedMessage(String msg) throws Exception;
}
