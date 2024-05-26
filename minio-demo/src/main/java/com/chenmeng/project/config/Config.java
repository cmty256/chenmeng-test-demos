package com.chenmeng.project.config;

import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author chenmeng
 */
@Configuration
public class Config {

    /**
     * 单例的，那么MinioClient对象有没有线程安全问题呢？
     * 答案是：没有线程安全问题
     * @return
     */
    @Bean
    public MinioClient minioClient() {
        //链式编程，构建MinioClient对象
        return MinioClient.builder()
                .endpoint("http://192.168.11.128:9000")
                .credentials("minioadmin", "minioadmin")
                .build();
    }
}
