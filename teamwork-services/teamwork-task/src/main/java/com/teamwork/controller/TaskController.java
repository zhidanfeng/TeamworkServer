package com.teamwork.controller;

import com.teamwork.service.TaskService;
import com.teamwork.vo.PersonalPlanVO;
import com.teamwork.vo.RequirementVO;
import com.teamwork.vo.Result;
import com.teamwork.vo.TaskGroupVO;
import com.teamwork.vo.condition.TaskFilterCondition;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("")
public class TaskController {

    @Resource
    private TaskService taskService;

    /**
     * 获取任务列表
     */
    @CrossOrigin(origins="*", maxAge=3600)
    @ResponseBody
    @GetMapping(value = "/task/list/{projectId}")
    public Result getAllByProjectId(@PathVariable String projectId) {
        List<TaskGroupVO> list = null;
        return list == null ? Result.failure() : Result.success(list);
    }

    /**
     * 新建任务>>需求管理
     */
    @CrossOrigin(origins="*", maxAge=3600)
    @ResponseBody
    @PostMapping(value = "/task/requirement/add")
    public Result addRequirement(@RequestBody RequirementVO param) {
        return this.taskService.addRequirement(param);
    }

    @CrossOrigin(origins="*", maxAge=3600)
    @ResponseBody
    @GetMapping(value = "/task/requirement/{taskId}")
    public Result getRequirement(@PathVariable long taskId) {
        RequirementVO vo = this.taskService.getRequirement(taskId);
        return vo == null ? Result.failure() : Result.success(vo);
    }

    /**
     * 更新任务
     */
    @CrossOrigin(origins="*", maxAge=3600)
    @ResponseBody
    @PutMapping(value = "/task/requirement/update")
    public Result updateRequirement(@RequestBody RequirementVO param) {
        boolean result = this.taskService.updateRequirement(param);
        return !result ? Result.failure() : Result.success(result);
    }

    @CrossOrigin(origins="*", maxAge=3600)
    @ResponseBody
    @GetMapping(value = "/project/{projectId}/tasks")
    public Result getAllByCondition(@PathVariable String projectId, TaskFilterCondition condition) {
        return this.taskService.getAllByCondition(condition);
    }

    /**
     * 新建任务>>个人安排
     */
    @CrossOrigin(origins="*", maxAge=3600)
    @ResponseBody
    @PostMapping(value = "/task/personalplan/add")
    public Result addPersonalPlan(@RequestBody PersonalPlanVO param) {
        return this.taskService.addPersonalPlan(param);
    }

    @CrossOrigin(origins="*", maxAge=3600)
    @ResponseBody
    @GetMapping(value = "/task/personalplan/{taskId}")
    public Result getPersonalPlan(@PathVariable long taskId) {
        PersonalPlanVO vo = this.taskService.getPersonalPlan(taskId);
        return vo == null ? Result.failure() : Result.success(vo);
    }

    /**
     * 更新任务
     */
    @CrossOrigin(origins="*", maxAge=3600)
    @ResponseBody
    @PutMapping(value = "/task/personalplan/update")
    public Result updatePersonalPlan(@RequestBody PersonalPlanVO param) {
        boolean result = this.taskService.updatePersonalPlan(param);
        return !result ? Result.failure() : Result.success(result);
    }

    @CrossOrigin(origins="*", maxAge=3600)
    @ResponseBody
    @DeleteMapping(value = "/task/{taskId}")
    public Result deleteTask(@PathVariable long taskId) {
        boolean result = this.taskService.delete(taskId);
        return !result ? Result.failure() : Result.success(result);
    }
}
