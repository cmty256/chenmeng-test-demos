package com.chenmeng.project;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

/**
 * Flowable 工作流学习 demo 启动类。
 *
 * <p>核心机制说明（对应 Spring Boot 自动装配）：
 * <ol>
 *     <li>引入 flowable-spring-boot-starter-process 后，Flowable 的自动装配类
 *         {@code FlowableAutoConfiguration} / {@code ProcessEngineAutoConfiguration}
 *         会自动向 Spring 容器注册 {@code ProcessEngine} 以及四个最常用的 Service：
 *         <ul>
 *             <li>{@code RepositoryService}：流程定义仓库——部署 BPMN、查询流程定义（对应 ACT_RE_* 表）</li>
 *             <li>{@code RuntimeService}：运行时——启动流程实例、读写流程变量（对应 ACT_RU_* 表）</li>
 *             <li>{@code TaskService}：任务——待办查询、完成任务、写审批意见（对应 ACT_RU_TASK / ACT_HI_COMMENT 表）</li>
 *             <li>{@code HistoryService}：历史——已走完节点、历史任务、历史流程实例（对应 ACT_HI_* 表）</li>
 *         </ul>
 *         所以业务代码里直接 {@code @Resource} 注入即可，不需要手动创建引擎。</li>
 *     <li>引擎默认使用 Spring 的主数据源（application-dev.yml 里配置的 MySQL），
 *         首次启动会自动建 30+ 张 ACT_ 开头的引擎表；业务表 leave_apply 由 sql 目录下的脚本手动建。</li>
 *     <li>classpath 下的 processes/*.bpmn20.xml 会被自动部署（"发布 = 部署"概念的直接印证），
 *         详见 resources/processes/leave.bpmn20.xml 文件头部注释。</li>
 * </ol>
 *
 * @author chenmeng
 */
@Slf4j
@SpringBootApplication
// 扫描 MyBatis-Plus 的 Mapper 接口，将其注册为 Spring Bean（业务表访问层）
@MapperScan("com.chenmeng.project.mapper")
public class WorkflowDemoApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(WorkflowDemoApplication.class);
        Environment env = app.run(args).getEnvironment();
        log.info("【{}】, 启动成功！！", env.getProperty("spring.application.name"));
        // 接口文档访问地址提示（knife4j 增强 UI），方便启动后直接打开逐个调用接口
        log.info("接口文档地址: http://127.0.0.1:{}{}/doc.html",
                env.getProperty("server.port"), env.getProperty("server.servlet.context-path"));
    }
}
