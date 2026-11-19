package com.chenmeng.project.vo;

import com.chenmeng.project.entity.LeaveDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 请假单响应 VO。
 *
 * <p>观察重点：status 字段随流程推进的变化——
 * <ul>
 *     <li>发起后：1-审批中（此时任务停在"领导审批"节点）；</li>
 *     <li>每个审批人 complete 之后刷新本列表：仍是 1-审批中（流程还没到 endEvent）；</li>
 *     <li>流程走到 endEvent 的瞬间：流程结束监听器把 status 改成 2-通过 / 3-拒绝。</li>
 * </ul>
 * 结论：业务状态不是审批接口直接改的，而是"流程实例结束"这一事件驱动的（监听器模式）。
 *
 * @author chenmeng
 */
@Data
@ApiModel("请假单响应")
public class LeaveRespVO implements Serializable {

    /** 序列化版本号 */
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "请假单 id（= 流程实例的 businessKey）", example = "1")
    private Long id;

    @ApiModelProperty(value = "请假人", example = "zhangsan")
    private String userId;

    @ApiModelProperty(value = "请假事由", example = "家里有事")
    private String reason;

    @ApiModelProperty(value = "请假天数", example = "2")
    private Integer day;

    @ApiModelProperty(value = "状态：1-审批中、2-审批通过、3-审批拒绝", example = "1")
    private Integer status;

    @ApiModelProperty(value = "状态中文描述", example = "审批中")
    private String statusDesc;

    @ApiModelProperty(value = "流程实例 id（Flowable 自动生成的 UUID 样式主键）", example = "a1b2c3d4-...")
    private String processInstanceId;

    @ApiModelProperty(value = "发起时间")
    private LocalDateTime createTime;

    /**
     * 状态枚举转中文（教学用，真实项目用字典表 system_dict_data）
     */
    public static String statusToDesc(Integer status) {
        if (LeaveDO.STATUS_APPROVING.equals(status)) {
            return "审批中";
        }
        if (LeaveDO.STATUS_APPROVED.equals(status)) {
            return "审批通过";
        }
        if (LeaveDO.STATUS_REJECTED.equals(status)) {
            return "审批拒绝";
        }
        return "未知状态";
    }
}
