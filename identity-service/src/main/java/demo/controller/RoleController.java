package demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import demo.dto.request.APIResponse;
import demo.dto.request.RoleRequest;
import demo.dto.response.RoleResponse;
import demo.service.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import lombok.AccessLevel;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j // sử dụng Lombok để tự động tạo logger
@RestController // đánh dấu đây là một controller trong Spring
@RequiredArgsConstructor // sử dụng Lombok để tự động tạo constructor với tất cả các trường final
@RequestMapping("/roles") // hỗ trợ gọi API với đường dẫn /users
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE) // sử dụng Lombok để tự động tạo getter và setter
public class RoleController {
    RoleService roleService;

    @PostMapping()
    APIResponse<RoleResponse> create(@Valid @RequestBody RoleRequest roleRequest) {
        return APIResponse.<RoleResponse>builder()
                .result(roleService.create(roleRequest))
                .build();
    }

    @GetMapping("")
    APIResponse<List<RoleResponse>> getAll() {
        return APIResponse.<List<RoleResponse>>builder()
                .result(roleService.getAll())
                .build();
    }

    @DeleteMapping("/{role}")
    APIResponse<Void> delete(@PathVariable String role) {
        roleService.delete(role);
        return APIResponse.<Void>builder().build();

    }

}
