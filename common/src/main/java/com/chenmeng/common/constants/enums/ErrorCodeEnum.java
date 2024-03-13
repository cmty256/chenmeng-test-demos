package com.chenmeng.common.constants.enums;

/**
 * 统一错误码
 *
 * @author 沉梦听雨
 */
public enum ErrorCodeEnum {

    /**
     * 错误码枚举
     */
    SUCCESS(0, "ok"),

    // params_error
    PARAMS_ERROR(40000, "请求参数错误"),

    // not_login_error
    NOT_LOGIN_ERROR(40100, "未登录"),

    // not_auth_error
    NO_AUTH_ERROR(40101, "无权限"),
    
    // not_found_error
    NOT_FOUND_ERROR(40400, "请求的数据不存在"),
    
    // forbidden_error
    FORBIDDEN_EEOR(40300, "禁止访问"),

    // system_error
    SYSTEM_ERROR(50000, "系统内部异常"),

    // operation_error
    OPERATION_ERROR(50001, "操作失败");

    /**
     * 状态码
     */
    private final int code;

    /**
     * 信息
     */
    private final String message;

    ErrorCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 获取错误状态码
     *
     * @return int
     */
    public int getCode() {
        return code;
    }

    /**
     * 获取错误响应信息
     *
     * @return {@code String}
     */
    public String getMessage() {
        return message;
    }

}
