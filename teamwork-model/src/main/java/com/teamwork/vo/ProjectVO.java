package com.teamwork.vo;

import lombok.Getter;
import lombok.Setter;

public class ProjectVO extends BaseVO {
    @Getter
    @Setter
    private String projectId;
    /**
     * 项目名称
     */
    @Getter
    @Setter
    private String projectName;
    /**
     * 项目简介
     */
    @Getter
    @Setter
    private String projectDesc;
    /**
     * 项目模板ID，为空表示没有使用模板
     */
    @Getter
    @Setter
    private String templateId;

    /**
     * 项目类型（1：需求管理；2：个人安排）
     */
    @Getter
    @Setter
    private Integer projectType;
}
