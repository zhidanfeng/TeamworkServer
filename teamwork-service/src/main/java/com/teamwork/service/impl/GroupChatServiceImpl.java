package com.teamwork.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.teamwork.consts.ExchangeName;
import com.teamwork.consts.RedisKey;
import com.teamwork.dao.GroupChatDao;
import com.teamwork.dao.UserDao;
import com.teamwork.entity.T_GROUPCHAT_MSG;
import com.teamwork.entity.T_USER;
import com.teamwork.service.GroupChatService;
import com.teamwork.utils.BeanUtil;
import com.teamwork.utils.DateUtil;
import com.teamwork.utils.JsonUtil;
import com.teamwork.utils.RedisUtil;
import com.teamwork.vo.ChatMessageVo;
import com.teamwork.vo.UserInfo;
import com.teamwork.vo.condition.GroupChatMsgFilterCondition;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class GroupChatServiceImpl implements GroupChatService {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private AmqpTemplate amqpTemplate;
    @Autowired
    private GroupChatDao groupChatDao;
    @Autowired
    private UserDao userDao;

    /**
     * 统一接收客户端的群聊消息，存储至redis
     */
    @Override
    public void receivedMessage(String msg) throws Exception {
        ChatMessageVo chatMessage = null;
        long score = DateUtil.convertDateToTimestamp(new Date());

        // 重新修改消息发送时间，以服务器时间为准
        //msg = "";
        try {
            chatMessage = JsonUtil.jsonToObject(msg, ChatMessageVo.class);
            //以消息时间为zset集合权重
            chatMessage.setMsgTime(score);
            msg = JsonUtil.objectToJson(chatMessage);
        } catch (Exception e) {
            System.out.println("聊天消息反序列化失败");
            //TODO
            throw new Exception(e.getCause());
        }

        //TODO 需要保证几步的原子性
        //region 消息存库
        T_GROUPCHAT_MSG entity = BeanUtil.copy(chatMessage, T_GROUPCHAT_MSG.class);
        entity.setUserId(chatMessage.getUser().getUserId());
        this.groupChatDao.insert(entity);
        //endregion

        //region 消息存入缓存
        // TODO (这里有个问题：如何保证MySQL和Redis在插入数据时的一致性)？先插入db,然后删除缓存，下次读取时再从mysql读取至redis
        redisUtil.zset(RedisKey.GROUP_CHAT_PREFIX + chatMessage.getProjectId(), msg, score);
        //endregion

        //region 入库与添加缓存成功后，转发给项目的其他成员
        this.amqpTemplate.convertAndSend(ExchangeName.GROUP_CHAT_EXCHANGE, chatMessage.getProjectId(), msg);
        //endregion
    }

    /**
     * 获取历史群聊消息
     */
    @Override
    public List<ChatMessageVo> getHistoryMessage(String projectId) throws Exception {
        String key = RedisKey.GROUP_CHAT_PREFIX + projectId;

        ArrayList<ChatMessageVo> messageList = new ArrayList<>();

        //先从redis读取，找不到key，再从db中获取
        if(redisUtil.hasKey(key)) {
            Set<Object> list1 = redisUtil.zgetAll(key);
            for (Object o : list1) {
                ChatMessageVo chatMessage = JsonUtil.jsonToObject(o.toString(), ChatMessageVo.class);
                messageList.add(chatMessage);
            }
        } else {
            //从db读取，写入redis缓存
            List<T_GROUPCHAT_MSG> list = this.groupChatDao.getHistoryMessage(projectId);
            if(list != null) {
                list.forEach((item) -> {
                    ChatMessageVo chatMessage = BeanUtil.copy(item, ChatMessageVo.class);

                    //region 设置消息发送者
                    chatMessage.setUser(this.getUserInfo(item.getUserId()));
                    //endregion

                    messageList.add(chatMessage);

                    try {
                        String msg = JsonUtil.objectToJson(chatMessage);

                        //region 消息存入缓存
                        redisUtil.zset(key, msg, chatMessage.getMsgTime());
                        //endregion
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        }

        return messageList;
    }

    @Override
    public List<ChatMessageVo> getHistoryMessage(GroupChatMsgFilterCondition condition) {
        String key = RedisKey.GROUP_CHAT_PREFIX + condition.getProjectId();

        ArrayList<ChatMessageVo> messageList = new ArrayList<>();

        //先从redis读取，找不到key，再从db中获取
        if(redisUtil.hasKey(key)) {
            Set<Object> list1 = redisUtil.zgetAll(key);
            for (Object o : list1) {
                ChatMessageVo chatMessage = null;
                try {
                    chatMessage = JsonUtil.jsonToObject(o.toString(), ChatMessageVo.class);
                    messageList.add(chatMessage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            //从db读取，写入redis缓存
//            List<T_GROUPCHAT_MSG> list = this.groupChatDao.getHistoryMessage2(condition);
            QueryWrapper<T_GROUPCHAT_MSG> wrapper = new QueryWrapper<>();
            wrapper.eq("project_id", condition.getProjectId());
            wrapper.between("msg_time", condition.getStartTime(), condition.getEndTime());
            List<T_GROUPCHAT_MSG> list = this.groupChatDao.selectList(wrapper);
            if(list != null) {
                list.forEach((item) -> {
                    ChatMessageVo chatMessage = BeanUtil.copy(item, ChatMessageVo.class);

                    //region 设置消息发送者
                    chatMessage.setUser(this.getUserInfo(item.getUserId()));
                    //endregion

                    messageList.add(chatMessage);

                    try {
                        String msg = JsonUtil.objectToJson(chatMessage);

                        //region 消息存入缓存
                        redisUtil.zset(key, msg, chatMessage.getMsgTime());
                        //endregion
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        }

        return messageList;
    }

    private UserInfo getUserInfo(Integer userId) {
        T_USER entity = this.userDao.selectById(userId);
        UserInfo user = BeanUtil.copy(entity, UserInfo.class);

        return user;
    }
}
