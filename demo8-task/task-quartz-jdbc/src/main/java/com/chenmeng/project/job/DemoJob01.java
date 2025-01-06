package com.chenmeng.project.job;

import com.chenmeng.project.service.DemoService;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import javax.annotation.Resource;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author chenmeng
 */
@DisallowConcurrentExecution
public class DemoJob01 extends QuartzJobBean {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private DemoService demoService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        logger.info("[executeInternal][我开始执行了, demoService 为 ({})]", demoService);
    }

}
