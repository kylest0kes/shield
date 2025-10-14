package com.shield.server.controller;

import java.security.Principal;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.shield.server.exception.UnauthorizedException;
import com.shield.server.model.MessagePayload;
import com.shield.server.service.MessageService;

import jakarta.validation.Valid;

@Controller
public class UserConnectionController {

    private static final Logger logger = LoggerFactory.getLogger(UserConnectionController.class);
    
    // In-memory user-to-session map for rapid dev; replace with distributed store for production
    private final ConcurrentMap<String, String> userSessions = new ConcurrentHashMap<>();

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private MessageService messageService;

    /**
     * Handles user connect event - map user ID to session ID For production,
     * integrate with Spring Session + Redis
     */
    @EventListener
    public void handleWebSocketConnect(SessionConnectedEvent e) {
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(e.getMessage());
        Principal userPrincipal = sha.getUser();
        String userId = (userPrincipal != null) ? userPrincipal.getName() : null;

        if (userId != null) {
            String sessionId = sha.getSessionId();
            userSessions.put(userId, sessionId);
            logger.info("User connected: {} with session ID: {}", userId, sessionId);
        }
    }

    /**
     * Handles user disconnect event - removes user ID and session ID
     */
    @EventListener
    public void handleWebSocketDisconnect(SessionDisconnectEvent e) {
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(e.getMessage());
        Principal userPrincipal = sha.getUser();
        String userId = (userPrincipal != null) ? userPrincipal.getName() : null;

        if (userId != null) {
            userSessions.remove(userId);
            logger.info("User {} disconnected", userId);
        }
    }

    /**
     * Handle inbound messages from clients at destination /app/send
     */
    @MessageMapping("/send")
    public void handleIncomingMessage(@Valid MessagePayload message, Principal principal) {
        if (principal == null) {
            // Handle unauthenticated user case
            throw new UnauthorizedException("User not authenticated");
        }

        String senderId = principal.getName();
        messageService.processAndRouteMessage(senderId, message);
    }

    /**
     * Utility method to send a message to a connected user or group Uses
     * MessageService if needed
     */
    public void sendMessage(MessagePayload message) {
        if (message.getGroupId() != null && !message.getGroupId().isBlank()) {
            // Send to group topic
            messagingTemplate.convertAndSend("/topic/" + message.getGroupId(), message);
        } else if (message.getRecipientId() != null && !message.getRecipientId().isBlank()) {
            // Send to individual user
            String sessionId = userSessions.get(message.getRecipientId());
            if (sessionId != null) {
                messagingTemplate.convertAndSendToUser(sessionId, "/queue/messages", message);
            } else {
                messageService.saveOfflineMessage(message.getRecipientId(), message);
            }
        } else {
            logger.error("No valid recipient or group for message");
        }
    }

}
