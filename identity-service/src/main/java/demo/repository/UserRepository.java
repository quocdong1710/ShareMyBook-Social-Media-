package demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import demo.entity.User;

@Repository // đánh dấu đây là một repository trong Spring
public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByUserName(String userName); // kiểm tra xem người dùng có tồn

    Optional<User> findByUserName(String username);
}
