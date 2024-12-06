package com.lastnyam.lastnyam_server.domain.post.domain;

import com.lastnyam.lastnyam_server.domain.store.domain.Store;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class FoodPost {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
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

    @Column(nullable = false, columnDefinition = "LONGBLOB")
    @Lob
    private byte[] image;

    @Column(nullable = false)
    private int reservationTimeLimit;

    @Setter
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PostStatus status;

    @Builder
    public FoodPost(Store store, FoodCategory category, String foodName, String content, int originPrice, int discountPrice, LocalDateTime endTime, int count, byte[] image, int reservationTimeLimit) {
        this.store = store;
        this.category = category;
        this.foodName = foodName;
        this.content = content;
        this.originPrice = originPrice;
        this.discountPrice = discountPrice;
        this.endTime = endTime;
        this.count = count;
        this.image = image;
        this.reservationTimeLimit = reservationTimeLimit;
        this.status = PostStatus.AVAILABLE;
    }
}
