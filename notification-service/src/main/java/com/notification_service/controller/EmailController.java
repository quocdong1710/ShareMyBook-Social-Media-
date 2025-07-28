package com.notification_service.controller;

import org.springframework.web.bind.annotation.RestController;

import com.notification_service.dto.request.SendEmailRequest;
import com.notification_service.dto.response.APIResponse;
import com.notification_service.dto.response.EmailResponse;
import com.notification_service.service.EmailService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailController {
    EmailService emailService;

    @PostMapping("/email/send")
    APIResponse<EmailResponse> sendEmail(@RequestBody SendEmailRequest request) {
        return APIResponse.<EmailResponse>builder()
                .result(emailService.sendEmail(request))
                .build();
    }

}
