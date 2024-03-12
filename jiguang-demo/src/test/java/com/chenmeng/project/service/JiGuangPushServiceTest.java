package com.chenmeng.project.service;

import com.chenmeng.project.dto.PushDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author 沉梦听雨
 */
@SpringBootTest
class JiGuangPushServiceTest {

    @Resource
    private JiGuangPushService jiGuangService; //注入极光推送服务类对象

    @Test
    void pushAll() {
        //定义和赋值推送实体
        PushDTO pushBean = new PushDTO();
        pushBean.setTitle("titleStr");
        pushBean.setAlert("alertStr");
        //额外推送信息
        Map<String, String> map = new HashMap<>();
        map.put("xxx", "xxx");
        pushBean.setExtras(map);
        //进行推送，推送到所有使用Android客户端的用户，返回推送结果布尔值
        boolean flag = jiGuangService.pushAndroid(pushBean);
        System.out.println("flag = " + flag);
    }
}