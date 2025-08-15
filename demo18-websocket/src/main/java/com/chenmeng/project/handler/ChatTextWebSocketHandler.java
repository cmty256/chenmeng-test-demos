package com.chenmeng.project.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * WebSocket 处理器（继承 Handler 写法）
 * 连接地址：ws://localhost:9118/websocket/ws/chat
 *
 * @author chenmeng
 */
@Slf4j
public class ChatTextWebSocketHandler extends TextWebSocketHandler {

    private static final CopyOnWriteArrayList<WebSocketSession> SESSIONS = new CopyOnWriteArrayList<>();

    /**
     * 连接建立后调用
     */
    @Override
    public void afterConnectionEstablished(@NonNull WebSocketSession session) {
        SESSIONS.add(session);
        log.info("[ChatWebSocketHandler]连接建立: {}", session.getId());
    }

    /**
     * 处理发送来的消息
     */
    @Override
    protected void handleTextMessage(@NonNull WebSocketSession session, TextMessage message) throws Exception {
        log.info("[ChatWebSocketHandler]收到消息: {}", message.getPayload());
        for (WebSocketSession s : SESSIONS) {
            s.sendMessage(new TextMessage("Echo: " +  message.getPayload()));
        }
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        super.handleMessage(session, message);
    }

    /**
     * 连接关闭后调用
     */
    @Override
    public void afterConnectionClosed(@NonNull WebSocketSession session, @NonNull CloseStatus status) {
        SESSIONS.remove(session);
        log.info("[ChatWebSocketHandler]连接关闭: {}", session.getId());
    }

    /**
     * 连接异常时调用
     */
    @Override
    public void handleTransportError(@NonNull WebSocketSession session, @NonNull Throwable exception) throws Exception {
        log.info("[ChatWebSocketHandler]连接异常: {}", session.getId());
        session.close();
        SESSIONS.remove(session);
        super.handleTransportError(session, exception);
    }
}