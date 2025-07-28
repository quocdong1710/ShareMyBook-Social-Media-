package demo.service;

import java.util.HashSet;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import demo.dto.request.UserCreationRequest;
import demo.dto.request.UserUpdateRequest;
import demo.dto.response.UserResponse;
import demo.entity.User;
import demo.enums.Role;
import demo.exception.AppException;
import demo.exception.ErrorCode;
import demo.mapper.ProfileMapper;
import demo.mapper.UserMapper;
import demo.repository.UserRepository;
import demo.repository.httpclient.ProfileClient;
import event.dto.NotificationEvent;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    ProfileClient profileClient;
    PasswordEncoder passwordEncoder;
    ProfileMapper profileMapper;
    KafkaTemplate<String, Object> kafkaTemplate; // Kafka template for sending messages

    public UserResponse createUser(UserCreationRequest request) {

        User user = userMapper.toUser(request); // ánh xạ từ UserCreationRequest sang User
        user.setPassword(passwordEncoder.encode(request.getPassword())); // mã hóa mật khẩu

        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name()); // thêm vai trò USER vào danh sách vai trò của người dùng

        // user.setRoles(roles); // thiết lập vai trò cho người dùng
        try {
            user = userRepository.save(user); // lưu user vào cơ sở dữ liệus
        } catch (DataIntegrityViolationException e) {
            throw new AppException(ErrorCode.USER_EXISTED); // nếu tên người dùng đã tồn tại, ném ngoại lệ
        }
        var profileRequest = profileMapper.toProfileCreationRequest(request);
        profileRequest.setUserId(user.getId());

        var profileResponse = profileClient.createProfile(profileRequest);
        log.info("Profile created: {}", profileResponse);

        NotificationEvent notificationEvent = NotificationEvent.builder()
                .channel("EMAIL")
                .recipient(request.getEmail())
                .subject("Welcome to ShareMyBooks")
                .body("Hello " + request.getUserName() + ", welcome to ShareMyBooks!")
                .build();

        kafkaTemplate.send("nofitication-delivery", notificationEvent);

        return userMapper.toUserResponse(user); // ánh xạ từ User sang UserResponse

    }

    // cập nhật thông tin người dùng theo userId
    public UserResponse updateUser(String userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        userMapper.updateUser(user, request); // cập nhật thông tin người dùng từ UserUpdateRequest

        return userMapper.toUserResponse(userRepository.save(user));
    }

    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext(); // lấy thông tin người dùng từ SecurityContext
        String userName = context.getAuthentication().getName();

        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return userMapper.toUserResponse(user); // ánh xạ từ User sang UserResponse

    }

    // lấy tất cả người dùng
    @PreAuthorize("hasRole('ADMIN')") // chỉ cho phép người dùng có vai trò ADMIN truy cập
    public List<UserResponse> getUser() {
        log.info("im method getUser() in UserService called"); // ghi log khi gọi phương thức
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList(); // lấy danh sách người dùng
                                                                                           // và ánh xạ sang
                                                                                           // UserResponse
    }

    // tìm người dùng theo userId
    @PostAuthorize("returnObject.userName == authentication.name or hasRole('ADMIN')") // chỉ cho phép người dùng có vai
                                                                                       // trò ADMIN hoặc
    public UserResponse getUser(String userId) {
        return userMapper.toUserResponse(
                userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found")));
    }

    // xóa người dùng theo userId
    public void deleteUser(String userId) {
        userRepository.deleteById(userId); // xóa người dùng theo userId
    }

}
