package com.teamwork.vo;

import lombok.Getter;
import lombok.Setter;

public class ChatMessageVo {
    @Getter
    @Setter
    private Integer msgId;
    @Getter
    @Setter
    private String msgContent;
    @Getter
    @Setter
    private UserInfo user;
    @Getter
    @Setter
    private String projectId;
    @Getter
    @Setter
    private long msgTime;
}
