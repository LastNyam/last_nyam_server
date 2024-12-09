package com.lastnyam.lastnyam_server.domain.user.domain;

import com.lastnyam.lastnyam_server.domain.store.domain.Store;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class LikeStore {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_store_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Store store;

    @Column(nullable = false)
    private boolean notification;

    @Builder
    public LikeStore(User user, Store store) {
        this.user = user;
        this.store = store;
        this.notification = true;
    }
}
