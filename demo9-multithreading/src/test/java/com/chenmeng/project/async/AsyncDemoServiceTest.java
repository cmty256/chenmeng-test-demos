package com.chenmeng.project.async;

import com.chenmeng.project.service.AsyncDemoService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.util.concurrent.SuccessCallback;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@SpringBootTest
public class AsyncDemoServiceTest {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private AsyncDemoService asyncDemoService;

    /**
     * 串行任务
     */
    @Test
    public void task01() {
        long now = System.currentTimeMillis();
        logger.info("[task01][开始执行]");

        asyncDemoService.execute01();
        asyncDemoService.execute02();

        logger.info("[task01][结束执行，消耗时长 {} 毫秒]", System.currentTimeMillis() - now);
    }

    /**
     * 异步任务，不阻塞
     */
    @Test
    public void task02() {
        long now = System.currentTimeMillis();
        logger.info("[task02][开始执行]");

        asyncDemoService.execute01Async();
        asyncDemoService.execute02Async();

        logger.info("[task02][结束执行，消耗时长 {} 毫秒]", System.currentTimeMillis() - now);
    }

    /**
     * 异步任务，阻塞等待结果
     */
    @Test
    public void task03() throws ExecutionException, InterruptedException {
        long now = System.currentTimeMillis();
        logger.info("[task03][开始执行]");

        // 执行任务
        Future<Integer> execute01Result = asyncDemoService.execute01AsyncWithFuture();
        Future<Integer> execute02Result = asyncDemoService.execute02AsyncWithFuture();
        // 阻塞等待结果
        execute01Result.get();
        execute02Result.get();

        logger.info("[task03][结束执行，消耗时长 {} 毫秒]", System.currentTimeMillis() - now);
    }

    @Test
    public void task04() throws ExecutionException, InterruptedException {
        long now = System.currentTimeMillis();
        logger.info("[task04][开始执行]");

        // 执行任务
        ListenableFuture<Integer> execute01Result = asyncDemoService.execute01AsyncWithListenableFuture();
        logger.info("[task04][execute01Result 的类型是：({})]", execute01Result.getClass().getSimpleName());
        execute01Result.addCallback(new SuccessCallback<Integer>() { // 增加成功的回调

            @Override
            public void onSuccess(Integer result) {
                logger.info("[onSuccess][result: {}]", result);
            }

        }, new FailureCallback() { // 增加失败的回调

            @Override
            public void onFailure(Throwable ex) {
                logger.info("[onFailure][发生异常]", ex);
            }

        });
        execute01Result.addCallback(new ListenableFutureCallback<Integer>() { // 增加成功和失败的统一回调

            @Override
            public void onSuccess(Integer result) {
                logger.info("[onSuccess][result: {}]", result);
            }

            @Override
            public void onFailure(Throwable ex) {
                logger.info("[onFailure][发生异常]", ex);
            }

        });
        // 阻塞等待结果
        execute01Result.get();

        logger.info("[task04][结束执行，消耗时长 {} 毫秒]", System.currentTimeMillis() - now);
    }

    /**
     * 测试异步任务全局异常捕获
     */
    @Test
    public void testGetException() throws InterruptedException {
        asyncDemoService.getException(1, 2);

        // sleep 1 秒，保证异步调用的执行
        Thread.sleep(1000);
    }

}
