package com.teamwork.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.teamwork.consts.ShiroConsts;
import com.teamwork.dao.UserDao;
import com.teamwork.entity.T_USER;
import com.teamwork.service.UserService;
import com.teamwork.token.JwtToken;
import com.teamwork.utils.BeanUtil;
import com.teamwork.utils.JwtUtil;
import com.teamwork.utils.ThreadLocalUtil;
import com.teamwork.vo.LoginVO;
import com.teamwork.vo.Result;
import com.teamwork.vo.ResultCode;
import com.teamwork.vo.UserInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public Result userLogin(LoginVO vo, HttpServletResponse response) {
        Assert.notNull(vo.getUserName(), "用户名不能为空");
        Assert.notNull(vo.getPassword(), "密码不能为空");

        QueryWrapper<T_USER> wrapper = new QueryWrapper<>();
        wrapper.eq("username", vo.getUserName());
        wrapper.eq("password", vo.getPassword());
        T_USER user = this.userDao.selectOne(wrapper);

        if (user == null)
            return Result.failure(ResultCode.USERNAME_NOT_FOUND);

        String strToken = this.loginSuccess(user.getUserName(), response);

//        Subject subject = SecurityUtils.getSubject();
//        AuthenticationToken token = new JwtToken(strToken);
//        subject.login(token);

        UserInfo userInfo = BeanUtil.copy(user, UserInfo.class);
        userInfo.setToken(strToken);
        return Result.success(userInfo);
    }

    /**
     * 用户登录成功，生成token
     */
    private String loginSuccess(String account, HttpServletResponse response) {
        String currentTimeMillis = String.valueOf(System.currentTimeMillis());
        String token = JwtUtil.sign(account, currentTimeMillis);
        //写入header
        response.setHeader(ShiroConsts.REQUEST_AUTH_HEADER, token);
        response.setHeader("Access-Control-Expose-Headers", ShiroConsts.REQUEST_AUTH_HEADER);

        return token;
    }
}
