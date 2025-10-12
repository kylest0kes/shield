package com.shield.server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
// import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

@Configuration
@EnableWebSocketMessageBroker
public class Config implements WebSocketMessageBrokerConfigurer {

    // Still needed:
    // implement more securtiy to be safe AF
    
    @Override
    public void registerStompEndpoints(@NonNull StompEndpointRegistry registry) {
        // setAllowedOriginPatters allows for all origins for simplicity; adjust for production use
        registry.addEndpoint("/shield").setAllowedOriginPatterns("*").withSockJS();
    }

    @Override
    public void configureMessageBroker(@NonNull MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");
        registry.setApplicationDestinationPrefixes("/app");
        registry.setUserDestinationPrefix("/user");
    }

    // implement size limits, timeouts, buffer sizes for Websocket frames in configureWebSocketTransport

    // @Override
    // public void configureWebSocketTransport(@NonNull WebSocketTransportRegistration registration) {
    //     // Example configurations; will adjust as needed
    //     registration.setMessageSizeLimit(128 * 1024); // 128 KB
    //     registration.setSendTimeLimit(20 * 1000); // 20 seconds
    //     registration.setSendBufferSizeLimit(512 * 1024); // 512 KB
    // }
}
