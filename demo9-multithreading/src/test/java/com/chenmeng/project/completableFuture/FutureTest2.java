package com.chenmeng.project.completableFuture;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 简单任务异步回调
 *
 * @author chenmeng
 */
public class FutureTest2 {

    // =============================处理异步结算结果=============================

    /**
     * thenRun()方法
     * <p>
     * 做完第一个任务后，再做第二个任务
     * 但是前后两个任务没有参数传递，第二个任务也没有返回值。
     * </p>
     *
     * @throws ExecutionException   如果计算过程中发生异常
     * @throws InterruptedException 如果当前线程在等待时被中断
     */
    @Test
    void testThenRun() throws ExecutionException, InterruptedException {
        CompletableFuture<String> firstFuture = CompletableFuture.supplyAsync(
                () -> {
                    System.out.println("先执行第一个CompletableFuture方法任务");
                    return "沉梦听雨";
                }
        );

        CompletableFuture<Void> thenRunFuture = firstFuture.thenRun(() -> {
            System.out.println("thenRun-接着执行第二个任务");
        });

        System.out.println("返回值：" + thenRunFuture.get());

        // 输出
        /*
        先执行第一个CompletableFuture方法任务
        thenRun-接着执行第二个任务
        返回值：null
         */
    }


    /**
     * thenAccept()方法
     * <p>
     * 做完第一个任务后，再做第二个任务
     * 可以接收入参，但是没有返回值。
     * </p>
     */
    @Test
    void testThenAccept() throws ExecutionException, InterruptedException {
        CompletableFuture<String> firstFuture = CompletableFuture.supplyAsync(
                () -> {
                    System.out.println("第一个CompletableFuture方法任务");
                    return "沉梦听雨";
                }
        );

        CompletableFuture<Void> thenAcceptFuture = firstFuture.thenAccept((a) -> {
            if ("沉梦听雨".equals(a)) {
                System.out.println("入参校验成功");
            }

            System.out.println("thenAccept-接着执行第二个任务");
        });

        System.out.println("返回值：" + thenAcceptFuture.get());

        // 输出
        /*
        第一个CompletableFuture方法任务
        入参校验成功
        thenAccept-接着执行第二个任务
        返回值：null
         */
    }

    /**
     * thenApply()方法
     * <p>
     * 做完第一个任务后，再做第二个任务
     * 可以接收入参，并且有返回值。
     * </p>
     */
    @Test
    void testThenApply() throws ExecutionException, InterruptedException {
        CompletableFuture<String> firstFuture = CompletableFuture.supplyAsync(
                () -> {
                    System.out.println("第一个CompletableFuture方法任务");
                    return "cmty256";
                }
        );

        CompletableFuture<String> thenApplyFuture = firstFuture.thenApply((a) -> {
            if ("沉梦听雨".equals(a)) {
                return "第一个任务的返回值";
            }

            return "thenApply-第二个任务的返回值";
        });

        System.out.println("返回值：" + thenApplyFuture.get());

        // 输出
        /*
        第一个CompletableFuture方法任务
        返回值：thenApply-第二个任务的返回值
         */
    }

    /**
     * whenComplete()方法
     * <p>
     * 两个任务在同一个线程中执行
     * 第二个任务可以接收入参
     * 第二个任务返回的是第一个任务的返回值
     * </p>
     */
    @Test
    void testWhenComplete() throws ExecutionException, InterruptedException {
        CompletableFuture<String> firstFuture = CompletableFuture.supplyAsync(
                () -> {
                    System.out.println("当前线程名称：" + Thread.currentThread().getName());
                    try {
                        Thread.sleep(2000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return "沉梦听雨";
                }
        );

        CompletableFuture<String> whenCompleteFuture = firstFuture.whenComplete((a, throwable) -> {
            System.out.println("当前线程名称：" + Thread.currentThread().getName());
            System.out.println("上个任务执行完啦，还把【" + a + "】传过来");
            if ("沉梦听雨".equals(a)) {
                System.out.println("入参校验成功");
            }

            System.out.println("whenComplete-接着执行第二个任务");
        });

        System.out.println("返回值：" + whenCompleteFuture.get());

        // 输出
        /*
        当前线程名称：ForkJoinPool.commonPool-worker-19
        当前线程名称：ForkJoinPool.commonPool-worker-19
        上个任务执行完啦，还把【沉梦听雨】传过来
        入参校验成功
        whenComplete-接着执行第二个任务
        返回值：沉梦听雨
         */
    }


    // =============================异常处理=============================

    /**
     * handle()方法
     * <p>
     * 该方法用于处理异步任务的结果或异常。
     * - 如果任务正常完成，则返回任务的结果；
     * - 如果任务抛出异常，则可以指定一个默认值或其他处理逻辑。
     * 在此示例中，异步任务会抛出一个 RuntimeException，
     * 而 handle() 方法会捕获该异常并返回一个默认字符串 "world!"。
     * </p>
     */
    @Test
    void testHandle() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            throw new RuntimeException("Computation error!");
            // return "world!";
        }).handle((res, ex) -> {
            // res 代表返回的结果
            // ex 的类型为 Throwable，代表抛出的异常

            if (ex != null) {
                // 异常被捕获: java.lang.RuntimeException: Computation error!
                System.out.println("异常被捕获: " + ex.getMessage());
                // 返回默认值 "world!"
                return "world!";
            }
            return (String) res;

            // return (String) (res != null ? res : "world!");
        });

        assertEquals("world!", future.get());
    }

    /**
     * exceptionally() 方法
     * <p>
     * 该方法用于处理异步任务中发生的异常。
     * 如果任务抛出异常，则可以指定一个默认值或其他处理逻辑。
     * 在此示例中，异步任务会抛出一个 RuntimeException，
     * 而 exceptionally() 方法会捕获该异常并返回一个默认字符串 "world!"。
     * </p>
     */
    @Test
    void testExceptionally() throws ExecutionException, InterruptedException {
        CompletableFuture<Object> future = CompletableFuture.supplyAsync(() -> {
            throw new RuntimeException("Computation error!");
        }).exceptionally(ex -> {
            // java.util.concurrent.CompletionException: java.lang.RuntimeException: Computation error!
            System.out.println(ex.toString());
            // 返回默认值 "world!"
            return "world!";
        });
        assertEquals("world!", future.get());
    }

    /**
     * completeExceptionally() 方法
     * <p>
     * 该方法用于手动将 CompletableFuture 标记为异常完成状态。
     * 在此示例中，我们创建了一个 CompletableFuture 对象，并使用 completeExceptionally() 方法手动设置一个异常。
     * 然后，调用 get() 方法会抛出该异常。
     * </p>
     */
    @Test
    void testCompleteExceptionally() throws InterruptedException {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();

        // 手动设置 CompletableFuture 为异常完成状态
        completableFuture.completeExceptionally(new RuntimeException("Calculation failed!"));

        try {
            // 直接 get() 会抛出异常
            completableFuture.get();
        } catch (ExecutionException e) {
            // 捕获到异常: Calculation failed!
            System.out.println("捕获到异常: " + e.getCause().getMessage());
        }
    }
}
