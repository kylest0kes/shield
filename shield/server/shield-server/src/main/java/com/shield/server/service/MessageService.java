package com.shield.server.service;

import com.shield.server.model.MessagePayload;

public interface MessageService {
    void processAndRouteMessage(String senderId, MessagePayload payload);
    void saveOfflineMessage(String recipientId, MessagePayload payload);
}
