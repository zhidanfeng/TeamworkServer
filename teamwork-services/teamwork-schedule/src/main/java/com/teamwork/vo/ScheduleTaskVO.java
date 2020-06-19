package com.teamwork.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ScheduleTaskVO {
    private long id;
    private long linkId;
    private String taskTable;
    private String taskCron;
    private int enabled;
    private Date gmtCreated;
    private Date gmtModified;
}
