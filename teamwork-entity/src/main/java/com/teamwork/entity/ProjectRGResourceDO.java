package com.teamwork.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * 项目权限组-菜单关联表
 */
@TableName(value = "link_project_rights_group_resource")
@Getter
@Setter
public class ProjectRGResourceDO extends BaseDO {
    /**
     * 资源id
     */
    @TableField(value = "resource_id")
    private long resourceId;
    /**
     * 权限组id
     */
    @TableField(value = "group_id")
    private String groupId;
}
