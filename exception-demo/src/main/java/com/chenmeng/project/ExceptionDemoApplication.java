package com.chenmeng.project;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author cmty256
 */
@SpringBootApplication
@MapperScan("com.chenmeng.project.mapper")
public class ExceptionDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExceptionDemoApplication.class, args);
    }

}