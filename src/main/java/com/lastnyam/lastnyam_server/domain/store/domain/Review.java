package com.lastnyam.lastnyam_server.domain.store.domain;

import com.lastnyam.lastnyam_server.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Review {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private User user;

    @Column(nullable = false)
    private int rating;

    private String content;

    @Builder
    public Review(Store store, User user, int rating, String content) {
        this.store = store;
        this.user = user;
        this.rating = rating;
        this.content = content;
    }
}
