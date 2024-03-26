package com.chenmeng.project.config;

import cn.jpush.api.JPushClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * 极光推送配置
 *
 * @author cmty256
 */
@Configuration
public class JiGuangConfig {

    /**
     * 极光官网-个人管理中心-appkey
     * https://www.jiguang.cn/
     */
    @Value("${jpush.appkey}")
    private String appkey;

    /**
     * 极光官网-个人管理中心-点击查看-secret
     */
    @Value("${jpush.secret}")
    private String secret;


    private JPushClient jPushClient;

    /**
     * 推送客户端
     * @return
     */
    @PostConstruct
    public void initJPushClient() {
        jPushClient = new JPushClient(secret, appkey);
    }

    /**
     * 获取推送客户端
     * @return
     */
    public JPushClient getJPushClient() {
        return jPushClient;
    }
}
