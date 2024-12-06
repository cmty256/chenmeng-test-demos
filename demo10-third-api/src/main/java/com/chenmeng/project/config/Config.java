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
    @Bean(name = "minioClient2")
    public MinioClient minioClient2() {
        //链式编程，构建MinioClient对象
        return MinioClient.builder()
                // 9000端口是API，9001端口是WebUI
                // .endpoint("http://192.168.2.129:9000")
                .endpoint("http://192.168.239.130:9000")
                // 访问密钥和秘密密钥
                .credentials("ROOTNAME", "CHANGEME123")
                .build();
    }
}
