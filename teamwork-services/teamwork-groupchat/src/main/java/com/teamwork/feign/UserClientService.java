package com.teamwork.feign;

import com.teamwork.vo.Result;
import com.teamwork.vo.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "API-UMS")
public interface UserClientService {

    @GetMapping(value = "/user/info")
    Result<UserInfo> getUserInfo(@RequestParam(value = "userId") long userId);
}
