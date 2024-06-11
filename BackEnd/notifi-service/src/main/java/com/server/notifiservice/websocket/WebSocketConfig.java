package com.server.notifiservice.websocket;


import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Cấu hình MessageBroker để xác định nơi máy chủ gửi và nhận thông điệp.
        config.enableSimpleBroker("/topic");
        // Xác định endpoint để máy khách kết nối với WebSocket.
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Đăng ký một WebSocket endpoint cho máy khách kết nối.
        registry.addEndpoint("/ws").setAllowedOrigins("http://localhost:8001").withSockJS();
    }

}
