package demo.mapper;

import org.mapstruct.Mapper;

import demo.dto.request.ProfileCreationRequest;
import demo.dto.request.UserCreationRequest;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    ProfileCreationRequest toProfileCreationRequest(UserCreationRequest request);
}
