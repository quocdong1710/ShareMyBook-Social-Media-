package demo.dto.request;

import java.time.LocalDate;

import demo.validation.DobConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // sử dụng Lombok để tự động tạo getter và setter
@NoArgsConstructor // tạo constructor không tham số
@AllArgsConstructor // tạo constructor có tham số
@Builder // sử dụng builder pattern để tạo đối tượng
public class ProfileCreationRequest {
    private String userId;
    private String firstName;
    private String lastName;
    @DobConstraint(min = 18, message = "INVALID_DOB")
    private LocalDate dob;

}
