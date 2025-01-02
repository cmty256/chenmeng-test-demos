package com.chenmeng.project.config;

import com.chenmeng.common.config.GlobalAsyncConfig;
import com.chenmeng.common.config.MyBatisPlusConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author chenmeng
 */
@Configuration
@Import({MyBatisPlusConfig.class, GlobalAsyncConfig.class})
public class DefaultConfig {
}