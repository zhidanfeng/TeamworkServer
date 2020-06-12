package com.teamwork.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户-项目关联
 */
@TableName(value = "link_project_user")
public class LINK_PROJECT_USER {
    @Getter
    @Setter
    private String userId;
    @Getter
    @Setter
    private String projectId;
}
