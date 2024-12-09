package com.lastnyam.lastnyam_server.domain.user.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LikeStoreInfo {
    private final Long storeId;
    private final String storeName;
    private final double temperature;
    private final byte[] image;
}
