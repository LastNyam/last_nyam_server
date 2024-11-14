package com.lastnyam.lastnyam_server.domain.user.domain;

import com.lastnyam.lastnyam_server.domain.store.domain.Store;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class LikeStore {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_store_id")
    private Long id;

    @Column(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Store store;

    @Column(nullable = false)
    private boolean notification;
}
