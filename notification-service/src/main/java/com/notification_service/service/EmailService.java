package com.notification_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.notification_service.dto.request.EmailRequest;
import com.notification_service.dto.request.SendEmailRequest;
import com.notification_service.dto.request.Sender;
import com.notification_service.dto.response.EmailResponse;
import com.notification_service.exception.AppException;
import com.notification_service.exception.ErrorCode;
import com.notification_service.repository.EmailClient;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailService {
    EmailClient emailClient;
    String apiKey = "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";

    public EmailResponse sendEmail(SendEmailRequest request) {
        EmailRequest emailRequest = EmailRequest.builder()
                .sender(Sender.builder()
                        .name("Dong Dep Trai")
                        .email("dongdeptrai0206@gmail.com")
                        .build())
                .to(List.of(request.getTo()))
                .subject(request.getSubject())
                .htmlContent(request.getHtmlContent())
                .build();
        try {
            return emailClient.sendEmail(apiKey, emailRequest);
        } catch (Exception e) {
            throw new AppException(ErrorCode.CANNOT_SEND_EMAIL);
        }
    }
}
