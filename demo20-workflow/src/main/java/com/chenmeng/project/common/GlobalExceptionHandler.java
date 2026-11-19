package com.chenmeng.project.common;

import lombok.extern.slf4j.Slf4j;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.common.engine.api.FlowableObjectNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器：把常见异常翻译成统一返回结构，避免把堆栈直接抛给调用方。
 *
 * <p>与 Flowable 相关的两类典型异常（建议在体验时故意触发一次，观察返回）：
 * <ul>
 *     <li>{@link FlowableObjectNotFoundException}：用不存在的 taskId / processInstanceId 查询或操作时抛出，
 *         对应底层是引擎查不到 ACT_RU_/ACT_HI_ 里的记录；</li>
 *     <li>{@link FlowableException}：引擎级异常的基类（表达式求值失败、状态不合法等）。
 *         注意：在 @Transactional 方法里抛出时整个事务（业务表 + ACT_* 表）会一起回滚，
 *         这是 Spring 集成 Flowable 的核心保证——业务数据和流程数据要么都成功、要么都回滚。</li>
 * </ul>
 *
 * @author chenmeng
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理 @RequestBody 上的 @Valid 参数校验失败（例如 day 传了 0）
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResult<Void> handleValidException(MethodArgumentNotValidException exception) {
        FieldError fieldError = exception.getBindingResult().getFieldError();
        String msg = fieldError == null ? "参数校验失败" : fieldError.getDefaultMessage();
        return CommonResult.fail(msg);
    }

    /**
     * 处理 @RequestParam 缺失（例如待办查询没传 assignee）
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public CommonResult<Void> handleMissingParamException(MissingServletRequestParameterException exception) {
        return CommonResult.fail("缺少必填参数: " + exception.getParameterName());
    }

    /**
     * Flowable 找不到对象（taskId / processInstanceId 不存在等），属于最常见的业务性失败
     */
    @ExceptionHandler(FlowableObjectNotFoundException.class)
    public CommonResult<Void> handleFlowableObjectNotFoundException(FlowableObjectNotFoundException exception) {
        log.warn("【flowable】对象不存在: {}", exception.getMessage());
        return CommonResult.fail("流程数据不存在: " + exception.getMessage());
    }

    /**
     * Flowable 引擎其他异常（含表达式求值失败等）
     */
    @ExceptionHandler(FlowableException.class)
    public CommonResult<Void> handleFlowableException(FlowableException exception) {
        log.error("【flowable】引擎异常", exception);
        return CommonResult.fail("工作流引擎异常: " + exception.getMessage());
    }

    /**
     * 业务代码主动抛出的状态异常（例如对已完成的任务重复审批）
     */
    @ExceptionHandler(IllegalStateException.class)
    public CommonResult<Void> handleIllegalStateException(IllegalStateException exception) {
        log.warn("【业务状态异常】{}", exception.getMessage());
        return CommonResult.fail(exception.getMessage());
    }

    /**
     * 兜底异常
     */
    @ExceptionHandler(Exception.class)
    public CommonResult<Void> handleException(Exception exception) {
        log.error("【系统异常】", exception);
        return CommonResult.fail("系统异常: " + exception.getMessage());
    }
}
