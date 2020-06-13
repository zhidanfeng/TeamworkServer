package com.teamwork.controller;

import com.teamwork.service.UserService;
import com.teamwork.vo.LoginVO;
import com.teamwork.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Value("${server.port}")
    String port;

    /**
     * 生成页面id
     */
    @CrossOrigin(origins="*", maxAge=3600)
    @ResponseBody
    @PostMapping(value = "/user/login")
    public Result userLogin(@RequestBody LoginVO vo, HttpServletResponse response) {
        System.out.println("port:" + this.port);
        return userService.userLogin(vo, response);
    }

    /**
     * 生成页面id
     */
    @CrossOrigin(origins="*", maxAge=3600)
    @ResponseBody
    @GetMapping(value = "/user/info")
    public Result getUserInfo(@RequestParam(value = "userId") long userId) {
        System.out.println("port:" + this.port);
        return userService.getUserInfo(userId);
    }
}
