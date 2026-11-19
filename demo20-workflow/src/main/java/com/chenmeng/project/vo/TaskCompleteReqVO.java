package com.chenmeng.project.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 审批（完成任务）请求 VO。
 *
 * <p>一次审批在引擎侧做的三件事（见 {@code LeaveService.completeTask}）：
 * <ol>
 *     <li>{@code TaskService.addComment}：把 comment 审批意见写入 ACT_HI_COMMENT 表（历史表，永不删除）；</li>
 *     <li>{@code TaskService.complete(taskId, variables)}：完成任务并写入流程变量 approved；</li>
 *     <li>引擎自动推进到下一个节点——排他网关按声明的顺序评估条件表达式
 *         （${!approved} → ${day > 3} → ${approved}），命中哪条走哪条分支。</li>
 * </ol>
 *
 * <p>"拒绝"的原理：approved=false 时网关第一条分支 ${!approved} 命中，流程直达 endEvent，
 * 实例结束，监听器把业务状态置为 3-拒绝。
 * 对照 yudao：yudao 的 reject 是"拒绝并终止流程"语义
 * （BpmTaskService.rejectTask 里先校验任务再走 changeActivityState/结束流程），
 * 本 demo 用纯 BPMN 网关分支实现同语义，更贴近引擎原生，两种实现殊途同归。
 *
 * @author chenmeng
 */
@Data
@ApiModel("审批（完成任务）请求")
public class TaskCompleteReqVO implements Serializable {

    /** 序列化版本号 */
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "任务 id（待办列表返回的 taskId）", required = true)
    @NotBlank(message = "任务 id 不能为空")
    private String taskId;

    @ApiModelProperty(value = "审批结果：true-通过、false-拒绝", required = true)
    @NotNull(message = "审批结果不能为空")
    private Boolean approved;

    @ApiModelProperty(value = "审批意见（会写入 ACT_HI_COMMENT 表，历史任务接口可查回）", example = "同意，注意交接")
    private String comment;
}
