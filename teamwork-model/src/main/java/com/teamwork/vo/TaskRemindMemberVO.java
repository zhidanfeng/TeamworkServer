package com.teamwork.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class TaskRemindMemberVO {
    private Long id;
    private Long ruleId;
    private Long userId;
    private String userName;
    private Date gmtCreated;
    private Date gmtModified;
}
