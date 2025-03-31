package com.yuu.admin_service.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.config.Customizer; // Import Customizer

@Configuration // 🛠 Đánh dấu class này là cấu hình bảo mật
public class SecurityConfig {

    // Khai báo danh sách user tạm thời trong bộ nhớ (In-Memory Authentication)
    @Bean
    public UserDetailsService userDetailsService() {
        // 🔹 Tạo user "admin" có role ADMIN
        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin") // Tên đăng nhập
                .password("123") // Mật khẩu
                .roles("ADMIN") // Vai trò (ROLE_ADMIN)
                .build();

        return new InMemoryUserDetailsManager(admin);
    }

    // Cấu hình bảo mật
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // 🔹 Tắt CSRF để có thể gọi API từ bên ngoài mà không bị lỗi
                .authorizeHttpRequests(auth -> auth
                        // Các trang công khai không yêu cầu đăng nhập
                        .requestMatchers("/", "/home", "/products/**", "/login", "/css/**", "/js/**").permitAll()

                        // Cho phép truy cập API products mà không cần đăng nhập
                        .requestMatchers("/api/products/**").permitAll()

                        // Trang `/dashboard/admin/**` chỉ cho phép ADMIN truy cập
                        .requestMatchers("/dashboard/admin/**").hasRole("ADMIN")

                        // Mọi request khác đều phải đăng nhập
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults()) // Bật xác thực Basic Auth cho API
                .formLogin(login -> login
                        .loginPage("/login").permitAll() // Cho phép mọi người vào trang login
                        .successHandler(customSuccessHandler()) // Gọi hàm xử lý sau khi đăng nhập thành công
                        .failureUrl("/login?error=true") // Nếu login thất bại, chuyển hướng về `/login?error=true`
                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // 🔹 Định nghĩa đường dẫn để logout
                        .logoutSuccessUrl("/") // Sau khi logout thì quay về trang chủ
                        .permitAll());

        return http.build();
    }

    // Xử lý điều hướng sau khi đăng nhập thành công
    @Bean
    public AuthenticationSuccessHandler customSuccessHandler() {
        return (request, response, authentication) -> {
            String redirectUrl = "/dashboard/admin/"; // Mặc định là chuyển vào dashboard admin
            response.sendRedirect(redirectUrl); // Điều hướng sau khi login thành công
        };
    }
}
