package demo.dto.request;

import java.time.LocalDate;

import demo.validation.DobConstraint;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // sử dụng Lombok để tự động tạo getter và setter
@NoArgsConstructor // tạo constructor không tham số
@AllArgsConstructor // tạo constructor có tham số
@Builder // sử dụng builder pattern để tạo đối tượng
public class UserCreationRequest {
    @Size(min = 3, max = 20, message = "USERNAME_INVALID")
    private String userName;

    @Size(min = 8, message = "PASSWORD_INVALID")
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    @DobConstraint(min = 18, message = "INVALID_DOB")
    private LocalDate dob;

}
