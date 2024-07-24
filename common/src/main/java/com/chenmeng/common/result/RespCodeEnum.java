package com.chenmeng.common.result;

/**
 * 通用响应码枚举
 *
 * @author chenmeng
 */
public enum RespCodeEnum {

    /**
     * 成功
     */
    SUCCESS(0, "操作成功"),

    /**
     * 失败
     */
    FAIL(-1, "操作失败"),

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
    ;

    /**
     * 状态码
     */
    private final int code;

    /**
     * 信息
     */
    private final String msg;

    RespCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
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
    public String getMsg() {
        return msg;
    }
}
