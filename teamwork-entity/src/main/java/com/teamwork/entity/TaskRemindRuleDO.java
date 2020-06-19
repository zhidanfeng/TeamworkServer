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
@TableName(value = "task_remind_rule")
public class TaskRemindRuleDO {
    @TableId(value = "id", type = IdType.NONE)
    private Long ruleId;
    @TableField(value = "task_id")
    private Long taskId;
    @TableField(value = "remind_field_type")
    private int remindFieldType;
    @TableField(value = "remind_time_length")
    private int remindTimeLength;
    @TableField(value = "remind_time_unit")
    private int remindTimeUnit;
    @TableField(value = "remind_timestamp")
    private Long remindTimestamp;
    @TableField(value = "gmt_created")
    private Date gmtCreated;
    @TableField(value = "gmt_modified")
    private Date gmtModified;
}
