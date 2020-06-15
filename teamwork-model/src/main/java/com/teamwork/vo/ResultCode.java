package com.teamwork.vo;

public enum ResultCode {

    /* 成功状态码 */
    SUCCESS(200, "成功"),
    FAILURE(500, "失败"),

    UNKNOWN_EXCEPTION(-1, "未知异常"),
    ILLEGAL_ARGUMENT_EXCEPTION(-1, "参数不合法"),

    /* 用户错误：20001-29999*/
    USER_NOT_LOGGED_IN(20001, "用户未登录"),
    USER_LOGIN_ERROR(20002, "账号不存在或密码错误"),
    USERNAME_NOT_FOUND(20003, "账号不存在"),
    PASSWORD_WRONG(20004, "密码错误"),
    USER_ACCOUNT_FORBIDDEN(20010, "账号已被禁用"),
    TOKEN_EXPIRED(401, "Token已过期"),
    REPEAT_REQUEST(20020, "重复请求");

    private Integer code;
    private String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer code() {
        return this.code;
    }

    public String message() {
        return this.message;
    }

    public static String getMessage(String name) {
        for (ResultCode item : ResultCode.values()) {
            if (item.name().equals(name)) {
                return item.message;
            }
        }
        return name;
    }

    public static Integer getCode(String name) {
        for (ResultCode item : ResultCode.values()) {
            if (item.name().equals(name)) {
                return item.code;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.name();
    }
}
