package com.teamwork.controller;

import com.teamwork.service.ProjectTemplateService;
import com.teamwork.vo.ProjectTemplateVO;
import com.teamwork.vo.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/project/template")
public class ProjectTemplateController {

    @Resource
    private ProjectTemplateService projectTemplateService;

    /**
     * 新建项目模板
     */
    @CrossOrigin(origins="*", maxAge=3600)
    @ResponseBody
    @PostMapping(value = "/add")
    public Result add(@RequestBody ProjectTemplateVO param) {
        String result = this.projectTemplateService.add(param);
        return "".equals(result) ? Result.failure() : Result.success(result);
    }

    @CrossOrigin(origins="*", maxAge=3600)
    @ResponseBody
    @GetMapping(value = "/list")
    public Result getAll() {
        List<ProjectTemplateVO> list = this.projectTemplateService.getAll();
        return list == null ? Result.failure() : Result.success(list);
    }
}
