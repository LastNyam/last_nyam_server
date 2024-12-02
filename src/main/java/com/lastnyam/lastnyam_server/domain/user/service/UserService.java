package com.lastnyam.lastnyam_server.domain.user.service;

import com.lastnyam.lastnyam_server.domain.user.domain.User;
import com.lastnyam.lastnyam_server.domain.user.dto.request.UpdateNicknameRequest;
import com.lastnyam.lastnyam_server.domain.user.dto.request.LoginRequest;
import com.lastnyam.lastnyam_server.domain.user.dto.request.SignupRequest;
import com.lastnyam.lastnyam_server.domain.user.dto.response.CheckNicknameResponse;
import com.lastnyam.lastnyam_server.domain.user.dto.response.LoginResponse;
import com.lastnyam.lastnyam_server.domain.user.dto.response.UserInfo;
import com.lastnyam.lastnyam_server.domain.user.repository.UserRepository;
import com.lastnyam.lastnyam_server.global.auth.jwt.TokenProvider;
import com.lastnyam.lastnyam_server.global.exception.ExceptionCode;
import com.lastnyam.lastnyam_server.global.exception.ServiceException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
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
            byteFile = file.getBytes();
        } catch (IOException e) {
            log.error("profileImage upload error: {}", e.getMessage());
            throw new ServiceException(ExceptionCode.FILE_IO_EXCEPTION);
        }

        savedUser.setProfileImage(byteFile);
    }


    public UserInfo getMyInformation(Long userId) {
        User savedUser = userRepository.findById(userId)
                .orElseThrow(() -> new ServiceException(ExceptionCode.USER_NOT_FOUND));

        return UserInfo.builder()
                .phoneNumber(savedUser.getPhoneNumber())
                .nickname(savedUser.getNickname())
                .acceptMarketing(savedUser.isAcceptsMarketing())
                .profileImage(savedUser.getProfileImage())
                .build();
    }
}
