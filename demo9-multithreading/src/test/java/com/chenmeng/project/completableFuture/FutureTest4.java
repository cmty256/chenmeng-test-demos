package com.chenmeng.project.completableFuture;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 模拟异步链路：
 * - 外层：按网关并发，每个网关一个 CompletableFuture，设 orTimeout
 * - 内层（ONVIF实时状态）：逐设备并发，每个设备一个 CompletableFuture，设 orTimeout
 * <p>
 * 场景：三个设备同属一个网关，设备A响应5ms，设备B响应10s，设备C响应5ms，connectTimeout=3s
 * 验证：设备A、C的结果不应被设备B的超时丢弃
 *
 * @author chenmeng
 */
@Slf4j
public class FutureTest4 {

    /** 模拟 connect.timeout.platformStatus */
    private static final long CONNECT_TIMEOUT = 3L;

    /** 线程池1（模拟 statusTaskExecutor） */
    private final ExecutorService statusTaskExecutor1 = Executors.newFixedThreadPool(4, r -> {
        Thread t = new Thread(r, "status1-pool");
        t.setDaemon(true);
        return t;
    });

    /** 线程池2（模拟 statusTaskExecutor） */
    private final ExecutorService statusTaskExecutor2 = Executors.newFixedThreadPool(4, r -> {
        Thread t = new Thread(r, "status2-pool");
        t.setDaemon(true);
        return t;
    });

    @Test
    public void testPartialTimeoutShouldNotLoseSuccessResult() {
        // 三个设备，同属一个网关
        List<FakeDevice> devices = Arrays.asList(
                new FakeDevice("device1", 5L),      // 5ms 响应
                new FakeDevice("device2", 10000L),  // 10s 响应
                new FakeDevice("device3", 5L)       // 5ms 响应
        );

        // ====== 修复前的逻辑（外层 orTimeout == 内层 orTimeout == 3s）======
        List<String> resultBefore = statusListV2_Before(devices);
        log.info("修复前返回: {}", resultBefore);

        // ====== 修复后的逻辑（外层 orTimeout = connectTimeout + 2，且 exceptionally 返回离线而非空）======
        List<String> resultAfter = statusListV2_After(devices);
        log.info("修复后返回: {}", resultAfter);
    }

    // ==================== 修复前 ====================
    private List<String> statusListV2_Before(List<FakeDevice> devices) {
        List<String> stautsList = Collections.synchronizedList(new LinkedList<>());

        CompletableFuture<List<String>> statusFuture = CompletableFuture
                .supplyAsync(() -> onvifRelDeviceStatus_Before(devices), statusTaskExecutor1)
                .orTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)          // 外层 3s 超时
                .exceptionally(throwable -> {
                    log.error("[修复前] 外层超时，丢弃所有结果", throwable);
                    return List.of();                                  // 返回空列表，已成功的结果也丢了
                })
                .whenComplete((result, throwable) -> {
                    if (result == null) return;
                    stautsList.addAll(result);
                });

        statusFuture.join();
        return stautsList;
    }

    private List<String> onvifRelDeviceStatus_Before(List<FakeDevice> devices) {
        List<CompletableFuture<String>> futures = devices.stream()
                .map(d -> CompletableFuture.supplyAsync(() -> {
                            sleep(d.delayMs);
                            return d.deviceId + "=online";
                        }, statusTaskExecutor1)
                        .orTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)  // 内层 3s 超时
                        .exceptionally(throwable -> {
                            log.error("[修复前] 设备 {} 查询超时", d.deviceId);
                            return null;
                        }))
                .collect(Collectors.toList());

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        return futures.stream()
                .map(CompletableFuture::join)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    // ==================== 修复后 ====================
    private List<String> statusListV2_After(List<FakeDevice> devices) {
        List<String> stautsList = Collections.synchronizedList(new LinkedList<>());

        // 外层异步调用网关查询，超时时间 > 内层逐设备超时，确保内层先完成
        long outerTimeout = CONNECT_TIMEOUT + 1;
        CompletableFuture<List<String>> statusFuture = CompletableFuture
                .supplyAsync(() -> onvifRelDeviceStatus_After(devices), statusTaskExecutor2)
                .orTimeout(outerTimeout, TimeUnit.SECONDS)
                .exceptionally(throwable -> {
                    log.error("[修复后] 外层超时", throwable);
                    return List.of();
                })
                .whenComplete((result, throwable) -> {
                    if (result == null) return;
                    stautsList.addAll(result);
                });

        statusFuture.join();
        return stautsList;
    }

    private List<String> onvifRelDeviceStatus_After(List<FakeDevice> devices) {
        List<CompletableFuture<String>> futures = devices.stream()
                .map(d -> CompletableFuture.supplyAsync(() -> {
                            sleep(d.delayMs);
                            return d.deviceId + "=online";
                        }, statusTaskExecutor2)
                        .orTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)  // 内层 3s 超时
                        .exceptionally(throwable -> {
                            log.error("[修复后] 设备 {} 查询超时，丢弃", d.deviceId);
                            return null;
                        }))
                .collect(Collectors.toList());

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        return futures.stream()
                .map(CompletableFuture::join)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    // ==================== 辅助 ====================
    private static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Data
    static class FakeDevice {
        final String deviceId;
        final long delayMs;  // 模拟网关响应耗时(ms)

        FakeDevice(String deviceId, long delayMs) {
            this.deviceId = deviceId;
            this.delayMs = delayMs;
        }
    }
}
