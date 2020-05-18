package com.teamwork.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 项目表
 */
@TableName(value = "t_project")
public class T_PROJECT {
    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.NONE)
    @Getter @Setter
    private String projectId;
    /**
     * 项目名称
     */
    @TableField(value = "project_name")
    @Getter @Setter
    private String projectName;
    /**
     * 项目简介
     */
    @TableField(value = "project_desc")
    @Getter @Setter
    private String projectDesc;
    /**
     * 项目类型（1：需求管理；2：个人安排）
     */
    @TableField(value = "type")
    @Getter
    @Setter
    private Integer projectType;
    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @Getter @Setter
    private Date createTime;
    /**
     * 修改时间
     */
    @TableField(value = "update_time")
    @Getter @Setter
    private Date updateTime;
}
