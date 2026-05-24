package com.notiflow.notification.service;

import java.time.Instant;
import org.springframework.stereotype.Service;
import com.notiflow.notification.dto.CreateNotificationRequest;
import com.notiflow.notification.enums.NotificationStatus;
import com.notiflow.notification.model.Notification;
import com.notiflow.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

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
        // Set notification properties based on the request
        return savedNotification;
    }
}
