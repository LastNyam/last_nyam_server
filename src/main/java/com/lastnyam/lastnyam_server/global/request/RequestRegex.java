package com.lastnyam.lastnyam_server.global.request;

import lombok.Getter;

@Getter
public class RequestRegex {
    public static final String PHONE_NUMBER = "^\\d{3}-\\d{3,4}-\\d{4}$";
    public static final String NICKNAME = "^[ㄱ-ㅎㅏ-ㅣa-zA-Z가-힣0-9]+$";
    public static final String PASSWORD = "^(?=.*[a-z])(?=.*[A-Z]|.*[0-9]|.*[!@#\\$%^&*(),.?\":{}|<>])[a-zA-Z0-9!@#\\$%^&*(),.?\":{}|<>].*$";
    public static final String BUSINESS_NUMBER = "^\\d{10}$";
    public static final String PHONE_VERIFICATION_CODE = "\\d{4}";
}
