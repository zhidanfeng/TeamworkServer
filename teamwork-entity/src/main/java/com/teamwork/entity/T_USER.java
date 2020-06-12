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
@TableName(value = "t_user")
@Getter
@Setter
public class T_USER {
    /**
     * 主键ID
     */
    @TableId(value = "user_id", type = IdType.NONE)
    private Long userId;
    /**
     * 用户名
     */
    @TableField(value = "username")
    private String userName;
    /**
     * 用户名
     */
    @TableField(value = "password")
    private String password;
}
