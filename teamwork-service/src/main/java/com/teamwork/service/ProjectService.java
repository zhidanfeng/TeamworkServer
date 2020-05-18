package com.teamwork.service;

import com.teamwork.vo.ProjectVO;

import java.util.List;

public interface ProjectService {
    /**
     * 获取所有标签
     */
    List<ProjectVO> getAll();
    /**
     * 新增项目
     */
    String saveProject(ProjectVO vo);
    /**
     * 获取用户参与的项目信息
     */
    List<String> getProjectByUserId(String userId);
    
}
