package com.chenmeng.project.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author chenmeng
 */
@Component
public class DemoJob {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final AtomicInteger counts = new AtomicInteger();

    /**
     * 每隔 2 秒执行一次
     */
    @Scheduled(fixedRate = 2000)
    public void execute() {
        logger.info("[execute][定时第 ({}) 次执行]", counts.incrementAndGet());
       // try {
       //     Thread.sleep(10000L);
       // } catch (InterruptedException e) {
       //     e.printStackTrace();
       // }
    }

}
