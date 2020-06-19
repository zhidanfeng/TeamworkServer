package com.teamwork.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@TableName(value = "task_remind_member")
public class TaskRemindMemberDO {
    @TableId(value = "id", type = IdType.NONE)
    private Long id;
    @TableField(value = "remind_rule_id")
    private long ruleId;
    @TableField(value = "user_id")
    private long userId;
    @TableField(value = "gmt_created")
    private Date gmtCreated;
    @TableField(value = "gmt_modified")
    private Date gmtModified;
}
