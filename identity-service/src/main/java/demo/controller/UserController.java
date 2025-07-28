package demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import demo.dto.request.APIResponse;
import demo.dto.request.UserCreationRequest;
import demo.dto.request.UserUpdateRequest;
import demo.dto.response.UserResponse;
import demo.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@Slf4j // sử dụng Lombok để tự động tạo logger
@RestController // đánh dấu đây là một controller trong Spring
@RequiredArgsConstructor // sử dụng Lombok để tự động tạo constructor với tất cả các trường final
@RequestMapping("/users") // hỗ trợ gọi API với đường dẫn /users
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    APIResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request) {
        return APIResponse.<UserResponse>builder()
                .result(userService.createUser(request))
                .build();
    }

    @GetMapping
    APIResponse<List<UserResponse>> getUser() {
        return APIResponse.<List<UserResponse>>builder()
                .result(userService.getUser())
                .build();
    }

    @GetMapping("/{userId}")
    APIResponse<UserResponse> getUser(@PathVariable("userId") String userId) {
        return APIResponse.<UserResponse>builder()
                .result(userService.getUser(userId))
                .build();
    }

    @GetMapping("/my-info") // đánh dấu đây là một phương thức xử lý GET request với tham số userId
    APIResponse<UserResponse> getMyInfo() {
        return APIResponse.<UserResponse>builder()
                .result(userService.getMyInfo()) // lấy người dùng theo userId
                .build(); // xây dựng APIResponse với người dùng tìm thấy
    }

    @PutMapping("/{userId}")
    APIResponse<UserResponse> updateUser(@PathVariable String userId, @RequestBody @Valid UserUpdateRequest request) {
        return APIResponse.<UserResponse>builder()
                .result(userService.updateUser(userId, request)) // cập nhật thông tin người dùng theo userId
                .build(); // xây dựng APIResponse với người dùng đã cập nhật

    }

    @DeleteMapping("/{userId}")
    APIResponse<String> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId); // xóa người dùng theo userId
        return APIResponse.<String>builder()
                .result("User deleted successfully") // trả về thông báo xóa thành công
                .build();
    }

}
