package demo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
// cung cấp userName và password để xác thực người dùng
public class AuthenticationRequest {
    private String userName; // tên đăng nhập của người dùng
    private String password; // mật khẩu của người dùng
}
