package com.teamwork.vo;

import lombok.Getter;
import lombok.Setter;

public class UserInfo {
    @Getter
    @Setter
    private Integer userId;
    @Getter
    @Setter
    private String userName;
    @Getter
    @Setter
    private String token;
}
