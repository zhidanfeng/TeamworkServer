package com.teamwork.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 需求任务表
 */
@TableName(value = "t_task_requirement")
public class T_TASK_REQUIREMENT {
    /**
     * 编号
     */
    @TableId(value = "task_id")
    @Getter
    @Setter
    private long taskId;
    /**
     * 需求来源
     */
    @Getter
    @Setter
    @TableField(value = "task_source")
    private int taskSource;
    /**
     * 需求提出人
     */
    @TableField(value = "proposer", updateStrategy = FieldStrategy.IGNORED)
    @Getter @Setter
    private String proposer;
    /**
     * 需求优先级
     */
    @TableField(value = "priority")
    @Getter @Setter
    private int priority;
    /**
     * 评估意见
     */
    @TableField(value = "opinion", updateStrategy = FieldStrategy.IGNORED)
    @Getter @Setter
    private String opinion;
    /**
     * 需求计划上线时间
     */
    @TableField(value = "plan_online_time", updateStrategy = FieldStrategy.IGNORED)
    @Getter @Setter
    private Date planOnlineTime;
    /**
     * 需求执行人
     */
    @TableField(value = "executor", updateStrategy = FieldStrategy.IGNORED)
    @Getter @Setter
    private String executor;
    /**
     * 备注
     */
    @TableField(value = "remark", updateStrategy = FieldStrategy.IGNORED)
    @Getter @Setter
    private String remark;
}
