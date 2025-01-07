package com.chenmeng.project.config;

import com.chenmeng.project.job.DemoJob01;
import com.chenmeng.project.job.DemoJob02;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Bean 自动设置
 *
 * @author chenmeng
 */
@Configuration
public class ScheduleConfiguration {

    public static class DemoJob01Configuration {

        /**
         * 创建 DemoJob01 的 JobDetail Bean 对象
         *
         * @return JobDetail
         */
        @Bean
        public JobDetail demoJob01() {
            return JobBuilder.newJob(DemoJob01.class)
                    .withIdentity("demoJob01") // 名字为 demoJob01
                    .storeDurably() // 没有 Trigger 关联的时候任务是否被保留。因为创建 JobDetail 时，还没 Trigger 指向它，所以需要设置为 true ，表示保留。
                    .build();
        }

        /**
         * 创建 DemoJob01 的 Trigger Bean 对象。
         * 其中，我们使用 SimpleScheduleBuilder 简单的调度计划的构造器，创建了每 5 秒执行一次，无限重复的调度计划
         *
         * @return Trigger
         */
        @Bean
        public Trigger demoJob01Trigger() {
            // 简单的调度计划的构造器
            SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                    .withIntervalInSeconds(5) // 频率 - 每 5 秒。
                    .repeatForever(); // 次数 - 无限重复。
            // Trigger 构造器
            return TriggerBuilder.newTrigger()
                    .forJob(demoJob01()) // 对应 Job 为 demoJob01
                    .withIdentity("demoJob01Trigger") // 名字为 demoJob01Trigger
                    .withSchedule(scheduleBuilder) // 对应 Schedule 为 scheduleBuilder
                    .build();
        }

    }

    public static class DemoJob02Configuration {

        /**
         * 创建 DemoJob02 的 JobDetail Bean 对象
         *
         * @return JobDetail
         */
        @Bean
        public JobDetail demoJob02() {
            return JobBuilder.newJob(DemoJob02.class)
                    .withIdentity("demoJob02") // 名字为 demoJob02
                    .storeDurably() // 没有 Trigger 关联的时候任务是否被保留。因为创建 JobDetail 时，还没 Trigger 指向它，所以需要设置为 true ，表示保留。
                    .build();
        }

        /**
         * 创建 DemoJob02 的 Trigger Bean 对象。
         * 其中，我们使用 CronScheduleBuilder 基于 Quartz Cron 表达式的调度计划的构造器，
         * 创建了每第 10 秒执行一次的调度计划。
         *
         * @return Trigger
         */
        @Bean
        public Trigger demoJob02Trigger() {
            //  基于 Quartz Cron 表达式的调度计划的构造器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0/3 * * * * ? *");
            // Trigger 构造器
            return TriggerBuilder.newTrigger()
                    .forJob(demoJob02()) // 对应 Job 为 demoJob02
                    .withIdentity("demoJob02Trigger") // 名字为 demoJob02Trigger
                    .withSchedule(scheduleBuilder) // 对应 Schedule 为 scheduleBuilder
                    .build();
        }

    }

}
