package com.shield.server.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.SockJsServiceRegistration;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.StompWebSocketEndpointRegistration;

public class ConfigTest {

    private Config config;

    @Mock
    private StompEndpointRegistry stompEndpointRegistry;

    @Mock
    private MessageBrokerRegistry messageBrokerRegistry;

    @Mock
    StompWebSocketEndpointRegistration stompWebSocketEndpointRegistration;

    @Mock
    SockJsServiceRegistration sockJsServiceRegistration;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        config = new Config();

        when(stompEndpointRegistry.addEndpoint(any(String[].class))).thenReturn(stompWebSocketEndpointRegistration);

        when(stompWebSocketEndpointRegistration.setAllowedOriginPatterns(any(String[].class)))
                .thenReturn(stompWebSocketEndpointRegistration);

        when(stompWebSocketEndpointRegistration.withSockJS()).thenReturn(sockJsServiceRegistration);
    }

    @Test
    public void testRegisterStompEndpoints() {
        config.registerStompEndpoints(stompEndpointRegistry);

        verify(stompEndpointRegistry, times(1)).addEndpoint("/shield");
    }

    @Test
    public void testConfigureMessageBroker() {
        config.configureMessageBroker(messageBrokerRegistry);

        verify(messageBrokerRegistry, times(1)).enableSimpleBroker("/topic");
        verify(messageBrokerRegistry, times(1)).setApplicationDestinationPrefixes("/app");
        verify(messageBrokerRegistry, times(1)).setUserDestinationPrefix("/user");
    }

    // Uncomment and implement this test when the method is activated
    // @Test
    // public void testConfigureWebSocketTransport() {
    // }
}
