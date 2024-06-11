package com.server.notifiservice.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketService {
    @Autowired
    private  SimpMessagingTemplate messagingTemplate;

    public void sendDataToClient(String topic,String message) {
        messagingTemplate.convertAndSend(topic, message);
    }
}
