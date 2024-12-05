package com.lastnyam.lastnyam_server.domain.store.dto.request;

import lombok.Getter;

@Getter
public class UpdateStoreAddressRequest {
    private String address;
    private String posX;
    private String posY;
}
