package com.chenmeng.project.controller;

import com.chenmeng.project.message.dto.HumanDTO;
import com.chenmeng.project.message.dto.VehicleDTO;
import com.chenmeng.project.message.producer.CustomSendService;
import com.chenmeng.project.message.producer.SendService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author chenmeng
 */
@RestController
@RequestMapping("/send")
@RequiredArgsConstructor
public class StreamController {

    private final SendService sendService;
    private final CustomSendService customSendService;

    /**
     * 普通字符串消息发送
     * localhost:port/send/hello
     *
     * @param msg 消息
     */
    @GetMapping("/{msg}")
    public String send(@PathVariable("msg") String msg){
        sendService.sendMsg(msg);
        return "发送成功";
    }

    /**
     * 自定义消息发送
     * localhost:port/send/face/hello
     *
     * @param msg 消息
     */
    @GetMapping("/face/{msg}")
    public String sendFaceMsg(@PathVariable("msg") String msg){
        customSendService.sendFaceMsg(msg);
        return "发送成功-Face";
    }

    /**
     * 自定义请求体发送
     * localhost:port/send/human
     *
     * @param dto 消息
     */
    @PostMapping("/human")
    public String sendHumanMsg(@RequestBody HumanDTO dto){
        customSendService.sendHumanMsg(dto);
        return "发送成功-Human";
    }

    /**
     * 消费重试测试
     * localhost:port/send/vehicle
     *
     * @param dto 消息
     */
    @PostMapping("/vehicle")
    public String sendVehicleMsg(@RequestBody VehicleDTO dto){
        customSendService.sendVehicleMsg(dto);
        return "发送成功-vehicle";
    }
}