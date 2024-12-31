package com.chenmeng.project.service;

import com.chenmeng.project.event.UserRegisterEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

/**
 * 事件监听器类（不需实现接口版）
 *
 * @author chenmeng
 */
@Service
public class InfoService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Order(2)
    @EventListener
    public void sendInfo(UserRegisterEvent event) {
        logger.info("[事件监听-sendInfo][给用户({}) 发送信息]", event.getUsername());
    }
}
