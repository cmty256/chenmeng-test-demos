package com.chenmeng.project.message.listener;

import com.chenmeng.project.message.channel.CustomSinkChannel;
import com.chenmeng.project.message.dto.HumanDTO;
import com.chenmeng.project.message.dto.VehicleDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.context.IntegrationContextUtils;
import org.springframework.messaging.support.ErrorMessage;

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

    // /**
    //  * 写法测试无效，暂时不知道原因
    //  * 局部的异常处理：通过订阅指定错误 Channel
    //  *
    //  * @param errorMessage
    //  */
    // @ServiceActivator(inputChannel = "error.vehicle_topic.vehicle_consumer_group")
    // public void handleError(ErrorMessage errorMessage) {
    //     log.error("[handleError][订阅到错误Channel：{}]", "error.vehicle_topic.vehicle_consumer_group");
    //     log.error("[handleError][payload：{}]", errorMessage.getPayload().getMessage());
    //     log.error("[handleError][originalMessage：{}]", errorMessage.getOriginalMessage());
    //     log.error("[handleError][headers：{}]", errorMessage.getHeaders());
    // }

    /**
     * 全局的异常处理：通过订阅全局错误 Channel
     *
     * @param errorMessage
     */
    @StreamListener(IntegrationContextUtils.ERROR_CHANNEL_BEAN_NAME) // errorChannel
    public void globalHandleError(ErrorMessage errorMessage) {
        log.error("[globalHandleError][payload：{}]", errorMessage.getPayload().getMessage());
        log.error("[globalHandleError][originalMessage：{}]", errorMessage.getOriginalMessage());
        log.error("[globalHandleError][headers：{}]", errorMessage.getHeaders());

        // 可存储到数据库，后续处理
    }
}