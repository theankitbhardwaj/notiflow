package com.notiflow.notification.service;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.notiflow.notification.dto.CreateNotificationRequest;
import com.notiflow.notification.enums.NotificationStatus;
import com.notiflow.notification.kafka.NotificationCreatedEvent;
import com.notiflow.notification.model.Notification;
import com.notiflow.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final KafkaTemplate<String, NotificationCreatedEvent> kafkaTemplate;
    @Value("${spring.kafka.topic-names.notification-created}")
    private String notfCreatedTopic;

    public Notification createNotification(CreateNotificationRequest request) {
        Notification notification = Notification.builder()
                .channel(request.channel())
                .recipient(request.recipient())
                .template(request.template())
                .payload(request.payload())
                .status(NotificationStatus.PENDING)
                .createdAt(Instant.now())
                .build();

        Notification savedNotification = notificationRepository.save(notification);

        NotificationCreatedEvent event = new NotificationCreatedEvent(savedNotification.getId(),
                savedNotification.getChannel(), savedNotification.getRecipient(), savedNotification.getTemplate(),
                savedNotification.getPayload());

        kafkaTemplate.send(notfCreatedTopic, savedNotification.getId().toString(), event);

        return savedNotification;
    }
}
