package com.chenmeng.project.threadPool;

import lombok.extern.slf4j.Slf4j;
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
}
