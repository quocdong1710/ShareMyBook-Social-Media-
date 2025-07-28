package com.file_service.exception;

import java.util.Map;
import java.util.Objects;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.file_service.dto.response.APIResponse;
import jakarta.validation.ConstraintViolation;
import lombok.extern.slf4j.Slf4j;

@Slf4j // sử dụng Lombok để tự động tạo logger
@ControllerAdvice // định nghĩa một lớp xử lý ngoại lệ toàn cục.
public class GlobalExceptionHandler {

    private static final String MIN_ATTRIBUTE = "min";

    @ExceptionHandler(value = RuntimeException.class) // xử lý ngoại lệ RuntimeException
    ResponseEntity<APIResponse> handlerRuntimeException(RuntimeException exception) {
        APIResponse apiResponse = new APIResponse();

        apiResponse.setStatusCode(500); // mã trạng thái HTTP 500 Internal Server Error
        apiResponse.setMessage(exception.getMessage()); // lấy thông điệp lỗi từ ngoại lệ

        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<APIResponse> handlerAppException(AppException exception) {
        ErrorCode errorCode = exception.getErrorCode(); // lấy mã lỗi từ ngoại lệ
        APIResponse apiResponse = new APIResponse();

        apiResponse.setStatusCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());

        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = Exception.class)
    ResponseEntity<APIResponse> handlerException(Exception exception) {
        APIResponse apiResponse = new APIResponse();

        apiResponse.setStatusCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        apiResponse.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());

        return ResponseEntity.badRequest().body(apiResponse);
    }

    @SuppressWarnings("unchecked")
    @ExceptionHandler(value = MethodArgumentNotValidException.class) // xử lý ngoại lệ MethodArgumentNotValidException
    ResponseEntity<APIResponse> handlerValidationException(MethodArgumentNotValidException exception) {
        @SuppressWarnings("null")
        String enumKey = exception.getFieldError().getDefaultMessage(); // lấy mã lỗi từ trường không hợp lệ
        ErrorCode errorCode = ErrorCode.INVALID_KEY;
        Map<String, Object> attributes = null;

        try {
            errorCode = ErrorCode.valueOf(enumKey);

            var constraintViolation = exception.getBindingResult().getAllErrors().get(0)
                    .unwrap(ConstraintViolation.class);
            attributes = constraintViolation.getConstraintDescriptor().getAttributes();

            log.info(attributes.toString());

        } catch (IllegalArgumentException e) {

        }

        APIResponse apiResponse = new APIResponse();
        apiResponse.setStatusCode(errorCode.getCode()); // đặt mã trạng thái HTTP
        apiResponse.setMessage(Objects.nonNull(attributes) ? mapAttribute(errorCode.getMessage(), attributes)
                : errorCode.getMessage()); // đặt thông điệp lỗi

        return ResponseEntity.status(errorCode.getStatusCode()).body(apiResponse);
    }

    @ExceptionHandler(value = AccessDeniedException.class) // xử lý ngoại lệ AccessDeniedException
    ResponseEntity<APIResponse> handlerAccessDeniedException(AccessDeniedException exception) {
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED; // sử dụng mã lỗi ACCESS_DENIED

        return ResponseEntity.status(errorCode.getStatusCode()).body(
                APIResponse.builder()
                        .statusCode(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build());
    }

    private String mapAttribute(String messages, Map<String, Object> attributes) {
        String minValue = String.valueOf(attributes.get(MIN_ATTRIBUTE));

        return messages.replace("{" + MIN_ATTRIBUTE + "}", minValue);
    }
}
