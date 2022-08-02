package com.websocket.edu.config.listener;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class SessionEventListener {
    @EventListener
    private void handleSessionConnected(SessionConnectedEvent event) {
        StompHeaderAccessor accessor
                = MessageHeaderAccessor.getAccessor(
                (GenericMessage)event.getMessage().getHeaders().get("simpConnectMessage"), StompHeaderAccessor.class);
        String userId = accessor.getUser().getName();
        String sessionId = accessor.getSessionId();
        System.out.println("userId = " + userId);
    }

    @EventListener
    public void onDisconnectEvent(SessionDisconnectEvent event) {
        System.out.println("세션 끊깁니다.");
    }
}
