package com.teamwork.vo.condition;

import lombok.Getter;
import lombok.Setter;

public class GroupChatMsgFilterCondition {
    @Getter
    @Setter
    private String projectId;
    @Getter
    @Setter
    private Long startTime;
    @Getter
    @Setter
    private Long endTime;
}
