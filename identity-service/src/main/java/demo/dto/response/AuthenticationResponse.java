package demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
// kiem tra xem người dùng đã xác thực thành công hay chưa
public class AuthenticationResponse {
    private String token;
    private boolean authenticated; // true nếu người dùng đã xác thực thành công, false nếu không
}
