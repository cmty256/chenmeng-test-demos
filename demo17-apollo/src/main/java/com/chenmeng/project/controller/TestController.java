package com.chenmeng.project.controller;

import com.chenmeng.project.config.MyConfigs;
import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author chenmeng
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private MyConfigs myConfigs;

    /**
     * 使用 @Value 注解获取配置
     */
    @RequestMapping("/hello")
    public String hello() {
        return "hello " + myConfigs.getName() + " " + myConfigs.getAge();
    }

    /**
     * 使用 Config API 获取配置
     */
    @RequestMapping("/hello2")
    public String hello2() {
        // Config config = ConfigService.getAppConfig();
        Config config = ConfigService.getConfig("application-dev.yml");
        String name = config.getProperty("test.name", "default-name");
        String age = config.getProperty("test.age", "18");
        return "hello " + name + " " + age;
    }
}
