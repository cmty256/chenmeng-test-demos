package com.chenmeng.project.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 发起请假请求 VO。
 *
 * <p>设计说明（对应"发起流程"的核心知识点）：
 * <ul>
 *     <li>user_id / reason / day：业务字段——既写入业务表 leave_apply，day 又作为流程变量传入引擎
 *         （BPMN 网关表达式 ${day > 3} 读的就是它）；</li>
 *     <li>leader / director / hr：审批人——只作为流程变量传入引擎，用于 BPMN 里
 *         {@code flowable:assignee="${leader}"} 这类表达式在"创建任务的那一刻"解析出办理人。
 *         不落业务表（真实项目会按组织架构/角色路由算出来，教学 demo 由前端直接指定）。</li>
 * </ul>
 *
 * <p>与 yudao 的对应关系：yudao OA 请假示例的 {@code BpmOALeaveCreateReqVO}，
 * 由 BpmOALeaveServiceImpl 调用 {@code BpmProcessInstanceApi.createProcessInstance(...)}
 * 完成本 demo 中 LeaveService.createLeave 的动作。
 *
 * @author chenmeng
 */
@Data
@ApiModel("发起请假请求")
public class LeaveCreateReqVO implements Serializable {

    /** 序列化版本号 */
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "请假人", required = true, example = "zhangsan")
    @NotBlank(message = "请假人不能为空")
    private String userId;

    @ApiModelProperty(value = "请假事由", required = true, example = "家里有事，需要回家处理")
    @NotBlank(message = "请假事由不能为空")
    private String reason;

    /**
     * 请假天数：网关分支的关键变量。
     * 体验建议：分别传 day=2（走"HR 备案"短分支）和 day=5（走"总监审批"长分支）各发起一次，
     * 观察 ACT_RU_TASK / ACT_HI_ACTINST 两条路径的差异。
     */
    @ApiModelProperty(value = "请假天数（>3 天会多走一级总监审批）", required = true, example = "2")
    @NotNull(message = "请假天数不能为空")
    @Min(value = 1, message = "请假天数至少为 1 天")
    @Max(value = 365, message = "请假天数不能超过 365 天")
    private Integer day;

    @ApiModelProperty(value = "领导审批人（一级审批）", required = true, example = "lisi")
    @NotBlank(message = "领导审批人不能为空")
    private String leader;

    @ApiModelProperty(value = "总监审批人（仅 day > 3 时才会走到）", required = true, example = "wangwu")
    @NotBlank(message = "总监审批人不能为空")
    private String director;

    @ApiModelProperty(value = "HR 备案人（流程最后一步）", required = true, example = "hr001")
    @NotBlank(message = "HR 备案人不能为空")
    private String hr;
}
