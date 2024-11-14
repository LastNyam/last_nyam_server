package com.lastnyam.lastnyam_server.global.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lastnyam.lastnyam_server.global.exception.ExceptionCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.lastnyam.lastnyam_server.global.response.ResponseUtil.createFailureResponse;

@Component
@RequiredArgsConstructor
public class JsonAccessDeniedHandler implements AccessDeniedHandler {
    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json;charset=UTF-8");
        String errorResponse = objectMapper.writeValueAsString(createFailureResponse(ExceptionCode.FORBIDDEN));
        response.getWriter().write(errorResponse);
    }
}