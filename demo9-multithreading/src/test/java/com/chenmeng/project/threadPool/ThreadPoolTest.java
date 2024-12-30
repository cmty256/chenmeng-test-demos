package com.chenmeng.project.threadPool;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.threads.ThreadPoolExecutor;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

/**
 * @author chenmeng
 */
@Slf4j
public class ThreadPoolTest {

    @Test
    void contextLoads() {
        // 创建含有3个线程的线程池
        ExecutorService threadPool = Executors.newFixedThreadPool(3);

        // 提交3个任务到线程池中
        for (int i = 0; i < 3; i++) {
            final int taskNo = i;
            threadPool.execute(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("执行任务{}", taskNo);
            });
        }

        // 关闭线程池
        threadPool.shutdown();
        System.out.println("线程池已关闭");
        // 如果线程池还没达到Terminated状态，说明线程池中还有任务没有执行完，则继续循环等待线程池执行完任务
        while (!threadPool.isTerminated()) {

        }
    }

    /**
     * Executor 示例
     */
    @Test
    void testExecutor() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        // 使用 execute()
        executorService.execute(() -> System.out.println("Executing a simple task"));

        // 使用 submit() with Runnable
        Future<?> future1 = executorService.submit(() -> System.out.println("Submitting a task without result"));
        future1.get(); // 等待任务完成，但不关心结果

        // 使用 submit() with Callable
        Future<Integer> future2 = executorService.submit(() -> {
            System.out.println("Submitting a task with result");
            return 42;
        });
        Integer result = future2.get(); // 获取任务结果
        System.out.println("Task result: " + result);

        executorService.shutdown();

        // 输出
        /*
        Executing a simple task
        Submitting a task without result
        Submitting a task with result
        Task result: 42
        */
    }

    /**
     * Executor 示例
     */
    @Test
    void testExecutor2() {
        // 创建一个线程池
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        // 创建实现Runnable接口的任务
        Runnable task1 = () -> {
            System.out.println("Task 1 is running on thread: " + Thread.currentThread().getName());
        };

        // 创建实现Callable接口的任务
        Callable<String> task2 = () -> {
            System.out.println("Task 2 is running on thread: " + Thread.currentThread().getName());
            return "Task 2 Result";
        };

        try {
            // 执行Runnable任务
            executorService.execute(task1);

            // 提交Callable任务，并获取Future对象
            Future<String> future = executorService.submit(task2);

            // 主线程等待Callable任务执行完成，并获取结果
            String result = future.get();
            System.out.println("Task 2 Result: " + result);

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            // 关闭线程池
            executorService.shutdown();
        }

        // 输出
        /*
        Task 1 is running on thread: pool-1-thread-1
        Task 2 is running on thread: pool-1-thread-2
        Task 2 Result: Task 2 Result
        */
    }

    /**
     * 通过 ThreadPoolExecutor 构造函数创建（推荐）
     */
    @Test
    void testCreateThreadPool() {
        // 创建一个固定大小为3的线程池
        ThreadPoolExecutor fixedPool = new ThreadPoolExecutor(
                3, // 核心线程数
                3, // 最大线程数
                0L, TimeUnit.MILLISECONDS, // 空闲线程存活时间（设置为 0L 意味着非核心线程在完成当前任务后将立即终止，不会等待任何额外的时间。）
                new LinkedBlockingQueue<>(10), // 阻塞队列，容量为10
                new ThreadPoolExecutor.CallerRunsPolicy() // 拒绝策略
        );

        // 创建一个单一线程的线程池
        ThreadPoolExecutor singlePool = new ThreadPoolExecutor(
                1, // 核心线程数
                1, // 最大线程数
                0L, TimeUnit.MILLISECONDS, // 空闲线程存活时间
                new LinkedBlockingQueue<>(), // 无界阻塞队列
                new ThreadPoolExecutor.AbortPolicy() // 拒绝策略
        );

        // 创建一个可缓存的线程池
        ThreadPoolExecutor cachedPool = new ThreadPoolExecutor(
                0, // 核心线程数
                Integer.MAX_VALUE, // 最大线程数
                60L, TimeUnit.SECONDS, // 空闲线程存活时间
                new SynchronousQueue<>(), // 同步队列
                new ThreadPoolExecutor.CallerRunsPolicy() // 拒绝策略
        );

        // 创建一个支持定时任务的线程池，指定核心线程数为2
        ScheduledThreadPoolExecutor scheduledPool = new ScheduledThreadPoolExecutor(2);
    }

    /**
     * 通过 Executor 框架的工具类 Executors 来创建（不推荐，可能导致 OOM）
     */
    @Test
    void testCreateThreadPool2() {
        // 固定大小的线程池
        ExecutorService fixedPool = Executors.newFixedThreadPool(3);
        // 单一线程的线程池
        ExecutorService singlePool = Executors.newSingleThreadExecutor();
        // 可缓存的线程池
        ExecutorService cachedPool = Executors.newCachedThreadPool();
        // 定时任务线程池
        ScheduledExecutorService scheduledPool = Executors.newScheduledThreadPool(2);
    }

    @Test
    void testCreateThreadPool3() {
        // 创建一个自定义的 ThreadPoolExecutor
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                2, // 核心线程数
                4, // 最大线程数
                60L, TimeUnit.SECONDS, // 空闲线程存活时间
                new LinkedBlockingQueue<>(10), // 阻塞队列，容量为10
                new ThreadPoolExecutor.CallerRunsPolicy() // 拒绝策略
        );

        // 提交任务给线程池
        for (int i = 0; i < 15; i++) {
            int taskId = i;
            executor.submit(() -> {
                System.out.println("Executing Task " + taskId);
                try {
                    Thread.sleep(2000); // 模拟任务执行时间
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        // 关闭线程池
        executor.shutdown();

        try {
            // 等待所有任务完成，最多等待60秒
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                System.out.println("Tasks did not terminate in the specified time.");
            } else {
                System.out.println("All tasks have been completed.");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
