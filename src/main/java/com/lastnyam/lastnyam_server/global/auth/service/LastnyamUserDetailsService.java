package com.lastnyam.lastnyam_server.global.auth.service;

import com.lastnyam.lastnyam_server.domain.owner.repository.OwnerRepository;
import com.lastnyam.lastnyam_server.global.auth.domain.UserPrincipal;
import com.lastnyam.lastnyam_server.domain.user.repository.UserRepository;
import com.lastnyam.lastnyam_server.global.exception.ExceptionCode;
import com.lastnyam.lastnyam_server.global.exception.ServiceException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@RequiredArgsConstructor
public class LastnyamUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final OwnerRepository ownerRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        if (isOwnerLogin()) {
            return ownerRepository.findById(Long.parseLong(userId))
                    .map(UserPrincipal::new)
                    .orElseThrow(() -> new ServiceException(ExceptionCode.USER_NOT_FOUND));
        }
        return userRepository.findById(Long.parseLong(userId))
                .map(UserPrincipal::new)
                .orElseThrow(() -> new ServiceException(ExceptionCode.USER_NOT_FOUND));
    }

    private boolean isOwnerLogin() {
        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            throw new IllegalStateException("No request attributes found");
        }
        HttpServletRequest request = attributes.getRequest();
        String path = request.getRequestURI();
        return path.startsWith("/api/owner");
    }
}