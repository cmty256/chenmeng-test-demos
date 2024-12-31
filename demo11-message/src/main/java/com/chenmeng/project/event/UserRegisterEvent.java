package com.chenmeng.project.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 用户注册事件
 *
 * @author chenmeng
 */
@Getter
public class UserRegisterEvent extends ApplicationEvent {

    /**
     * 用户名
     */
    private String username;

    public UserRegisterEvent(Object source) {
        super(source);
    }

    public UserRegisterEvent(Object source, String username) {
        super(source);
        this.username = username;
    }
}
