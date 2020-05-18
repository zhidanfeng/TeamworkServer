package com.teamwork.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * 资源表
 */
@TableName(value = "t_resource")
@Getter
@Setter
public class ResourceDO extends BaseDO {
    /**
     * 资源id
     */
    @TableId(value = "resource_id", type = IdType.NONE)
    private long resourceId;
    /**
     * 资源名称
     */
    @TableField(value = "resource_name")
    private String resourceName;
    /**
     * 资源描述
     */
    @TableField(value = "resource_desc")
    private String resourceDesc;
    /**
     * 所属菜单分类id
     */
    @TableField(value = "category_id")
    private long categoryId;
}
