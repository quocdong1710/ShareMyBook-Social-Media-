package com.chat_service.service;

import java.util.Objects;

import org.springframework.stereotype.Service;

import com.chat_service.dto.request.IntrospectRequest;
import com.chat_service.dto.response.IntrospectResponse;
import com.chat_service.repository.httpclient.IdentityClient;

import feign.FeignException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class IdentityService {
    IdentityClient identityClient;

    public IntrospectResponse introspect(IntrospectRequest token) {
        try {
            var result = identityClient.introspect(token).getResult();
            if (Objects.isNull(result)) {
                return IntrospectResponse.builder().valid(false).build();
            }
            return result;
        } catch (FeignException e) {
            log.error("introspect error : {}", e.getMessage(), e);
            return IntrospectResponse.builder().valid(false).build();
        }
    }
}
