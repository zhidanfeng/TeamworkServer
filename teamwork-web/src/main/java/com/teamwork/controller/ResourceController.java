package com.teamwork.controller;

import com.teamwork.service.ResourceService;
import com.teamwork.vo.ResourceVO;
import com.teamwork.vo.Result;
import com.teamwork.vo.paging.PageRequestVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/resource")
public class ResourceController {

    @Resource
    private ResourceService resourceService;

    /**
     * 新建项目模板
     */
    @CrossOrigin(origins="*", maxAge=3600)
    @ResponseBody
    @GetMapping(value = "/list")
    public Result getAll() {
        return resourceService.selectAll();
    }

    /**
     * 新建项目模板
     */
    @CrossOrigin(origins="*", maxAge=3600)
    @ResponseBody
    @GetMapping(value = "/listbypage")
    public Result getByPage(HttpServletRequest request) {
        int pageNum = Integer.valueOf(request.getParameter("pageNum"));
        int pageSize = Integer.valueOf(request.getParameter("pageSize"));
        PageRequestVO vo = new PageRequestVO(pageNum, pageSize);
        return resourceService.selectByPage(vo);
    }

    @CrossOrigin(origins="*", maxAge=3600)
    @ResponseBody
    @PostMapping(value = "/add")
    public Result add(@RequestBody ResourceVO param) {
        return resourceService.insert(param);
    }

    @CrossOrigin(origins="*", maxAge=3600)
    @ResponseBody
    @PutMapping("/{resourceId}")
    public Result update(@PathVariable long resourceId, @RequestBody ResourceVO model) {
        return resourceService.update(resourceId, model);
    }

    @CrossOrigin(origins="*", maxAge=3600)
    @ResponseBody
    @DeleteMapping("/{resourceId}")
    public Result deleteByKey(@PathVariable long resourceId) {
        return resourceService.deleteByKey(resourceId);
    }
}
