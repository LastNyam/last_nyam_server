package com.lastnyam.lastnyam_server.domain.user.service;

import com.lastnyam.lastnyam_server.domain.user.domain.User;
import com.lastnyam.lastnyam_server.domain.user.dto.request.SignupRequest;
import com.lastnyam.lastnyam_server.domain.user.repository.UserRepository;
import com.lastnyam.lastnyam_server.global.exception.ExceptionCode;
import com.lastnyam.lastnyam_server.global.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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


}
