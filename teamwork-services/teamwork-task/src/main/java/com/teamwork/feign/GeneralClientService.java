package com.teamwork.feign;

import com.teamwork.vo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "API-GENERAL")
public interface GeneralClientService {
    @GetMapping(value = "/user/info")
    Result<String> getViewId(@RequestParam(value = "viewId") String viewId);
}
