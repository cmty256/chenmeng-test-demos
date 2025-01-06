package com.chenmeng.project.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @author chenmeng
 */
public class DemoJob02 extends QuartzJobBean {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        logger.info("[DemoJob02-executeInternal][我开始执行了]");
    }

}
