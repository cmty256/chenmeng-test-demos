package com.chenmeng.project.mq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author chenmeng
 */
@Slf4j
@Component
@RocketMQMessageListener(consumerGroup = "my-boot-consumer-group1",topic = "my-boot-topic")
public class MyConsumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        log.info("收到的消息：{}", message);
    }

}