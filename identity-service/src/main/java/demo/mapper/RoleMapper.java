package demo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import demo.entity.Role;
import demo.dto.request.RoleRequest;
import demo.dto.response.RoleResponse;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}
