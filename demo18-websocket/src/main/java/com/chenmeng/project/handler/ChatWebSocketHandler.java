package com.chenmeng.project.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * WebSocket 处理器（实现接口写法）
 * 地址：ws://localhost:9118/websocket/ws/chat2
 *
 * @author chenmeng
 */
@Slf4j
public class ChatWebSocketHandler implements WebSocketHandler {

    /**
     * 保存所有已连接的会话
     */
    private static final Set<WebSocketSession> SESSIONS = new CopyOnWriteArraySet<>();

    @Override
    public void afterConnectionEstablished(@NonNull WebSocketSession session) {
        SESSIONS.add(session);
        log.info("[ChatWebSocketHandler] 连接建立: {}", session.getId());
    }

    @Override
    public void handleMessage(@NonNull WebSocketSession session,
                              @NonNull WebSocketMessage<?> message) throws IOException {
        String payload = message.getPayload().toString();
        log.info("[ChatWebSocketHandler] 收到消息: {}", payload);

        // 广播消息给所有连接的客户端
        for (WebSocketSession s : SESSIONS) {
            if (s.isOpen()) {
                s.sendMessage(new TextMessage("Echo: " + payload));
            }
        }
    }

    @Override
    public void handleTransportError(@NonNull WebSocketSession session,
                                     @NonNull Throwable exception) throws IOException {
        log.error("[ChatWebSocketHandler] 连接异常: {}", session.getId(), exception);
        if (session.isOpen()) {
            session.close();
        }
        SESSIONS.remove(session);
    }

    @Override
    public void afterConnectionClosed(@NonNull WebSocketSession session,
                                      @NonNull CloseStatus closeStatus) {
        SESSIONS.remove(session);
        log.info("[ChatWebSocketHandler] 连接关闭: {}, 原因: {}", session.getId(), closeStatus);
    }

    @Override
    public boolean supportsPartialMessages() {
        // 是否支持分片消息，这里简单返回 false
        return false;
    }
}
