package com.chenmeng.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.*;
import org.springframework.util.backoff.BackOff;
import org.springframework.util.backoff.FixedBackOff;

/**
 * @author chenmeng
 */
@Configuration
public class KafkaConfiguration {

    /**
     * 增加消费异常的 ErrorHandler 处理器
     *
     * @param template
     * @return
     */
    @Bean
    @Primary // 标记为主要的错误处理器，Spring 在自动装配时会优先选择带有 @Primary 注解的 Bean
    public DefaultErrorHandler kafkaErrorHandler(KafkaTemplate<?, ?> template) {
        // 创建 DeadLetterPublishingRecoverer 对象：
        // 该恢复器用于将处理失败的消息发送到一个死信队列（Dead Letter Queue），
        // 这里使用 KafkaTemplate 作为参数，将失败的消息发送到另一个 Kafka 主题。
        ConsumerRecordRecoverer recoverer = new DeadLetterPublishingRecoverer(template);

        // 创建 FixedBackOff 对象：
        // FixedBackOff 定义了重试策略，重试的间隔和最大重试次数。
        // 这里设置了每次重试间隔为 10 秒（10 * 1000L），
        // 最大重试次数为 3 次，即如果消费失败，最多会尝试 3 次。
        BackOff backOff = new FixedBackOff(10 * 1000L, 3L);

        // 创建 DefaultErrorHandler 对象：
        // DefaultErrorHandler 是 Spring Kafka 用来处理消息消费失败的错误处理器，
        // 它结合了消息重试和消息发送到死信队列的机制。
        // 它使用上面创建的 recoverer 和 backOff 对象来定义处理失败消息时的行为。
        return new DefaultErrorHandler(recoverer, backOff);
    }


//    @Bean
//    @Primary
//    public BatchErrorHandler kafkaBatchErrorHandler() {
//        // 创建 SeekToCurrentBatchErrorHandler 对象
//        SeekToCurrentBatchErrorHandler batchErrorHandler = new SeekToCurrentBatchErrorHandler();
//        // 创建 FixedBackOff 对象
//        BackOff backOff = new FixedBackOff(10 * 1000L, 3L);
//        batchErrorHandler.setBackOff(backOff);
//        // 返回
//        return batchErrorHandler;
//    }

}
