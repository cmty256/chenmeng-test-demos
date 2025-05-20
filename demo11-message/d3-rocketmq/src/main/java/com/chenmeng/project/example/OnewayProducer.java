package com.chenmeng.project.example;

import com.chenmeng.project.constants.Constants;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import static com.chenmeng.project.constants.Constants.DEFAULT_NAMESRV_ADDR;

/**
 * 单向消息生产者
 *
 * @author chenmeng
 */
public class OnewayProducer {
    public static void main(String[] args) throws Exception {
        // Instantiate with a producer group name.
        DefaultMQProducer producer = new DefaultMQProducer(Constants.TEST_PRODUCER_GROUP);
        // Specify name server addresses.
        producer.setNamesrvAddr(DEFAULT_NAMESRV_ADDR);
        // Launch the instance.
        producer.start();
        
        for (int i = 0; i < 10; i++) {
            // Create a message instance, specifying topic, tag and message body.
            Message msg = new Message(Constants.TEST_TOPIC /* Topic */,
                    "TagA" /* Tag */,
                    ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
            );
            // Call send message to deliver message to one of brokers.
            producer.sendOneway(msg);
        }
        // Wait for sending to complete -- 等待发送完成
        Thread.sleep(5000);
        
        producer.shutdown();
    }
}