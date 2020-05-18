package com.teamwork.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 任务列表
 */
@Getter
@Setter
@TableName(value = "t_task_list")
public class T_TASK_LIST {
    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.NONE)
    private Long id;
    /**
     * 所属分组ID
     */
    @TableField(value = "group_id")
    private String groupId;
    /**
     * 任务分组名称
     */
    @TableField(value = "list_name")
    private String listName;
    /**
     * 排序号
     */
    @TableField(value = "seq")
    private int seq;
    /**
     * 是否允许删除(0:不允许；1：允许)
     */
    @TableField(value = "can_delete")
    private int canDelete;
    private Date createTime;
    private Date updateTime;
}
