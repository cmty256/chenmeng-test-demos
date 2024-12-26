package com.chenmeng.project.config;

import com.chenmeng.common.interceptor.MyBatisPrintInterceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * mybatis自定义配置
 *
 * @author chenmeng
 */
// @Configuration
public class MyBatisConfig {

    @Resource
    private List<SqlSessionFactory> sqlSessionFactoryList;

    @Value("${application.env:dev}")
    private String env;

    private static final String ON_LINE = "online";

    @PostConstruct
    public void addMyBatisInterceptor() {
        if (ON_LINE.equals(env)) {
            return;
        }
        MyBatisPrintInterceptor interceptor = new MyBatisPrintInterceptor();
        for (SqlSessionFactory sqlSessionFactory : sqlSessionFactoryList) {
            // 添加自定义属性
            sqlSessionFactory.getConfiguration()
                    .addInterceptor(interceptor);
        }
    }

}
