package com.lastnyam.lastnyam_server.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ExceptionCode {
    // common
    UNEXPECTED_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C001", "예상치 못한 서버 에러 발생"),
    BINDING_ERROR(HttpStatus.BAD_REQUEST, "C002", "바인딩시 에러 발생")

    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
