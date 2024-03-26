package com.chenmeng.project.service;

import com.chenmeng.project.dto.PushDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.Map;

/**
 * @author cmty256
 */
@SpringBootTest
class JiGuangPushServiceTest {

    @Resource
    private JiGuangPushService jiGuangService; //注入极光推送服务类对象

    @Test
    void pushAll() {
        // 定义和赋值推送实体
        PushDTO pushBean = new PushDTO();
        pushBean.setTitle("沉梦通知");
        pushBean.setAlert("消息来了~~");
        // 额外推送信息
        Map<String, String> map = new HashMap<>();
        map.put("key", "value");
        pushBean.setExtras(map);
        // 进行推送，推送到所有使用Android客户端的用户，返回推送结果布尔值
        boolean flag = jiGuangService.pushAndroid(pushBean);
        System.out.println("flag = " + flag);
    }
}