package com.websocket.edu.controller;


import com.websocket.edu.domain.SocketUser;
import com.websocket.edu.service.RedisPublisher;
import com.websocket.edu.service.RedisSubscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpSession;

@Controller
public class ChatController {
    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);

    private static final String SESSION = "TEMP_SESSION";

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private RedisPublisher redisPublisher;

    @Autowired
    private RedisSubscriber redisSubscriber;

    @Autowired
    private RedisMessageListenerContainer redisMessageListener;

//    @Autowired
//    private ChatRoomRepository chatRoomRepository;

    @MessageMapping("chat")
    @SendTo("/topic/message")
    public String chat(String message, SimpMessageHeaderAccessor messageHeaderAccessor, StompHeaderAccessor accessor) {
        HttpSession session = (HttpSession) messageHeaderAccessor.getSessionAttributes().get(SESSION);
        System.out.println("Client to Server chat Message: " + message + ", >> sessionId: " +  accessor.getSessionId());

        return message;
    }

    @MessageMapping("info")
    @SendToUser("/queue/info")
    public String info(String message, SimpMessageHeaderAccessor messageHeaderAccessor, StompHeaderAccessor accessor) {
        HttpSession session = (HttpSession) messageHeaderAccessor.getSessionAttributes().get(SESSION);

        System.out.println("Client to Server info Message: " + message + ", >> sessionId: " +  accessor.getSessionId());

        return message;
    }

    @MessageMapping("info2")
    public void info2(SocketUser user, SimpMessageHeaderAccessor messageHeaderAccessor, StompHeaderAccessor accessor) {
        HttpSession session = (HttpSession) messageHeaderAccessor.getSessionAttributes().get(SESSION);

        System.out.println("Client to Server info2 UserName: " + user.getUserName() + ", >> sessionId: " +  accessor.getSessionId());

        simpMessagingTemplate.convertAndSendToUser(user.getUserName(), "/queue/info2", user.getMessage());
    }

    /**
     * websocket "/pub/chat/message"??? ???????????? ???????????? ????????????.
     */
    @MessageMapping("/redis/message")
    public void message(SocketUser user) {
        ChannelTopic test = new ChannelTopic(user.getUserName());

        redisPublisher.publish(test, user);
    }

}
