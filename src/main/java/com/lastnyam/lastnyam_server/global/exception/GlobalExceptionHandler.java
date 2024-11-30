package com.lastnyam.lastnyam_server.global.exception;

import com.lastnyam.lastnyam_server.global.response.ResponseBody;
import com.lastnyam.lastnyam_server.global.response.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ResponseBody<Void>> businessException(ServiceException e) {
        ExceptionCode exceptionCode = e.getExceptionCode();
        return ResponseEntity.status(exceptionCode.getStatus())
                .body(ResponseUtil.createFailureResponse(exceptionCode));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseBody<Void>> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        String customMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return ResponseEntity
                .status(ExceptionCode.BINDING_ERROR.getStatus())
                .body(ResponseUtil.createFailureResponse(ExceptionCode.BINDING_ERROR, customMessage));
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ResponseBody<Void>> handleNotFound(NoResourceFoundException e) {
        return ResponseEntity.status(ExceptionCode.INVALID_ENDPOINT.getStatus())
                .body(ResponseUtil.createFailureResponse(ExceptionCode.INVALID_ENDPOINT));
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseBody<Void>> exception(Exception e) {
        log.error("Exception Message: {}", e.getMessage());
        e.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseUtil.createFailureResponse(ExceptionCode.UNEXPECTED_SERVER_ERROR));
    }
}
