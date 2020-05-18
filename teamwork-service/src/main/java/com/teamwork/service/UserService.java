package com.teamwork.service;

import com.teamwork.vo.LoginVO;
import com.teamwork.vo.Result;

import javax.servlet.http.HttpServletResponse;

public interface UserService {
    /**
     * 登录
     */
    Result userLogin(LoginVO vo, HttpServletResponse response);
}
