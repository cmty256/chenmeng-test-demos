package com.chenmeng.project.mq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author chenmeng
 */
@Slf4j
@Component
public class MyProducer {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    /**
     * 发送消息
     *
     * @param topic   主题
     * @param message 消息
     */
    public void sendMessage(String topic, String message) {
        rocketMQTemplate.convertAndSend(topic, message);
    }

    /**
     * 发送事务消息
     *
     * @param topic 主题
     * @param msg   消息
     * @throws InterruptedException 中断异常
     */
    public void sendMessageInTransaction(String topic, String msg) throws InterruptedException {
        String[] tags = new String[]{"TagA", "TagB", "TagC", "TagD", "TagE"};
        for (int i = 0; i < 10; i++) {
            // 创建一个消息对象，并通过调用withPayload()方法，向消息对象中添加了一个负载，即要发送的字符串类型的数据
            Message<String> message = MessageBuilder.withPayload(msg).build();
            // topic 和 tag 整合成一个字符串
            String destination = topic + ":" + tags[i % tags.length];
            // 第一个destination是消息要发送的目的地topic，第二个destination是消息携带的业务数据
            TransactionSendResult sendResult = rocketMQTemplate.sendMessageInTransaction(destination, message, destination);
            log.info("[sendMessageInTransaction]sendResult: {}", sendResult);
            // 暂停10毫秒，以模拟消息的发送和处理过程所需要的时间
            Thread.sleep(10);
        }
    }
}