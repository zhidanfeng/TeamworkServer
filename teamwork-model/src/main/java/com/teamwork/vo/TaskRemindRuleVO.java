package com.teamwork.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class TaskRemindRuleVO {
    private Long ruleId;
    private Long taskId;
    private int remindFieldType;
    private int remindTimeLength;
    private int remindTimeUnit;
    private Long remindTimestamp;
    private Date gmtCreated;
    private Date gmtModified;

    private List<TaskRemindMemberVO> memberList;
}
