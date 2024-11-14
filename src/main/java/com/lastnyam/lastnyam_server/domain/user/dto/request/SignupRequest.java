package com.lastnyam.lastnyam_server.domain.user.dto.request;

import lombok.Getter;

@Getter
public class SignupRequest {
    private String nickname;
    private String password;
    private String phoneNumber;
    // TODO. bool로도 되나? 테스트
    private String acceptMarketing;
}
