package demo.configuration;

import java.util.HashSet;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import demo.enums.Role;
import demo.entity.User;
import demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor

@Configuration
public class ApplicationInitConfig {

    private final PasswordEncoder passwordEncoder;

    @Bean
    @ConditionalOnProperty(prefix = "spring", value = "datasource.driverClassName", havingValue = "com.mariadb.jdbc.Driver")
    ApplicationRunner applicationRunner(UserRepository userRepository) {
        log.info("init application");
        return args -> {
            if (userRepository.findByUserName("admin").isEmpty()) {
                var roles = new HashSet<String>();
                roles.add(Role.ADMIN.name());

                User user = User.builder()
                        .userName("admin")
                        .password(passwordEncoder.encode("admin"))
                        // .roles(roles)
                        .build();

                userRepository.save(user);
                log.warn("admin user created with username: admin and password: admin");
            }
        };
    }
}
