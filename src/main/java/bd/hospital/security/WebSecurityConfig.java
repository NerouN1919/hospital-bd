package bd.hospital.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {
    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorize) -> {
                    authorize
                            .requestMatchers("/statistic").hasRole("ADMIN")
                            .anyRequest().authenticated().anyRequest();
                })
                .formLogin((form) -> form
                        .defaultSuccessUrl("/?page=1", true)
                )
                .logout((logout) -> logout
                        .logoutUrl("/logout")
                );
        return http.build();
    }
}
