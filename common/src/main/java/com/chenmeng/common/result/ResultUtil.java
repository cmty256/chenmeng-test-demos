package com.chenmeng.common.result;

import com.chenmeng.common.response.BaseResponse;
import com.chenmeng.common.constants.enums.ErrorCodeEnum;

/**
 * 返回结果工具类
 *
 * @author chenmeng
 * @date 2023/06/19
 */
public class ResultUtil {

    /**
     * 成功
     *
     * @param data 数据
     * @return {@code BaseResponse<T>}
     */
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(0, "ok", data);
    }

    /**
     * 失败
     *
     * @param errorCodeEnum 错误码
     * @return {@code BaseResponse<Void>}
     */
    public static BaseResponse<Void> error(ErrorCodeEnum errorCodeEnum) {
        return new BaseResponse<>(errorCodeEnum);
    }

    /**
     * 失败
     *
     * @param code 状态码
     * @param message 信息
     * @return {@code BaseResponse<Void>}
     */
    public static BaseResponse<Void> error(int code, String message) {
        return new BaseResponse<>(code, message, null);
    }

    /**
     * 失败
     *
     * @param errorCodeEnum 错误码
     * @param message 信息
     * @return {@code BaseResponse<Void>}
     */
    public static BaseResponse<Void> error(ErrorCodeEnum errorCodeEnum, String message) {
        return new BaseResponse<>(errorCodeEnum.getCode(), message, null);
    }
}
