package com.chenmeng.project.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * apollo 配置项，会自动更新值
 *
 * @author chenmeng
 */
@Component
@Getter
public class MyConfigs {

    @Value("${test.name:default-name}")
    private String name;

    @Value("${test.age:18}")
    private String age;
}
