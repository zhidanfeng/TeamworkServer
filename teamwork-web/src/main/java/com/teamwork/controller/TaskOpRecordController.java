package com.teamwork.controller;

import com.teamwork.service.TaskOpRecordService;
import com.teamwork.utils.SnowflakeIdWorker;
import com.teamwork.vo.Result;
import com.teamwork.vo.TaskAttachmentVO;
import com.teamwork.vo.TaskOpRecordVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/operaterecord")
public class TaskOpRecordController {

    @Resource
    private TaskOpRecordService taskOpRecordService;
    @Resource
    private SnowflakeIdWorker idWorker;

    /**
     * 新增一条操作记录
     */
    @CrossOrigin(origins="*", maxAge=3600)
    @ResponseBody
    @PostMapping(value = "/add")
    public Result addTaskGroup(@RequestBody TaskOpRecordVO param) {
        boolean result = this.taskOpRecordService.addRecord(param);
        return !result ? Result.failure() : Result.success(result);
    }

    @CrossOrigin(origins="*", maxAge=3600)
    @ResponseBody
    @GetMapping(value = "/list/{taskId}")
    public Result getAllByProjectId(@PathVariable long taskId) {
        List<TaskOpRecordVO> list = this.taskOpRecordService.getListByTaskId(taskId);
        return list == null ? Result.failure() : Result.success(list);
    }

    private HashMap<String, String> taskAttachmentTemp = new HashMap<>();
    private HashMap<String, TaskAttachmentVO> taskAttachmentMap = new HashMap<>();

    @CrossOrigin(origins="*", maxAge=3600)
    @ResponseBody
    @PostMapping(value = "/upload")
    public Result upload(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        TaskAttachmentVO attachmentIdList = this.taskOpRecordService.addAttachment(request);
        return Result.success(attachmentIdList);
    }

    @CrossOrigin(origins="*", maxAge=3600)
    @ResponseBody
    @GetMapping(value = "/attachmentupload/list/{taskId}")
    public Result getAttachmentUploadRecords(@PathVariable long taskId) {
        List<TaskAttachmentVO> list = this.taskOpRecordService.getTempAttachmentList(taskId);
        return list == null ? Result.failure() : Result.success(list);
    }

    private Long addRecord(MultipartHttpServletRequest multiRequest) {
        String taskId = multiRequest.getParameter("taskId");
        String recordType = multiRequest.getParameter("recordType");
        String operatorName = multiRequest.getParameter("operatorName");

        TaskOpRecordVO record = new TaskOpRecordVO();
        record.setRecordId(idWorker.nextId());
        record.setTaskId(Long.parseLong(taskId));
        record.setOperatorName(operatorName);
        record.setRecordType(Integer.getInteger(recordType));
        boolean isAdd = this.taskOpRecordService.addRecord(record);
        return isAdd ? record.getRecordId() : 0;
    }
}
