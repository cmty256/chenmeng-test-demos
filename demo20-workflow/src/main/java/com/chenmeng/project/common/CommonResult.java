package com.chenmeng.project.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 统一接口返回结构（教学用的极简版）。
 *
 * <p>与 yudao 的对应关系：yudao 里的 {@code CommonResult<T>} 与此类结构几乎一样
 * （code + data + msg 三件套）。工作流模块返回的接口数据里，流程相关的字段
 * 全部来自 Flowable 引擎的表，业务字段来自业务表——通过 businessKey 关联，
 * 两者可以在一个 VO 里自由组装，这正是"业务表 + 工作流"松耦合集成的体现。
 *
 * @author chenmeng
 */
@Data
public class CommonResult<T> implements Serializable {

    /** 响应码：200 成功，500 业务/系统失败 */
    private Integer code;

    /** 提示信息（失败时为可读的错误原因） */
    private String msg;

    /** 响应数据 */
    private T data;

    /** 序列化版本号 */
    private static final long serialVersionUID = 1L;

    /**
     * 成功返回（带数据）
     *
     * @param data 响应数据
     * @return 统一返回结构
     */
    public static <T> CommonResult<T> success(T data) {
        CommonResult<T> result = new CommonResult<>();
        result.code = 200;
        result.msg = "操作成功";
        result.data = data;
        return result;
    }

    /**
     * 失败返回
     *
     * @param msg 失败原因（给调用方可读的提示，例如"任务不存在或已完成: xxx"）
     * @return 统一返回结构
     */
    public static <T> CommonResult<T> fail(String msg) {
        CommonResult<T> result = new CommonResult<>();
        result.code = 500;
        result.msg = msg;
        return result;
    }
}
