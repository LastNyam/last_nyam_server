package com.lastnyam.lastnyam_server.domain.post.domain;

import com.lastnyam.lastnyam_server.domain.store.domain.Store;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class FoodPost {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    private FoodCategory category;

    @Column(nullable = false)
    private String foodName;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int originPrice;

    @Column(nullable = false)
    private int discountPrice;

    @Column(nullable = false)
    private LocalDateTime endTime;

    @Column(nullable = false)
    private int count;

    @Lob
    private byte[] image;
}
