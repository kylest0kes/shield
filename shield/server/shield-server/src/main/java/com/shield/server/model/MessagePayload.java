package com.shield.server.model;

import java.time.Instant;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MessagePayload {

    @NotBlank(message = "Message content cannot be blank")
    @Size(max = 2000, message = "Message content too long")
    private String content;

    @NotNull(message = "Timestamp is required")
    private Instant timestamp;

    @NotBlank(message = "Sender ID is required")
    private String senderId;

    @NotBlank(message = "Recipient ID is required")
    private String recipientId;

    private String messageType;
}