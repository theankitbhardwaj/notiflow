package com.notiflow.notification.dto;

import java.util.Map;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateNotificationRequest(
        @NotBlank String channel,
        @NotBlank @Email String recipient,
        @NotBlank String template,
        @NotNull Map<String, Object> payload) {
}