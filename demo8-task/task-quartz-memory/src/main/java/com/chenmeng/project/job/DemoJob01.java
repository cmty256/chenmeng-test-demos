package com.chenmeng.project.job;

import com.chenmeng.project.service.DemoService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import javax.annotation.Resource;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 每次 DemoJob01 都会被 Quartz 创建出一个新的 Job 对象，执行任务。
 *
 * @author chenmeng
 */
public class DemoJob01 extends QuartzJobBean {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    // 计数器
    private final AtomicInteger counts = new AtomicInteger();

    @Resource
    private DemoService demoService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        // counts.incrementAndGet() 的值一直为 1，说明每次 DemoJob01 都是新创建的。
        logger.info("[executeInternal][定时第 ({}) 次执行, demoService 为 ({})]", counts.incrementAndGet(),
                demoService);
    }

}
