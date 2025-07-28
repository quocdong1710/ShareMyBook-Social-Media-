package com.profile_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.profile_service.dto.request.ProfileCreationRequest;
import com.profile_service.dto.request.UpdateProfileRequest;
import com.profile_service.dto.response.UserProfileResponse;
import com.profile_service.entity.UserProfile;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {

    UserProfile toUserProfile(ProfileCreationRequest request);

    UserProfileResponse toUserProfileResponse(UserProfile userProfile);

    void update(@MappingTarget UserProfile entity, UpdateProfileRequest request);
}
