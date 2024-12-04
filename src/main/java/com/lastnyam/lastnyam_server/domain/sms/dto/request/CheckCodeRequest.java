package com.lastnyam.lastnyam_server.domain.sms.dto.request;

import com.lastnyam.lastnyam_server.global.request.RequestRegex;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class CheckCodeRequest {
    @NotBlank(message = "전화번호는 빈 값일 수 없습니다.")
    @Pattern(regexp = RequestRegex.PHONE_NUMBER, message = "올바른 전화번호 형식이 아닙니다. (ex: 010-0000-0000)")
    private String phoneNumber;

    @NotBlank(message = "인증코드는 빈 값일 수 없습니다.")
    @Pattern(regexp = RequestRegex.PHONE_VERIFICATION_CODE, message = "인증코드는 4자리의 숫자로만 구성되어야 합니다.")
    private String verification;
}