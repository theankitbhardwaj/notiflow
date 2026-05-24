package com.notiflow.notification.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.notiflow.notification.dto.CreateNotificationRequest;
import com.notiflow.notification.model.Notification;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import com.notiflow.notification.service.NotificationService;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Notification createNotification(@Valid @RequestBody CreateNotificationRequest request) throws Exception {
        return notificationService.createNotification(request);
    }
}
