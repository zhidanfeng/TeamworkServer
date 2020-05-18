package com.teamwork.controller;

import com.teamwork.service.GroupChatService;
import com.teamwork.vo.ChatMessageVo;
import com.teamwork.vo.Result;
import com.teamwork.vo.condition.GroupChatMsgFilterCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GroupChatController {

    @Autowired
    private GroupChatService groupChatService;

    /**
     *
     */
    @CrossOrigin(origins="*", maxAge=3600)
    @ResponseBody
    @GetMapping(value = "/groupchat/history/{projectId}")
    public Result getHistoryMessage(@PathVariable String projectId) throws Exception {
        List<ChatMessageVo> result = null;
        result = groupChatService.getHistoryMessage(projectId);
        return result == null ? Result.failure() : Result.success(result);
    }

    @CrossOrigin(origins="*", maxAge=3600)
    @PostMapping(value = "/groupchat/history")
    public Result getHistoryMessage(@RequestBody GroupChatMsgFilterCondition condition) {
        List<ChatMessageVo> result = null;
        result = groupChatService.getHistoryMessage(condition);
        return result == null ? Result.failure() : Result.success(result);
    }
}
