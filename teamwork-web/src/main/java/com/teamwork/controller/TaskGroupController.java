package com.teamwork.controller;

import com.teamwork.service.TaskGroupService;
import com.teamwork.vo.Result;
import com.teamwork.vo.TaskGroupVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/taskgroup")
public class TaskGroupController {

    @Resource
    private TaskGroupService taskGroupService;

    /**
     * 新建任务分组
     */
    @CrossOrigin(origins="*", maxAge=3600)
    @ResponseBody
    @PostMapping(value = "/add")
    public Result addTaskGroup(@RequestBody TaskGroupVO param) {
        return this.taskGroupService.add(param);
    }

    /**
     * 更新任务分组
     */
    @CrossOrigin(origins="*", maxAge=3600)
    @ResponseBody
    @PutMapping(value = "/update")
    public Result updateTaskGroup(@RequestBody TaskGroupVO param) {
        boolean result = this.taskGroupService.update(param);
        return !result ? Result.failure() : Result.success(result);
    }

    @CrossOrigin(origins="*", maxAge=3600)
    @ResponseBody
    @GetMapping(value = "/list/{projectId}")
    public Result getAllByProjectId(@PathVariable String projectId) {
        return this.taskGroupService.getAll(projectId);
    }

    /**
     * 更新任务分组
     */
    @CrossOrigin(origins="*", maxAge=3600)
    @ResponseBody
    @DeleteMapping(value = "/{groupId}")
    public Result deleteTaskGroup(@PathVariable String groupId) {
        boolean result = this.taskGroupService.delete(groupId);
        return !result ? Result.failure() : Result.success(result);
    }
}
