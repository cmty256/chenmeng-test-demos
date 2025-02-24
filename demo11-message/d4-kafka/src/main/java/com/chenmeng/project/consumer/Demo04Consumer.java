package com.chenmeng.project.consumer;

import com.chenmeng.project.message.Demo04Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author chenmeng
 */
@Component
public class Demo04Consumer {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @KafkaListener(topics = Demo04Message.TOPIC,
            groupId = "demo04-com.chenmeng.project.consumer-group-" + Demo04Message.TOPIC)
    public void onMessage(Demo04Message message) {
        logger.info("[onMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), message);
        // 注意，此处抛出一个 RuntimeException 异常，模拟消费失败
        throw new RuntimeException("这里故意抛出一个异常");
    }

}
