package com.teamwork.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfo {
    private long userId;
    private String userName;
    private String password;
    private String token;
}
