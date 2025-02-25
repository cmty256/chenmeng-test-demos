package com.chenmeng.project.message.producer;

import com.chenmeng.project.message.channel.CustomSourceChannel;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

import javax.annotation.Resource;

/**
 * 自定义通道发送业务类（生产者）
 *
 * @author chenmeng
 */
@EnableBinding(value = {CustomSourceChannel.class})
public class CustomSendService {

    @Resource
    private CustomSourceChannel sourceChannel;

    public void sendFaceMsg(String msg) {
        sourceChannel.faceOutput()
                .send(MessageBuilder
                        .withPayload(msg)
                        .build());
    }

    public void sendHumanMsg(String msg) {
        sourceChannel.humanOutput()
                .send(MessageBuilder
                        .withPayload(msg)
                        .build());
    }

    public void sendVehicleMsg(String msg) {
        sourceChannel.vehicleOutput()
                .send(MessageBuilder
                        .withPayload(msg)
                        .build());
    }
}
