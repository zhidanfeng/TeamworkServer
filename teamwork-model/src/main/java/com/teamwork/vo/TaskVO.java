package com.teamwork.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TaskVO extends BusinessVO {
    private long taskId;
    private String taskName;
    private String listId;
    private String projectId;
    /**
     * 优先级id
     */
    private Long priorityId;
    private String parentTaskId;
    private List<TaskVO> subTaskList;
    private String viewId;
}
