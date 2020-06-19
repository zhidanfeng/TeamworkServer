package com.teamwork.controller;

import com.teamwork.service.TaskRemindConfigService;
import com.teamwork.vo.Result;
import com.teamwork.vo.TaskRemindRuleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/task/remind")
public class TaskRemindConfigController {

    @Autowired
    private TaskRemindConfigService remindConfigService;

    @CrossOrigin(origins="*", maxAge=3600)
    @ResponseBody
    @GetMapping(value = "/list/{taskId}")
    public Result getAll(@PathVariable String taskId) {
        return remindConfigService.selectListByTaskId(taskId);
    }

    @CrossOrigin(origins="*", maxAge=3600)
    @ResponseBody
    @PostMapping(value = "/add")
    public Result add(@RequestBody TaskRemindRuleVO param) {
        return remindConfigService.insert(param);
    }

    @CrossOrigin(origins="*", maxAge=3600)
    @ResponseBody
    @PostMapping(value = "/updatebatch")
    public Result updateBatch(@RequestBody List<TaskRemindRuleVO> param) {
        return remindConfigService.updateBatch(param);
    }

    @CrossOrigin(origins="*", maxAge=3600)
    @ResponseBody
    @PutMapping("/{key}")
    public Result update(@PathVariable long key, @RequestBody TaskRemindRuleVO model) {
        return remindConfigService.update(key, model);
    }

    @CrossOrigin(origins="*", maxAge=3600)
    @ResponseBody
    @DeleteMapping("/{key}")
    public Result deleteByKey(@PathVariable long key) {
        return remindConfigService.deleteByKey(key);
    }
}
