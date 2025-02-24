package com.chenmeng.project.producer;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

@SpringBootTest
public class Demo01ProducerTest {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Demo01Producer producer;

    @Test
    public void testSyncSend() throws ExecutionException, InterruptedException {
        int id = (int) (System.currentTimeMillis() / 1000);
        SendResult<Object, Object> result = producer.syncSend(id);
        logger.info("[testSyncSend][发送编号：[{}] 发送结果：[{}]]", id, result);

        // 阻塞等待，保证消费（测试场景下的阻塞等待）
        new CountDownLatch(1).await();
    }

    @Test
    public void testASyncSend() throws InterruptedException {
        int id = (int) (System.currentTimeMillis() / 1000);
        producer.asyncSend(id).addCallback(new ListenableFutureCallback<SendResult<Object, Object>>() {

            @Override
            public void onFailure(@NotNull Throwable e) {
                logger.info("[testASyncSend][发送编号：[{}] 发送异常]]", id, e);
            }

            @Override
            public void onSuccess(SendResult<Object, Object> result) {
                logger.info("[testASyncSend][发送编号：[{}] 发送成功，结果为：[{}]]", id, result);
            }

        });

        // 阻塞等待，保证消费
        new CountDownLatch(1).await();
    }

   // @Test
   // public void testOnewaySend() throws InterruptedException {
   //     int id = (int) (System.currentTimeMillis() / 1000);
   //     producer.onewaySend(id);
   //     logger.info("[testOnewaySend][发送编号：[{}] 发送完成]", id);
   //
   //     // 阻塞等待，保证消费
   //     new CountDownLatch(1).await();
   // }
   //
   // @Test
   // public void testSyncSendMore() throws ExecutionException, InterruptedException {
   //     for (int i = 0; i < 1000; i++) {
   //         int id = (int) (System.currentTimeMillis() / 1000);
   //         SendResult result = producer.syncSend(id);
   //         logger.info("[testSyncSend][发送编号：[{}] 发送结果：[{}]]", id, result);
   //         Thread.sleep(10);
   //     }
   //
   //     // 阻塞等待，保证消费
   //     new CountDownLatch(1).await();
   // }
   //
   // @Test
   // public void block() throws InterruptedException {
   //     // 阻塞等待，保证消费
   //     new CountDownLatch(1).await();
   // }

}
