package com.chenmeng.project.handler;

import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * WebSocket 处理器（注解式写法）
 * 连接地址：ws://localhost:9118/websocket/ws/chat/{username}
 *
 * @author chenmeng
 */
@Component
@Slf4j
@EqualsAndHashCode()
@ServerEndpoint("/ws/chat/{username}")
public class ChatEndpointHandler {

    /**
     * 保存所有连接的客户端
     * CopyOnWrite 是读多写少场景的高效选择（大部分时间是广播消息 = 读操作）
     */
    private static final Set<ChatEndpointHandler> CLIENTS = new CopyOnWriteArraySet<>();

    /**
     * 当前会话
     */
    private Session session;

    /**
     * 用户名
     */
    private String username;

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) {
        this.session = session;
        this.username = username;
        CLIENTS.add(this);
        log.info("[ChatEndpoint] 连接建立: {} (sessionId={})", username, session.getId());
    }

    @OnMessage
    public void onMessage(String message) {
        log.info("[ChatEndpoint] 收到消息: {} -> {}", username, message);
        // 广播消息
        for (ChatEndpointHandler client : CLIENTS) {
            try {
                client.session.getBasicRemote()
                        .sendText("Echo from " + username + ": " + message);
            } catch (IOException e) {
                log.error("[ChatEndpoint] 发送消息失败", e);
            }
        }
    }

    @OnClose
    public void onClose() {
        CLIENTS.remove(this);
        log.info("[ChatEndpoint] 连接关闭: {}", username);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("[ChatEndpoint] 连接异常: {}", username, error);
    }
}