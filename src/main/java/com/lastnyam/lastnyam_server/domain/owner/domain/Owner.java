package com.lastnyam.lastnyam_server.domain.owner.domain;

import com.lastnyam.lastnyam_server.domain.store.domain.Store;
import com.lastnyam.lastnyam_server.global.auth.UserRole;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Entity
public class Owner {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "owner_id")
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    private Store store;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @Builder
    public Owner(Store store, String phoneNumber, String password) {
        this.store = store;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.role = UserRole.ROLE_OWNER;
    }
}
