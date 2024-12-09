package com.lastnyam.lastnyam_server.domain.post.dto.response;

import com.lastnyam.lastnyam_server.domain.post.domain.PostStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PostInfoWithPosition {
    private Long foodId;
    private String foodCategory;
    private String foodName;
    private int originPrice;
    private int discountPrice;
    private LocalDateTime endTime;
    private String posX;
    private String posY;
    private byte[] image;
}
