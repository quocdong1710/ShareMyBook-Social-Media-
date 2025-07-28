package demo.configuration;

import org.springframework.web.filter.CorsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import demo.enums.Role;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

        @Autowired
        private CustomJwtDecoder customJwtDecoder;

        private String[] PUBLIC_ENDPOINT = {
                        "/users/register", // Allow POST requests to /users without authentication
                        "/auth/token", // Allow POST requests to /auth/token without authentication
                        "/auth/introspect", // Allow POST requests to /auth/introspect without authentication
                        "/auth/logout",
                        "/auth/refreshToken",
        };

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
                httpSecurity.authorizeHttpRequests(
                                requests -> requests.requestMatchers(HttpMethod.POST, PUBLIC_ENDPOINT).permitAll()
                                                .requestMatchers(HttpMethod.GET, "/users").hasRole(Role.ADMIN.name())
                                                .anyRequest().authenticated()); // Allow POST requests to
                                                                                // PUBLIC_ENDPOINT without
                                                                                // authentication
                httpSecurity.oauth2ResourceServer(
                                httpSecurityOAuth2ResourceServerConfigurer -> httpSecurityOAuth2ResourceServerConfigurer
                                                .jwt(jwtConfigurer -> jwtConfigurer.decoder(customJwtDecoder)
                                                                .jwtAuthenticationConverter(
                                                                                jwtAuthenticationConverter()))
                                                .authenticationEntryPoint(new JwtAuthenticationEntryPoint())); // Enable
                // JWT
                // authentication

                httpSecurity.csrf(AbstractHttpConfigurer::disable); // Disable CSRF for simplicity, not recommended for
                                                                    // production

                return httpSecurity.build();
        }

        @Bean
        public CorsFilter corsFilter() {
                CorsConfiguration corsConfiguration = new CorsConfiguration();
                corsConfiguration.addAllowedOrigin("localhost:3000"); // Allow requests from localhost:3000
                corsConfiguration.addAllowedMethod("*"); // Allow all HTTP methods
                corsConfiguration.addAllowedHeader("/**"); // Allow all headers

                UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
                urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration); // Apply CORS
                                                                                                     // configuration

                return new CorsFilter(urlBasedCorsConfigurationSource);
        }

        @Bean
        JwtAuthenticationConverter jwtAuthenticationConverter() {
                JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
                jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_"); // Prefix for scopes in JWT

                JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
                jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter); // Set
                return jwtAuthenticationConverter;
        }

        @Bean
        PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder(10);
        }
}