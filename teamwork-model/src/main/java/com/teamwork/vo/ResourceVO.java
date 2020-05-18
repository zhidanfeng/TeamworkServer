package com.teamwork.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceVO {
    @ApiModelProperty(value = "资源ID")
    private long resourceId;
    @ApiModelProperty(value = "资源名称")
    private String resourceName;
    /**
     * 资源描述
     */
    @ApiModelProperty(value = "资源描述")
    private String resourceDesc;
    /**
     * 所属菜单分类id
     */
    @ApiModelProperty(value = "资源分类id")
    private long categoryId;
}
