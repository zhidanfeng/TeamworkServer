package com.teamwork.controller;

import com.teamwork.service.ProjectService;
import com.teamwork.vo.ProjectVO;
import com.teamwork.vo.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/project")
public class ProjectController {

    @Resource
    private ProjectService projectService;

    /**
     * 新建项目
     */
    @CrossOrigin(origins="*", maxAge=3600)
    @ResponseBody
    @PostMapping(value = "/add")
    public Result addProject(@RequestBody ProjectVO param) {
        String result = this.projectService.saveProject(param);
        return "".equals(result) ? Result.failure() : Result.success(result);
    }

    @CrossOrigin(origins="*", maxAge=3600)
    @ResponseBody
    @GetMapping(value = "/list")
    public Result getAllProject() {
        List<ProjectVO> list = this.projectService.getAll();
        return list == null ? Result.failure() : Result.success(list);
    }

    /**
     * 获取用户参与的项目信息
     */
    @CrossOrigin(origins="*", maxAge=3600)
    @ResponseBody
    @GetMapping(value = "/user/{userId}")
    public Result getProjectByUserId(@PathVariable String userId) {
        List<String> list = this.projectService.getProjectByUserId(userId);
        return list == null ? Result.failure() : Result.success(list);
    }
}
