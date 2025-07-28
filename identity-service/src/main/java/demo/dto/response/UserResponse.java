package demo.dto.response;

import java.time.LocalDate;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private String id;
    private String userName;
    private String firstName;
    private String lastName;
    private LocalDate dob;
    private Set<RoleResponse> roles; // danh sách các vai trò của người dùngs
}
