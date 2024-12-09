package com.lastnyam.lastnyam_server.domain.user.service;

import com.lastnyam.lastnyam_server.domain.reservation.repository.ReservationRepository;
import com.lastnyam.lastnyam_server.domain.store.domain.Store;
import com.lastnyam.lastnyam_server.domain.store.repository.StoreRepository;
import com.lastnyam.lastnyam_server.domain.user.domain.LikeStore;
import com.lastnyam.lastnyam_server.domain.user.domain.User;
import com.lastnyam.lastnyam_server.domain.user.dto.request.UpdateNicknameRequest;
import com.lastnyam.lastnyam_server.domain.user.dto.request.LoginRequest;
import com.lastnyam.lastnyam_server.domain.user.dto.request.SignupRequest;
import com.lastnyam.lastnyam_server.domain.user.dto.response.CheckNicknameResponse;
import com.lastnyam.lastnyam_server.domain.user.dto.response.LikeStoreInfo;
import com.lastnyam.lastnyam_server.domain.user.dto.response.LoginResponse;
import com.lastnyam.lastnyam_server.domain.user.dto.response.UserInfo;
import com.lastnyam.lastnyam_server.domain.user.repository.LikeStoreRepository;
import com.lastnyam.lastnyam_server.domain.user.repository.UserRepository;
import com.lastnyam.lastnyam_server.global.auth.jwt.TokenProvider;
import com.lastnyam.lastnyam_server.global.exception.ExceptionCode;
import com.lastnyam.lastnyam_server.global.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;
    private final StoreRepository storeRepository;
    private final LikeStoreRepository likeStoreRepository;

    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    @Transactional
    public void signup(SignupRequest request) {
        userRepository.findByPhoneNumber(request.getPhoneNumber())
                .ifPresent(it -> {
                    throw new ServiceException(ExceptionCode.DUPLICATED_PHONE_NUMBER);
                });

        userRepository.findByNickname(request.getNickname())
                .ifPresent(it -> {
                    throw new ServiceException(ExceptionCode.DUPLICATED_NICKNAME);
                });

        User user = this.convertToUser(request);
        userRepository.save(user);
    }

    private User convertToUser(SignupRequest request) {
        return User.builder()
                .phoneNumber(request.getPhoneNumber())
                .password(passwordEncoder.encode(request.getPassword()))
                .nickname(request.getNickname())
                .acceptsMarketing(request.getAcceptMarketing())
                .build();
    }

    public LoginResponse login(LoginRequest request) {
        User savedUser = userRepository.findByPhoneNumber(request.getPhoneNumber())
                .orElseThrow(() -> new ServiceException(ExceptionCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(request.getPassword(), savedUser.getPassword())) {
            throw new ServiceException(ExceptionCode.PASSWORD_INVALID);
        }

        String token = tokenProvider.createToken(savedUser.getId(), savedUser.getRole());
        return LoginResponse.builder()
                .token(token)
                .build();
    }

    public CheckNicknameResponse checkNickname(String nickname) {
        boolean isDuplicated = userRepository.findByNickname(nickname).isPresent();
        return CheckNicknameResponse.builder()
                .duplicated(isDuplicated)
                .build();
    }

    @Transactional
    public void updateNickname(Long userId, UpdateNicknameRequest request) {
        User savedUser = userRepository.findById(userId)
                .orElseThrow(() -> new ServiceException(ExceptionCode.USER_NOT_FOUND));

        userRepository.findByNickname(request.getNickname())
                .ifPresent(i -> {
                    throw new ServiceException(ExceptionCode.DUPLICATED_NICKNAME);
                });

        savedUser.setNickname(request.getNickname());
    }

    @Transactional
    public void updateProfileImage(Long userId, MultipartFile file) {
        User savedUser = userRepository.findById(userId)
                .orElseThrow(() -> new ServiceException(ExceptionCode.USER_NOT_FOUND));

        byte[] byteFile = null;
        try {
            byteFile = file.isEmpty() ? null : file.getBytes();
        } catch (IOException e) {
            log.error("profileImage upload error: {}", e.getMessage());
            throw new ServiceException(ExceptionCode.FILE_IO_EXCEPTION);
        }

        savedUser.setProfileImage(byteFile);
    }


    public UserInfo getMyInformation(Long userId) {
        User savedUser = userRepository.findById(userId)
                .orElseThrow(() -> new ServiceException(ExceptionCode.USER_NOT_FOUND));

        int orderCount = reservationRepository.countByUserAndReceivedStatus(savedUser);

        return UserInfo.builder()
                .phoneNumber(savedUser.getPhoneNumber())
                .nickname(savedUser.getNickname())
                .acceptMarketing(savedUser.isAcceptsMarketing())
                .profileImage(savedUser.getProfileImage())
                .orderCount(orderCount)
                .build();
    }

    @Transactional
    public void likeStore(Long storeId, Long userId) {
        User savedUser = userRepository.findById(userId)
                .orElseThrow(() -> new ServiceException(ExceptionCode.USER_NOT_FOUND));

        Store savedStore = storeRepository.findById(storeId)
                .orElseThrow(() -> new ServiceException(ExceptionCode.STORE_NOT_FOUND));

        likeStoreRepository.findByUserAndStore(savedUser, savedStore)
                .ifPresent(it -> {
                    throw new ServiceException(ExceptionCode.LIKE_STORE_EXISTS);
                });

        LikeStore likeStore = LikeStore.builder()
                .store(savedStore)
                .user(savedUser)
                .build();

        likeStoreRepository.save(likeStore);
    }

    @Transactional
    public void unlikeStore(Long storeId, Long userId) {
        User savedUser = userRepository.findById(userId)
                .orElseThrow(() -> new ServiceException(ExceptionCode.USER_NOT_FOUND));

        Store savedStore = storeRepository.findById(storeId)
                .orElseThrow(() -> new ServiceException(ExceptionCode.STORE_NOT_FOUND));

        LikeStore likeStore = likeStoreRepository.findByUserAndStore(savedUser, savedStore)
                .orElseThrow(() -> new ServiceException(ExceptionCode.LIKE_STORE_NOT_FOUND));

        likeStoreRepository.delete(likeStore);
    }

    @Transactional(readOnly = true)
    public List<LikeStoreInfo> getLikeStores(Long userId) {
        User savedUser = userRepository.findById(userId)
                .orElseThrow(() -> new ServiceException(ExceptionCode.USER_NOT_FOUND));

        return likeStoreRepository.findByUser(savedUser).stream()
                .map(LikeStore::getStore)
                .map(store -> LikeStoreInfo.builder()
                        .storeId(store.getId())
                        .storeName(store.getName())
                        .temperature(store.getTemperature())
                        .image(store.getImage())
                        .build()
                ).toList();
    }
}
