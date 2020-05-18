package com.teamwork.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 任务分组表
 */
@Getter
@Setter
@TableName(value = "t_task_group")
public class T_TASK_GROUP {
    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.NONE)
    @Getter
    @Setter
    private Long id;
    /**
     * 所属项目ID
     */
    @Getter
    @Setter
    private String projectId;
    /**
     * 任务分组名称
     */
    @TableField(value = "group_name")
    @Getter @Setter
    private String groupName;
    /**
     * 排序号
     */
    @TableField(value = "seq")

    private int seq;
    private Date createTime;
    private Date updateTime;
}
