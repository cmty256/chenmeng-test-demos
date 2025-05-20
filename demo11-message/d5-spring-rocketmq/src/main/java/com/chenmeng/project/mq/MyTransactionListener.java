package com.chenmeng.project.mq;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.support.RocketMQUtil;
import org.springframework.messaging.Message;
import org.springframework.messaging.converter.StringMessageConverter;

/**
 * @author chenmeng
 */
@Slf4j
@RocketMQTransactionListener(rocketMQTemplateBeanName = "rocketMQTemplate")
public class MyTransactionListener implements RocketMQLocalTransactionListener {

    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object arg) {
        String destination = (String) arg;
        // 把spring的message转换成RocketMQ的message
        org.apache.rocketmq.common.message.Message message1 = RocketMQUtil.convertToRocketMessage(
                new StringMessageConverter(),
                "utf-8",
                destination,
                message
        );
        // 获取message1上的tag对内容
        String tags = message1.getTags();
        if (StringUtils.contains(tags, "TagA")) {
            log.info("执行本地事务，提交事务");
            // 返回提交事务状态，表示允许消费者消费该消息
            return RocketMQLocalTransactionState.COMMIT;
        } else if (StringUtils.contains(tags, "TagB")) {
            log.info("执行本地事务，回滚事务");
            // 返回回滚事务状态，表示该消息将被删除，不允许消费
            return RocketMQLocalTransactionState.ROLLBACK;
        } else {
            log.info("执行本地事务，中间状态");
            // 返回中间状态，表示需要回查才能确定状态
            return RocketMQLocalTransactionState.UNKNOWN;
        }
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        return null;
    }
}