package com.chenmeng.common.response;

import com.chenmeng.common.constants.enums.ErrorCodeEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * 通用返回类
 *
 * @author 沉梦听雨
 */
@Data
public class BaseResponse<T> implements Serializable {

    /**
     * 状态码
     */
    private int code;

    /**
     * 响应信息
     */
    private String message;

    /**
     * 数据
     */
    private T data;

    public BaseResponse(int code, String message, T data) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public BaseResponse(int code, T data) {
        this(code, "", data);
    }

    public BaseResponse(ErrorCodeEnum errorCodeEnum) {
        this(errorCodeEnum.getCode(), errorCodeEnum.getMessage(), null);
    }
}
