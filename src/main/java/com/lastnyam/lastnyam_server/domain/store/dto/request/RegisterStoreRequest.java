package com.lastnyam.lastnyam_server.domain.store.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter
public class RegisterStoreRequest {
    // TODO. validation 추가
    private String storeName;
    private String businessNumber;
    private MultipartFile storeImage;
    private String callNumber;
    private String address;
    private String posX;
    private String posY;
}
