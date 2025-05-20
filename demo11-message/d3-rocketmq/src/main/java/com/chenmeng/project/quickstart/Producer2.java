package com.chenmeng.project.quickstart;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.nio.charset.StandardCharsets;

import static com.chenmeng.project.constants.Constants.*;

/**
 * @author chenmeng
 */
public class Producer2 {

    public static void main(String[] args) throws Exception {
        // 1.创建生产者
        DefaultMQProducer producer = new DefaultMQProducer(TEST_PRODUCER_GROUP);
        // 2.指定nameserver的地址
        producer.setNamesrvAddr(DEFAULT_NAMESRV_ADDR);
        // 3.启动生产者
        producer.start();
        // 4.创建消息
        for (int i = 0; i < 10; i++) {
            Message message = new Message(TEST_TOPIC, "TagA", ("hello rocketmq" + i).getBytes(StandardCharsets.UTF_8));
            // 5.发送消息
            SendResult sendResult = producer.send(message);
            System.out.println(sendResult);
        }
        // 6.关闭生产者
        producer.shutdown();
    }
}
