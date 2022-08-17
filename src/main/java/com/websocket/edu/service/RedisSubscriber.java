package com.websocket.edu.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.websocket.edu.domain.SocketUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RedisSubscriber implements MessageListener {

    private final RedisTemplate redisTemplate;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            SocketUser socketUser = (SocketUser) redisTemplate.getDefaultSerializer().deserialize(message.getBody());
            simpMessagingTemplate.convertAndSendToUser(socketUser.getUserName(), "/queue/info3", socketUser.getMessage());

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
