package com.lastnyam.lastnyam_server.domain.user.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class UserInfo {
    private String nickname;
    private String phoneNumber;
    private Boolean acceptMarketing;
    private byte[] profileImage;
}
