package com.websocket.edu.hello;


import com.websocket.edu.domain.SocketUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HelloController {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @RequestMapping("/chat/send")
    @ResponseBody
    public String test(HttpServletRequest request, @RequestBody SocketUser user) {

        simpMessagingTemplate.convertAndSendToUser(user.getUserName(), "/queue/info2", user.getMessage());

        return "success";
    }

}
