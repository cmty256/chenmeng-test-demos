# demo20-workflow：Flowable 工作流学习 demo

> **Flowable 6.8.0**（`flowable-spring-boot-starter-process`）+ Spring Boot 2.7.17 + Java 11 + MySQL + MyBatis-Plus
>
> 定位：**脱离 yudao 封装，在轻量环境里用 Flowable 原生 API 跑通"请假审批"完整闭环**——
> 发起 → 待办 → 审批（通过/拒绝）→ 业务状态回写 → 历史查询 → 流程轨迹（流程图高亮数据）。
> 每个类、每个关键方法都有详细中文注释讲解原理，建议对照源码 + 数据库一起体验。
>
> 版本约束：Flowable 6.x 才支持 Spring Boot 2.x + Java 11；Flowable 7.x 要求 Spring Boot 3.x + Java 17，本工程**禁止**升级到 7.x。

---

## 一、流程全景（leave.bpmn20.xml）

```
开始(startLeave)
   │
   ▼
领导审批 leaderTask（assignee = ${leader}）
   │
   ▼
排他网关 gatewayLeader ──① ${!approved} 拒绝 ──────────► 结束 endLeave（业务状态=3 拒绝）
   │
   ├─② ${day > 3}：请假超过 3 天 ──► 总监审批 directorTask（${director}）
   │                                    │
   │                                    ▼
   │                               排他网关 gatewayDirector
   │                                    ├─ ${!approved} 拒绝 ──► 结束 endLeave
   │                                    └─ ${approved} ─────► HR 备案 hrTask ──► 结束 endLeave
   │
   └─③ ${approved}：3 天及以内 ──► HR 备案 hrTask（${hr}）──► 结束 endLeave
```

- **网关条件按 BPMN 里出线的声明顺序评估**：先判拒绝（`${!approved}`），再判长请假（`${day > 3}`），最后 `${approved}` 兜底；
- `day` / `leader` / `director` / `hr` / `approved` 都是**流程变量**：发起时传入，每次审批 `complete(taskId, {approved: xxx})` 时覆盖；
- **拒绝 = 网关分支直达结束节点**（业务状态 3-拒绝由流程结束监听器回写）——对照 yudao 的 `BpmTaskService.rejectTask`（终止流程语义），两者业务效果一致，本 demo 的拒绝路径直接画在 BPMN 里，流程图所见即所得。

---

## 二、环境准备

1. **建表**：执行本模块 `sql/demo20_workflow.sql`（可重复执行）：
   - 库沿用本工程共用的 `chenmeng_test`（与其他 demo 模块共用，靠表名区分）；
   - 建业务表 `leave_apply`（**表名不用 `leave`**：LEAVE 是 MySQL 保留字，建表/查询都要加反引号，教学工程刻意避开）；
   - **Flowable 引擎的 30+ 张 `ACT_*` 表不需要手动建**——应用首次启动时引擎按 `flowable.database-schema-update: true` 自动建表。
2. **改连接配置**：`src/main/resources/application-dev.yml` 中的数据源 `username` / `password` 按本地 MySQL 修改（默认 `localhost:3306/chenmeng_test`，root/123456 占位）。

## 三、启动

```powershell
# 在项目根目录（注意本工程需要 JDK 11，先切 JAVA_HOME）
mvn -pl demo20-workflow -am compile
mvn -pl demo20-workflow spring-boot:run
```

- 启动日志看到 `【demo20-workflow】, 启动成功！！` 即可；
- **首次启动会自动建 30+ 张 `ACT_` 开头的表，属于正常现象**（Flowable 引擎自管，禁止手工改这些表的数据）；
- 接口文档：`http://127.0.0.1:9120/workflow/doc.html`（knife4j）；
- 服务地址：`http://127.0.0.1:9120/workflow`（端口延续工程约定：demoN → 91NN，本模块 = 9120）。

---

## 四、接口清单

| 接口 | 说明 | 核心引擎 API |
|---|---|---|
| `POST /leave/create` | 发起请假（userId、reason、day、leader、director、hr） | `runtimeService.startProcessInstanceByKey` |
| `GET /leave/list` | 请假单列表（观察 status 随流程变化） | —（只查业务表） |
| `GET /task/todo?assignee=xxx` | 查某人待办 | `taskService.createTaskQuery().taskAssignee` |
| `POST /task/complete` | 审批（taskId、approved、comment） | `taskService.addComment` + `taskService.complete` |
| `GET /process/history?processInstanceId=xxx` | 历史任务（含审批意见） | `historyService.createHistoricTaskInstanceQuery` |
| `GET /process/track?processInstanceId=xxx` | 流程轨迹节点（流程图高亮数据） | `historyService.createHistoricActivityInstanceQuery`（`finished()` / `unfinished()`） |

---

## 五、推荐体验步骤（学习重点）

> 全程开着数据库客户端，每调一个接口就去库表看一眼——**工作流的本质就是引擎在 `ACT_*` 表上的数据流转**。

### 场景 A：短请假（day=2）全链路通过

1. **发起**：`POST /leave/create`
   ```json
   { "userId": "zhangsan", "reason": "家里有事", "day": 2, "leader": "lisi", "director": "wangwu", "hr": "hr001" }
   ```
   响应里记下 `processInstanceId`。**看库**：
   - `leave_apply`：新增一行，`status=1`（审批中），`process_instance_id` 已回填；
   - `ACT_RE_PROCDEF`：早就有 `KEY_='leave'` 的流程定义（启动时自动部署的）；
   - `ACT_RU_EXECUTION`：新增流程实例行，**`BUSINESS_KEY_` = 业务表主键**（businessKey 桥）；
   - `ACT_RU_TASK`：新增"领导审批"任务，`ASSIGNEE_='lisi'`；
   - `ACT_HI_PROCINST` / `ACT_HI_ACTINST` / `ACT_HI_TASKINST`：**同步**出现记录（历史是实时写的，不是结束后才写！此时历史任务的 `END_TIME_` 为 NULL）。

2. **查待办**：`GET /task/todo?assignee=lisi` → 拿到 `taskId`。
   **看库**：`ACT_RU_TASK` 对应行还在（活任务才在 RU 表）。

3. **领导审批通过**：`POST /task/complete`
   ```json
   { "taskId": "上一步的taskId", "approved": true, "comment": "同意，注意工作交接" }
   ```
   **看库**：
   - `ACT_RU_TASK`：原"领导审批"行**被删除**，新增"HR 备案"行（`ASSIGNEE_='hr001'`）——因为 `day=2 <= 3`，网关走了 `${approved}` 分支，**跳过了总监**；
   - `ACT_HI_TASKINST`：领导审批行补上 `END_TIME_`；
   - `ACT_HI_COMMENT`：新增审批意见（`FULL_MSG_` 存完整意见）；
   - `ACT_HI_ACTINST`：`leaderTask`、`gatewayLeader`、`hrTask` 等活动节点记录依次出现。

4. **HR 备案**：`GET /task/todo?assignee=hr001` → `POST /task/complete`（`approved=true`）。
   **看库（最关键的一步）**：
   - `ACT_RU_EXECUTION` / `ACT_RU_TASK`：**该流程的运行时数据被引擎全部删除**（活流程数据"消失"）；
   - `ACT_HI_PROCINST`：流程实例行补 `END_TIME_`、`END_ACT_ID_='endLeave'`；
   - `leave_apply`：`status` 变为 **2-审批通过**——不是审批接口直接改的，而是**流程结束监听器**（`LeaveProcessEndListener`，挂在 process 的 end 事件上）回写的。

5. **验证历史与轨迹**（流程已结束照样能查——历史永存）：
   - `GET /process/history?processInstanceId=xxx`：2 条任务全有 `endTime` + 各自的 `comment`；
   - `GET /process/track?processInstanceId=xxx`：`startLeave → leaderTask → gatewayLeader → hrTask → endLeave`，全部 `finished=true`。把返回的 `activityId` 对照 BPMN 文件里的节点 id——这就是前端流程图高亮的全部原理。
   - **看库**：`ACT_HI_VARINST` 里仍能查到 `day`、`approved` 等历史变量。

### 场景 B：长请假（day=5，多一级总监审批）

重复场景 A，但发起时 `day=5`：领导通过后，网关走 `${day > 3}` 分支，待办先到 `wangwu`（总监审批），再到 `hr001`。对照 `ACT_HI_ACTINST` 看多出来的 `directorTask`、`gatewayDirector` 节点记录。

### 场景 C：拒绝（任一环节 approved=false）

发起一张新单，审批时传 `"approved": false`：流程经网关第一条出线 `${!approved}` **直达结束**，`leave_apply.status` 变为 **3-审批拒绝**，后续节点不再产生任务。

### 场景 D：故意踩坑（体验引擎异常）

- 拿已完成的 `taskId` 再审批一次 → 引擎查不到活任务，接口返回"任务不存在或已完成"；
- 乱传不存在的 `processInstanceId` 查历史 → `FlowableObjectNotFoundException` 被 `GlobalExceptionHandler` 翻译成友好错误。

---

## 六、BPMN 文件与自动部署机制（"发布 = 部署"）

- 文件位置：`src/main/resources/processes/leave.bpmn20.xml`；
- Spring Boot 集成 Flowable 后，**启动时自动扫描 `classpath:processes/` 下的 BPMN 并部署**（`flowable.check-process-definitions: true`，见 application.yml）；
- 部署动作的落库位置：
  - `ACT_RE_DEPLOYMENT`：部署记录；
  - `ACT_RE_PROCDEF`：流程定义行，`KEY_='leave'`（= BPMN 的 `process id`），**每次修改 BPMN 并重启，`VERSION_` 自动 +1**，历史版本保留；
  - `ACT_GE_BYTEARRAY`：BPMN XML 原文；
- `startProcessInstanceByKey("leave")` 按 KEY 取**最新版本**的定义来创建实例——这就是 yudao 里"流程定义管理 → 发布新版本"的底层机制，只不过 yudao 的发布入口是 `RepositoryService.createDeployment()` 等 API（页面按钮 → API → 本质相同）。

## 七、businessKey 与业务表集成模式

```
leave_apply（业务表）                       ACT_*（引擎表）
┌──────────────────────┐                  ┌──────────────────────────────┐
│ id  ←─────────────── businessKey ──────→│ ACT_RU_EXECUTION.BUSINESS_KEY_ │
│ process_instance_id ←─────── 回写 ──────│ ACT_RU_EXECUTION.ID_           │
│ status（监听器回写）  │                  │ ACT_RU_TASK.ASSIGNEE_ / ...    │
└──────────────────────┘                  └──────────────────────────────┘
```

- 发起三步曲（`LeaveService.createLeave`）：**插业务表 → 启动流程（businessKey=业务主键）→ 回写流程实例 id**，同一事务；
- 待办组装（`LeaveService.listTodoTask`）：任务上没有 businessKey（它是**流程实例级**属性），需按 `processInstanceId` 反查一次流程实例，再用 businessKey 反查业务表，把"审的是谁的什么单"带上；
- 业务状态（status）只在**流程结束监听器**里回写（2-通过 / 3-拒绝）——业务状态的唯一真相来源是流程本身。

## 八、概念对照表：本 demo ↔ yudao BPM

> yudao（ruoyi-vue-pro）的 `yudao-spring-boot-starter-bpm` 同样基于 `flowable-spring-boot-starter-process`，
> 只是在原生 API 外封装了"用户/角色/部门解析、权限、多租户、消息通知"等平台能力。

| 本 demo（Flowable 原生） | yudao 封装 | 说明 |
|---|---|---|
| `runtimeService.startProcessInstanceByKey(key, businessKey, vars)` | `BpmProcessInstanceApi.createProcessInstance(userId, reqVO)` | 发起流程，businessKey=业务主键 |
| `taskService.createTaskQuery().taskAssignee(...)` | `BpmTaskApi.getTaskTaskList(...)` / 待办分页 | 查待办 |
| `taskService.complete(taskId, {approved})` | `BpmTaskApi.approveTask(userId, reqVO)` | 通过 |
| 网关出线 `${!approved}` 直达结束 | `BpmTaskService.rejectTask(...)`（终止流程语义） | 拒绝：殊途同归 |
| `taskService.addComment(taskId, pid, comment)` | 审批意见 / reason 记录 | 意见存 `ACT_HI_COMMENT` |
| `ExecutionListener`（process end 事件回写业务状态） | `BpmProcessInstanceStatusEventListener` | 流程结束 → 业务状态落库 |
| `historyService.createHistoricActivityInstanceQuery()` 的 finished/unfinished | `BpmProcessInstanceService` 流程图高亮 | 前端高亮的数据来源 |
| BPMN 放 `processes/` 目录自动部署 | 流程定义管理 → 发布（`RepositoryService`） | 发布 = 部署 |
| `leave_apply` + `LeaveDO` + businessKey 桥 | `BpmOALeave` 模式 | 业务表集成范式 |

## 九、常用引擎表速查

| 表 | 含义 | 体验时机 |
|---|---|---|
| `ACT_RE_PROCDEF` | 流程定义（KEY_ / VERSION_） | 启动后：确认自动部署、版本 |
| `ACT_RU_EXECUTION` | 运行时流程实例（BUSINESS_KEY_） | 发起后新增，流程结束**删除** |
| `ACT_RU_TASK` | 运行时任务（ASSIGNEE_） | 有待办时在，complete 后删除 |
| `ACT_HI_PROCINST` | 历史流程实例 | 全程在，结束时补 `END_TIME_` |
| `ACT_HI_TASKINST` | 历史任务 | **任务创建时即写入**，进行中 `END_TIME_` 为 NULL |
| `ACT_HI_ACTINST` | 历史活动（每个走过的节点一行） | 流程轨迹 / 图高亮数据源 |
| `ACT_HI_COMMENT` | 审批意见 | `addComment` 后 |
| `ACT_HI_VARINST` | 历史变量 | 流程结束后仍可查 `day` / `approved` |

> RU（runtime）与 HI（history）的本质区别：**运行时表只存"活着的"，流程结束即删除；历史表实时写入、永不删除**。
> 所以"进行中"要查 RU 表，"回溯/审计/高亮"要查 HI 表。

## 十、常见问题

- **启动报连接失败**：检查 MySQL 是否启动、`application-dev.yml` 的账号密码是否正确、库是否已建；
- **首次启动慢**：引擎在自动建 `ACT_*` 表，属正常现象；
- **改了 BPMN 不生效**：重启应用即触发重新部署（版本 +1）；老流程实例仍走老版本定义，新实例走新版本——版本化是 BPMN 的内置能力；
- **审批意见没返回**：检查调 complete 时是否传了 `comment`（意见存在 `ACT_HI_COMMENT.FULL_MSG_`）；
- **流程结束后 `ACT_RU_TASK` 查不到数据**：正常，运行时数据已被引擎删除，改用 `/process/history`。
