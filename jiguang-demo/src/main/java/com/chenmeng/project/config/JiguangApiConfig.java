package com.chenmeng.project.config;

import cn.jiguang.sdk.api.AdminApi;
import cn.jiguang.sdk.api.DeviceApi;
import cn.jiguang.sdk.api.PushApi;
import cn.jiguang.sdk.api.ReportApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author chenmeng
 */
@Configuration
public class JiguangApiConfig {

    @Value("${jiguang.api.app-key}")
    private String appKey;

    @Value("${jiguang.api.master-secret}")
    private String masterSecret;

    @Value("${jiguang.api.dev-key}")
    private String devKey;

    @Value("${jiguang.api.dev-secret}")
    private String devSecret;

    @Bean
    public PushApi pushApi() {
        return new PushApi.Builder()
                .setAppKey(appKey)
                .setMasterSecret(masterSecret)
                .build();
    }

    @Bean
    public DeviceApi deviceApi() {
        return new DeviceApi.Builder()
                .setAppKey(appKey)
                .setMasterSecret(masterSecret)
                .build();
    }

    @Bean
    public ReportApi reportApi() {
        return new ReportApi.Builder()
                .setAppKey(appKey)
                .setMasterSecret(masterSecret)
                .build();
    }

    @Bean
    public AdminApi adminApi() {
        return new AdminApi.Builder()
                .setDevKey(devKey)
                .setDevSecret(devSecret)
                .build();
    }

}