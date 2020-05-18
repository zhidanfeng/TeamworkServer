package com.teamwork.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class TaskGroupVO {
    @Getter
    @Setter
    private Long id;
    @Getter
    @Setter
    private String projectId;
    @Getter
    @Setter
    private String groupName;
    @Getter
    @Setter
    private int seq;
    @Getter
    @Setter
    private int canDelete;
    @Getter
    @Setter
    private List<TaskVO> taskList;
}
