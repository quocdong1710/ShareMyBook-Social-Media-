package com.chat_service.repository.httpclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.chat_service.dto.request.IntrospectRequest;
import com.chat_service.dto.response.APIResponse;
import com.chat_service.dto.response.IntrospectResponse;

@FeignClient(name = "identity-client", url = "${app.services.identity.url}")
public interface IdentityClient {
    @PostMapping("/auth/introspect")
    APIResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest token);
}
