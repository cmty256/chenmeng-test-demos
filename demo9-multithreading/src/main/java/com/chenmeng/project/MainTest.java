package com.chenmeng.project;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONException;
import cn.hutool.json.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author chenmeng
 */
@Slf4j
public class MainTest {

    // 线程池大小
    private static final int THREAD_POOL_SIZE = 5;
    // 批量处理大小
    private static final int BATCH_SIZE = 2;
    // 最大记录数量
    private static final int MAX_RECORDS = 10;

    public static void main(String[] args) throws InterruptedException {
        DateTime startTime = new DateTime();
        log.info("开始执行任务，时间：{}", startTime);

        ExecutorService executorService = new ThreadPoolExecutor(
                THREAD_POOL_SIZE,
                THREAD_POOL_SIZE,
                0L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(),
                new BasicThreadFactory.Builder().namingPattern("thread-%d").build()
        );
        AtomicInteger processedRecords = new AtomicInteger(0);
        List<Integer> allRecords = Collections.synchronizedList(new ArrayList<>());

        int pageIndex = 1;
        AtomicBoolean hasMoreData = new AtomicBoolean(true);

        while (hasMoreData.get()) {
            JSONObject liftParam = new JSONObject();
            liftParam.putOnce("pageIndex", pageIndex);

            // 异步获取数据
            executorService.submit(() -> {
                try {
                    JSONObject liftResult = getDate(liftParam);

                    if (liftResult == null || liftResult.getInt("status") != 200) {
                        hasMoreData.set(false);
                        return;
                    }

                    JSONArray liftArray = liftResult.getJSONObject("data").getJSONArray("result");
                    if (liftArray == null || liftArray.isEmpty()) {
                        hasMoreData.set(false);
                        return;
                    }

                    List<Integer> records = new ArrayList<>();
                    for (int i = 0; i < Math.min(BATCH_SIZE, liftArray.size()); i++) {
                        records.add(liftArray.getInt(i));
                    }

                    synchronized (allRecords) {
                        allRecords.addAll(records);
                        int currentSize = processedRecords.addAndGet(records.size());
                        if (currentSize >= MAX_RECORDS) {
                            log.info("达到最大记录数限制，终止任务");
                            hasMoreData.set(false);
                        }
                    }
                } catch (Exception e) {
                    log.error("处理数据异常：", e);
                    hasMoreData.set(false);
                }
            });

            pageIndex++;
            if (pageIndex > 10) { // 模拟结束条件
                hasMoreData.set(false);
            }
        }

        executorService.shutdown();
        try {
            boolean terminated = executorService.awaitTermination(1, TimeUnit.HOURS);
            if (!terminated) {
                log.info("线程池未能在1小时内终止所有任务");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("等待线程池终止时被中断");
        }

        DateTime endTime = new DateTime();
        long duration = DateUtil.between(startTime, endTime, DateUnit.SECOND);
        log.info("执行完毕, 耗时：{}秒, 数量：{}", duration, allRecords.size());
    }

    private static JSONObject getDate(JSONObject liftParam) throws JSONException {
        int pageIndex = liftParam.getInt("pageIndex");
        if (pageIndex >= 10) { // 模拟数据结束
            return null;
        }
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < BATCH_SIZE; i++) {
            jsonArray.put(new JSONObject().putOnce("id", i).putOnce("name", "name" + (pageIndex * BATCH_SIZE + i)));
        }
        return new JSONObject().putOnce("status", 200).putOnce("data", new JSONObject().putOnce("result", jsonArray));
    }
}
