package com.teamwork.controller;

import com.teamwork.service.PriorityService;
import com.teamwork.service.ProjectTemplateService;
import com.teamwork.vo.PriorityVO;
import com.teamwork.vo.ProjectTemplateVO;
import com.teamwork.vo.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/priority")
public class PriorityController {

    @Resource
    private PriorityService priorityService;

    /**
     * 新建项目模板
     */
    @CrossOrigin(origins="*", maxAge=3600)
    @ResponseBody
    @GetMapping(value = "/list")
    public Result getAll() {
        return priorityService.selectAll();
    }

    @CrossOrigin(origins="*", maxAge=3600)
    @ResponseBody
    @PostMapping(value = "/add")
    public Result add(@RequestBody PriorityVO param) {
        return priorityService.insert(param);
    }

    @CrossOrigin(origins="*", maxAge=3600)
    @ResponseBody
    @PutMapping("/{priorityId}")
    public Result update(@PathVariable long priorityId, @RequestBody PriorityVO model) {
        return priorityService.update(priorityId, model);
    }

    @CrossOrigin(origins="*", maxAge=3600)
    @ResponseBody
    @DeleteMapping("/{priorityId}")
    public Result deleteByKey(@PathVariable long priorityId) {
        return priorityService.deleteByKey(priorityId);
    }
}
