package com.chenmeng.project;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

/**
 * @author chenmeng
 */
@Slf4j
@SpringBootApplication
public class SpringRocketmqApplication {

    public static void main(String[] args) {
        // SpringApplication.run(SpringRocketmqApplication.class, args);

        SpringApplication app = new SpringApplication(SpringRocketmqApplication.class);
        Environment env = app.run(args).getEnvironment();
        // 获取服务器的Scheme（HTTP或HTTPS）
        String scheme = "true".equals(env.getProperty("server.ssl.enabled", "false")) ? "https" : "http";
        log.info("【{}】, 启动成功！！", env.getProperty("spring.application.name"));
        log.info("地址: {}://127.0.0.1:{}{}", scheme, env.getProperty("server.port"), env.getProperty("server.servlet.context-path"));
    }

}
