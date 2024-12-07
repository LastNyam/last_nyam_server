package com.lastnyam.lastnyam_server.domain.store.service;

import com.lastnyam.lastnyam_server.domain.owner.domain.Owner;
import com.lastnyam.lastnyam_server.domain.owner.repository.OwnerRepository;
import com.lastnyam.lastnyam_server.domain.store.domain.Store;
import com.lastnyam.lastnyam_server.domain.store.dto.request.RegisterStoreRequest;
import com.lastnyam.lastnyam_server.domain.store.dto.request.UpdateStoreAddressRequest;
import com.lastnyam.lastnyam_server.domain.store.dto.response.StoreInfo;
import com.lastnyam.lastnyam_server.domain.store.repository.StoreRepository;
import com.lastnyam.lastnyam_server.global.exception.ExceptionCode;
import com.lastnyam.lastnyam_server.global.exception.ServiceException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreService {
    private final OwnerRepository ownerRepository;
    private final StoreRepository storeRepository;

    @Transactional
    public void registerStore(RegisterStoreRequest request, Long userId) {
        Owner savedOwner = ownerRepository.findById(userId)
                .orElseThrow(() -> new ServiceException(ExceptionCode.USER_NOT_FOUND));

        // 테스트를 위해 중복 허용
//        storeRepository.findByBusinessNumber(request.getBusinessNumber())
//                .ifPresent(store -> {
//                    throw new ServiceException(ExceptionCode.DUPLICATED_BUSINESS_NUMBER);
//                });

        byte[] byteFile = null;
        if (request.getStoreImage() != null) {
            try {
                byteFile = request.getStoreImage().getBytes();
            } catch (IOException e) {
                log.error("profileImage upload error: {}", e.getMessage());
                throw new ServiceException(ExceptionCode.FILE_IO_EXCEPTION);
            }
        }

        Store newStore = Store.builder()
                .name(request.getStoreName())
                .businessNumber(request.getBusinessNumber())
                .image(byteFile)
                .positionX(request.getPosX())
                .positionY(request.getPosY())
                .contactNumber(request.getCallNumber())
                .address(request.getAddress())
                .build();

        Store savedStore = storeRepository.save(newStore);
        savedOwner.setStore(savedStore);
    }

    @Transactional
    public void updateStoreName(String storeName, Long userId) {
        Owner savedUser = ownerRepository.findById(userId)
                .orElseThrow(() -> new ServiceException(ExceptionCode.USER_NOT_FOUND));

        Store userStore = savedUser.getStore();

        userStore.setName(storeName);
    }

    @Transactional
    public void updateStoreAddress(UpdateStoreAddressRequest request, Long userId) {
        Owner savedUser = ownerRepository.findById(userId)
                .orElseThrow(() -> new ServiceException(ExceptionCode.USER_NOT_FOUND));

        Store userStore = savedUser.getStore();

        userStore.setAddress(request.getAddress());
        userStore.setPositionX(request.getPosX());
        userStore.setPositionY(request.getPosY());
    }

    @Transactional
    public void updateStoreContactNumber(String callNumber, Long userId) {
        Owner savedUser = ownerRepository.findById(userId)
                .orElseThrow(() -> new ServiceException(ExceptionCode.USER_NOT_FOUND));

        Store userStore = savedUser.getStore();

        userStore.setContactNumber(callNumber);
    }

    @Transactional(readOnly = true)
    public StoreInfo getMyStoreInfo(Long userId) {
        Owner savedUser = ownerRepository.findById(userId)
                .orElseThrow(() -> new ServiceException(ExceptionCode.USER_NOT_FOUND));

        Store myStore = savedUser.getStore();

        if (myStore == null) {
            throw new ServiceException(ExceptionCode.STORE_NOT_FOUND);
        }

        return this.convertStoreInfo(myStore);
    }

    private StoreInfo convertStoreInfo(Store store) {
        return StoreInfo.builder()
                .storeId(store.getId())
                .storeName(store.getName())
                .storeImage(store.getImage())
                .callNumber(store.getContactNumber())
                .address(store.getAddress())
                .temperature(store.getTemperature())
                .build();
    }
}
