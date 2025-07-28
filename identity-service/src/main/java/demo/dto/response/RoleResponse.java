package demo.dto.response;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleResponse {
    private String name; // tên quyền
    private String description; // mô tả quyền, có thể để trống
    private Set<PermissionResponse> permissions;
}