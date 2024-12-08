package com.lastnyam.lastnyam_server.domain.store.dto.request;

import lombok.Getter;

@Getter
public class UploadReviewRequest {
    private Long storeId;
    private Long foodId;
    private int rating;
    private String content;
    private String recipe;
}
