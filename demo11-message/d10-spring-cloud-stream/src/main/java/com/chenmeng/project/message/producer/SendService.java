package com.chenmeng.project.message.producer;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;

import javax.annotation.Resource;

/**
 * 发送业务类（生产者）
 *
 * @author chenmeng
 */
@EnableBinding(Source.class)
public class SendService {

    @Resource
    private Source source;

    public void sendMsg(String msg) {
        source.output()
                .send(MessageBuilder
                        .withPayload(msg)
                        .build());
    }

    public void sendBody(Object object) {
        source.output()
                .send(MessageBuilder
                        .withPayload(object)
                        .build());
    }
}