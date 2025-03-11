package com.chenmeng.common.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.*;

/**
 * 全局异步任务配置类
 *
 * @author chenmeng
 */
@Slf4j
@EnableAsync
@Configuration
public class GlobalAsyncConfig implements AsyncConfigurer {

    @Bean(name = "myExecutor")
    public ThreadPoolTaskExecutor myExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(50);
        executor.setQueueCapacity(1000);
        executor.setThreadNamePrefix("myExecutor-");
        executor.setKeepAliveSeconds(60);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

    /**
     * 创建自定义异步线程池
     *
     * @return Executor
     */
    @Bean(name = "asyncExecutor")
    public Executor asyncExecutor() {
        // 1、核心线程数
        int corePoolSize = 10;
        // 2、最大线程数
        int maxPoolSize = 50;
        // 3、非核心线程空闲存活时间
        long keepAliveTime = 60L;
        // 4、时间单位: 秒
        TimeUnit unit = TimeUnit.SECONDS;
        // 5、工作队列
        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();
        // 6、设置线程工厂（线程名称格式）
        ThreadFactory threadFactory = new ThreadFactoryBuilder()
                .setNameFormat("async-pool-%d")
                .build();
        // 7、设置拒绝策略
        ThreadPoolExecutor.CallerRunsPolicy rejectedExecutionHandler = new ThreadPoolExecutor.CallerRunsPolicy();

        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                corePoolSize,
                maxPoolSize,
                keepAliveTime,
                unit,
                workQueue,
                threadFactory,
                rejectedExecutionHandler
        );

        // 允许核心线程超时
        executor.allowCoreThreadTimeOut(true);

        return executor;
    }

    @Override
    public Executor getAsyncExecutor() {
        // 返回自定义线程池
        return asyncExecutor();
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        // 设置异步执行时的异常处理器
        return new GlobalAsyncUncaughtExceptionHandler();
    }

    public static class GlobalAsyncUncaughtExceptionHandler implements AsyncUncaughtExceptionHandler {
        @Override
        public void handleUncaughtException(Throwable ex, Method method, @NotNull Object... params) {
            log.error("[handleUncaughtException][method({}) params({}) 发生异常]", method.getName(), Arrays.deepToString(params), ex);
        }
    }
}