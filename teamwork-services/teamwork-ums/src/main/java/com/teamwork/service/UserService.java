package com.teamwork.service;

import com.teamwork.vo.LoginVO;
import com.teamwork.vo.Result;
import com.teamwork.vo.UserInfo;

import javax.servlet.http.HttpServletResponse;

public interface UserService extends BaseService<UserInfo> {
    /**
     * 登录
     */
    Result userLogin(LoginVO vo, HttpServletResponse response);
}
