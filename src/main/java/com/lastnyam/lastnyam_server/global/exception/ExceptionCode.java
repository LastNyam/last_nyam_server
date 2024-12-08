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
    SEND_MESSAGE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "C005", "메시지 전송 실패"),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "C006","허용하지 않는 method"),

    // auth
    FORBIDDEN(HttpStatus.FORBIDDEN, "A001", "권한 없음"),
    UN_AUTHENTICATION(HttpStatus.UNAUTHORIZED, "A002", "인증 정보가 존재하지 않음"),
    CODE_EXPIRY(HttpStatus.UNAUTHORIZED, "A003", "인증번호가 만료되었거나 존재하지 않음"),

    // user & owner
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "U001", "사용자를 찾을 수 없음"),
    DUPLICATED_PHONE_NUMBER(HttpStatus.CONFLICT, "U002", "휴대폰 번호 중복"),
    DUPLICATED_NICKNAME(HttpStatus.CONFLICT, "U003", "닉네임 중복"),
    PASSWORD_INVALID(HttpStatus.BAD_REQUEST, "U004", "비밀번호 불일치"),
    DUPLICATED_BUSINESS_NUMBER(HttpStatus.CONFLICT, "U005", "사업자등록번호 중복"),

    // food
    FOOD_CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "F001", "존재하지 않는 음식 카테고리"),
    FOOD_POST_NOT_FOUND(HttpStatus.NOT_FOUND, "F002", "존재하지 않는 상품"),
    INVALID_POST_STATUS(HttpStatus.BAD_REQUEST, "F003", "잘못된 status (SOLD_OUT or HIDDEN or AVAILABLE)"),
    FOOD_NOT_ENOUGH_OR_NOT_AVAILABLE(HttpStatus.UNPROCESSABLE_ENTITY, "F004", "요청한 예약 개수보다 음식 재고가 부족하거나 예약 불가 상태"),
    FOOD_POST_RESERVATION_EXISTS(HttpStatus.CONFLICT, "F005", "수락 대기 중인 예약이 존재"),

    // store
    STORE_NOT_FOUND(HttpStatus.NOT_FOUND, "S001", "가게가 존재하지 않음"),

    // reservation
    RESERVATION_NOT_FOUND(HttpStatus.NOT_FOUND, "R001", "예약이 존재하지 않음"),
    CONCURRENT_UPDATE(HttpStatus.CONFLICT, "R002", "소비자가 이미 예약을 취소함"),

    ;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
