package com.chenmeng.project.quickstart;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

import static com.chenmeng.project.constants.Constants.*;

/**
 * @author chenmeng
 */
public class Consumer2 {

    public static void main(String[] args) throws MQClientException {
        // 1.创建消费者对象
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(TEST_CONSUMER_GROUP);
        // 2.指明nameserver的地址
        consumer.setNamesrvAddr(DEFAULT_NAMESRV_ADDR);
        // 3.订阅主题:topic 和 过滤消息用的tag表达式
        consumer.subscribe(TEST_TOPIC, "*");
        // 4.创建一个监听器，当broker把消息推过来时调用
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {

                for (MessageExt msg : msgs) {
//                    System.out.println("收到的消息："+new String(msg.getBody()));
                    System.out.println("收到的消息：" + msg);
                }
                // 返回一个消费成功状态
                // return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                return null;
            }
        });
        // 5.启动消费者
        consumer.start();
        System.out.println("消费者已启动");
    }
}
