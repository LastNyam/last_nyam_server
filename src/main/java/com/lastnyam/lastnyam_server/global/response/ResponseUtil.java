package com.lastnyam.lastnyam_server.global.response;

import com.lastnyam.lastnyam_server.global.exception.ExceptionCode;

public class ResponseUtil {
    public static ResponseBody<Void> createSuccessResponse() {
        return new SuccessResponseBody<>();
    }

    public static <T> ResponseBody<T> createSuccessResponse(T data) {
        return new SuccessResponseBody<>(data);
    }

    public static ResponseBody<Void> createFailureResponse(ExceptionCode exceptionCode) {
        return new FailureResponseBody(
                exceptionCode.getCode(),
                exceptionCode.getMessage()
        );
    }

    public static ResponseBody<Void> createFailureResponse(ExceptionCode exceptionCode, String customMessage) {
        return new FailureResponseBody(
                exceptionCode.getCode(),
                customMessage
        );
    }
}
