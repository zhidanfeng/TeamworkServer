package com.teamwork.controller;

import com.teamwork.service.CommonService;
import com.teamwork.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommonController {

    @Autowired
    private CommonService commonService;

    /**
     * 生成页面id
     */
    @CrossOrigin(origins="*", maxAge=3600)
    @ResponseBody
    @GetMapping(value = "/viewid/{viewId}")
    public Result getViewId(@PathVariable String viewId) {
        String result = commonService.getViewId(viewId);
        return "".equals(result) ? Result.failure() : Result.success(result);
    }
}
