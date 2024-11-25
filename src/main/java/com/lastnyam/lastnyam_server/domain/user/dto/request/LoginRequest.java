package com.lastnyam.lastnyam_server.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class LoginRequest {

    @NotBlank(message = "전화번호는 빈 값일 수 없습니다.")
    @Pattern(regexp = RequestRegex.PHONE_NUMBER, message = "올바른 전화번호 형식이 아닙니다. (ex: 010-0000-0000)")
    private String phoneNumber;

    @NotBlank(message = "비밀번호는 빈 값일 수 없습니다.")
    @Size(min = 10, message = "비밀번호는 10자 이상이어야 합니다.")
    @Pattern(regexp = RequestRegex.PASSWORD,
            message = "비밀번호는 영어 대소문자와 숫자 또는 특수문자 중 2종류 이상이 포함되어야 합니다.")
    private String password;
}
