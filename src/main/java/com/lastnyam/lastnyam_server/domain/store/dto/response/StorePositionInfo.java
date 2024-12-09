package com.lastnyam.lastnyam_server.domain.store.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StorePositionInfo {
    private Long storeId;
    private String storeName;
    private String posX;
    private String posY;
}
