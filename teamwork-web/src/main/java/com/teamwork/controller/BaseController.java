package com.teamwork.controller;

import com.teamwork.exceptions.TeamworkException;
import com.teamwork.vo.Result;
import com.teamwork.vo.ResultCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class BaseController {
    @ExceptionHandler(IllegalArgumentException.class)
    public Result exception(HttpServletRequest request, Exception ex) {
        Result result = Result.failure(ResultCode.FAILURE, "IllegalArgumentException");
        return result;
    }

    @ExceptionHandler(IndexOutOfBoundsException.class)
    public String exception2(HttpServletRequest request, Exception ex) {
        if(ex instanceof IllegalArgumentException) {
            return "IndexOutOfBoundsException";
        }
        return "hehe";
    }

    @ExceptionHandler(TeamworkException.class)
    public Result exception3(HttpServletRequest request, TeamworkException ex) {
        ex.printStackTrace();
        Result result = new Result(ex.getErrorCode(), ex.getMessage());
        return result;
    }
}
