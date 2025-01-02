package com.chenmeng.project.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.Future;

/**
 * @author chenmeng
 */
@Service
public class AsyncDemoService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public Integer execute01() {
        logger.info("[execute01]");
        sleep(10);
        return 1;
    }

    public Integer execute02() {
        logger.info("[execute02]");
        sleep(5);
        return 2;
    }

    @Async
    public Integer execute01Async() {
        return this.execute01();
    }

    @Async
    public Integer execute02Async() {
        return this.execute02();
    }

    @Async
    public Future<Integer> execute01AsyncWithFuture() {
        return AsyncResult.forValue(this.execute01());
    }

    @Async
    public Future<Integer> execute02AsyncWithFuture() {
        return AsyncResult.forValue(this.execute02());
    }

    @Async
    public ListenableFuture<Integer> execute01AsyncWithListenableFuture() {
        try {
            return AsyncResult.forValue(this.execute02());
        } catch (Throwable ex) {
            return AsyncResult.forExecutionException(ex);
        }
    }

    private static void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Async
    public void getException(Integer a, Integer b) {
        throw new RuntimeException("有异常！！！");
    }
}
