package com.chenmeng.project.producer;

import com.chenmeng.project.message.Demo04Message;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;

/**
 * @author chenmeng
 */
@Component
public class Demo04Producer {

    @Resource
    private KafkaTemplate<Object, Object> kafkaTemplate;

    public SendResult<Object, Object> syncSend(Integer id) throws ExecutionException, InterruptedException {
        // 创建 Demo04Message 消息
        Demo04Message message = new Demo04Message();
        message.setId(id);
        message.setMark("说明");
        // 同步发送消息
        return kafkaTemplate.send(Demo04Message.TOPIC, message).get();
    }

}
