package demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import demo.dto.request.APIResponse;
import demo.dto.request.PermissionRequest;
import demo.dto.response.PermissionResponse;
import demo.service.PermissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j // sử dụng Lombok để tự động tạo logger
@RestController // đánh dấu đây là một controller trong Spring
@RequiredArgsConstructor // sử dụng Lombok để tự động tạo constructor với tất cả các trường final
@RequestMapping("/permissions") // hỗ trợ gọi API với đường dẫn /users
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class PermissionController {
    PermissionService permissionService;

    @PostMapping()
    APIResponse<PermissionResponse> create(@RequestBody @Valid PermissionRequest request) {
        return APIResponse.<PermissionResponse>builder()
                .result(permissionService.create(request))
                .build();
    }

    @GetMapping()
    public String getMethodName(@RequestParam String param) {
        return new String();
    }

    APIResponse<List<PermissionResponse>> getAll() {
        return APIResponse.<List<PermissionResponse>>builder()
                .result(permissionService.getAll())
                .build();
    }

    @DeleteMapping("/{permission}")
    APIResponse<Void> delete(@PathVariable String permission) {
        permissionService.delete(permission);
        return APIResponse.<Void>builder()
                .build();
    }

}
