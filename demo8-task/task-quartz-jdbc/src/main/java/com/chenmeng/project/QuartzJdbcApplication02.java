package com.chenmeng.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 测试集群下的运行情况
 *
 * @author chenmeng
 */
@SpringBootApplication
public class QuartzJdbcApplication02 {

    public static void main(String[] args) {
        // 设置 Tomcat 随机端口
        System.setProperty("server.port", "0");

        // 启动 Spring Boot 应用
        SpringApplication.run(QuartzJdbcApplication02.class, args);
    }

}