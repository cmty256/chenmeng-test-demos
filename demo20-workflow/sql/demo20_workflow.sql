-- ============================================================================
-- demo20-workflow 数据库初始化脚本（Flowable 工作流学习 demo）
--
-- 使用说明：
--   1. 本脚本【可重复执行】：建表用 IF NOT EXISTS 防重，重复执行不会报错、不丢数据；
--   2. 本脚本只建业务表 leave_apply；Flowable 引擎的 30+ 张 ACT_ 表【不需要手动建】——
--      应用首次启动时引擎按 flowable.database-schema-update: true 配置自动建表（ACT_RE_* / ACT_RU_* / ACT_HI_* / ACT_GE_* 等）；
--   3. 库 chenmeng_test 与本工程其他 demo 模块共用，靠表名区分：
--      业务表 leave_apply、引擎表 ACT_* 均有独立前缀，与其他模块的表互不冲突；
--   4. 修改 application-dev.yml 中的数据库连接信息后，启动应用即可开始体验。
--
-- 引擎表前缀速查（详见模块 README.md 的"推荐体验步骤"）：
--   ACT_RE_*  repository：流程定义（部署了什么流程、版本）
--   ACT_RU_*  runtime   ：运行时（活着的流程实例/任务/变量，流程结束即删除）
--   ACT_HI_*  history   ：历史（任务/节点/变量/意见，只增不删，审计和高亮全靠它）
--   ACT_GE_*  general   ：通用（BPMN XML 资源文件内容就存在 ACT_GE_BYTEARRAY）
-- ============================================================================

-- ----------------------------------------------------------------------------
-- 1. 共用库（本工程约定：所有 demo 模块统一用 chenmeng_test 库，表名区分模块）
-- ----------------------------------------------------------------------------
create database if not exists chenmeng_test
    default character set utf8mb4
    collate utf8mb4_general_ci;

use chenmeng_test;

-- ----------------------------------------------------------------------------
-- 2. 业务表：请假单（对应实体 com.chenmeng.project.entity.LeaveDO）
--
--    设计要点（"业务表集成模式"）：
--      a. 业务字段（user_id/reason/day）+ 状态字段（status）都在业务表里，流程数据全在 ACT_* 表里；
--      b. process_instance_id 桥字段：业务表记录流程实例 id（发起时回写）；
--      c. 反向桥是 businessKey：流程实例记录业务表主键（存 ACT_RU_EXECUTION.BUSINESS_KEY_，
--         引擎按 TASK_ID_/PROC_INST_ID_ 关联，本表无对应列）；
--      d. 表名不用 leave：LEAVE 是 MySQL 保留字（循环控制语句），建表/查询都要加反引号转义，教学工程刻意避开。
-- ----------------------------------------------------------------------------
create table if not exists `leave_apply` (
    `id`                  bigint       not null auto_increment comment '主键（= 流程实例的 businessKey）',
    `user_id`             varchar(64)  not null comment '请假人',
    `reason`              varchar(255) not null comment '请假事由',
    `day`                 int          not null comment '请假天数（同时作为流程变量，网关条件 ${day > 3} 依据）',
    `status`              tinyint      not null default 1 comment '状态：1-审批中、2-审批通过、3-审批拒绝（由流程结束监听器回写）',
    `process_instance_id` varchar(64)  default null comment '流程实例 id（Flowable 生成，发起时回写）',
    `create_time`         datetime     not null default current_timestamp comment '创建时间（发起请假时间）',
    `update_time`         datetime     not null default current_timestamp on update current_timestamp comment '更新时间',
    primary key (`id`)
) engine = innodb
  auto_increment = 1
  default charset = utf8mb4
  collate = utf8mb4_general_ci comment ='请假单（业务表，对应 yudao 的 bpm_oa_leave 示例）';
