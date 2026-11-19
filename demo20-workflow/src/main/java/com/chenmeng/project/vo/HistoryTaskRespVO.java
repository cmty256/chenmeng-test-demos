package com.chenmeng.project.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 历史任务响应 VO（对应 ACT_HI_TASKINST 表的数据）。
 *
 * <p>与待办（ACT_RU_TASK）的核心区别——这是本 demo 要点破的知识点：
 * <ul>
 *     <li>ACT_RU_*（runtime）：只存"活着的"流程/任务，流程一结束、任务一完成就从 RU 表删除；</li>
 *     <li>ACT_HI_*（history）：从任务创建那刻就写入，完成后只补 END_TIME_ 等结束字段，永不删除。
 *         所以历史任务里"进行中任务"的 endTime 为 null，"已完成任务"的 endTime 有值。</li>
 * </ul>
 * 验证方法：发起请假后立刻查 /process/history，会发现"领导审批"任务已经在历史表里了（endTime=null），
 * 同时 ACT_RU_TASK 里也有它——两张表同时有数据；审批完成后 RU 表那条消失，HI 表那条补上 endTime。
 *
 * <p>历史任务的完整生命周期还包含审批意见（ACT_HI_COMMENT，通过 {@code taskService.addComment} 写入），
 * 本 VO 把意见按 taskId 关联出来，还原"谁在什么时间审的、批了什么话"。
 *
 * @author chenmeng
 */
@Data
@ApiModel("历史任务响应")
public class HistoryTaskRespVO implements Serializable {

    /** 序列化版本号 */
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "任务 id")
    private String taskId;

    @ApiModelProperty(value = "任务名称", example = "领导审批")
    private String taskName;

    @ApiModelProperty(value = "办理人", example = "lisi")
    private String assignee;

    @ApiModelProperty(value = "任务开始时间（= 审批人开始能看到这条待办的时间）")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "任务结束时间（null 表示进行中，还没被人审批）")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "审批意见（ACT_HI_COMMENT 表按 taskId 关联回来）", example = "同意")
    private String comment;
}
