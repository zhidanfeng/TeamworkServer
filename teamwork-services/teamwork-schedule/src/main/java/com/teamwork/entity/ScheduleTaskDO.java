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
@TableName(value = "t_schedule_task")
public class ScheduleTaskDO {
    @TableId(value = "id", type = IdType.NONE)
    private Long id;
    @TableField(value = "task_linkid")
    private Long linkId;
    @TableField(value = "task_table")
    private String taskTable;
    @TableField(value = "task_cron")
    private String taskCron;
    @TableField(value = "enabled")
    private int enabled;
    @TableField(value = "gmt_created")
    private Date gmtCreated;
    @TableField(value = "gmt_modified")
    private Date gmtModified;
}
