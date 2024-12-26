package com.chenmeng.project.config;

import com.chenmeng.common.config.MyBatisPlusConfig;
import com.chenmeng.common.utils.EasyExcelUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author chenmeng
 */
@Configuration
@Import({MyBatisPlusConfig.class, EasyExcelUtil.class})
public class DefaultConfig {
}