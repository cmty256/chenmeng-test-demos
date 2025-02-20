package com.chenmeng.project.service;

import com.chenmeng.project.event.UserRegisterEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;

/**
 * 事件发布者类
 *
 * @author chenmeng
 */
@Service
public class UserService implements ApplicationEventPublisherAware {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void register(String username) {
        // ... 执行注册逻辑
        logger.info("[事件发布-register][执行用户({}) 的注册逻辑]", username);

        // ... 发布
        applicationEventPublisher.publishEvent(new UserRegisterEvent(this, username));
    }
}
