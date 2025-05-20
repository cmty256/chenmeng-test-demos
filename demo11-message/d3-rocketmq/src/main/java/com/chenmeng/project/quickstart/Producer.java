/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements. See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.chenmeng.project.quickstart;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import static com.chenmeng.project.constants.Constants.*;

/**
 * 本类演示了如何使用提供的 {@link DefaultMQProducer} 将消息发送到 Broker。
 */
public class Producer {

    /**
     * 发送的消息数量。
     */
    public static final int MESSAGE_COUNT = 10;
    public static final String PRODUCER_GROUP = TEST_PRODUCER_GROUP;
    public static final String TOPIC = "quickstart";
    public static final String TAG = "TagA";

    public static void main(String[] args) throws MQClientException, InterruptedException {

        /*
         * 使用生产者组名称实例化。
         */
        DefaultMQProducer producer = new DefaultMQProducer(PRODUCER_GROUP);

        /*
         * 指定名称服务器地址。
         *
         * 或者，您可以通过导出环境变量 NAMESRV_ADDR 来指定名称服务器地址
         * <pre>
         * {@code
         *  producer.setNamesrvAddr("name-server1-ip:9876;name-server2-ip:9876");
         * }
         * </pre>
         */
        // 调试时取消下一行注释，namesrvAddr 应设置为您本地的地址
        producer.setNamesrvAddr(DEFAULT_NAMESRV_ADDR);

        /*
         * 启动实例。
         */
        producer.start();

        for (int i = 0; i < MESSAGE_COUNT; i++) {
            try {

                /*
                 * 创建一个消息实例，指定主题、标签和消息体。
                 */
                Message msg = new Message(TOPIC /* 主题 */,
                        TAG /* 标签 */,
                        ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* 消息体 */
                );

                /*
                 * 调用 send 方法将消息传递给其中一个 Broker。
                 */
                SendResult sendResult = producer.send(msg);
                /*
                 * 发送消息有多种方式，如果您不关心发送结果，可以使用以下方式
                 * {@code
                 * producer.sendOneway(msg);
                 * }
                 */

                /*
                 * 如果您想以同步方式获取发送结果，可以使用以下发送方法
                 * {@code
                 * SendResult sendResult = producer.send(msg);
                 * System.out.printf("%s%n", sendResult);
                 * }
                 */

                /*
                 * 如果您想以异步方式获取发送结果，可以使用以下发送方法
                 * {@code
                 *
                 *  producer.send(msg, new SendCallback() {
                 *  @Override
                 *  public void onSuccess(SendResult sendResult) {
                 *      // 在这里做您想做的事情
                 *  }
                 *
                 *  @Override
                 *  public void onException(Throwable e) {
                 *      // 在这里做您想做的事情
                 *  }
                 *});
                 *
                 *}
                 */

                System.out.printf("%s%n", sendResult);
            } catch (Exception e) {
                e.printStackTrace();
                Thread.sleep(1000);
            }
        }

        /*
         * 当不再使用生产者实例时关闭它。
         */
        producer.shutdown();
    }
}