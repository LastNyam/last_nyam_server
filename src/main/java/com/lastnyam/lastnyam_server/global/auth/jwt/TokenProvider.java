package com.lastnyam.lastnyam_server.global.auth.jwt;

import com.lastnyam.lastnyam_server.global.auth.domain.UserPrincipal;
import com.lastnyam.lastnyam_server.global.auth.service.LastnyamUserDetailsService;
import com.lastnyam.lastnyam_server.global.auth.UserRole;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Component
public class TokenProvider implements AuthenticationProvider {
    // TODO. 만료 시간 30분으로 해둠. 필요 시 추후 변경
    private final long EXPIRATION_TIME = 3*60*60*1000L;
    private final LastnyamUserDetailsService userDetailsService;

    @Value("${jwt.secret-key}")
    private String secretKey;

    public String createToken(Long id, UserRole role) {
        Claims claims = Jwts.claims()
                .subject("ACCESS_TOKEN")
                .add("id", id)
                .add("role", role)
                .build();

        Date now = new Date(System.currentTimeMillis());

        return Jwts.builder()
                .claims(claims)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + EXPIRATION_TIME))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                .compact();
    }

    public String validateToken(String token) {
        if (token == null) {
            return null;
        }

        try {
            Claims claims = Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return claims.get("id").toString();

        } catch (ExpiredJwtException e) {
            log.error("JWT가 만료되었습니다.");

        } catch (Exception e) {
            log.error("토큰이 유효하지 않습니다.");
        }

        return null;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserPrincipal userPrincipal = (UserPrincipal) userDetailsService.loadUserByUsername((String) authentication.getPrincipal());
        return new UsernamePasswordAuthenticationToken(userPrincipal, "", userPrincipal.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

}