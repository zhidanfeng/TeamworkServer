package com.teamwork.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 任务>>个人安排表
 */
@TableName(value = "t_task_personalplan")
public class T_TASK_PERSONALPLAN {
    /**
     * 编号
     */
    @TableId(value = "task_id")
    @Getter
    @Setter
    private long taskId;
    /**
     * 截止时间
     */
    @TableField(value = "end_time", updateStrategy = FieldStrategy.IGNORED)
    @Getter @Setter
    private Date endTime;
    /**
     * 重复类型
     */
    @TableField(value = "repeat_type", updateStrategy = FieldStrategy.IGNORED)
    @Getter @Setter
    private int repeatType;
}
