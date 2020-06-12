package com.teamwork.controller;

import com.teamwork.service.TaskListService;
import com.teamwork.vo.Result;
import com.teamwork.vo.TaskListVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/tasklist")
public class TaskListController {

    @Resource
    private TaskListService taskListService;

    /**
     * 新建任务分组
     */
    @CrossOrigin(origins="*", maxAge=3600)
    @ResponseBody
    @PostMapping(value = "/add")
    public Result addTaskGroup(@RequestBody TaskListVO param) {
        return this.taskListService.add(param);
    }

    /**
     * 更新任务分组
     */
    @CrossOrigin(origins="*", maxAge=3600)
    @ResponseBody
    @PutMapping(value = "/update")
    public Result updateTaskGroup(@RequestBody TaskListVO param) {
        boolean result = this.taskListService.update(param);
        return !result ? Result.failure() : Result.success(result);
    }

    @CrossOrigin(origins="*", maxAge=3600)
    @ResponseBody
    @GetMapping(value = "/list/{groupId}")
    public Result getAllByProjectId(@PathVariable String groupId) {
        List<TaskListVO> list = this.taskListService.getAll(groupId);
        return list == null ? Result.failure() : Result.success(list);
    }

    /**
     * 更新任务分组
     */
    @CrossOrigin(origins="*", maxAge=3600)
    @ResponseBody
    @DeleteMapping(value = "/{groupId}")
    public Result deleteTaskGroup(@PathVariable String groupId) {
        boolean result = this.taskListService.delete(groupId);
        return !result ? Result.failure() : Result.success(result);
    }
}
