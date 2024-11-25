package com.lastnyam.lastnyam_server.domain.user.dto.request;

import lombok.Getter;

@Getter
public class RequestRegex {
    // TODO. 전화번호 양식 프론트와 확정
    public static final String PHONE_NUMBER = "^\\d{3}-\\d{3,4}-\\d{4}$";
    public static final String NICKNAME = "^[ㄱ-ㅎㅏ-ㅣa-zA-Z가-힣0-9]+$";
    public static final String PASSWORD = "^(?=.*[a-z])(?=.*[A-Z]|.*[0-9]|.*[!@#\\$%^&*(),.?\":{}|<>])[a-zA-Z0-9!@#\\$%^&*(),.?\":{}|<>].*$";
}
