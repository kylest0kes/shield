package com.shield.server.model;

import java.time.Instant;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class MessagePayload {

    @NotBlank(message = "Message content cannot be blank")
    @Size(max = 2000, message = "Message content too long")
    private String content;

    @NotNull(message = "Timestamp is required")
    private Instant timestamp;

    @NotBlank(message = "Sender ID is required")
    private String senderId;

    private String recipientId;

    private String messageType;
    
    private String groupId;
}