package com.lastnyam.lastnyam_server.domain.user.domain;

import com.lastnyam.lastnyam_server.global.auth.UserRole;
import com.lastnyam.lastnyam_server.global.domain.AuditEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Entity
public class User extends AuditEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Setter
    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Setter
    @Lob
    private byte[] profileImage;

    @Column(nullable = false)
    private boolean acceptsMarketing;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @Setter
    private String fcmToken;

    @Builder
    public User(String phoneNumber, String nickname, String password, boolean acceptsMarketing) {
        this.phoneNumber = phoneNumber;
        this.nickname = nickname;
        this.password = password;
        this.acceptsMarketing = acceptsMarketing;
        this.role = UserRole.ROLE_USER;
    }
}
