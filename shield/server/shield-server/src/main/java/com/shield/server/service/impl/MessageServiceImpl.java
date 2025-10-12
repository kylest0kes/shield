package com.shield.server.service.impl;

import org.springframework.stereotype.Service;

import com.shield.server.service.MessageService;

@Service
public class MessageServiceImpl implements MessageService {
    @Override
    public void processAndRouteMessage(String senderId, com.shield.server.model.MessagePayload payload) {
        // Implementation for processing and routing the message
    }

    @Override
    public void saveOfflineMessage(String recipientId, com.shield.server.model.MessagePayload payload) {
        // Implementation for saving offline messages
    }    
}
