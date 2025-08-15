package com.chenmeng.project.config;

import com.chenmeng.project.handler.ChatTextWebSocketHandler;
import com.chenmeng.project.handler.ChatWebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * websocket 配置类
 *
 * @author chenmeng
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    /**
     * （注解写法）创建一个ServerEndpointExporter对象，这个对象会自动注册使用了@ServerEndpoint注解的类
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    /**
     * 注册处理器
     */
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry
                // 添加处理器
                .addHandler(new ChatTextWebSocketHandler(), "/ws/chat")
                .addHandler(new ChatWebSocketHandler(), "/ws/chat2")
                // 允许跨域
                .setAllowedOrigins("*");
    }
}