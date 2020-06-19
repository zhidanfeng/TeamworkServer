package com.teamwork.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
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
    private long parentTaskId;
    private List<TaskVO> subTaskList;
    private String viewId;
    /**
     * 任务开始时间
     */
    private Date startTime;
    /**
     * 任务截止时间
     */
    private Date endTime;
}
