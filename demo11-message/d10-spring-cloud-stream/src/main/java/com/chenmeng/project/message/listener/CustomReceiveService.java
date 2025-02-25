package com.chenmeng.project.message.listener;

import com.chenmeng.project.message.channel.CustomSinkChannel;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

/**
 * 接收业务类（消费者）
 *
 * @author chenmeng
 */
@EnableBinding(CustomSinkChannel.class)
public class CustomReceiveService {

    @StreamListener(CustomSinkChannel.FACE_INPUT)
    public void receiveFace(Object payload) {
        System.out.println(payload);
    }

    @StreamListener(CustomSinkChannel.HUMAN_INPUT)
    public void receiveHuman(String msg) {
        System.out.println(msg);
    }

    @StreamListener(CustomSinkChannel.VEHICLE_INPUT)
    public void receiveVehicle(String msg) {
        System.out.println(msg);
    }
}