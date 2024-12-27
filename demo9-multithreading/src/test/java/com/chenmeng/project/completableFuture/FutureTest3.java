package com.chenmeng.project.completableFuture;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 多个组合处理
 *
 * @author chenmeng
 */
public class FutureTest3 {

    /**
     * thenCombineAsync() 方法测试
     * <p>
     * 该方法用于组合两个异步任务的结果。
     * 在此示例中，我们创建了两个异步任务，并使用 thenCombineAsync() 方法将它们的结果组合在一起。
     * 第一个任务是一个已经完成的 CompletableFuture，第二个任务通过 supplyAsync() 方法异步执行。
     * 最终，两个任务的结果会被组合成一个新的字符串，并通过 join() 方法获取结果。
     * </p>
     */
    @Test
    void testThenCombineAsync() {
        // 创建一个已经完成的 CompletableFuture，结果为 "第一个异步任务"
        CompletableFuture<String> firstFuture = CompletableFuture.completedFuture("第一个异步任务");

        // 创建一个固定大小的线程池
        ExecutorService executor = Executors.newFixedThreadPool(10);

        // 创建第二个异步任务，并使用 thenCombineAsync() 方法组合两个任务的结果
        CompletableFuture<String> future = CompletableFuture
                // 第二个异步任务
                .supplyAsync(() -> "第二个异步任务", executor)
                // 第三个任务，组合前两个任务的结果
                .thenCombineAsync(firstFuture, (s, other) -> {
                    System.out.println(s); // 打印 supplyAsync 任务的结果
                    System.out.println(other); // 打印 firstFuture 任务的结果
                    return "两个异步任务的组合"; // 返回组合后的结果
                }, executor);

        // 获取并打印组合后的结果
        System.out.println(future.join());

        // 关闭线程池
        executor.shutdown();

        // 输出
        /*
        第二个异步任务
        第一个异步任务
        两个异步任务的组合
        */
    }

    /**
     * allOf() 方法测试
     * <p>
     * 该方法用于组合多个 CompletableFuture 任务，确保所有任务都完成后才继续执行后续操作。
     * 在此示例中，我们创建了两个异步任务，并使用 allOf() 方法将它们组合在一起。
     * 当所有任务完成后，会执行 whenComplete() 方法中的回调，打印 "finish"。
     * </p>
     */
    @Test
    void testAllOf() throws ExecutionException, InterruptedException {
        // 创建第一个异步任务，任务完成后打印 "我执行完了"
        CompletableFuture<Void> a = CompletableFuture.runAsync(() -> System.out.println("我执行完了"));

        // 创建第二个异步任务，任务完成后打印 "我也执行完了"
        CompletableFuture<Void> b = CompletableFuture.runAsync(() -> System.out.println("我也执行完了"));

        // 使用 CompletableFuture.allOf 组合两个异步任务，由于 runAsync 方法中的任务是异步执行的，具体的执行顺序可能会有所不同
        // 当所有任务完成后，执行 whenComplete 回调
        CompletableFuture<Void> allOfFuture = CompletableFuture.allOf(a, b)
                // 回调函数中的参数 res 和 ex 分别表示结果和异常
                // 在 allOf 的情况下，res 为 null，ex 为可能的异常（如果没有异常则为 null）
                .whenComplete((res, ex) -> System.out.println("finish"));

        // 为 null
        allOfFuture.get();

        // 输出
        /*
        我也执行完了
        我执行完了
        finish
        */
    }

    /**
     * anyOf() 方法测试
     * <p>
     * 该方法用于组合多个 CompletableFuture 任务，只要其中任何一个任务完成，就会继续执行后续操作。
     * 在此示例中，我们创建了两个异步任务，并使用 anyOf() 方法将它们组合在一起。
     * 只要其中一个任务完成，就会执行 whenComplete() 方法中的回调，打印 "finish"。
     * </p>
     */
    @Test
    void testAnyOf() {
        // 创建第一个异步任务，任务完成后打印 "我执行完了"
        // 该任务会休眠 3 秒钟
        CompletableFuture<Void> a = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(3000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("我执行完了");
        });

        // 创建第二个异步任务，任务完成后打印 "我也执行完了"
        CompletableFuture<Void> b = CompletableFuture.runAsync(() -> System.out.println("我也执行完了"));

        // 使用 CompletableFuture.anyOf 组合两个异步任务
        // 只要其中一个任务完成，就会执行 whenComplete 回调
        CompletableFuture<Object> anyOfFuture = CompletableFuture.anyOf(a, b)
                .whenComplete((res, ex) -> System.out.println("finish"));

        // 等待任意一个给定的 CompletableFuture 完成
        anyOfFuture.join();

        // 输出（这里由于第一个任务会休眠 3 秒，所以一直会输出第一种情况）
        /*
        我也执行完了
        finish
        或者
        我执行完了
        finish
        */
    }
}
