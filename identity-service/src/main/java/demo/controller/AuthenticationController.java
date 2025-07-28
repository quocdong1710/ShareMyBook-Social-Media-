package demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nimbusds.jose.JOSEException;

import demo.dto.request.APIResponse;
import demo.dto.request.AuthenticationRequest;
import demo.dto.request.IntrospectRequest;
import demo.dto.request.LogoutRequest;
import demo.dto.request.RefreshRequest;
import demo.dto.response.AuthenticationResponse;
import demo.dto.response.IntrospectResponse;
import demo.service.AuthenticationService;
import lombok.RequiredArgsConstructor;

import java.text.ParseException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/auth") // hỗ trợ gọi API với đường dẫn /auth
@RequiredArgsConstructor
public class AuthenticationController {
        private final AuthenticationService authencationService;

        @PostMapping("/token")
        APIResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
                var result = authencationService.authenticate(request);
                return APIResponse.<AuthenticationResponse>builder()
                                .result(result)
                                .build();
        }

        @PostMapping("/introspect")
        APIResponse<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request)
                        throws JOSEException, ParseException {
                var result = authencationService.introspect(request);
                return APIResponse.<IntrospectResponse>builder()
                                .result(result)
                                .build();
        }

        // logout
        @PostMapping("/logout")
        APIResponse<Void> logout(@RequestBody LogoutRequest request)
                        throws JOSEException, ParseException {
                authencationService.logout(request);
                return APIResponse.<Void>builder()
                                .build();
        }

        @PostMapping("/refreshToken")
        APIResponse<AuthenticationResponse> refresh(@RequestBody RefreshRequest request)
                        throws JOSEException, ParseException {
                var result = authencationService.refreshToken(request);
                return APIResponse.<AuthenticationResponse>builder()
                                .result(result)
                                .build();
        }

}
