package com.lastnyam.lastnyam_server.domain.post.dto.request;

import lombok.Getter;

@Getter
public class UpdatePostStatusRequest {
    // TODO. 검증
    private Long foodId;
    private String status;
}
