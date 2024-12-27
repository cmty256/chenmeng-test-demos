package com.chenmeng.project.completableFuture;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 创建 CompletableFuture
 *
 * @author chenmeng
 */
public class FutureTest1 {

    // =============================new 关键字=============================

    /**
     * new 关键字创建 CompletableFuture
     * 利用 complete 方法手动完成 CompletableFuture
     */
    @Test
    void testNew() throws ExecutionException, InterruptedException {
        // 1、创建一个新的未完成的 CompletableFuture
        CompletableFuture<String> future = new CompletableFuture<>();

        // 模拟异步操作完成后手动完成 CompletableFuture
        String expectedResult = "Hello, World!";
        future.complete(expectedResult);

        // 测试是否成功完成并返回预期结果
        assertEquals(expectedResult, future.get());

        // 2、测试异常完成的情况
        CompletableFuture<Void> failingFuture = new CompletableFuture<>();
        RuntimeException expectedException = new RuntimeException("Oops!");
        failingFuture.completeExceptionally(expectedException);

        try {
            failingFuture.get();
            fail("Expected exception not thrown");
        } catch (ExecutionException e) {
            assertInstanceOf(RuntimeException.class, e.getCause());
            assertEquals(expectedException.getMessage(), e.getCause().getMessage());
        }
    }

    /**
     * 静态方法 completedFuture 创建一个已完成的 CompletableFuture
     * 底层用的也是 new
     */
    @Test
    void testCompletedFuture() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture.completedFuture("hello!");
        assertEquals("hello!", future.get());
    }

    // =============================静态工厂方法=============================

    /**
     * 静态工厂方法 supplyAsync 和 runAsync 的区别
     * supplyAsync 支持返回值
     * runAsync    不支持返回值
     */
    @Test
    void testSupplyAsyncAndRunAsync() throws ExecutionException, InterruptedException {
        CompletableFuture<Void> runFuture = CompletableFuture.runAsync(() -> System.out.println("hello runAsync!"));
        // 控制台输出 "hello!"
        runFuture.get();

        CompletableFuture<String> supplyFuture = CompletableFuture.supplyAsync(() -> "hello supplyAsync!");
        // 控制台 不会 输出 "hello!"
        supplyFuture.get();
        // 进行断言，判断返回值是否为 "hello!"，不通过就会抛出异常
        assertEquals("hello supplyAsync!", supplyFuture.get());
    }

    /**
     * 自定义线程池写法
     */
    @Test
    void testSupplyAsyncAndRunAsync2() {
        // 自定义线程池
        ExecutorService executor = Executors.newCachedThreadPool();

        // runAsync的使用
        CompletableFuture<Void> runFuture = CompletableFuture.runAsync(() -> System.out.println("run, cmty256"), executor);

        // supplyAsync的使用
        CompletableFuture<String> supplyFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("supply, cmty256");
            return "cmty256";
        }, executor);

        System.out.println("=============================异步操作，输出顺序不定=============================");

        // runAsync的future没有返回值，输出null
        System.out.println(runFuture.join());

        System.out.println("=============================异步操作，输出顺序不定=============================");

        // supplyAsync的future，有返回值
        System.out.println(supplyFuture.join());


        executor.shutdown(); // 线程池需要关闭
    }
}