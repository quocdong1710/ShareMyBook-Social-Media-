package com.profile_service.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.profile_service.dto.request.SearchUserRequest;
import com.profile_service.dto.response.APIResponse;
import com.profile_service.dto.response.UserProfileResponse;
import com.profile_service.service.UserProfileService;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class UserProfileController {
    UserProfileService userProfileService;

    @GetMapping("/users/{profileId}")
    APIResponse<UserProfileResponse> getProfile(@PathVariable String profileId) {
        return APIResponse.<UserProfileResponse>builder()
                .result(userProfileService.getProfile(profileId))
                .build();
    }

    @GetMapping("/users")
    APIResponse<List<UserProfileResponse>> getAllProfiles() {
        return APIResponse.<List<UserProfileResponse>>builder()
                .result(userProfileService.getAllProfiles())
                .build();
    }

    @GetMapping("/users/my-profile")
    APIResponse<UserProfileResponse> getMyProfile() {
        return APIResponse.<UserProfileResponse>builder()
                .result(userProfileService.getProfile(null))
                .build();
    }

    @PutMapping("/users/avatar")
    APIResponse<UserProfileResponse> updateAvatar(@RequestParam("file") MultipartFile file) {
        return APIResponse.<UserProfileResponse>builder()
                .result(userProfileService.updateAvatar(file))
                .build();
    }

    @PostMapping("/users/search")
    APIResponse<List<UserProfileResponse>> search(@RequestBody SearchUserRequest request) {
        return APIResponse.<List<UserProfileResponse>>builder()
                .result(userProfileService.search(request))
                .build();
    }
}
