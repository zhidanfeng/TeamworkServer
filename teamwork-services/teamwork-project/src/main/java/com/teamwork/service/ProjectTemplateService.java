package com.teamwork.service;

import com.teamwork.vo.ProjectTemplateVO;

import java.util.List;

public interface ProjectTemplateService {
    /**
     * 获取所有标签
     */
    List<ProjectTemplateVO> getAll();
    /**
     * 新增项目
     */
    String add(ProjectTemplateVO vo);
}
