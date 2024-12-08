package com.lastnyam.lastnyam_server.domain.reservation.domain;

import com.lastnyam.lastnyam_server.domain.post.domain.FoodPost;
import com.lastnyam.lastnyam_server.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class Reservation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private FoodPost foodPost;

    @Column(nullable = false)
    private int number;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReservationStatus status;

    @Setter
    private LocalDateTime acceptTime;

    @Setter
    private String cancellationReason;

    @Version
    private int version;

    @Builder
    public Reservation(int number, FoodPost foodPost, User user) {
        this.number = number;
        this.foodPost = foodPost;
        this.user = user;
        this.status = ReservationStatus.BEFORE_ACCEPT;
    }
}
