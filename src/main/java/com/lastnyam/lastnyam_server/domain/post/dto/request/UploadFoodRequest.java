package com.lastnyam.lastnyam_server.domain.post.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Getter
@Setter
public class UploadFoodRequest {
    // TODO. 검증
    private String foodCategory;
    private String foodName;
    private String content;
    private int originPrice;
    private int discountPrice;
    private LocalDateTime endTime;
    private int count;
    private int reservationTime;
    private String recipe;
    private MultipartFile recipeImage;
    private MultipartFile foodImage;
}
