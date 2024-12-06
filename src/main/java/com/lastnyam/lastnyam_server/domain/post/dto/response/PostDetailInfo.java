package com.lastnyam.lastnyam_server.domain.post.dto.response;

import com.lastnyam.lastnyam_server.domain.post.domain.PostStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class PostDetailInfo {
    private Long storeId;
    private String storeName;
    private String foodName;
    private String content;
    private int originPrice;
    private int discountPrice;
    private LocalDateTime endTime;
    private int count;
    private int reservationTime;
    private PostStatus status;
    private byte[] image;
    private List<RecipeInfo> recommendRecipe;
}
