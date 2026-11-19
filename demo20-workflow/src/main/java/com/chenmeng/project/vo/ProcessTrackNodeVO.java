package com.chenmeng.project.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 流程轨迹节点 VO（对应 ACT_HI_ACTINST 表每个节点一条记录）。
 *
 * <p>这就是前端"流程图高亮"的数据来源，还原原理分三步：
 * <ol>
 *     <li>前端拿到 BPMN XML 渲染出流程图（节点 id 与本 VO 的 activityId 一一对应）；</li>
 *     <li>后端用 {@code HistoryService.createHistoricActivityInstanceQuery()}
 *         查出该流程实例"已经走过 + 正在走"的节点（分别对应 finished / unfinished 两种状态）；</li>
 *     <li>前端把 finished 节点涂成绿色（走过的路径）、unfinished 节点涂成高亮闪烁（当前位置），
 *         对照 activityId 刷色即可，没有任何魔法。</li>
 * </ol>
 * yudao 的 {@code BpmProcessInstanceService.getProcessInstanceBpmnModelView}（高亮流程图）
 * 底层同样是这一套查询 + BPMN 模型组装。
 *
 * <p>一个流程实例会按经过顺序在 ACT_HI_ACTINST 产生记录：
 * startEvent → userTask → exclusiveGateway → ... → endEvent，
 * 网关/连线/事件节点也都算"活动（Activity）"，所以本 VO 的 activityType 会看到
 * startEvent / userTask / exclusiveGateway / endEvent 等取值。
 *
 * @author chenmeng
 */
@Data
@ApiModel("流程轨迹节点响应")
public class ProcessTrackNodeVO implements Serializable {

    /** 序列化版本号 */
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "节点 id（= BPMN XML 里的元素 id，前端流程图按此高亮）", example = "leaderTask")
    private String activityId;

    @ApiModelProperty(value = "节点名称", example = "领导审批")
    private String activityName;

    @ApiModelProperty(value = "节点类型：startEvent-开始 / userTask-用户任务 / exclusiveGateway-排他网关 / endEvent-结束")
    private String activityType;

    @ApiModelProperty(value = "办理人（仅 userTask 有值）", example = "lisi")
    private String assignee;

    @ApiModelProperty(value = "进入该节点的时间")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "离开该节点的时间（null 表示当前停留的节点）")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "该节点是否已走完（false = 当前停留节点，前端高亮闪烁的位置）")
    private Boolean finished;
}
