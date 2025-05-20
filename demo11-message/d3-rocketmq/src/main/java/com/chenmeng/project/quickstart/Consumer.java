/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.chenmeng.project.quickstart;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;

import static com.chenmeng.project.constants.Constants.*;

/**
 * 本示例展示了如何使用提供的 {@link DefaultMQPushConsumer} 订阅并消费消息。
 */
public class Consumer {

    public static final String CONSUMER_GROUP = TEST_CONSUMER_GROUP;
    public static final String TOPIC = "quickstart";

    public static void main(String[] args) throws MQClientException {

        /*
         * 使用指定的消费者组名称实例化。
         */
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(CONSUMER_GROUP);

        /*
         * 指定名称服务器地址。
         * <p/>
         *
         * 或者，您可以通过导出环境变量 NAMESRV_ADDR 来指定名称服务器地址
         * <pre>
         * {@code
         * consumer.setNamesrvAddr("name-server1-ip:9876;name-server2-ip:9876");
         * }
         * </pre>
         */
        // 调试时取消下一行注释，namesrvAddr 应设置为您本地的地址
        consumer.setNamesrvAddr(DEFAULT_NAMESRV_ADDR);

        /*
         * 如果是全新的消费者组，指定从何处开始消费。
         */
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

        /*
         * 订阅一个或多个主题进行消费。
         */
        consumer.subscribe(TOPIC, "*");

        /*
         * 注册回调函数，在从 Broker 获取到消息时执行。
         */
        consumer.registerMessageListener((MessageListenerConcurrently) (msg, context) -> {
            System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msg);
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });

        /*
         * 启动消费者实例。
         */
        consumer.start();

        System.out.printf("Consumer Started.%n");
    }
}