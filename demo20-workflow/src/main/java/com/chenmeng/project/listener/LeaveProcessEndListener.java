package com.chenmeng.project.listener;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.chenmeng.project.entity.LeaveDO;
import com.chenmeng.project.mapper.LeaveMapper;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * 流程结束监听器：流程实例走到 endEvent、即将结束时被引擎回调，
 * 根据"最后一次审批写入的流程变量 approved"把业务表状态更新为 2-通过 / 3-拒绝。
 *
 * <p>【这是"流程引擎驱动业务状态"的核心模式】业务代码发起/审批时从不直接改业务状态，
 * 只推进流程；业务状态只在流程结束这一个点由监听器回写。好处：
 * <ul>
 *     <li>业务状态的唯一真相来源是流程本身，不会出现"流程没走完、单子却显示已通过"的脏状态；</li>
 *     <li>流程怎么改分支（加节点、改网关条件），业务状态回写逻辑完全不用动。</li>
 * </ul>
 *
 * <p>【与 yudao 的对应关系】本类对应 yudao 的 {@code BpmProcessInstanceStatusEventListener}
 * （yudao-spring-boot-starter-bpm 包内，基于 {@code AbstractFlowableEngineEventListener} 监听
 * {@code ProcessInstanceEnded} 事件总线事件，在流程实例结束时把业务状态回写为最终结果）。
 * 两种挂接方式的对比：
 * <table border="1">
 *     <tr><th></th><th>本 demo（BPMN 声明式）</th><th>yudao（引擎事件总线）</th></tr>
 *     <tr><td>配置位置</td><td>写死在 BPMN XML 的 process 节点上</td><td>Java 代码注册到引擎的事件分发器，与 BPMN 解耦</td></tr>
 *     <tr><td>触发时机</td><td>end 事件（实例结束）</td><td>ProcessInstanceEnded（实例结束）——时机等价</td></tr>
 *     <tr><td>适用场景</td><td>单流程、学习阶段直观易懂</td><td>所有流程统一收口处理（yudao 是平台化封装）</td></tr>
 * </table>
 *
 * <p>【为什么能注入 Spring Bean】BPMN 里配置的是
 * {@code delegateExpression="${leaveProcessEndListener}"}（见 leave.bpmn20.xml）：
 * Flowable 与 Spring Boot 集成后，表达式解析走 Spring 的 BeanFactory，
 * {@code ${leaveProcessEndListener}} 会从 Spring 容器里按名字取 Bean——
 * 所以本类标了 {@code @Component("leaveProcessEndListener")} 并能 {@code @Resource} 注入 Mapper。
 * 如果 BPMN 里改用 {@code class="..."} 方式，引擎会自己 new 一个对象，无法享受 Spring 注入
 * （那是另一种脱离 Spring 时的写法，初学时容易在这里困惑）。
 *
 * <p>【事务特性】监听器在 complete 事务内同步执行：
 * 若本监听器抛异常，调用方 {@code TaskService.complete} 的事务整体回滚
 * （ACT_* 表的推进和业务表状态更新一起回滚），不会出现两边不一致。
 *
 * @author chenmeng
 */
@Slf4j
@Component("leaveProcessEndListener")
public class LeaveProcessEndListener implements ExecutionListener {

    /** 序列化版本号（ExecutionListener 部分实现可能需要序列化，这里声明保持规范） */
    private static final long serialVersionUID = 1L;

    @Resource
    private LeaveMapper leaveMapper;

    /**
     * 引擎回调方法：流程实例结束时执行。
     *
     * @param execution 当前执行（流程实例级别的 root execution，即常说的 Execution）
     */
    @Override
    public void notify(DelegateExecution execution) {
        // 1. 从流程实例上取 businessKey：发起流程时传入的业务表主键（leave_apply.id）
        //    对应底层列 ACT_RU_EXECUTION.BUSINESS_KEY_（流程实例级属性，结束后转入 ACT_HI_PROCINST.BUSINESS_KEY_）
        String businessKey = execution.getProcessInstanceBusinessKey();
        if (businessKey == null || businessKey.isEmpty()) {
            log.warn("流程实例 {} 结束时没有 businessKey，跳过业务状态回写", execution.getProcessInstanceId());
            return;
        }

        // 2. 读取流程变量 approved：每个审批人 complete 任务时写入（会覆盖前一个人的值），
        //    因此此刻读到的是"最后一个审批人"的结果——这正是拒绝分支能直达结束的原因
        Object approvedValue = execution.getVariable("approved");
        boolean approved = Boolean.TRUE.equals(approvedValue);

        // 3. 回写业务表状态：2-通过 / 3-拒绝。
        //    不用 selectById 再 updateById 两步，而是直接用条件更新一步到位，减少一次查询。
        Integer finalStatus = approved ? LeaveDO.STATUS_APPROVED : LeaveDO.STATUS_REJECTED;
        leaveMapper.update(null, Wrappers.<LeaveDO>lambdaUpdate()
                .eq(LeaveDO::getId, Long.valueOf(businessKey))
                .set(LeaveDO::getStatus, finalStatus));

        log.info("流程实例 {} 结束（businessKey={}，approved={}），业务状态已更新为：{}",
                execution.getProcessInstanceId(), businessKey, approvedValue,
                Objects.equals(finalStatus, LeaveDO.STATUS_APPROVED) ? "2-审批通过" : "3-审批拒绝");
    }
}
