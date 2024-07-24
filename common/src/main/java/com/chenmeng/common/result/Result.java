package com.chenmeng.common.result;

import lombok.Data;

import static com.chenmeng.common.result.RespCodeEnum.FAIL;
import static com.chenmeng.common.result.RespCodeEnum.SUCCESS;

/**
 * 通用返回结果类
 *
 * @author chenmeng
 */
@Data
public class Result<T> {

    /**
     * 响应码
     */
    protected int code;
    /**
     * 响应信息
     */
    protected String msg;
    /**
     * 响应数据
     */
    protected T data;

    public Result() {
    }

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(RespCodeEnum respCodeEnum) {
        this.code = respCodeEnum.getCode();
        this.msg = respCodeEnum.getMsg();
    }

    public static <T> Result<T> success() {
        return new Result<>(SUCCESS);
    }

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>(SUCCESS);
        result.setData(data);
        return result;
    }

    public static <T> Result<T> success(RespCodeEnum respCodeEnum, T data) {
        Result<T> result = new Result<>(respCodeEnum);
        result.setData(data);
        return result;
    }

    public static <T> Result<T> fail() {
        return new Result<>(FAIL);
    }

    public static <T> Result<T> fail(RespCodeEnum respCodeEnum) {
        return new Result<>(respCodeEnum);
    }

    public static <T> Result<T> fail(Integer code, String msg) {
        return new Result<>(code, msg);
    }

    public static <T> Result<T> error(Integer code, String msg) {
        return new Result<>(code, msg);
    }

    public static <T> Result<T> status(boolean flag) {
        return flag ? success() : fail();
    }
}
