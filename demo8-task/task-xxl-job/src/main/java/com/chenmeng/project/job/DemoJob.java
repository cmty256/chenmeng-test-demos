package com.chenmeng.project.job;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
     * BEAN 模式（方法形式）
     * 注解配置：
     * - 为Job方法添加注解 "@XxlJob(value="自定义jobhandler名称", init = "JobHandler初始化方法", destroy = "JobHandler销毁方法")"，
     * - 注解value值对应的是调度中心新建任务的JobHandler属性的值。
     */
    @XxlJob("demoJob")
    public void execute() throws Exception {
        // 业务打印日志，不能在调度中心查看
        logger.info("[execute][定时第 ({}) 次执行]", counts.incrementAndGet());

        // 官方打印日志，可在调度中心查看
        XxlJobHelper.log("[demoJob][定时第 ({}) 次执行]", counts.incrementAndGet());
    }
}
