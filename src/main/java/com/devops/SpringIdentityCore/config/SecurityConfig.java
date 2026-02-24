package com.devops.SpringIdentityCore.config;

import com.devops.SpringIdentityCore.security.CustomAccessDeniedHandler;
import com.devops.SpringIdentityCore.security.CustomAuthenticationEntryPoint;
import com.devops.SpringIdentityCore.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // מאפשר שימוש ב-@PreAuthorize ב-Controllers
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final CustomAuthenticationEntryPoint authEntryPoint;
    private final CustomAccessDeniedHandler accessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                // טיפול בשגיאות אבטחה עם הודעות
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(authEntryPoint) // שגיאת 401 (לא מחובר)
                        .accessDeniedHandler(accessDeniedHandler) // שגיאת 403 (אין הרשאה)
                )
                .authorizeHttpRequests(auth -> auth
                        // נתיבים פתוחים לכולם (Swagger ואימות)
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/api/v1/auth/**"
                        ).permitAll()
                        // הגנה גורפת על נתיבי ניהול - דורש תפקיד ADMIN
                        .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
                        // כל שאר הבקשות דורשות הזדהות
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}