package com.notification_service.controller;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.event.dto.NotificationEvent;
import com.notification_service.dto.request.Recipient;
import com.notification_service.dto.request.SendEmailRequest;
import com.notification_service.service.EmailService;

import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Component
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotificationController {

    EmailService emailService;

    @KafkaListener(topics = "nofitication-delivery")
    public void listenNotificationDelivery(NotificationEvent message) {
        log.info("Received message from Kafka: {}", message);
        emailService.sendEmail(SendEmailRequest.builder()
                .to(Recipient.builder()
                        .email(message.getRecipient())
                        .build())
                .subject(message.getSubject())
                .htmlContent(message.getBody())
                .build());
    }
}
