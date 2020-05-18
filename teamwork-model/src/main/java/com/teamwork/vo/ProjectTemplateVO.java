package com.teamwork.vo;

import lombok.Getter;
import lombok.Setter;

public class ProjectTemplateVO extends BaseVO {
    @Getter
    @Setter
    private String templateId;
    /**
     * 项目名称
     */
    @Getter
    @Setter
    private String templateName;
    /**
     * 项目简介
     */
    @Getter
    @Setter
    private String templateDesc;
    /**
     * 项目模板ID，为空表示没有使用模板
     */
    @Getter
    @Setter
    private int seq;
}
