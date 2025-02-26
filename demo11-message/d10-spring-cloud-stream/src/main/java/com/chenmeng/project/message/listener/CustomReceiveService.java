package com.chenmeng.project.message.listener;

import com.chenmeng.project.message.channel.CustomSinkChannel;
import com.chenmeng.project.message.dto.HumanDTO;
import com.chenmeng.project.message.dto.VehicleDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;

/**
 * 自定义通道接收业务类（消费者）
 *
 * @author chenmeng
 */
@Slf4j
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
        log.info("Vehicle payload = {}", payload);
        // 无论设置了多少次重试，最终都只会打印一次异常
        throw new RuntimeException("异常测试 -- 重试机制");
    }
}