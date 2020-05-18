package com.teamwork.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * 任务分组模板表
 *
 * 用于预先制定每种类型的任务的流程或者各类状态
 */
@Getter
@Setter
@TableName(value = "t_taskgroup_template")
public class T_TASKGROUP_TEMPLATE {
    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.NONE)
    private String id;
    /**
     * 项目模板id
     */
    @TableField(value = "template_id")
    private String templateId;
    /**
     * 任务分组名称
     */
    @TableField(value = "group_name")
    private String groupName;
    /**
     * 排序号
     */
    @TableField(value = "seq")
    private int seq;
}
