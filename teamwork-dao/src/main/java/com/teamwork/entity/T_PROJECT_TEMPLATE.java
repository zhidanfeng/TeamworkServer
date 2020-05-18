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
@TableName(value = "t_project_template")
public class T_PROJECT_TEMPLATE {
    /**
     * 项目模板编号
     */
    @TableId(value = "id", type = IdType.NONE)
    @Getter
    @Setter
    private String templateId;

    /**
     * 项目模板名称
     */
    @TableField(value = "template_name")
    @Getter @Setter
    private String templateName;
    /**
     * 排序号
     */
    @TableField(value = "seq")
    @Getter @Setter
    private int seq;
    /**
     * 项目简介
     */
    @Getter @Setter
    private String description;
    /**
     * 模板封面id
     */
    @TableField(value = "cover_id")

    private String coverId;
    private Date createTime;
    private Date updateTime;
}
