package com.lastnyam.lastnyam_server.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ExceptionCode {
    // common
    UNEXPECTED_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C001", "예상치 못한 서버 에러 발생"),
    BINDING_ERROR(HttpStatus.BAD_REQUEST, "C002", "바인딩시 에러 발생"),
    INVALID_ENDPOINT(HttpStatus.NOT_FOUND, "C003", "잘못된 주소 요청"),
    FILE_IO_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "C004", "파일 업로드 실패"),

    // auth
    FORBIDDEN(HttpStatus.FORBIDDEN, "A001", "권한 없음"),
    UN_AUTHENTICATION(HttpStatus.UNAUTHORIZED, "A002", "인증 정보가 존재하지 않음"),

    // user & owner
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "U001", "사용자를 찾을 수 없음"),
    DUPLICATED_PHONE_NUMBER(HttpStatus.CONFLICT, "U002", "휴대폰 번호 중복"),
    DUPLICATED_NICKNAME(HttpStatus.CONFLICT, "U003", "닉네임 중복"),
    PASSWORD_INVALID(HttpStatus.BAD_REQUEST, "U004", "비밀번호 불일치"),
    DUPLICATED_BUSINESS_NUMBER(HttpStatus.CONFLICT, "U005", "사업자등록번호 중복"),

    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
