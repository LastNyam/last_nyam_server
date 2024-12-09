package com.lastnyam.lastnyam_server.domain.store.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewInfo {
    private String userNickname;
    private int rating;
    private String content;
}
