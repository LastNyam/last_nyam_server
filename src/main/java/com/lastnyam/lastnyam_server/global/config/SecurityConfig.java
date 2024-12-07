package com.lastnyam.lastnyam_server.global.config;

import com.lastnyam.lastnyam_server.global.auth.handler.JsonAccessDeniedHandler;
import com.lastnyam.lastnyam_server.global.auth.handler.JsonAuthenticationEntryPoint;
import com.lastnyam.lastnyam_server.global.auth.jwt.JwtAuthenticationFilter;
import com.lastnyam.lastnyam_server.global.auth.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private static final String ADMIN = "ADMIN";
    private static final String USER = "USER";
    private static final String OWNER = "OWNER";

    private final JsonAuthenticationEntryPoint jsonAuthenticationEntryPoint;
    private final JsonAccessDeniedHandler jsonAccessDeniedHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, TokenProvider tokenProvider, AuthenticationManager authenticationManager) throws Exception {
        http
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests((auth) -> auth
                        // owner
                        .requestMatchers(HttpMethod.POST,
                                "/api/owner/auth/signup", "/api/owner/auth/send-code/phone",
                                "/api/owner/auth/check/phone", "/api/owner/auth/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/owner/food/{foodId}").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/owner/**").hasRole(OWNER)
                        .requestMatchers(HttpMethod.POST, "/api/owner/**").hasRole(OWNER)
                        .requestMatchers(HttpMethod.PATCH,"/api/owner/**").hasRole(OWNER)
                        .requestMatchers(HttpMethod.DELETE,"/api/owner/**").hasRole(OWNER)

                        // user
                        .requestMatchers(HttpMethod.POST,
                                "/api/user/auth/signup", "/api/user/auth/send-code/phone",
                                "/api/user/auth/check/phone", "/api/user/auth/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/user/auth/my-info").hasRole(USER)
                        .requestMatchers(HttpMethod.GET, "/api/user/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/user/**").hasRole(USER)
                        .requestMatchers(HttpMethod.PATCH,"/api/user/**").hasRole(USER)
                        .requestMatchers(HttpMethod.PUT,"/api/user/**").hasRole(USER)
                        .requestMatchers(HttpMethod.PATCH,"/api/user/**").hasRole(USER)
                        .requestMatchers(HttpMethod.DELETE, "api/user/**").hasRole(USER)

                        // admin
                        .anyRequest().hasRole(ADMIN)
                )
                .addFilterBefore(new JwtAuthenticationFilter(tokenProvider, authenticationManager), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(e -> e
                        .authenticationEntryPoint(jsonAuthenticationEntryPoint)
                        .accessDeniedHandler(jsonAccessDeniedHandler))
        ;

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(TokenProvider tokenProvider) {
        return new ProviderManager(tokenProvider);
    }

}