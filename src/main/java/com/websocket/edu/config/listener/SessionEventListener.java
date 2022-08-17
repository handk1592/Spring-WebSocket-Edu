package com.websocket.edu.config.listener;

import com.websocket.edu.service.RedisSubscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class SessionEventListener {

    @Autowired
    private RedisSubscriber redisSubscriber;

    @Autowired
    private RedisMessageListenerContainer redisMessageListener;

    @EventListener
    private void handleSessionConnected(SessionConnectedEvent event) {
        StompHeaderAccessor accessor
                = MessageHeaderAccessor.getAccessor(
                (GenericMessage)event.getMessage().getHeaders().get("simpConnectMessage"), StompHeaderAccessor.class);

        String userId = accessor.getUser().getName();
        String sessionId = accessor.getSessionId();

        System.out.println("connected userId = " + userId);
        System.out.println("세션 연결할때 구독하즈아");

        ChannelTopic topic = new ChannelTopic(userId);
        redisMessageListener.addMessageListener(redisSubscriber, topic);
    }

    @EventListener
    public void onDisconnectEvent(SessionDisconnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());

        String userId = accessor.getUser().getName();
        String sessionId = accessor.getSessionId();

        System.out.println("disconnected userId = " + userId);
        System.out.println("세션 연결할때 구독해제 하즈아");

        ChannelTopic topic = new ChannelTopic(userId);
        redisMessageListener.removeMessageListener(redisSubscriber, topic);
    }
}
