package com.websocket.edu.service;

import com.websocket.edu.domain.SocketUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RedisPublisher {
    private final RedisTemplate<String, Object> redisTemplate;

    public void publish(ChannelTopic topic, SocketUser user) {
        redisTemplate.convertAndSend(topic.getTopic(), user);
    }
}
