package com.chenmeng.project.controller;

import com.chenmeng.project.message.producer.SendService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author chenmeng
 */
@RestController
@RequestMapping("/send")
public class StreamController {

    @Resource
    private SendService sendService;

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

}