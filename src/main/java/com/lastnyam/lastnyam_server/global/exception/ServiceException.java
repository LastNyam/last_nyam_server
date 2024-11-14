package com.lastnyam.lastnyam_server.global.exception;

import lombok.Getter;

@Getter
public class ServiceException extends RuntimeException {
    private final ExceptionCode exceptionCode;

    public ServiceException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }
}
