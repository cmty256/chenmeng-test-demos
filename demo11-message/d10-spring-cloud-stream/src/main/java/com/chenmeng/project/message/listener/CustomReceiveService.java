package com.chenmeng.project.message.listener;

import com.chenmeng.project.message.channel.CustomSinkChannel;
import com.chenmeng.project.message.dto.HumanDTO;
import com.chenmeng.project.message.dto.VehicleDTO;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;

/**
 * 自定义通道接收业务类（消费者）
 *
 * @author chenmeng
 */
@EnableBinding(CustomSinkChannel.class)
public class CustomReceiveService {

    @StreamListener(CustomSinkChannel.FACE_INPUT)
    public void receiveFace(Object payload) {
        System.out.println("Face payload = " + payload);
    }

    @StreamListener(CustomSinkChannel.HUMAN_INPUT)
    public void receiveHuman(HumanDTO payload) {
        System.out.println("Human payload = " + payload);
    }

    @StreamListener(CustomSinkChannel.VEHICLE_INPUT)
    public void receiveVehicle(VehicleDTO payload) {
        System.out.println("Vehicle payload = " + payload);
        // throw new RuntimeException("异常测试 -- 重试机制");
    }
}