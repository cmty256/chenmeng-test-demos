package com.chenmeng.project.service;

import com.chenmeng.project.event.UserRegisterEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 事件监听器类（实现接口版）
 *
 * @author chenmeng
 */
@Service
public class EmailService implements ApplicationListener<UserRegisterEvent> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    @Async // 可以不加，锦上添花，设置 @Async 注解，声明异步执行。毕竟实际场景下，发送邮件可能比较慢，又是非关键逻辑。
    public void onApplicationEvent(UserRegisterEvent event) {
        logger.info("[事件监听-onApplicationEvent][给用户({}) 发送邮件]", event.getUsername());
    }
}
