package com.api_gateway.service;

import org.springframework.stereotype.Service;

import com.api_gateway.dto.request.IntrospectRequest;
import com.api_gateway.dto.response.APIResponse;
import com.api_gateway.dto.response.IntrospectResponse;
import com.api_gateway.repository.IdentityClient;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class IdentityService {
    IdentityClient identityClient;

    public Mono<APIResponse<IntrospectResponse>> introspectToken(String token) {
        return identityClient.introspectToken(IntrospectRequest.builder()
                .token(token)
                .build());
    }

}
