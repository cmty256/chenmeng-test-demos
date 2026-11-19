package com.chenmeng.project.service;

import com.chenmeng.project.entity.LeaveDO;
import com.chenmeng.project.mapper.LeaveMapper;
import com.chenmeng.project.vo.HistoryTaskRespVO;
import com.chenmeng.project.vo.LeaveCreateReqVO;
import com.chenmeng.project.vo.LeaveRespVO;
import com.chenmeng.project.vo.ProcessTrackNodeVO;
import com.chenmeng.project.vo.TaskCompleteReqVO;
import com.chenmeng.project.vo.TaskRespVO;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 请假业务服务：把"业务表"和"工作流引擎"粘合起来的全部代码都在这里。
 *
 * <p>【总纲：业务表集成模式（对应 yudao 的 BpmOALeave 模式）】
 * <pre>
 *   发起时序（本服务 {@link #createLeave}）：
 *     ① LeaveMapper.insert      —— 业务数据先落业务表 leave_apply（status=1 审批中）
 *     ② RuntimeService.startProcessInstanceByKey("leave", businessKey=业务表主键, 流程变量)
 *        —— 引擎同步执行：建流程实例(ACT_RU_EXECUTION) → 创建第一个任务(ACT_RU_TASK) → 写历史(ACT_HI_*)
 *     ③ LeaveMapper.updateById  —— 把流程实例 id 回写业务表
 *   三步在同一个事务里：任何一步失败，业务表和引擎表一起回滚（Spring 集成 Flowable 的核心保证）。
 *
 *   审批时序（本服务 {@link #completeTask}）：
 *     ① TaskService.addComment  —— 审批意见写 ACT_HI_COMMENT
 *     ② TaskService.complete(taskId, {approved: ...})
 *        —— 引擎同步执行：删 ACT_RU_TASK 当前任务 → 网关评估条件表达式 → 推进/结束 → 删或更新 ACT_RU_EXECUTION
 *     ③ 若走到 endEvent：流程结束监听器回调，把业务表 status 改成 2/3
 * </pre>
 *
 * <p>【与 yudao 的 API 对照】
 * <table border="1">
 *     <tr><th>本 demo（Flowable 原生 API）</th><th>yudao 封装层</th></tr>
 *     <tr><td>runtimeService.startProcessInstanceByKey(key, businessKey, vars)</td><td>BpmProcessInstanceApi.createProcessInstance(userId, reqVO)</td></tr>
 *     <tr><td>taskService.createTaskQuery().taskAssignee(...)</td><td>BpmTaskApi.getTaskTaskList / TaskService.getTodoTaskPage</td></tr>
 *     <tr><td>taskService.complete(taskId, vars)</td><td>BpmTaskApi.approveTask(userId, reqVO)</td></tr>
 *     <tr><td>taskService.addComment(...)</td><td>BpmTaskService.approveTask 内部的 addComment + reason 记录</td></tr>
 *     <tr><td>historyService.createHistoricXxxQuery()</td><td>BpmProcessInstanceService 的流程详情/历史节点查询</td></tr>
 *     <tr><td>ExecutionListener（流程结束回写状态）</td><td>BpmProcessInstanceStatusEventListener（事件总线，同样在流程结束时回写业务状态）</td></tr>
 * </table>
 * yudao 的封装本质 = 在这些原生 API 外面加了"用户/角色/部门解析、权限校验、消息通知、多租户、异步"等平台能力，
 * 核心流转逻辑与本类完全同构——理解了本类，yudao BPM 的源码就只剩"平台工程"这一层壳。
 *
 * @author chenmeng
 */
@Slf4j
@Service
public class LeaveService {

    /**
     * 流程定义 key：必须与 leave.bpmn20.xml 里 <process id="leave"> 完全一致，
     * 引擎按 key 定位"最新版本的流程定义"（BPMN 每次自动部署版本号 +1，同名 key 始终取最新版）。
     */
    private static final String PROCESS_DEFINITION_KEY = "leave";

    @Resource
    private LeaveMapper leaveMapper;

    /**
     * 运行时服务：启动流程实例、流程变量读写（数据在 ACT_RU_* 表）。
     * 流程实例一旦结束，运行时表里的数据会被引擎删除，所以"活流程"才查得到。
     */
    @Resource
    private RuntimeService runtimeService;

    /**
     * 任务服务：待办查询、完成任务、写审批意见（数据在 ACT_RU_TASK / ACT_HI_TASKINST / ACT_HI_COMMENT 表）。
     */
    @Resource
    private TaskService taskService;

    /**
     * 历史服务：查询已发生的一切（数据在 ACT_HI_* 表，只增不删）。
     * 不管流程活没活，历史都在——审计、回溯、流程图高亮全靠它。
     */
    @Resource
    private HistoryService historyService;

    // ====================================================================
    // 1. 发起请假（业务表 + 启动流程）
    // ====================================================================

    /**
     * 发起请假：业务表集成模式的三步曲（插业务表 → 启动流程 → 回写流程实例 id）。
     *
     * <p>体验时对照数据库看：
     * <ul>
     *     <li>leave_apply 多一行（status=1，process_instance_id 已回填）；</li>
     *     <li>ACT_RU_EXECUTION 多一行流程实例（BUSINESS_KEY_ = 业务表主键）；</li>
     *     <li>ACT_RU_TASK 多一行"领导审批"任务（ASSIGNEE_ = leader 变量值）；</li>
     *     <li>ACT_HI_PROCINST / ACT_HI_ACTINST / ACT_HI_TASKINST 同步出现记录（历史是实时写的，不是结束后才写）。</li>
     * </ul>
     *
     * @param reqVO 发起请求（请假人、事由、天数、各级审批人）
     * @return 创建成功的请假单（含业务 id、流程实例 id，后续体验会用到）
     */
    @Transactional(rollbackFor = Exception.class)
    public LeaveRespVO createLeave(LeaveCreateReqVO reqVO) {
        // ─── 第 ① 步：业务数据先落业务表 ───
        // 此刻还不认识流程引擎，先保证业务单据存在；MyBatis-Plus 配置 IdType.AUTO，
        // insert 后数据库自增主键会回填到 leaveDO.getId()
        LeaveDO leaveDO = new LeaveDO();
        leaveDO.setUserId(reqVO.getUserId());
        leaveDO.setReason(reqVO.getReason());
        leaveDO.setDay(reqVO.getDay());
        leaveDO.setStatus(LeaveDO.STATUS_APPROVING);
        leaveDO.setCreateTime(LocalDateTime.now());
        leaveMapper.insert(leaveDO);

        // ─── 第 ② 步：组装流程变量并启动流程实例 ───
        // 流程变量的作用域是"整个流程实例"（存在 ACT_RU_VARIABLE 表，流程结束后可在 ACT_HI_VARINST 查到）：
        //   - day / reason：业务数据，网关表达式 ${day > 3} 直接读取；
        //   - leader / director / hr：审批人，BPMN 里 flowable:assignee="${leader}" 在"创建任务那一刻"解析；
        //   - approved：先给个"通过"的初始值，之后每个审批人 complete 时用新的值覆盖（拒绝语义的基础）
        Map<String, Object> processVariables = new HashMap<>(8);
        processVariables.put("day", reqVO.getDay());
        processVariables.put("reason", reqVO.getReason());
        processVariables.put("leader", reqVO.getLeader());
        processVariables.put("director", reqVO.getDirector());
        processVariables.put("hr", reqVO.getHr());
        processVariables.put("approved", Boolean.TRUE);

        // businessKey = 业务表主键：引擎表与业务表的"单向桥"
        // （引擎侧只存这个字符串，不感知业务表结构，保持两者解耦）
        String businessKey = String.valueOf(leaveDO.getId());
        // startProcessInstanceByKey(key, businessKey, variables)：
        // 引擎同步执行"建实例 + 走到第一个 userTask 停下"，方法返回时"领导审批"任务已经创建好了
        ProcessInstance processInstance = runtimeService
                .startProcessInstanceByKey(PROCESS_DEFINITION_KEY, businessKey, processVariables);

        // ─── 第 ③ 步：流程实例 id 回写业务表 ───
        // 与 businessKey 方向相反：业务表也记下流程实例 id，"从业务单正向跳转流程详情"就靠它
        leaveDO.setProcessInstanceId(processInstance.getId());
        leaveMapper.updateById(leaveDO);

        log.info("【请假单创建成功】业务主键={}，流程实例={}，businessKey={}",
                leaveDO.getId(), processInstance.getId(), businessKey);
        return toLeaveRespVO(leaveDO);
    }

    /**
     * 请假单列表：直接查业务表（不需要碰引擎）。
     *
     * <p>这就是"业务状态"与"流程状态"分离的好处——列表页只依赖业务表，
     * 状态字段由流程结束监听器维护，随时能观察到它随流程推进而变化。
     */
    public List<LeaveRespVO> listLeave() {
        return leaveMapper.selectList(null).stream()
                .map(this::toLeaveRespVO)
                .collect(Collectors.toList());
    }

    // ====================================================================
    // 2. 待办查询（ACT_RU_TASK）
    // ====================================================================

    /**
     * 查某人的待办任务列表。
     *
     * <p>待办查询只查 ACT_RU_TASK（运行时任务表）：
     * 任务被 complete 的瞬间，引擎把这一行从 RU 表删除（对"待办"来说完成的任务就不该再出现），
     * 同时 ACT_HI_TASKINST 里的对应记录补上 END_TIME_ —— 所以待办和历史查的是两张表、两种语义。
     *
     * <p>每条待办再按 businessKey 反查业务表，把"审的是谁的什么单"带上：
     * 这一步就是 businessKey 的日常用法——审批人视角的页面需要业务信息，而引擎表里没有。
     *
     * @param assignee 办理人（对应发起时传的 leader / director / hr）
     * @return 待办列表（引擎字段 + 业务字段组装）
     */
    public List<TaskRespVO> listTodoTask(String assignee) {
        // createTaskQuery() 链式查询 = 引擎版的 MyBatis-Plus Wrapper：
        // taskAssignee 过滤 ASSIGNEE_ 列，orderByTaskCreateTime 按创建时间排序
        List<Task> taskList = taskService.createTaskQuery()
                .taskAssignee(assignee)
                .orderByTaskCreateTime().desc()
                .list();
        if (CollectionUtils.isEmpty(taskList)) {
            return new ArrayList<>();
        }

        // ── 关键知识点：businessKey 在"流程实例"上，不在 Task 上 ──
        // Task 接口（org.flowable.task.api.Task）没有 getBusinessKey() 方法——
        // businessKey 是流程实例级属性，存在 ACT_RU_EXECUTION.BUSINESS_KEY_ 列；
        // 所以要拿任务的 businessKey，需用 task.getProcessInstanceId() 反查一次流程实例。
        // （本 demo 每个流程同一时刻最多 1 个待办任务，逐实例查询即可；
        //   生产环境任务量大时，应改用 TaskQuery.processInstanceBusinessKey(...) 或批量查询避免 N+1）
        Map<String, String> businessKeyByProcessInstanceId = new HashMap<>(taskList.size());
        for (Task task : taskList) {
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                    .processInstanceId(task.getProcessInstanceId())
                    .singleResult();
            if (processInstance != null) {
                businessKeyByProcessInstanceId.put(task.getProcessInstanceId(), processInstance.getBusinessKey());
            }
        }

        // 收集每个任务的 businessKey，批量反查业务表（避免循环里逐条查库）
        List<Long> leaveIds = businessKeyByProcessInstanceId.values().stream()
                .filter(StringUtils::hasText)
                .distinct()
                .map(Long::valueOf)
                .collect(Collectors.toList());
        Map<Long, LeaveDO> leaveMap = leaveIds.isEmpty() ? new HashMap<>() :
                leaveMapper.selectBatchIds(leaveIds).stream()
                        .collect(Collectors.toMap(LeaveDO::getId, Function.identity()));

        // 组装：引擎字段 + 业务字段
        return taskList.stream()
                .map(task -> {
                    TaskRespVO taskRespVO = new TaskRespVO();
                    taskRespVO.setTaskId(task.getId());
                    taskRespVO.setTaskName(task.getName());
                    taskRespVO.setAssignee(task.getAssignee());
                    taskRespVO.setTaskCreateTime(toLocalDateTime(task.getCreateTime()));
                    taskRespVO.setProcessInstanceId(task.getProcessInstanceId());
                    String taskBusinessKey = businessKeyByProcessInstanceId.get(task.getProcessInstanceId());
                    taskRespVO.setBusinessKey(taskBusinessKey);
                    if (StringUtils.hasText(taskBusinessKey)) {
                        LeaveDO leaveDO = leaveMap.get(Long.valueOf(taskBusinessKey));
                        if (leaveDO != null) {
                            taskRespVO.setLeaveUserId(leaveDO.getUserId());
                            taskRespVO.setLeaveReason(leaveDO.getReason());
                            taskRespVO.setLeaveDay(leaveDO.getDay());
                        }
                    }
                    return taskRespVO;
                })
                .collect(Collectors.toList());
    }

    // ====================================================================
    // 3. 审批（写意见 + 完成任务，驱动网关分支）
    // ====================================================================

    /**
     * 审批：记录意见 → 完成任务 → 引擎自动走网关分支。
     *
     * <p>complete 之后发生什么（引擎同步执行，方法返回前全部完成）：
     * <ol>
     *     <li>当前任务从 ACT_RU_TASK 删除，ACT_HI_TASKINST 对应记录补 END_TIME_；</li>
     *     <li>流程变量 approved 被本次入参覆盖（每次 complete 传的变量写入流程实例变量表）；</li>
     *     <li>引擎走到排他网关 exclusiveGateway，按 BPMN 里出线的声明顺序逐条评估条件表达式：
     *         ${!approved} → ${day > 3} → ${approved}，命中一条立即走那条出线；</li>
     *     <li>approved=false 命中第一条：直达 endEvent，流程实例结束
     *         → ACT_RU_* 里的本流程数据全部删除 → 监听器把业务表 status 改成 3-拒绝；</li>
     *     <li>approved=true 且 day>3：创建"总监审批"任务停住；
     *         approved=true 且 day<=3：创建"HR 备案"任务停住（短路径跳过了总监审批）。</li>
     * </ol>
     *
     * <p>关于"拒绝"（yudao 的 reject 语义）：本 demo 用"网关分支直通结束"实现，
     * yudao 的 BpmTaskService.rejectTask 则是"把流程实例直接结束掉"（运行时终止），两者业务效果一致——
     * 单据立刻变为"拒绝"终态，不再产生新任务。网关分支方案的优点是拒绝路径画在 BPMN 里，流程图所见即所得。
     *
     * @param reqVO 审批请求（taskId + approved + comment）
     */
    @Transactional(rollbackFor = Exception.class)
    public void completeTask(TaskCompleteReqVO reqVO) {
        // 1. 校验任务存在（拿着已完成的 taskId 再调一次，会在这里被拦下——
        //    因为已完成的任务已从 ACT_RU_TASK 删除，createTaskQuery 只查活任务）
        Task task = taskService.createTaskQuery()
                .taskId(reqVO.getTaskId())
                .singleResult();
        if (task == null) {
            throw new IllegalStateException("任务不存在或已完成: " + reqVO.getTaskId());
        }

        // 2. 记录审批意见：写入 ACT_HI_COMMENT 表（永不删除，历史任务接口按 taskId 查回）。
        //    对照：yudao 审批时的"审批意见/备注"也是走 TaskService.addComment
        if (StringUtils.hasText(reqVO.getComment())) {
            taskService.addComment(task.getId(), task.getProcessInstanceId(), reqVO.getComment());
        }

        // 3. 完成任务并写入流程变量 approved。
        //    complete(taskId, variables) 的第二个参数会在"离开任务前"合并进流程实例变量，
        //    网关评估表达式时读到的就是这次传入的最新值
        Map<String, Object> completeVariables = new HashMap<>(2);
        completeVariables.put("approved", reqVO.getApproved());
        taskService.complete(task.getId(), completeVariables);

        // 4. 教学增强：complete 返回后流程到底走到哪了？
        //    拿流程实例 id 去"运行时"再查一次：查得到=流程还活着（停在新节点），查不到=流程已结束
        ProcessInstance stillRunning = runtimeService.createProcessInstanceQuery()
                .processInstanceId(task.getProcessInstanceId())
                .singleResult();
        if (stillRunning == null) {
            log.info("【审批完成】任务 {} 已完成，approved={}，流程 {} 已走到 endEvent 结束（业务状态由监听器回写）",
                    task.getName(), reqVO.getApproved(), task.getProcessInstanceId());
        } else {
            // 流程未结束，看看当前停在了哪个节点（当前活动节点，不含网关/连线这类瞬时节点）
            List<Task> nextTasks = taskService.createTaskQuery()
                    .processInstanceId(task.getProcessInstanceId())
                    .list();
            List<String> nextTaskNames = nextTasks.stream()
                    .map(nextTask -> nextTask.getName() + "(" + nextTask.getAssignee() + ")")
                    .collect(Collectors.toList());
            log.info("【审批完成】任务 {} 已完成，approved={}，流程推进到下一节点: {}",
                    task.getName(), reqVO.getApproved(), nextTaskNames);
        }
    }

    // ====================================================================
    // 4. 历史查询（ACT_HI_TASKINST / ACT_HI_COMMENT）
    // ====================================================================

    /**
     * 查指定流程实例的历史任务（含进行中的任务，含每步审批意见）。
     *
     * <p>常见误解澄清——"历史任务 = 已完成的任务"？不对！
     * ACT_HI_TASKINST 在任务创建时就写入（END_TIME_ 为 null），完成时才补 END_TIME_，
     * 所以本接口刚发起请假就查得到"领导审批"（进行中）；
     * 全部审批结束后再查，每条记录都有 END_TIME_，才是完整审计链。
     *
     * <p>流程结束后 ACT_RU_EXECUTION / ACT_RU_TASK 里的数据已被引擎删除，
     * 但本接口依然查得到——"运行时数据会消失、历史数据永留存"就是 RU 与 HI 表的本质区别。
     *
     * @param processInstanceId 流程实例 id
     * @return 按开始时间升序的历史任务列表
     */
    public List<HistoryTaskRespVO> listHistoryTask(String processInstanceId) {
        // 先校验流程实例存在（不管活的还是已结束的，历史表里都应该有）
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
                .processInstanceId(processInstanceId)
                .singleResult();
        if (historicProcessInstance == null) {
            throw new IllegalStateException("流程实例不存在: " + processInstanceId);
        }

        // 历史任务查询（ACT_HI_TASKINST）：按开始时间升序 = 审批发生顺序
        List<HistoricTaskInstance> historicTaskList = historyService.createHistoricTaskInstanceQuery()
                .processInstanceId(processInstanceId)
                .orderByHistoricTaskInstanceStartTime().asc()
                .list();

        // 审批意见（ACT_HI_COMMENT）：taskService 查意见，按 taskId 映射到上面每条任务
        Map<String, String> commentMap = taskService.getProcessInstanceComments(processInstanceId).stream()
                .filter(comment -> StringUtils.hasText(comment.getTaskId()))
                .collect(Collectors.toMap(
                        org.flowable.engine.task.Comment::getTaskId,
                        org.flowable.engine.task.Comment::getFullMessage,
                        (firstComment, secondComment) -> firstComment + "；" + secondComment));

        return historicTaskList.stream()
                .map(historicTask -> {
                    HistoryTaskRespVO historyTaskRespVO = new HistoryTaskRespVO();
                    historyTaskRespVO.setTaskId(historicTask.getId());
                    historyTaskRespVO.setTaskName(historicTask.getName());
                    historyTaskRespVO.setAssignee(historicTask.getAssignee());
                    historyTaskRespVO.setStartTime(toLocalDateTime(historicTask.getStartTime()));
                    historyTaskRespVO.setEndTime(toLocalDateTime(historicTask.getEndTime()));
                    historyTaskRespVO.setComment(commentMap.get(historicTask.getId()));
                    return historyTaskRespVO;
                })
                .collect(Collectors.toList());
    }

    // ====================================================================
    // 5. 流程轨迹（ACT_HI_ACTINST：流程图高亮的数据来源）
    // ====================================================================

    /**
     * 查指定流程实例的"流程轨迹"：从开始到当前，每个走过的节点一行数据。
     *
     * <p>数据来自 ACT_HI_ACTINST（历史活动实例表），"活动"= BPMN 里的各种节点
     * （startEvent / userTask / exclusiveGateway / endEvent 全都算），每进入一个节点插一行，
     * 离开时补 END_TIME_。
     *
     * <p>本方法专门用 finished() / unfinished() 两次查询演示这两个过滤条件：
     * <ul>
     *     <li>finished()：已走完的节点 → 前端流程图上涂"绿色（走过的路）"；</li>
     *     <li>unfinished()：当前停留的节点 → 前端流程图上"高亮闪烁（你在这里）"。</li>
     * </ul>
     * 两次结果合并按时间排序返回，对照 activityId 与 BPMN 节点 id，
     * 即可完全复现 yudao（或任何工作流前端）"流程图高亮"的实现原理。
     *
     * @param processInstanceId 流程实例 id
     * @return 按时间升序的轨迹节点列表
     */
    public List<ProcessTrackNodeVO> trackProcess(String processInstanceId) {
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
                .processInstanceId(processInstanceId)
                .singleResult();
        if (historicProcessInstance == null) {
            throw new IllegalStateException("流程实例不存在: " + processInstanceId);
        }

        // 已完成的节点（走过的路）：END_TIME_ 非空
        List<HistoricActivityInstance> finishedActivities = historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(processInstanceId)
                .finished()
                .list();

        // 未完成的节点（当前停留处）：END_TIME_ 为空。流程结束后不会有未完成节点
        List<HistoricActivityInstance> unfinishedActivities = historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(processInstanceId)
                .unfinished()
                .list();

        // 合并两组并按进入时间排序，还原完整轨迹
        List<HistoricActivityInstance> allActivities = new ArrayList<>(finishedActivities.size() + unfinishedActivities.size());
        allActivities.addAll(finishedActivities);
        allActivities.addAll(unfinishedActivities);
        allActivities.sort(Comparator.comparing(
                HistoricActivityInstance::getStartTime,
                Comparator.nullsLast(Comparator.naturalOrder())));

        return allActivities.stream()
                .map(activity -> {
                    ProcessTrackNodeVO trackNode = new ProcessTrackNodeVO();
                    trackNode.setActivityId(activity.getActivityId());
                    trackNode.setActivityName(activity.getActivityName());
                    trackNode.setActivityType(activity.getActivityType());
                    trackNode.setAssignee(activity.getAssignee());
                    trackNode.setStartTime(toLocalDateTime(activity.getStartTime()));
                    trackNode.setEndTime(toLocalDateTime(activity.getEndTime()));
                    // 6.8.0 的 HistoricActivityInstance 没有暴露 isFinished() 方法，
                    // 用 END_TIME_ 是否非空等价判断：进入节点时插行（START_TIME_ 有值），
                    // 离开节点时补 END_TIME_，所以 endTime == null 即"当前停留的节点"
                    trackNode.setFinished(activity.getEndTime() != null);
                    return trackNode;
                })
                .collect(Collectors.toList());
    }

    // ====================================================================
    // 私有工具方法
    // ====================================================================

    /**
     * Flowable 的 API 返回 java.util.Date（历史 API 设计如此），
     * 本工程 VO 统一用 LocalDateTime，这里做一次时区安全的转换。
     */
    private LocalDateTime toLocalDateTime(Date date) {
        if (date == null) {
            return null;
        }
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * DO → 响应 VO 转换（含状态中文描述，方便直接观察）。
     */
    private LeaveRespVO toLeaveRespVO(LeaveDO leaveDO) {
        LeaveRespVO leaveRespVO = new LeaveRespVO();
        leaveRespVO.setId(leaveDO.getId());
        leaveRespVO.setUserId(leaveDO.getUserId());
        leaveRespVO.setReason(leaveDO.getReason());
        leaveRespVO.setDay(leaveDO.getDay());
        leaveRespVO.setStatus(leaveDO.getStatus());
        leaveRespVO.setStatusDesc(LeaveRespVO.statusToDesc(leaveDO.getStatus()));
        leaveRespVO.setProcessInstanceId(leaveDO.getProcessInstanceId());
        leaveRespVO.setCreateTime(leaveDO.getCreateTime());
        return leaveRespVO;
    }
}
