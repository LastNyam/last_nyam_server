package com.lastnyam.lastnyam_server.global.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lastnyam.lastnyam_server.global.exception.ExceptionCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.lastnyam.lastnyam_server.global.response.ResponseUtil.createFailureResponse;

@Slf4j
@Component
@RequiredArgsConstructor
public class JsonAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        String errorResponse = objectMapper.writeValueAsString(createFailureResponse(ExceptionCode.UN_AUTHENTICATION));
        response.getWriter().write(errorResponse);
    }
}