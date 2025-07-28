package demo.repository.httpclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import demo.configuration.AuthenticationRequestInterceptor;
import demo.dto.request.APIResponse;
import demo.dto.request.ProfileCreationRequest;
import demo.dto.response.UserProfileResponse;

@FeignClient(name = "profile-service", url = "${app.services.profile}", configuration = {
        AuthenticationRequestInterceptor.class })
public interface ProfileClient {
    @PostMapping(value = "/internal/users", produces = MediaType.APPLICATION_JSON_VALUE)
    APIResponse<UserProfileResponse> createProfile(@RequestBody ProfileCreationRequest request);
}
