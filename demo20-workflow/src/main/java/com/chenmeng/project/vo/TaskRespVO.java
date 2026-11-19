package com.chenmeng.project.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 待办任务响应 VO。
 *
 * <p>关键设计：一条待办 = "引擎数据" + "业务数据" 的组装结果。
 * <ul>
 *     <li>引擎侧：taskId / taskName / assignee / processInstanceId / taskCreateTime
 *         —— 来自 ACT_RU_TASK 表（{@code TaskService.createTaskQuery()}）；</li>
 *     <li>业务侧：leaveUserId / leaveReason / leaveDay —— 通过任务上的 businessKey
 *         反查业务表 leave_apply 得到（这就是 businessKey 存在的意义：从流程反向定位业务单）。</li>
 * </ul>
 * 审批人打开待办，既知道"该我办什么节点"（引擎），又知道"我审的是谁的什么单"（业务）。
 *
 * <p>对照 yudao：yudao 的待办列表由 {@code BpmTaskService.getTaskTodoPage} 提供，
 * 同样是先查引擎任务、再按 processInstanceId/businessKey 回查业务表组装 VO。
 *
 * @author chenmeng
 */
@Data
@ApiModel("待办任务响应")
public class TaskRespVO implements Serializable {

    /** 序列化版本号 */
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "任务 id（审批时作为 taskId 传入）")
    private String taskId;

    @ApiModelProperty(value = "任务名称（对应 BPMN 里 userTask 的 name 属性）", example = "领导审批")
    private String taskName;

    @ApiModelProperty(value = "办理人（对应 BPMN 里 ${leader} 等表达式解析出的值）", example = "lisi")
    private String assignee;

    @ApiModelProperty(value = "任务创建时间")
    private LocalDateTime taskCreateTime;

    @ApiModelProperty(value = "流程实例 id")
    private String processInstanceId;

    @ApiModelProperty(value = "业务键（= leave_apply.id，引擎表与业务表的关联字段）", example = "1")
    private String businessKey;

    // ========== 以下为反查业务表得到的业务信息 ==========

    @ApiModelProperty(value = "请假人", example = "zhangsan")
    private String leaveUserId;

    @ApiModelProperty(value = "请假事由", example = "家里有事")
    private String leaveReason;

    @ApiModelProperty(value = "请假天数", example = "2")
    private Integer leaveDay;
}
