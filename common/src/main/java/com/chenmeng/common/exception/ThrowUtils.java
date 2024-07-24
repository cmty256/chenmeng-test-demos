package com.chenmeng.common.exception;

import com.chenmeng.common.result.RespCodeEnum;

/**
 * 抛异常工具类
 *
 * @author chenmeng
 */
public class ThrowUtils {

    /**
     * 条件成立则抛异常
     *
     * @param condition 条件
     * @param runtimeException 运行时异常
     */
    public static void throwIf(boolean condition, RuntimeException runtimeException) {
        if (condition) {
            throw runtimeException;
        }
    }

    /**
     * 条件成立则抛异常
     *
     * @param condition 条件
     * @param respCodeEnum 响应码枚举
     */
    public static void throwIf(boolean condition, RespCodeEnum respCodeEnum) {
        throwIf(condition, new BusinessException(respCodeEnum));
    }

    /**
     * 条件成立则抛异常
     *
     * @param condition 条件
     * @param respCodeEnum 响应码枚举
     * @param message 自定义错误信息
     */
    public static void throwIf(boolean condition, RespCodeEnum respCodeEnum, String message) {
        throwIf(condition, new BusinessException(respCodeEnum, message));
    }
}
