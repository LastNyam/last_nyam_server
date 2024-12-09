package com.lastnyam.lastnyam_server.domain.store.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StoreInfo {
    private final String storeName;
    private final String address;
    private final String callNumber;
    private final byte[] storeImage;
    private final double temperature;
    @JsonProperty("isLike")
    private final boolean like;
}
