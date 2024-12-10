package com.lastnyam.lastnyam_server.domain.sms.dto;

import lombok.Getter;

@Getter
public class FcmNotificationRequest {
    private String targetToken;
    private String title;
    private String body;
}
