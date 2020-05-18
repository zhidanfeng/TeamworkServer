package com.teamwork.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 任务表
 */
@TableName(value = "t_task")
@Getter
@Setter
public class T_TASK {
    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.NONE)
    private long taskId;
    /**
     * 所属分组ID
     */
    @TableField(value = "list_id")
    private String listId;
    /**
     * 所属项目id(冗余字段，方便过滤)
     */
    @TableField(value = "project_id")
    private String projectId;
    /**
     * 任务名称
     */
    @TableField(value = "task_name")
    private String taskName;
    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;
    /**
     * 父任务id
     */
    @TableField(value = "parent_taskid")
    private String parentTaskId;
    /**
     * 优先级
     */
    @TableField(value = "priority_id")
    private Long priorityId;
    private Date createTime;
    private Date updateTime;
}
