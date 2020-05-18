package com.teamwork.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class PriorityVO {
    private Long priorityId;
    private String priorityName;
    private int prioritySeq;
    private String priorityColor;
    private Date gmtCreated;
    private Date gmtModified;
}
