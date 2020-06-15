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
@TableName(value = "t_priority")
public class PriorityDO {
    @TableId(value = "priority_id", type = IdType.NONE)
    private Long priorityId;
    @TableField(value = "priority_name")
    private String priorityName;
    @TableField(value = "priority_seq")
    private int prioritySeq;
    @TableField(value = "priority_color")
    private String priorityColor;
    @TableField(value = "default_flag")
    private int defaultFlag;
    @TableField(value = "gmt_created")
    private Date gmtCreated;
    @TableField(value = "gmt_modified")
    private Date gmtModified;
}
