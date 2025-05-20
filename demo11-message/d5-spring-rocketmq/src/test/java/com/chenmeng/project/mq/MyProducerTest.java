package com.chenmeng.project.mq;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@Slf4j
@SpringBootTest
class MyProducerTest {

    @Resource
    private MyProducer myProducer;

    @Test
    void testSendMessage() {
        String topic = "my-boot-topic";
        String message = "hello rocketmq springboot message!";
        myProducer.sendMessage(topic, message);
        log.info("消息发送成功！");
    }

    @Test
    void testSendMessageInTransaction() throws InterruptedException {
        String topic = "my-boot-topic";
        String message = "hello rocketmq transaction springboot message";
        myProducer.sendMessageInTransaction(topic, message);
        log.info("事务消息发送成功！");
    }
}