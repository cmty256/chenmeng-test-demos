package com.chenmeng.project.springboot;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class MyProducerTest {

    @Resource
    private MyProducer myProducer;

    @Test
    void testSendMessage() {
        String topic = "my-boot-topic";
        String message = "hello rocketmq springboot message!";
        myProducer.sendMessage(topic, message);
        System.out.println("消息发送成功！");
    }
}