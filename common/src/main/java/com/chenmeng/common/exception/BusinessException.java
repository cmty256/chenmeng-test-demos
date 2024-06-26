package com.chenmeng.common.exception;

import com.chenmeng.common.constants.enums.ErrorCodeEnum;

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

    public BusinessException(ErrorCodeEnum errorCodeEnum) {
        super((errorCodeEnum.getMessage()));
        this.code = errorCodeEnum.getCode();
    }

    public BusinessException(ErrorCodeEnum errorCodeEnum, String message) {
        super(message);
        this.code = errorCodeEnum.getCode();
    }

    public int getCode() {
        return code;
    }

}
