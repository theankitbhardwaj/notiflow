package com.notiflow.emailworker.kafka;

import com.notiflow.emailworker.model.Notification;
import com.notiflow.emailworker.repository.NotificationRepository;
import com.notiflow.emailworker.enums.NotificationStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class NotificationConsumer {
    private final NotificationRepository notificationRepository;
    private final JavaMailSender mailSender;
    private static final String NotifCreatedTopic = "notification.created";

    @KafkaListener(topics = NotifCreatedTopic)
    public void processNotification(NotificationCreatedEvent event) {
        log.info("Received notification: {}", event.notificationId());

        try {
            SimpleMailMessage message =
                    new SimpleMailMessage();

            message.setTo(event.recipient());
            message.setFrom("mail@notiflow.com");

            message.setSubject(
                    "Notiflow Notification"
            );

            message.setText(
                    "Hello " +
                            event.payload().get("name")
            );

            mailSender.send(message);

            Notification notification =
                    notificationRepository.findById(
                            event.notificationId()
                    ).orElseThrow();

            notification.setStatus(
                    NotificationStatus.SENT
            );

            notificationRepository.save(notification);

            log.info(
                    "Email sent successfully: {}",
                    event.notificationId()
            );
        } catch (MailException ex) {
            log.error(
                    "Failed to process notification: {}",
                    event.notificationId(),
                    ex
            );

            Notification notification =
                    notificationRepository.findById(
                            event.notificationId()
                    ).orElseThrow();

            notification.setStatus(
                    NotificationStatus.FAILED
            );

            notificationRepository.save(notification);
        }
    }
}
