package com.teamwork.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class TaskListVO {
    @Getter
    @Setter
    private Long id;
    @Getter
    @Setter
    private String groupId;
    @Getter
    @Setter
    private String listName;
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
