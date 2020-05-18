package com.teamwork.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * 群聊记录表
 */
@TableName(value = "t_groupchat_msg")
public class T_GROUPCHAT_MSG {
    /**
     * 主键ID
     */
    @TableId(value = "msg_id", type = IdType.AUTO)
    @Getter @Setter
    private Integer msgId;
    /**
     * 消息内容
     */
    @TableField(value = "msg_content")
    @Getter @Setter
    private String msgContent;
    /**
     * 消息发送时间
     */
    @TableField(value = "msg_time")
    @Getter @Setter
    private long msgTime;
    /**
     * 消息发送者
     */
    @TableField(value = "user_id")
    @Getter
    @Setter
    private int userId;
    /**
     * 所属项目id
     */
    @TableField(value = "project_id")
    @Getter @Setter
    private String projectId;
}
