package com.api_gateway.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
// cung cấp userName và password để xác thực người dùng
public class IntrospectRequest {
    private String token; // token được cung cấp từ client để xác thực người dùng
}
