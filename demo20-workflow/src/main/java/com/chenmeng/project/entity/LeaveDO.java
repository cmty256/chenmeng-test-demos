package com.chenmeng.project.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 请假单业务表实体（对应表 leave_apply）。
 *
 * <p>这是"业务表集成模式"的核心：业务数据（请假事由、天数、状态）存在自己的业务表里，
 * 流程数据（任务、节点、变量）由 Flowable 存在 ACT_* 表里，两者通过两个"桥字段"关联：
 * <ul>
 *     <li>{@link #processInstanceId}：业务表记录流程实例 id——从业务单反向定位流程；</li>
 *     <li>businessKey：流程实例上记录业务表主键（{@link #id}）——从流程任务反向定位业务单。
 *         businessKey 不在本表里，它存在 ACT_RU_EXECUTION.BUSINESS_KEY_ / ACT_HI_PROCINST.BUSINESS_KEY_ 列上。</li>
 * </ul>
 *
 * <p>与 yudao 的对应关系：本类对应 yudao 的 {@code BpmOALeave}（OA 请假示例的 DO），
 * yudao 里同样是"业务表存业务字段 + process_instance_id 存流程实例 id + 发起时以业务主键作为 businessKey"，
 * 这是所有"业务 + 工作流"集成的通用范式。
 *
 * <p>表名为什么叫 leave_apply 而不是 leave：LEAVE 在 MySQL 里是保留字（循环控制语句），
 * 建表和写 SQL 都要加反引号转义，教学/生产都容易踩坑，故避开。
 *
 * @author chenmeng
 */
@Data
@TableName("leave_apply")
public class LeaveDO implements Serializable {

    /** 序列化版本号 */
    private static final long serialVersionUID = 1L;

    /** 状态：审批中（发起后、流程未结束前的状态） */
    public static final Integer STATUS_APPROVING = 1;

    /** 状态：审批通过（流程走完 endEvent 且最终 approved = true） */
    public static final Integer STATUS_APPROVED = 2;

    /** 状态：审批拒绝（任一审批环节 approved = false，流程经网关分支直达 endEvent） */
    public static final Integer STATUS_REJECTED = 3;

    /**
     * 主键：业务表的唯一标识，同时作为流程实例的 businessKey。
     * MyBatis-Plus 插入后会把数据库自增 id 回填到该字段（IdType.AUTO）。
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 请假人（教学 demo 里直接用字符串用户名，真实项目应为用户表的用户 id） */
    @TableField("user_id")
    private String userId;

    /** 请假事由 */
    @TableField("reason")
    private String reason;

    /**
     * 请假天数：既存业务表，又作为流程变量传给引擎。
     * BPMN 排他网关的条件表达式 ${day > 3} 读取的就是这个流程变量（Integer 类型，支持 JUEL 数字比较）。
     */
    @TableField("day")
    private Integer day;

    /**
     * 审批状态：1 审批中、2 审批通过、3 审批拒绝。
     * 注意：业务表状态不由本模块的代码直接改成"通过/拒绝"，
     * 而是由流程结束监听器 {@code LeaveProcessEndListener} 在流程实例结束事件里统一回写——
     * 这对应 yudao 的 {@code BpmProcessInstanceStatusEventListener} 模式，保证"流程结束"和"业务状态落库"严格同步。
     */
    @TableField("status")
    private Integer status;

    /**
     * 流程实例 id：发起流程后回写到业务表。
     * 注意 ACT_RU_EXECUTION 里的记录在流程结束后会被删除（运行时表只存"活着的"流程），
     * 但 id 本身长期保留在本字段上，随时可去 ACT_HI_PROCINST 查这个流程实例的完整历史。
     */
    @TableField("process_instance_id")
    private String processInstanceId;

    /** 创建时间（发起请假的时间） */
    @TableField("create_time")
    private LocalDateTime createTime;

    /** 更新时间（由数据库 on update current_timestamp 自动维护） */
    @TableField("update_time")
    private LocalDateTime updateTime;
}
