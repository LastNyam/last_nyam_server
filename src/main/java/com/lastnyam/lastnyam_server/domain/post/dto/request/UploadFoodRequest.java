package com.lastnyam.lastnyam_server.domain.post.dto.request;

import org.springframework.web.multipart.MultipartFile;

public class UploadFoodRequest {
    private String foodCategory;
    private String foodName;
    private String originPrice;
    private String discountPrice;
    // TODO. 정규식..
    private String endTime;
    private MultipartFile image;
}
