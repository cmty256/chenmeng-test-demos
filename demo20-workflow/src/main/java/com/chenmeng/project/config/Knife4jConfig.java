package com.chenmeng.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Knife4j 接口文档配置（基于 springfox + knife4j 增强界面）。
 *
 * <p>学习工作流时强烈建议用文档界面逐个调用接口：
 * 先发起请假 → 查待办 → 审批 → 看状态变化 → 查历史/轨迹，
 * 每调用一步就回数据库看一眼 ACT_* 表的数据变化，比写页面直观得多。
 *
 * <p>文档地址：http://127.0.0.1:9120/workflow/doc.html
 * （端口/上下文路径见 application-dev.yml，spring.mvc.pathmatch 需要 ANT_PATH_MATCHER 兼容 springfox，
 * 该配置已写入 application-dev.yml）
 *
 * @author chenmeng
 */
@Configuration
// springfox 3.0 虽然支持 OpenAPI 规范，但 @EnableSwagger2 依然是 spring-boot 2.7 下最稳的组合（knife4j 3.0.3 官方推荐）
@EnableSwagger2
public class Knife4jConfig {

    /**
     * API 文档分组：扫描 controller 包下所有接口
     */
    @Bean
    public Docket defaultApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title("demo20-workflow 接口文档")
                        .description("Flowable 6.8.0 原生 API 学习 demo：请假审批全流程闭环")
                        .version("1.0")
                        .build())
                .select()
                // 指定 Controller 扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.chenmeng.project.controller"))
                .paths(PathSelectors.any())
                .build();
    }
}
