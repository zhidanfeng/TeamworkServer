package com.teamwork.controller;

import com.teamwork.service.UserService;
import com.teamwork.utils.ThreadLocalUtil;
import com.teamwork.vo.LoginVO;
import com.teamwork.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 生成页面id
     */
    @CrossOrigin(origins="*", maxAge=3600)
    @ResponseBody
    @PostMapping(value = "/user/login")
    public Result userLogin(@RequestBody LoginVO vo, HttpServletResponse response) {
        return userService.userLogin(vo, response);
    }
}
