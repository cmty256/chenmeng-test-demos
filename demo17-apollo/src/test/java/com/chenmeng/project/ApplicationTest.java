package com.chenmeng.project;

import com.chenmeng.project.config.MyConfigs;
import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author chenmeng
 */
@SpringBootTest
public class ApplicationTest {

    @Resource
    private MyConfigs myConfigs;

    @Test
    void test1() {
        System.out.println("读取到的配置：" + myConfigs.getName());
    }

    @Test
    void test2() {
        Config config = ConfigService.getAppConfig(); //config instance is singleton for each namespace and is never null
        String someKey = "user.age";
        String someDefaultValue = "17";
        String value = config.getProperty(someKey, someDefaultValue);
        System.out.println("读取到的配置：" + value);
    }

}
