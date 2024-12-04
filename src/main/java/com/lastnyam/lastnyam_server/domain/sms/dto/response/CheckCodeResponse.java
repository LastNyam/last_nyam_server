package com.lastnyam.lastnyam_server.domain.sms.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CheckCodeResponse {
    private Boolean isValidCode;
}
