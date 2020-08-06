package com.teamwork.exceptions;

import com.teamwork.vo.ResultCode;
import lombok.Getter;
import lombok.Setter;

public class TeamworkException extends RuntimeException {
    @Getter
    @Setter
    private int errorCode = ResultCode.UNKNOWN_EXCEPTION.code();

    public TeamworkException() {
        super(ResultCode.UNKNOWN_EXCEPTION.message());
    }

    public TeamworkException(ResultCode errCode) {
        super(errCode.message());
        this.errorCode = errCode.code();
    }

    public TeamworkException(String msg) {
        super(msg);
        this.errorCode = ResultCode.ILLEGAL_ARGUMENT_EXCEPTION.code();
    }

    /***
     * @name: fillInStackTrace
     * @description: 避免对api异常进行昂贵且无用的堆栈跟踪
     * @return: java.lang.Throwable
     * @since: 2020/8/6 20:16
     * @author: zhidanfeng
     */
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
