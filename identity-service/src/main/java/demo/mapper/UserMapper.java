package demo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import demo.dto.request.UserCreationRequest;
import demo.dto.request.UserUpdateRequest;
import demo.dto.response.UserResponse;
import demo.entity.User;

@Mapper(componentModel = "spring") // sử dụng MapStruct để ánh xạ giữa các đối tượng
public interface UserMapper {

    User toUser(UserCreationRequest request);

    UserResponse toUserResponse(User user);

    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
