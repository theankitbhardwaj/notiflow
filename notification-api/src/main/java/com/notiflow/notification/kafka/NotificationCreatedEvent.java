package com.notiflow.notification.kafka;

import java.util.Map;
import java.util.UUID;

public record NotificationCreatedEvent(
        UUID notificationId,
        String channel,
        String recipient,
        String template,
        Map<String, Object> payload) {
}