package demo.mapper;

import org.mapstruct.Mapper;
import demo.dto.request.PermissionRequest;
import demo.dto.response.PermissionResponse;
import demo.entity.Permission;

@Mapper(componentModel = "spring")
public interface PermissionMapper {

    Permission toPermission(PermissionRequest request); // ánh xạ từ PermissionRequest sang Permission

    PermissionResponse toPermissionResponse(Permission permission); // ánh xạ từ Permission sang PermissionResponse

}
