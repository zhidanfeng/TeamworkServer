package com.teamwork.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * 项目权限组表
 */
@TableName(value = "project_authority_group")
@Getter
@Setter
public class ProjectRightsGroupDO extends BaseDO {
    /**
     * 项目权限组id
     */
    @TableId(value = "group_id", type = IdType.NONE)
    private String groupId;
    /**
     * 权限组名称
     */
    @TableId(value = "group_name")
    private long groupName;
    /**
     * 是否为默认权限组
     */
    @TableId(value = "default_group")
    private boolean defaultGroup;
}
