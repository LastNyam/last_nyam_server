package com.lastnyam.lastnyam_server.domain.store.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MyPageStoreInfo {
    private Long storeId;
    private String storeName;
    private byte[] storeImage;
    private String callNumber;
    private String address;
    private double temperature;
}
