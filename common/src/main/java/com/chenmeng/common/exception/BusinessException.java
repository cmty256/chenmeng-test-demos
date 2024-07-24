package com.chenmeng.common.exception;

import com.chenmeng.common.result.RespCodeEnum;

/**
 * 自定义异常类
 *
 * @author chenmeng
 */
public class BusinessException extends RuntimeException {

    private final int code;

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(RespCodeEnum respCodeEnum) {
        super((respCodeEnum.getMsg()));
        this.code = respCodeEnum.getCode();
    }

    public BusinessException(RespCodeEnum respCodeEnum, String message) {
        super(message);
        this.code = respCodeEnum.getCode();
    }

    public int getCode() {
        return code;
    }

}
