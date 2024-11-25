package com.lastnyam.lastnyam_server.domain.user.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UpdateNicknameRequest {

    @NotNull(message = "닉네임은 빈 값일 수 없습니다.")
    @Pattern(regexp = RequestRegex.NICKNAME, message = "닉네임은 한글, 영문, 숫자만 입력 가능합니다.")
    private String nickname;
}
