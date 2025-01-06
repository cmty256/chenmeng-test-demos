package com.chenmeng.project;

import com.chenmeng.project.job.DemoJob01;
import com.chenmeng.project.job.DemoJob02;
import org.junit.jupiter.api.Test;
import org.mockito.internal.util.collections.Sets;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Scheduler 手动设置
 *
 * @author chenmeng
 */
@SpringBootTest
public class QuartzSchedulerTest {

    @Autowired
    private Scheduler scheduler;

    @Test
    public void addDemoJob01Config() throws SchedulerException {
        // 创建 JobDetail
        JobDetail jobDetail = JobBuilder.newJob(DemoJob01.class)
                .withIdentity("demoJob01") // 名字为 demoJob01
                .storeDurably() // 没有 Trigger 关联的时候任务是否被保留。因为创建 JobDetail 时，还没 Trigger 指向它，所以需要设置为 true ，表示保留。
                .build();
        // 创建 Trigger
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(5) // 频率。
                .repeatForever(); // 次数。
        Trigger trigger = TriggerBuilder.newTrigger()
                .forJob(jobDetail) // 对应 Job 为 demoJob01
                .withIdentity("demoJob01Trigger") // 名字为 demoJob01Trigger
                .withSchedule(scheduleBuilder) // 对应 Schedule 为 scheduleBuilder
                .build();
        // 添加调度任务（将 JobDetail 和 Trigger 持久化到数据库）
        scheduler.scheduleJob(jobDetail, trigger);
    }

    @Test
    public void addDemoJob02Config() throws SchedulerException {
        // 创建 JobDetail
        JobDetail jobDetail = JobBuilder.newJob(DemoJob02.class)
                .withIdentity("demoJob02") // 名字为 demoJob02
                .storeDurably() // 没有 Trigger 关联的时候任务是否被保留。因为创建 JobDetail 时，还没 Trigger 指向它，所以需要设置为 true ，表示保留。
                .build();
        // 创建 Trigger
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0/10 * * * * ? *");
        Trigger trigger = TriggerBuilder.newTrigger()
                .forJob(jobDetail) // 对应 Job 为 demoJob01
                .withIdentity("demoJob02Trigger") // 名字为 demoJob01Trigger
                .withSchedule(scheduleBuilder) // 对应 Schedule 为 scheduleBuilder
                .build();
        // 添加调度任务（将 JobDetail 和 Trigger 持久化到数据库）
        scheduler.scheduleJob(jobDetail, trigger);

        // 如果想要覆盖数据库中的 Quartz 定时任务的配置，可以调用此方法，传入 replace = true 进行覆盖配置。
        // scheduler.scheduleJob(jobDetail, Sets.newSet(trigger), true);
    }

}
