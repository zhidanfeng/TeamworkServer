package com.teamwork.controller;

import com.teamwork.service.CommonService;
import com.teamwork.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommonController {

    @Autowired
    private CommonService commonService;

    /**
     * 生成页面id
     */
    @CrossOrigin(origins="*", maxAge=3600)
    @ResponseBody
    @GetMapping(value = "/viewid")
    public Result getViewId() {
        String result = commonService.getViewId();
        return "".equals(result) ? Result.failure() : Result.success(result);
    }
}
