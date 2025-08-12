package com.chenmeng.project.config;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.model.ConfigChange;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author chenmeng
 */
@Component
public class ApolloConfigListener {

    /**
     * 方式 1：直接在 Spring Bean 中用 ConfigService 注册监听器
     *
     * @PostConstruct 注解标记的方法会在 Spring Bean 初始化完成后自动执行（只执行一次，在 Bean 初始化完成后）
     */
    @PostConstruct
    public void init() {
        // 获取默认 application 命名空间的配置
        Config config = ConfigService.getAppConfig();
        // 真正的持续监听
        config.addChangeListener((ConfigChangeEvent changeEvent) -> {
            for (String key : changeEvent.changedKeys()) {
                ConfigChange change = changeEvent.getChange(key);
                System.out.printf("[init]配置变化: key=%s, old=%s, new=%s, type=%s%n",
                        change.getPropertyName(),
                        change.getOldValue(),
                        change.getNewValue(),
                        change.getChangeType());
            }
        });
    }

    /**
     * 方式 2：在 Spring Bean 中用 @ApolloConfigChangeListener 注解注册监听器
     */
    @ApolloConfigChangeListener
    public void onChange(ConfigChangeEvent changeEvent) {
        for (String key : changeEvent.changedKeys()) {
            ConfigChange change = changeEvent.getChange(key);
            System.out.printf("[onChange]配置变化: key=%s, old=%s, new=%s, type=%s%n",
                    change.getPropertyName(),
                    change.getOldValue(),
                    change.getNewValue(),
                    change.getChangeType());
        }
    }

    /**
     * 监听指定 namespace 的配置变化
     */
    @ApolloConfigChangeListener("application-dev.yml")
    public void onChangeByNamespace(ConfigChangeEvent changeEvent) {
        for (String key : changeEvent.changedKeys()) {
            ConfigChange change = changeEvent.getChange(key);
            System.out.printf("[onChangeByNamespace]配置变化: key=%s, old=%s, new=%s, type=%s%n",
                    change.getPropertyName(),
                    change.getOldValue(),
                    change.getNewValue(),
                    change.getChangeType());
        }
    }
}