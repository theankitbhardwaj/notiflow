package com.notiflow.notification.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.notiflow.notification.model.Notification;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {

}
