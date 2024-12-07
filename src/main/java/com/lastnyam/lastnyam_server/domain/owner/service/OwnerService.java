package com.lastnyam.lastnyam_server.domain.owner.service;

import com.lastnyam.lastnyam_server.domain.owner.repository.OwnerRepository;
import com.lastnyam.lastnyam_server.domain.owner.domain.Owner;
import com.lastnyam.lastnyam_server.domain.owner.dto.request.LoginRequest;
import com.lastnyam.lastnyam_server.domain.owner.dto.request.SignupRequest;
import com.lastnyam.lastnyam_server.domain.owner.dto.response.LoginResponse;
import com.lastnyam.lastnyam_server.domain.store.repository.StoreRepository;
import com.lastnyam.lastnyam_server.global.auth.jwt.TokenProvider;
import com.lastnyam.lastnyam_server.global.exception.ExceptionCode;
import com.lastnyam.lastnyam_server.global.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OwnerService {
    private final OwnerRepository ownerRepository;
    private final StoreRepository storeRepository;

    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    public LoginResponse signup(SignupRequest request) {
        ownerRepository.findByPhoneNumber(request.getPhoneNumber())
                .ifPresent(it -> {
                    throw new ServiceException(ExceptionCode.DUPLICATED_PHONE_NUMBER);
                });

        storeRepository.findByBusinessNumber(request.getBusinessNumber())
                .ifPresent(it -> {
                    throw new ServiceException(ExceptionCode.DUPLICATED_BUSINESS_NUMBER);
                });

        Owner owner = this.convertToOwner(request);
        Owner savedUser = ownerRepository.save(owner);

        String token = tokenProvider.createToken(savedUser.getId(), savedUser.getRole());
        return LoginResponse.builder()
                .token(token)
                .build();
    }

    private Owner convertToOwner(SignupRequest request) {
        return Owner.builder()
                .phoneNumber(request.getPhoneNumber())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
    }

    public LoginResponse login(LoginRequest request) {
        Owner savedUser = ownerRepository.findByPhoneNumber(request.getPhoneNumber())
                .orElseThrow(() -> new ServiceException(ExceptionCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(request.getPassword(), savedUser.getPassword())) {
            throw new ServiceException(ExceptionCode.PASSWORD_INVALID);
        }

        String token = tokenProvider.createToken(savedUser.getId(), savedUser.getRole());
        return LoginResponse.builder()
                .token(token)
                .build();
    }

}
